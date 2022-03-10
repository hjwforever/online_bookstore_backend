package com.springboot.online_bookstore_backend.service;

import com.github.pagehelper.PageInfo;
import com.springboot.online_bookstore_backend.controller.dto.BookInfoDto;
import com.springboot.online_bookstore_backend.controller.dto.CategorysDto;
import com.springboot.online_bookstore_backend.domain.Book_Category;
import com.springboot.online_bookstore_backend.domain.Book_Info;
import com.springboot.online_bookstore_backend.domain.Order_Detail;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface BookInfoService {

    /**
     * 获取书籍详情业务逻辑
     * @param book_id
     * @return
     */
    Book_Info showBookInfoService(long book_id);

    /**
     * 获取书籍的类别嵌套关系列表
     * @return
     */
    List<CategorysDto> getBookCategorys();

    /**
     * 获取所有叶子节点的类别
     * @return
     */
    List<Book_Category> getAllLeafCategories();

    /**
     * 随机获取推荐商品
     * @param bookNum
     * @return
     */
    List<Book_Info> getRandomBookRecommend(int bookNum);

    /**
     * 获取某个类别的前几热门书籍
     * @param cate_id 类别id
     * @param top top几
     * @return
     */
    List<Book_Info> selectTopBooksByCategory(long cate_id, int top);

    /**
     * 获取前几热门类别的热门书籍
     * @param cate_top
     * @param book_top
     * @return
     */
    Map<String, List<Book_Info>> selectTopCategoriesBooks(int cate_top, int book_top);

    /**
     * 将类别访问量增加1
     * @param cate_id
     */
    void increaseCategoryLookAmount(long cate_id);

    /**
     * 复杂查询
     * @param keyword
     * @param cate_id
     * @param lowPrice
     * @param highPrice
     * @return
     */
    PageInfo<Book_Info> complexQuery(String keyword, long cate_id, double lowPrice, double highPrice, int pageNum, int pageSize);

    /**
     * 获取某个类别下所有叶子节点列表
     * @param cate_id
     * @return
     */
    List<Long> getLeafCategoryByRoot(long cate_id);

    /**
     * 根据类别id获取该类别及其所有父类别的列表
     * @param cate_id
     * @return
     */
    List<Book_Category> getCurrentCategoriesList(long cate_id);

    /**
     * 根据一个类别列表，获取该列表中每一个类别的同级类别列表并放到一个Map中
     * @return
     */
    Map<Long, List<Book_Category>> getLevelMap(List<Book_Category> bookCategoryList);

    /**
     * 判断订单商品列表中是否存在商品库存不足
     * @param orderDetailList
     * @return
     */
    boolean isOutOfStock(List<Order_Detail> orderDetailList);


    /**
     * 下架书籍（将库存数量修改为零）
     * @param book_id
     * @return
     */
    Boolean changeStoreAmountToZeroService(long book_id);

    /**
     * 新增商品类别
     * @param parent_id
     * @param catename
     * @return
     */
    Boolean addBookCategoryService(long parent_id, String catename);

    /**
     * 将图片添加到文件夹中
     * @param file
     * @return
     */
    String addBookToFile(MultipartFile file) throws IOException;

    /**
     * 上架新书
     * @param file
     * @param bookInfo
     * @return
     */
    Boolean addBookService(MultipartFile file, Book_Info bookInfo) throws IOException;
}
