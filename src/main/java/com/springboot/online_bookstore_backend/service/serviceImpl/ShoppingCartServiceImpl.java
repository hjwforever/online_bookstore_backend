package com.springboot.online_bookstore_backend.service.serviceImpl;

import com.springboot.online_bookstore_backend.controller.dto.ShoppingCartDto;
import com.springboot.online_bookstore_backend.domain.Book_Info;
import com.springboot.online_bookstore_backend.domain.Shopping_Cart;
import com.springboot.online_bookstore_backend.repository.Book_InfoDao;
import com.springboot.online_bookstore_backend.repository.Shopping_CartDao;
import com.springboot.online_bookstore_backend.repository.UserDao;
import com.springboot.online_bookstore_backend.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Resource
    private Shopping_CartDao shoppingCartDao;

    @Resource
    private Book_InfoDao bookInfoDao;

    @Override
    public List<ShoppingCartDto> showShoppingCartListService(long user_id){
        long bookid;
        List<Shopping_Cart> shoppingCartList = shoppingCartDao.findAllShoppingCard(user_id);
        List<ShoppingCartDto> shoppingCartDtoList = new LinkedList<>();

        //数据库查询整个书籍详细信息表
        List<Book_Info> bookInfoList = bookInfoDao.findAllBookInfo();

        //前端发来的用户不存在
        if(shoppingCartList.isEmpty()){
            return null;
        }

        //循环遍历shoppingCartList得到shoppingCartDtoList
        for(Shopping_Cart shoppingcart:shoppingCartList ){
            bookid = shoppingcart.getBook_id();
            ShoppingCartDto shoppingCartDto = new ShoppingCartDto();

            shoppingCartDto.setUser_id(user_id);
            shoppingCartDto.setBook_id(shoppingcart.getBook_id());
            shoppingCartDto.setCart_id(shoppingcart.getCart_id());
            shoppingCartDto.setAmount(shoppingcart.getAmount());

            //循环遍历整个书籍详细信息表
            for(Book_Info bookinfo:bookInfoList){
                //如果找到相同bookid
                if(bookinfo.getBook_id()==bookid){
                    shoppingCartDto.setAuthor(bookinfo.getAuthor());
                    shoppingCartDto.setBookname(bookinfo.getBookname());
                    shoppingCartDto.setS_image(bookinfo.getS_image());
                    shoppingCartDto.setPrice(bookinfo.getPrice());
                    shoppingCartDto.setPress(bookinfo.getPress());
                    shoppingCartDto.setStore_amount(bookinfo.getStore_amount());
                }
            }

            shoppingCartDtoList.add(shoppingCartDto);
        }
        return shoppingCartDtoList;
    }

    @Override
    public Shopping_Cart addBookToShoppingCartService(long user_id, long book_id, int amount) {
        //判断该书籍在书籍详情数据库中是否存在
        if(bookInfoDao.findBookInfo(book_id)!=null){
            Shopping_Cart shoppingCart = new Shopping_Cart();
            shoppingCart.setUser_id(user_id);
            shoppingCart.setBook_id(book_id);

            Integer old_amount = shoppingCartDao.findBookAmount(user_id, book_id);

            //判断该书籍在购物车中是否存在
            if(old_amount != null){
                Integer new_amount = old_amount + amount;
                //判断判断是否修改成功
                if(shoppingCartDao.changeAmount(user_id, book_id, new_amount)){
                    shoppingCart.setAmount(new_amount);
                    return shoppingCart;
                }
                else{
                    return null;
                }
            }
            else{
                shoppingCart.setAmount(amount);
                //判断是否添加成功
                if(shoppingCartDao.insertBook(shoppingCart)){
                    return shoppingCart;
                }
                else{
                    return null;
                }
            }
        }
        else{
            return null;
        }
    }

    @Override
    public Shopping_Cart changeAmountFromShoppingCartService(long user_id, long book_id, int amount) {
        //判断该书籍在书籍详情数据库中是否存在
        if(bookInfoDao.findBookInfo(book_id)!=null){
            //判断该书籍在购物车中是否存在
            if(shoppingCartDao.findBookAmount(user_id, book_id) != null){
                shoppingCartDao.changeAmount(user_id, book_id, amount);
                Shopping_Cart shoppingCart = new Shopping_Cart();
                shoppingCart.setUser_id(user_id);
                shoppingCart.setBook_id(book_id);
                shoppingCart.setAmount(amount);
                return shoppingCart;
            }
            else{
                return null;
            }
        }
        else{
            return null;
        }
    }

    @Override
    public Boolean deleteBookFromShoppingCartService(long user_id, List<Long> book_id) {
        if(shoppingCartDao.deleteBook(user_id, book_id)){
            return true;
        }
        else {
            return false;
        }
    }
}
