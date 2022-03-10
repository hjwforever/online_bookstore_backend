package com.springboot.online_bookstore_backend.service;

import com.springboot.online_bookstore_backend.domain.Label;

import java.util.List;

public interface LabelService {
    List<Label> insertLabelService(Label label);
}
