package com.example.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.Service.IBookService;
import com.example.demo.controller.utils.R;
import com.example.demo.pojo.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/books")
public class BookController {
    @Autowired
    private IBookService bookService;

    @GetMapping
    public R getAll() {
        return new R(true, bookService.list());
    }

    @PostMapping
    public R save(@RequestBody Book book) {
        boolean flag = bookService.save(book);
        return new R(flag, flag ? "添加成功" : "添加失败");
    }

    @PutMapping
    public R update(@RequestBody Book book) {
        boolean flag = bookService.updateById(book);
        return new R(flag, flag ? "编辑成功" : "编辑失败");
    }

    @DeleteMapping("{id}")
    public R delete(@PathVariable Integer id) {
        boolean flag = bookService.removeById(id);
        return new R(flag, flag ? "删除成功" : "删除失败");
    }

    @GetMapping("{id}")
    public R getById(@PathVariable Integer id) {
        Book book = bookService.getById(id);
        return new R(true, book);
    }

    @GetMapping("{currentPage}/{pageSize}")
    public R getByPage(@PathVariable int currentPage, @PathVariable int pageSize, Book book) {
        IPage<Book> page = bookService.getPage(currentPage, pageSize, book);
        if (currentPage > page.getPages()) {
            page = bookService.getPage((int) page.getPages(), pageSize, book);
        }
        return new R(true, page);
    }
}
