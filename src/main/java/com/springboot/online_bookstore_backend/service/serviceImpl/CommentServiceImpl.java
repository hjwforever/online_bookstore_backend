package com.springboot.online_bookstore_backend.service.serviceImpl;

import com.springboot.online_bookstore_backend.controller.dto.CommentReplyDto;
import com.springboot.online_bookstore_backend.domain.Comment;
import com.springboot.online_bookstore_backend.domain.Reply;
import com.springboot.online_bookstore_backend.repository.CommentDao;
import com.springboot.online_bookstore_backend.repository.ReplyDao;
import com.springboot.online_bookstore_backend.service.CommentService;
import com.springboot.online_bookstore_backend.utils.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentDao commentDao;
    @Resource
    private ReplyDao replyDao;

    @Override
    public List<Comment> insertCommentService(Comment comment) {
        commentDao.insert(comment);
        return commentDao.findBookComment(comment.getBook_id());
    }

    @Override
    public List<CommentReplyDto> findAllByBookId(long bookId) {

        List<Comment> commentList = commentDao.findBookComment(bookId);
        List<CommentReplyDto> commentReplyDtoList =new LinkedList<>();

        //前端发来的bookId不存在
        if(commentList.isEmpty()){
            return null;
        }

        //遍历commentList获得每个commentId
        for (Comment comment:commentList){
            CommentReplyDto commentReplyDto = new CommentReplyDto();

            //获得commentId后找到对应的replyList添加到Dto中
            commentReplyDto.setComment_id(comment.getComment_id());
            commentReplyDto.setBook_id(bookId);
            commentReplyDto.setUser_id(comment.getUser_id());
            commentReplyDto.setNickname(comment.getNickname());
            commentReplyDto.setDate(comment.getDate());
            commentReplyDto.setContent(comment.getContent());
            commentReplyDto.setReplyList(replyDao.findAllReplies(comment.getComment_id()));

            commentReplyDtoList.add(commentReplyDto);

        }

        return commentReplyDtoList;
    }
}
