package com.easy.readbook.bean.search;

import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.bean.search
 * @Date : 下午 5:31
 * @Email : qiqiang213@gmail.com
 * @Describe : http://api.zhuishushenqi.com/book/auto-complete?query=%E4%BB%99%E5%B1%B1
 */
public class ZhuiShuShenQi {
    /**
     * keywords : ["仙上仙","仙上的戏精夫人","仙上的高冷转世转丢了","仙上，请留步"]
     * ok : true
     */

    private List<String> keywords;
    private boolean ok;

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
