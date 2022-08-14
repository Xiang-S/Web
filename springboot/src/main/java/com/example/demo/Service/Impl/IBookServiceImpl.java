package com.example.demo.Service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.Service.IBookService;
import com.example.demo.dao.BookDao;
import com.example.demo.pojo.Book;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class IBookServiceImpl extends ServiceImpl<BookDao, Book> implements IBookService {

    @Autowired
    private BookDao bookDao;
    @Override
    public IPage<Book> getPage(int currentPage, int pageSize,Book queryBook) {
        LambdaQueryWrapper<Book> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(Strings.isNotEmpty(queryBook.getName()),Book::getName,queryBook.getName());
        queryWrapper.like(Strings.isNotEmpty(queryBook.getType()),Book::getType,queryBook.getType());
        queryWrapper.like(Strings.isNotEmpty(queryBook.getDescription()),Book::getDescription,queryBook.getDescription());
        IPage page = new Page(currentPage, pageSize);
        return bookDao.selectPage(page,queryWrapper);
    }
}
