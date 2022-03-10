package com.springboot.online_bookstore_backend.service.serviceImpl;

import com.springboot.online_bookstore_backend.domain.Label;
import com.springboot.online_bookstore_backend.repository.LabelDao;
import com.springboot.online_bookstore_backend.service.LabelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LabelServiceImpl implements LabelService {
    @Resource
    private LabelDao labelDao;

    @Override
    public List<Label> insertLabelService(Label label) {
        return null;
    }
}
