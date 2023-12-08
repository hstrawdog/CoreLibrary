package com.easy.readbook.bean.search;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : com.easy.readbook.bean.search
 * @Date : 下午 5:14
 * @Email : qiqiang213@gmail.com
 * @Describe : http://search.zongheng.com/search/suggest?keyword=%E4%BB%99%E5%B1%B1
 */
public class ZongHeng {
    private ArrayList<String> books;
    private List<String> authors;
    public ArrayList<String> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<String> books) {
        this.books = books;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }
}
