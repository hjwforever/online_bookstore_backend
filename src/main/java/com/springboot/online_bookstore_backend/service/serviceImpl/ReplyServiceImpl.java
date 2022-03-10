package com.springboot.online_bookstore_backend.service.serviceImpl;

import com.springboot.online_bookstore_backend.domain.Reply;
import com.springboot.online_bookstore_backend.repository.ReplyDao;
import com.springboot.online_bookstore_backend.service.ReplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {
    @Resource
    private ReplyDao replyDao;

    @Override
    public List<Reply> insertReplyService(Reply reply) {
        replyDao.insert(reply);

        return replyDao.findAllReplies(reply.getComment_id());
    }
}
