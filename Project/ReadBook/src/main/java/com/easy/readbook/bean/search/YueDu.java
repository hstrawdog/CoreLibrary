package com.easy.readbook.bean.search;

import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : com.easy.readbook.bean.search
 * @Date : 下午 5:30
 * @Email : qiqiang213@gmail.com
 * @Describe : http://yuedu.163.com/querySearchHints.do?keyword=%E4%BB%99%E5%B1%B1
 */
public class YueDu {

    /**
     * keyword : 仙山
     * ResultCode : 0
     * resultCode : 0
     * book : [{"name":"乡村有座仙山"}]
     */

    private String keyword;
    private int ResultCode;
    private int resultCode;
    private List<BookDTO> book;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getResultCode() {
        return ResultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public List<BookDTO> getBook() {
        return book;
    }

    public void setBook(List<BookDTO> book) {
        this.book = book;
    }

    public static class BookDTO {
        /**
         * name : 乡村有座仙山
         */

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
