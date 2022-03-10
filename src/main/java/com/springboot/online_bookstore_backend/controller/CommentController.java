package com.springboot.online_bookstore_backend.controller;

import com.springboot.online_bookstore_backend.controller.dto.CommentReplyDto;
import com.springboot.online_bookstore_backend.controller.dto.UserMsgDto;
import com.springboot.online_bookstore_backend.domain.Comment;
import com.springboot.online_bookstore_backend.domain.Reply;
import com.springboot.online_bookstore_backend.service.CommentService;
import com.springboot.online_bookstore_backend.service.ReplyService;
import com.springboot.online_bookstore_backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/item/comment")
public class CommentController {
    @Resource
    private CommentService commentService;
    @Resource
    private ReplyService replyService;

    /**
     *评论某本书
     * @param book_id
     * @param content
     * @param request
     * @return
     */
    @PostMapping("/add")
    public Result<List<Comment>> CommentController(@RequestParam long book_id,@RequestParam String content, HttpServletRequest request){

        HttpSession session = request.getSession();
        UserMsgDto userMsg = (UserMsgDto)session.getAttribute("userMsg");

        Comment comment = new Comment();

        comment.setBook_id(book_id);
        comment.setContent(content);
        comment.setUser_id(userMsg.getUser_id());
        comment.setNickname(userMsg.getNickname());

        List<Comment> commentList = commentService.insertCommentService(comment);
        return Result.success("评论成功！");
    }

    /**
     * 回复某个评论
     * @param comment_id
     * @param content
     * @param request
     * @return
     */
    @PostMapping("/reply")
    public Result<List<Reply>> ReplyController(@RequestParam long comment_id,@RequestParam String content, HttpServletRequest request){


        HttpSession session = request.getSession();
        UserMsgDto userMsg = (UserMsgDto)session.getAttribute("userMsg");

        Reply reply = new Reply();

        reply.setComment_id(comment_id);
        reply.setContent(content);
        reply.setUser_id(userMsg.getUser_id());
        reply.setNickname(userMsg.getNickname());

        replyService.insertReplyService(reply);
        return Result.success("回复成功！");
    }

    /**
     * 获取某本书的所有评论与回复
     * @param book_id
     * @return
     */
    @GetMapping("/list")
    public Result<List<CommentReplyDto>> ListController(@RequestParam long book_id){
        List<CommentReplyDto> commentReplyDtoList = commentService.findAllByBookId(book_id);

        //判断前端返回的bookId是否存在
        if(commentReplyDtoList != null){
            return Result.success(commentReplyDtoList,"成功！");
        }

        return Result.error("90","此商品暂无评论！");

    }
}
