package com.springboot.online_bookstore_backend.controller;

import com.github.pagehelper.PageInfo;
import com.springboot.online_bookstore_backend.controller.dto.*;
import com.springboot.online_bookstore_backend.domain.Book_Category;
import com.springboot.online_bookstore_backend.domain.Book_Info;
import com.springboot.online_bookstore_backend.domain.Privilege;
import com.springboot.online_bookstore_backend.service.BookInfoService;
import com.springboot.online_bookstore_backend.service.UserRecommendService;
import com.springboot.online_bookstore_backend.utils.Result;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/item")
public class CommodityController {

    @Resource
    private BookInfoService bookinfoService;

    @Resource
    private UserRecommendService userRecommendService;


    // Get方法，获取首页基本信息，包括书籍分类、不同类别的top几（分页）、个性化推荐（分页）
    @GetMapping("/home")
    public Result<HomePageDto> getHomeMsg(@RequestParam int recommendNum, @RequestParam int cate_top, @RequestParam int book_top, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserMsgDto userMsgDto = (UserMsgDto) session.getAttribute("userMsg");
        System.out.println(userMsgDto);

        HomePageDto homePageDto;
        List<CategorysDto> categorysDto = bookinfoService.getBookCategorys();
        Map<String, List<Book_Info>> popularCategories = bookinfoService.selectTopCategoriesBooks(cate_top, book_top);
        String msg = "";
        // 用户未登录
        if (userMsgDto == null) {
            List<Book_Info> recommandBooks = bookinfoService.getRandomBookRecommend(recommendNum);
            homePageDto = new HomePageDto(categorysDto, recommandBooks, popularCategories);
            msg = "未登录时的首页";
        } else {  // todo 用户登录后个性化推荐
            List<Book_Info> recommendBookList = userRecommendService.getUserRecommendsList(userMsgDto.getUser_id(),recommendNum);
            msg = "登录时的首页智能算法推荐！";
            //冷启动时随机推荐
            if(recommendBookList == null || recommendBookList.isEmpty()){
                recommendBookList = bookinfoService.getRandomBookRecommend(recommendNum);
                msg = "登录时的首页冷启动随机推荐！";
            }

            homePageDto = new HomePageDto(categorysDto, recommendBookList, popularCategories);
        }
        return Result.success(homePageDto, msg);
    }

    // Get方法，获取首页个性化书籍列表
    @GetMapping("/recommend")
    public Result<List<Book_Info>> getRedommendBooks(@RequestParam int recommendNum, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserMsgDto userMsgDto = (UserMsgDto) session.getAttribute("userMsg");

        List<Book_Info> recommendBookList;
        String msg = "";
        if (userMsgDto == null) {
            recommendBookList = bookinfoService.getRandomBookRecommend(recommendNum);
            msg = "未登录用户，获取推荐书籍成功！";
        } else {
            // todo 当用户登录时要根据推荐算法分析用户喜好书籍推荐，现在暂时随机推荐
//            recommendBookList = bookinfoService.getRandomBookRecommend(recommendNum);
            recommendBookList = userRecommendService.getUserRecommendsList(userMsgDto.getUser_id(),recommendNum);
            msg = "登录用户，智能算法推荐！";
            //冷启动时随机推荐
            if(recommendBookList == null || recommendBookList.isEmpty()){
                recommendBookList = bookinfoService.getRandomBookRecommend(recommendNum);
                msg = "登录用户，冷启动随机推荐！";
            }

        }
        return Result.success(recommendBookList, msg);
    }

    // Get方法，获取某个类别的Top几的书籍
    @GetMapping("/popular")
    public Result<List<Book_Info>> getTopBooksByCategory(@RequestParam long cate_id, @RequestParam int top) {
        List<Book_Info> resultBookList = bookinfoService.selectTopBooksByCategory(cate_id, top);
        if (resultBookList.size() == 0) {
            return Result.error("965", "未获取到数据！");
        } else {
            return Result.success(resultBookList, "获取热门列表成功！");
        }
    }

