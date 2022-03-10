package com.springboot.online_bookstore_backend.controller;

import com.springboot.online_bookstore_backend.controller.dto.ShoppingCartDto;
import com.springboot.online_bookstore_backend.controller.dto.UserMsgDto;
import com.springboot.online_bookstore_backend.domain.Shopping_Cart;
import com.springboot.online_bookstore_backend.service.ShoppingCartService;
import com.springboot.online_bookstore_backend.utils.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/user/cart")
public class ShoppingCartController {
    @Resource
    private ShoppingCartService shoppingCartService;


    // 获取购物车商品列表
    @GetMapping("/itemlist")
    public Result<List<ShoppingCartDto>> showShoppingCartList(HttpServletRequest request){
        HttpSession session = request.getSession();
        UserMsgDto userMsgDto = (UserMsgDto)session.getAttribute("userMsg");
        List<ShoppingCartDto> shoppingCartDtoList = shoppingCartService.showShoppingCartListService(userMsgDto.getUser_id());


        if(shoppingCartDtoList != null){
            return Result.success(shoppingCartDtoList,"获取购物车商品列表成功！");
        }
        else{
            return Result.error("222","该用户购物车为空！");
        }
    }

    // 添加商品到购物车
    @PostMapping("/additem")
    public Result<Shopping_Cart> addBookToShoppingCart(HttpServletRequest request, @RequestParam long book_id, @RequestParam int amount){
        HttpSession session = request.getSession();
        UserMsgDto userMsgDto = (UserMsgDto)session.getAttribute("userMsg");

        Shopping_Cart shoppingCart = shoppingCartService.addBookToShoppingCartService(userMsgDto.getUser_id(), book_id, amount);

        if(shoppingCart != null){
            return Result.success(shoppingCart, "添加商品成功！");
        }
        else{
            return Result.error("333", "添加商品失败!");
        }
    }

    // 在购物车中调整某个商品的数量
    @GetMapping("/changenum")
    public Result<Shopping_Cart> changeAmountFromBookShoppingCart(HttpServletRequest request, @RequestParam long book_id, @RequestParam int amount){
        HttpSession session = request.getSession();
        UserMsgDto userMsgDto = (UserMsgDto)session.getAttribute("userMsg");

        Shopping_Cart shoppingCart = shoppingCartService.changeAmountFromShoppingCartService(userMsgDto.getUser_id(), book_id, amount);

        if(shoppingCart != null){
            return Result.success(shoppingCart,"修改数量成功！");
        }
        else{
            return Result.error("444","修改数量失败！");
        }
    }

    // 从购物车删除商品（单个或批量）
    @GetMapping("/delitem")
    public Result delBookFromBookShoppingCart(HttpServletRequest request, @RequestBody List<Long> bookIdList){
        HttpSession session = request.getSession();
        UserMsgDto userMsgDto = (UserMsgDto)session.getAttribute("userMsg");

        if(shoppingCartService.deleteBookFromShoppingCartService(userMsgDto.getUser_id(), bookIdList)){
            return Result.success("删除成功！");
        }
        else{
            return Result.error("555","删除失败！");
        }
    }


}
