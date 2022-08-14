package com.example.demo.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.Book;

public interface IBookService extends IService<Book> {
    IPage<Book> getPage(int currentPage,int pageSize,Book book);
}
