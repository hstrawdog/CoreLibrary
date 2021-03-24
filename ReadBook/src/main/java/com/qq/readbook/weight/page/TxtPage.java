package com.qq.readbook.weight.page;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.weight.page
 * @FileName :   TxtPage
 * @Date : 2021/2/20 0020  上午 9:44
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 */
public class TxtPage {
    public int position;
    public String title;
    /**
     * 当前 lines 中为 title 的行数。
     */
    public int titleLines;
    public List<String> lines;
    private String pic;

    public int getPosition() {
        return position;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }


    public List<String> getLines() {
        if (lines == null) {
            return new ArrayList<>();
        }
        return lines;
    }


    public String getPic() {
        return pic == null ? "" : pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