    // Get方法，复杂搜索，返回书籍列表（可根据是否包含类别列表、类别id、关键字、页数等参数搜索，等于同时实现搜索与获取书籍列表的功能）
    @GetMapping("/search")
    public Result<SearchResultDto> complexSearch(@RequestParam(required = false, defaultValue = "false") boolean isContentCategory,
                                                     @RequestParam(required = false, defaultValue = "") String keyword,
                                                     @RequestParam(required = false, defaultValue = "-1") Long cate_id,
                                                     @RequestParam(required = false, defaultValue = "-1") Float lowPrice,
                                                     @RequestParam(required = false, defaultValue = "-1") Float highPrice,
                                                     @RequestParam(required = false, defaultValue = "1") int pageNum,
                                                     @RequestParam(required = false, defaultValue = "60") int pageSize) {

        SearchResultDto searchResult = new SearchResultDto();
        if(isContentCategory && cate_id != -1){
            List<Book_Category> currentCategoriesList = bookinfoService.getCurrentCategoriesList(cate_id);
            Map<Long,List<Book_Category>> levelMap = bookinfoService.getLevelMap(currentCategoriesList);
            searchResult.setCurrentCategoriesList(currentCategoriesList);
            searchResult.setLevelMap(levelMap);
        }
        PageInfo<Book_Info> searchResultPage = bookinfoService.complexQuery(keyword, cate_id, lowPrice, highPrice,pageNum,pageSize);
        searchResult.setSearchResultPage(searchResultPage);

        return Result.success(searchResult, "查询成功！");
    }

    // Get方法，获取商品详情，根据路径参数获取，如 http://localhost:8081/item/341414
    @GetMapping("/detail/{book_id}")
    public Result<Book_Info> showBookInfo(@PathVariable long book_id) {
        Book_Info bookinfo = bookinfoService.showBookInfoService(book_id);

        //判断前端返回的bookid是否存在
        if (bookinfo != null) {
            return Result.success(bookinfo, "返回商品详情成功！");
        } else {
            return Result.error("111", "返回商品详情失败");
        }
    }


    // Get方法，获取分类列表
    @GetMapping("/categorys")
    public Result<List<CategorysDto>> getCategoryList() {
        return Result.success(bookinfoService.getBookCategorys(), "成功获取分类列表！");
    }

    // 获取所有叶子节点的类别
    @GetMapping("/leafCategories")
    public Result<List<Book_Category>> getLeafCategories() {
        return Result.success(bookinfoService.getAllLeafCategories(), "成功获取分类列表！");
    }

    // Post方法，上架新书
    @PostMapping("/listNewBook")
    public Result addNewBook(@RequestParam("file") MultipartFile file,
                             @RequestParam long cate_id,
                             @RequestParam String bookname,
                             @RequestParam String press,
                             @RequestParam String author,
                             @RequestParam Date publish_date,
                             @RequestParam String isbn,
                             @RequestParam double price,
                             @RequestParam int pages,
                             @RequestParam long store_amount,
                             @RequestParam String description) throws IOException {

        Book_Info bookInfo = new Book_Info();
        bookInfo.setCate_id(cate_id);
        bookInfo.setBookname(bookname);
        bookInfo.setPress(press);
        bookInfo.setAuthor(author);
        bookInfo.setPublish_date(publish_date);
        bookInfo.setIsbn(isbn);
        bookInfo.setPrice(price);
        bookInfo.setPages(pages);
        bookInfo.setStore_amount(store_amount);
        bookInfo.setDescription(description);

        if(bookinfoService.addBookService(file, bookInfo)){
            return Result.success("上架新书成功！");
        }
        else {
            return Result.error("10","上架新书失败！");
        }
    }

    // Get方法，下架书籍
    @GetMapping("/delBook/{book_id}")
    public Result bookOffShelves(@PathVariable long book_id){
        if(bookinfoService.changeStoreAmountToZeroService(book_id)){
            return Result.success("下架成功！");
        }
        else {
            return Result.error("888","下架失败!");
        }
    }

    //新增类别
    @PostMapping("/addCategory")
    public Result addBookCategory(@RequestParam long parent_id, @RequestParam String catename, HttpServletRequest request){
        HttpSession session = request.getSession();
        UserMsgDto userMsgDto = (UserMsgDto)session.getAttribute("userMsg");

        boolean hasPrivilege = true;

//        for(Privilege privilege: userMsgDto.getPrivilegeList()){
//            if (privilege.getDescription().equals("新增类别")){//判断是否有新增类别权限
//                hasPrivilege = true;
//            }
//        }

        if(hasPrivilege){
            if(bookinfoService.addBookCategoryService(parent_id, catename)){
                return Result.success("新增类别成功！");
            }
            else {
                return Result.error("999","新增类别失败！");
            }
        }
        else {
            return Result.error("999","新增类别失败！");
        }
    }
}
