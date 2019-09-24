package com.hqq.core.recycler;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.banner
 * @FileName :   BaseBannerBean
 * @Date : 2018/6/19 0019  下午 5:37
 * @Email :  qiqiang213@gmail.com
 * @Descrive : TODO
 */
@Deprecated
public interface BaseBannerBean {
    /**
     * 图片url
     *
     * @return
     */
    String getBannerUrl();

    /**
     * 标题
     *
     * @return
     */
    String getBannerTitle();

    /**
     * tag  其他 这边应该是要一个Object
     * 保存一些需要通过banner 传递的数据
     * @return
     */
    Object getBannerTag();
}
