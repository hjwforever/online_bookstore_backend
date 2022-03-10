package com.springboot.online_bookstore_backend.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.online_bookstore_backend.controller.dto.BookInfoDto;
import com.springboot.online_bookstore_backend.controller.dto.CategorysDto;
import com.springboot.online_bookstore_backend.domain.Book_Category;
import com.springboot.online_bookstore_backend.domain.Book_Info;
import com.springboot.online_bookstore_backend.domain.Order_Detail;
import com.springboot.online_bookstore_backend.repository.Book_CategoryDao;
import com.springboot.online_bookstore_backend.repository.Book_InfoDao;
import com.springboot.online_bookstore_backend.service.BookInfoService;
import com.springboot.online_bookstore_backend.utils.MyProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class BookInfoServiceImpl implements BookInfoService {
    @Resource
    private MyProps myProps;

    @Resource
    private Book_InfoDao bookinfoDao;

    @Resource
    private Book_CategoryDao book_categoryDao;

    @Override
    public Book_Info showBookInfoService(long book_id){
        System.out.println(book_id);

        bookinfoDao.increaseLookAmount(book_id);
        Book_Info bookinfo = bookinfoDao.findBookInfo(book_id);
        this.increaseCategoryLookAmount(bookinfo.getCate_id());

        //判断前端发来的booid是否存在
        if(bookinfo == null){
            return null;
        } else {
            return bookinfo;
        }
    }

    @Override
    public List<CategorysDto> getBookCategorys() {
        // 数据库中获取的所有类别对象
        List<Book_Category> book_categories_list = book_categoryDao.findAll();
        // 返回给前端的类别根列表，每个根类别中会嵌套子类别
        List<CategorysDto> requestCategorysDtoList = new ArrayList<>();
        // 通过一个队列实现生成嵌套结构
        Queue<CategorysDto> parentsQueue = new LinkedList<>();

        // 先将所有一级类别添加到队列中并添加到返回前端的类别列表中
        Iterator<Book_Category> iterator = book_categories_list.iterator();
        while (iterator.hasNext()) {
            Book_Category bookCategory = iterator.next();
            if (bookCategory.getParent_id() == -1) {
                CategorysDto rootCategory = new CategorysDto(bookCategory);
                parentsQueue.offer(rootCategory);
                requestCategorysDtoList.add(rootCategory);
                iterator.remove();
            }
        }

        // 每次从队列中取出一个类别判断其是否有子类别，有则为其添加子类别并将子类别压入队列中
        while (parentsQueue.size() != 0) {
            CategorysDto parentCategoryDto = parentsQueue.poll();

            Iterator<Book_Category> iterator2 = book_categories_list.iterator();
            while (iterator2.hasNext()) {
                Book_Category bookCategory = iterator2.next();
                if (bookCategory.getParent_id() == parentCategoryDto.getCate_id()) {
                    CategorysDto subCategoryDto = new CategorysDto(bookCategory);
                    parentsQueue.offer(subCategoryDto);
                    parentCategoryDto.getChildren().add(subCategoryDto);
                    iterator2.remove();
                }
            }

        }

        return requestCategorysDtoList;
    }

    @Override
    public List<Book_Category> getAllLeafCategories() {
        book_categoryDao.findAllLeafCategories();
        return book_categoryDao.findAllLeafCategories();
    }

    @Override
    public List<Book_Info> getRandomBookRecommend(int bookNum) {
        return bookinfoDao.selectBooksRandomly(bookNum);
    }

    @Override
    public List<Book_Info> selectTopBooksByCategory(long cate_id, int top) {
        return bookinfoDao.selectTopBooksByCategory(cate_id,top);
    }

    @Override
    public Map<String, List<Book_Info>> selectTopCategoriesBooks(int cate_top, int book_top) {
        Map<String, List<Book_Info>> popularCategories = new HashMap<>();
        List<Book_Category> bookCategoryList = book_categoryDao.selectTopCategories(cate_top);
        for(Book_Category popularCategory : bookCategoryList){
            List<Book_Info> pupularBooks = selectTopBooksByCategory(popularCategory.getCate_id(),book_top);
            popularCategories.put(popularCategory.getCatename(), pupularBooks);
        }
        return popularCategories;
    }

    @Override
    public void increaseCategoryLookAmount(long cate_id) {
        if(cate_id==-1) return;
        Book_Category bookCategory = book_categoryDao.findByCateId(cate_id);
        if (bookCategory != null){
            book_categoryDao.increaseCategoryLookAmount(bookCategory.getCate_id());
            increaseCategoryLookAmount(bookCategory.getParent_id());
        }
    }

    @Override
    public List<Long> getLeafCategoryByRoot(long cate_id){
        List<Long> resultList = new ArrayList<>();
        Book_Category parentCategory = book_categoryDao.findByCateId(cate_id);
        if(!parentCategory.isIs_parent()){
            resultList.add(cate_id);
            return resultList;
        }

        Queue<Long> tempQueue = new LinkedList<>();
        tempQueue.offer(cate_id);
        while(tempQueue.size()!=0){
            long parentId = tempQueue.poll();
            List<Book_Category> subCateList = book_categoryDao.findByParentId(parentId);
            for(Book_Category subCate : subCateList){
                if(subCate.isIs_parent()){
                    tempQueue.offer(subCate.getCate_id());
                }else{
                    resultList.add(subCate.getCate_id());
                }
            }
        }
        return resultList;
    }

    @Override
    public List<Book_Category> getCurrentCategoriesList(long cate_id) {
        List<Book_Category> resultCategoriesList = new ArrayList<>();
        Book_Category currentCategory = book_categoryDao.findByCateId(cate_id);
        resultCategoriesList.add(currentCategory);
        while(currentCategory.getParent_id() != -1){
            currentCategory = book_categoryDao.findByCateId(currentCategory.getParent_id());
            resultCategoriesList.add(currentCategory);
        }
        // 反转
        Collections.reverse(resultCategoriesList);
        return resultCategoriesList;
    }

    @Override
    public Map<Long, List<Book_Category>> getLevelMap(List<Book_Category> bookCategoryList) {
        Map<Long, List<Book_Category>> resultMap = new HashMap<>();
        for(Book_Category bookCategory : bookCategoryList){
            resultMap.put(bookCategory.getCate_id(), book_categoryDao.findByParentId(bookCategory.getParent_id()));
        }
        return resultMap;
    }

    @Override
    public boolean isOutOfStock(List<Order_Detail> orderDetailList) {
        for(Order_Detail orderDetail : orderDetailList){
            Book_Info bookInfo = bookinfoDao.findBookInfo(orderDetail.getBook_id());
            if (bookInfo.getStore_amount()<orderDetail.getAmount()){
                return true;
            }
        }
        return false;
    }

    @Override
    public PageInfo<Book_Info> complexQuery(String keyword, long cate_id, double lowPrice, double highPrice, int pageNum, int pageSize) {

        List<Long> cateIdList;
        if(cate_id == -1){
            cateIdList = new ArrayList<>();
        }else{
            cateIdList = this.getLeafCategoryByRoot(cate_id);
        }

        PageHelper.startPage(pageNum, pageSize);
        List<Book_Info> lists = bookinfoDao.complexQuery(keyword, cateIdList, lowPrice, highPrice);
        PageInfo<Book_Info> pageInfo = new PageInfo<Book_Info>(lists);
        return pageInfo;
    }

    @Override
    public Boolean changeStoreAmountToZeroService(long book_id) {
        if(bookinfoDao.changeStoreAmountToZero(book_id)){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Boolean addBookCategoryService(long parent_id, String catename) {
        List<String> allBookCategory = book_categoryDao.findAllCatename();
        Book_Category bookCategory = new Book_Category();

        if(allBookCategory.stream().anyMatch(item->item.equals(catename))){
            return false;
        }
        else {
            bookCategory.setCatename(catename);
            bookCategory.setParent_id(parent_id);
            bookCategory.setIs_parent(false);
            bookCategory.setLook_amount(0);

            if(book_categoryDao.addbook_category(bookCategory)){
                return true;
            }
            else {
                return false;
            }
        }
    }

    @Override
    public String addBookToFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        } else {
            //文件名
            String fileName = file.getOriginalFilename();
            //后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));

            if (!(".jpg.png").contains(suffixName)) {
                return null;
            }
            // 新文件名
            fileName = UUID.randomUUID() + suffixName;
            //获取系统时间
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int date = calendar.get(Calendar.DATE);

            System.out.println(year + "/" + month + "/" + date);

            //创建文件夹
            String localPath = new File("").getAbsolutePath();
//            String relativePath = "\\src\\main\\resources\\static\\images\\bookFace\\" + year + "\\" + month + "\\" + date;
            String relativePath = "/src/main/resources/static/images/bookFace/" + year + "/" + month + "/" + date;
            localPath = localPath + relativePath;

            System.out.println(localPath);

//            System.out.println(localPath);

            File tempFile = new File(localPath, fileName);
            //判断路径是否存在，不存在则创建一个
            if (!tempFile.getParentFile().exists()) {
                tempFile.getParentFile().mkdirs();
            }
            //转存文件

            file.transferTo(tempFile);

//            String pathInDB = "http://localhost:8081/" + "images/bookFace/" + year + "/" + month + "/" + date + "/" + fileName;

            String pathInDB = "http://"+myProps.getDomainname()+":"+myProps.getPort()+"/" + "images/bookFace/" + year + "/" + month + "/" + date + "/" + fileName;
            return pathInDB;
        }
    }

    @Override
    public Boolean addBookService(MultipartFile file, Book_Info bookInfo) throws IOException {


        List<String> isbnList = bookinfoDao.findIsbnList();

        //判断上架的新书籍在数据库中是否已存在
        if(isbnList.stream().anyMatch(item->item.equals(bookInfo.getIsbn()))){
            return false;
        }
        else {
            String pathInDB = addBookToFile(file);
            System.out.println(pathInDB);
            if(pathInDB!=null && pathInDB !=""){
                bookInfo.setS_image(pathInDB);
                bookInfo.setB_image(pathInDB);

                if(bookinfoDao.insertBook(bookInfo)){
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }

    }
}
