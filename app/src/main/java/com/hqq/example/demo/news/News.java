package com.hqq.example.demo.news;

import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo.news
 * @FileName :   News
 * @Date : 2020/8/5 0005  上午 10:37
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class News {


    /**
     * stat : 1
     * data : [{"uniquekey":"7ec7bccfd79a56702bc9fb1f6d94a5c2","title":"幽默笑话：小明至今单身，这和他奇葩的搭讪方式不无关系","date":"2020-08-05 10:05","category":"头条","author_name":"幽默笑话段子集锦","url":"https://mini.eastday.com/mobile/200805100513953.html","thumbnail_pic_s":"https://06imgmini.eastday.com/mobile/20200805/2020080510_a4ff0ab99e154125970f95bbb5d88dff_1752_cover_mwpm_03200403.jpg","thumbnail_pic_s02":"http://06imgmini.eastday.com/mobile/20200805/2020080510_886cd6148c7d439d8964d8dbd2639534_0492_cover_mwpm_03200403.jpg","thumbnail_pic_s03":"http://06imgmini.eastday.com/mobile/20200805/2020080510_440e4cded56343e4a05a8679741ee141_8166_cover_mwpm_03200403.jpg"},{"uniquekey":"b489f90d10547c1ed0b8b418fdbdae83","title":"你瞧，你瞧，中国的\u201c气质\u201d好起来了","date":"2020-08-05 10:01","category":"头条","author_name":"人民日报海外网","url":"https://mini.eastday.com/mobile/200805100156756.html","thumbnail_pic_s":"https://01imgmini.eastday.com/mobile/20200805/20200805100156_9711e0e11695c3613b6e38bfc648fccb_1_mwpm_03200403.jpg"},{"uniquekey":"520540fb72a02cf139ef799662b4e2d8","title":"幽默笑话：我家楼顶有人养鸽子 我实在忍不下去了","date":"2020-08-05 09:54","category":"头条","author_name":"怪咖搞笑","url":"https://mini.eastday.com/mobile/200805095446146.html","thumbnail_pic_s":"https://06imgmini.eastday.com/mobile/20200805/20200805095446_31398d0ecb724b4caee90070e6ae2dfc_2_mwpm_03200403.jpg","thumbnail_pic_s02":"http://06imgmini.eastday.com/mobile/20200805/20200805095446_31398d0ecb724b4caee90070e6ae2dfc_8_mwpm_03200403.jpg","thumbnail_pic_s03":"http://06imgmini.eastday.com/mobile/20200805/20200805095446_31398d0ecb724b4caee90070e6ae2dfc_4_mwpm_03200403.jpg"},{"uniquekey":"a4eef2c74874896df4b45563fea30c76","title":"街拍：明眸善睐的美女，一件橘黄色短袖配运动裤，运动时尚风范","date":"2020-08-05 09:45","category":"头条","author_name":"娱乐大咖无极限","url":"https://mini.eastday.com/mobile/200805094521161.html","thumbnail_pic_s":"https://06imgmini.eastday.com/mobile/20200805/2020080509_423867935fa54239a0106e3356519717_4405_cover_mwpm_03200403.jpg"},{"uniquekey":"a5bc93fa978ae1edddfe99e214103f8c","title":"福建省武平县发展林下经济\u201c点绿成金\u201d","date":"2020-08-05 09:41","category":"头条","author_name":"人民网","url":"https://mini.eastday.com/mobile/200805094100571.html","thumbnail_pic_s":"https://01imgmini.eastday.com/mobile/20200805/20200805094100_6c6d55297e07fa42d9f69a5c274aca1a_1_mwpm_03200403.jpg","thumbnail_pic_s02":"http://01imgmini.eastday.com/mobile/20200805/20200805094100_6c6d55297e07fa42d9f69a5c274aca1a_5_mwpm_03200403.jpg","thumbnail_pic_s03":"http://01imgmini.eastday.com/mobile/20200805/20200805094100_6c6d55297e07fa42d9f69a5c274aca1a_3_mwpm_03200403.jpg"},{"uniquekey":"58dbed8065bcb1b09f91f280fc821de2","title":"CBA京粤之战5大争议判罚复盘：杜锋大闹技术台、方硕失控砸球","date":"2020-08-05 09:33","category":"头条","author_name":"罗掌柜体育","url":"https://mini.eastday.com/mobile/200805093342987.html","thumbnail_pic_s":"https://06imgmini.eastday.com/mobile/20200805/2020080509_3d2fa14a76b84ae8abef4050118dc080_4748_mwpm_03200403.jpg","thumbnail_pic_s02":"http://06imgmini.eastday.com/mobile/20200805/2020080509_6515d7ced1df4b9597e77fa9b5117050_8362_mwpm_03200403.jpg","thumbnail_pic_s03":"http://06imgmini.eastday.com/mobile/20200805/2020080509_799cb2715a764ce894a5da1157aa7598_9095_mwpm_03200403.jpg"},{"uniquekey":"f96cb43fa4a555fcf2409ebd395c2595","title":"惊人相似的一幕！首钢队复制中国男篮世界杯剧情：最后失误遭逆转","date":"2020-08-05 09:33","category":"头条","author_name":"瑜说还休","url":"https://mini.eastday.com/mobile/200805093312282.html","thumbnail_pic_s":"https://00imgmini.eastday.com/mobile/20200805/2020080509_6f53648ba04340febce4ed7863da8132_6185_cover_mwpm_03200403.jpg","thumbnail_pic_s02":"http://00imgmini.eastday.com/mobile/20200805/2020080509_a803361e36f44ecaa4682e32f96c3e2c_8107_cover_mwpm_03200403.jpg","thumbnail_pic_s03":"http://00imgmini.eastday.com/mobile/20200805/2020080509_8bd83e95f7e44fe58f61ed0e4bc207c1_5845_cover_mwpm_03200403.jpg"},{"uniquekey":"1099a844c5d842b6f263e6931ca11a41","title":"这就是一场丑闻！名嘴怒斥京粤大战，裁判问题再成焦点，姚明难了","date":"2020-08-05 09:33","category":"头条","author_name":"篮球殿堂","url":"https://mini.eastday.com/mobile/200805093301319.html","thumbnail_pic_s":"https://09imgmini.eastday.com/mobile/20200805/2020080509_870384c263cc4038a69f75ba822e2e8b_7882_mwpm_03200403.jpg","thumbnail_pic_s02":"http://09imgmini.eastday.com/mobile/20200805/2020080509_6f0acf88e5fe4e4596aa8393d98c9a63_8296_mwpm_03200403.jpg","thumbnail_pic_s03":"http://09imgmini.eastday.com/mobile/20200805/2020080509_4a27357ec1d544c7b0fc73794359ea7e_5358_mwpm_03200403.jpg"},{"uniquekey":"bb98c2aaf4bcf4c03bc6ea854fe45a93","title":"从15万多下探至10万，油耗5.9L，堪称最良心的合资中级车","date":"2020-08-05 09:29","category":"头条","author_name":"隔壁说车老王","url":"https://mini.eastday.com/mobile/200805092933391.html","thumbnail_pic_s":"https://07imgmini.eastday.com/mobile/20200805/20200805092933_96c75b1338f9a00a3eea755498600b4f_2_mwpm_03200403.jpg","thumbnail_pic_s02":"http://07imgmini.eastday.com/mobile/20200805/20200805092933_96c75b1338f9a00a3eea755498600b4f_1_mwpm_03200403.jpg","thumbnail_pic_s03":"http://07imgmini.eastday.com/mobile/20200805/20200805092933_96c75b1338f9a00a3eea755498600b4f_3_mwpm_03200403.jpg"},{"uniquekey":"acefdc51dd6ec014d18607051247832b","title":"研究证明远古火星或被冰川覆盖","date":"2020-08-05 09:26","category":"头条","author_name":"环球网","url":"https://mini.eastday.com/mobile/200805092609272.html","thumbnail_pic_s":"https://08imgmini.eastday.com/mobile/20200805/20200805092609_6f46f1ac1db2c665fe0e9a5f77fe57cd_1_mwpm_03200403.jpg","thumbnail_pic_s02":"http://08imgmini.eastday.com/mobile/20200805/20200805092609_6f46f1ac1db2c665fe0e9a5f77fe57cd_2_mwpm_03200403.jpg"},{"uniquekey":"8e48c0155721bcbc4058a5100e5e9437","title":"美女街拍：穿打底裤的小姐姐，曲线苗条，尽显青春活力！","date":"2020-08-05 09:24","category":"头条","author_name":"首席时尚官儿","url":"https://mini.eastday.com/mobile/200805092446912.html","thumbnail_pic_s":"https://07imgmini.eastday.com/mobile/20200805/2020080509_1f44d60a5cd34a128c338bb78173b540_0019_cover_mwpm_03200403.jpg","thumbnail_pic_s02":"http://07imgmini.eastday.com/mobile/20200805/2020080509_22c20981f63c4881a8bafa40bd265784_6259_cover_mwpm_03200403.jpg","thumbnail_pic_s03":"http://07imgmini.eastday.com/mobile/20200805/2020080509_cc6364cc2ef141039a0a8bb33ec8686b_1201_cover_mwpm_03200403.jpg"},{"uniquekey":"1f25cf8aa19eb5dea2b964c3e8c48c83","title":"美女街拍：成熟时尚的小姐姐，打底裤的打扮，穿出女神魅力！","date":"2020-08-05 09:18","category":"头条","author_name":"首席时尚官儿","url":"https://mini.eastday.com/mobile/200805091838414.html","thumbnail_pic_s":"https://06imgmini.eastday.com/mobile/20200805/2020080509_3af2e936dcfd44aaa81764a90c1dd610_1594_cover_mwpm_03200403.jpg","thumbnail_pic_s02":"http://06imgmini.eastday.com/mobile/20200805/2020080509_7f1c5e1e53974e0592739e842999f6d7_9359_cover_mwpm_03200403.jpg","thumbnail_pic_s03":"http://06imgmini.eastday.com/mobile/20200805/2020080509_313f4c785f764c71b5fdc90030f7cd9b_2187_cover_mwpm_03200403.jpg"},{"uniquekey":"d6c945801227cd4e5b657082d4bab969","title":"疏远中国，不符合英国的根本外交利益","date":"2020-08-05 09:18","category":"头条","author_name":"纵相新闻","url":"https://mini.eastday.com/mobile/200805091805622.html","thumbnail_pic_s":"https://00imgmini.eastday.com/mobile/20200805/2020080509_1398ab4fd6de48f1b9c61ff951722d1a_8515_cover_mwpm_03200403.jpg","thumbnail_pic_s02":"http://00imgmini.eastday.com/mobile/20200805/2020080509_82bb32855d424b4f8b7f42ddf22cdb33_5670_cover_mwpm_03200403.jpg"},{"uniquekey":"1bf5fcc112724d7a68490d0fd2f8501a","title":"曝光！浙江一家政工干出这种事！","date":"2020-08-05 09:13","category":"头条","author_name":"光明网","url":"https://mini.eastday.com/mobile/200805091330593.html","thumbnail_pic_s":"https://03imgmini.eastday.com/mobile/20200805/20200805091330_59ca3a2af0b2905cf9f4b8ce08cacd8c_2_mwpm_03200403.jpg","thumbnail_pic_s02":"http://03imgmini.eastday.com/mobile/20200805/20200805091330_59ca3a2af0b2905cf9f4b8ce08cacd8c_3_mwpm_03200403.jpg","thumbnail_pic_s03":"http://03imgmini.eastday.com/mobile/20200805/20200805091330_59ca3a2af0b2905cf9f4b8ce08cacd8c_1_mwpm_03200403.jpg"},{"uniquekey":"af9b23a94ead5c68aed355a61dd16e85","title":"黎巴嫩最高防务委员会宣布贝鲁特为受灾城市","date":"2020-08-05 09:12","category":"头条","author_name":"光明网","url":"https://mini.eastday.com/mobile/200805091217888.html","thumbnail_pic_s":"https://00imgmini.eastday.com/mobile/20200805/20200805091217_1de63a2350bda875e1d211cf5fecffc9_1_mwpm_03200403.jpg"},{"uniquekey":"b60e25ae308b741075af54bed0350ed9","title":"安倍累到吐血？日本政府：没有的事","date":"2020-08-05 09:08","category":"头条","author_name":"北京日报客户端","url":"https://mini.eastday.com/mobile/200805090858128.html","thumbnail_pic_s":"https://04imgmini.eastday.com/mobile/20200805/20200805090858_f88d91ed43f30f9bed333a90d5b299da_1_mwpm_03200403.jpg"},{"uniquekey":"df8d44bf477bb0b1faecfdd60181b26c","title":"幽默笑话：大半夜的我正做梦娶媳妇呢，女同事敲门把我惊醒了","date":"2020-08-05 09:08","category":"头条","author_name":"幽默笑话段子集锦","url":"https://mini.eastday.com/mobile/200805090851306.html","thumbnail_pic_s":"https://03imgmini.eastday.com/mobile/20200805/2020080509_c8cba3467e124a17bb3ed7d75af37590_2569_cover_mwpm_03200403.jpg","thumbnail_pic_s02":"http://03imgmini.eastday.com/mobile/20200805/2020080509_0cb7970900c24e2cb7bfcec6e3550a9e_6106_cover_mwpm_03200403.jpg","thumbnail_pic_s03":"http://03imgmini.eastday.com/mobile/20200805/2020080509_a950ebf31f614020b9e29804f3dd1695_5441_cover_mwpm_03200403.jpg"},{"uniquekey":"ed79929e6ac9c7e8f2eabf85f45d4ad9","title":"有原则，有骨气的三大星座，不骄不躁，淡泊名利，活得十分清醒","date":"2020-08-05 09:01","category":"头条","author_name":"清醒姐星座","url":"https://mini.eastday.com/mobile/200805090149719.html","thumbnail_pic_s":"https://02imgmini.eastday.com/mobile/20200805/2020080508_c283694009dc4254a7b3c7456ed5621f_4246_mwpm_03200403.jpg","thumbnail_pic_s02":"http://02imgmini.eastday.com/mobile/20200805/2020080508_b6192e4ea89b4295b512388961c67d0b_9421_mwpm_03200403.jpg","thumbnail_pic_s03":"http://02imgmini.eastday.com/mobile/20200805/2020080508_d5a82c3121cd4bdf8127454ac53c78ec_4658_mwpm_03200403.jpg"},{"uniquekey":"46b5a2d60e53111babc2d74371a07f2d","title":"开心一笑：本人未婚，慵懒在家打游戏，弟媳妇扭捏地走了过来说\u2026","date":"2020-08-05 08:59","category":"头条","author_name":"搞笑奇葩菌","url":"https://mini.eastday.com/mobile/200805085933482.html","thumbnail_pic_s":"https://08imgmini.eastday.com/mobile/20200805/20200805085933_5cd1c7009b1fc21a5f6f4485dfeb6046_2_mwpm_03200403.jpg","thumbnail_pic_s02":"http://08imgmini.eastday.com/mobile/20200805/20200805085933_5cd1c7009b1fc21a5f6f4485dfeb6046_1_mwpm_03200403.jpg","thumbnail_pic_s03":"http://08imgmini.eastday.com/mobile/20200805/20200805085933_5cd1c7009b1fc21a5f6f4485dfeb6046_3_mwpm_03200403.jpg"},{"uniquekey":"9d4441bb343ff3a9fa5fe87ff55a94eb","title":"开心一笑：中午到食堂去吃饭，像往常一样先去洗手拧开水龙头\u2026\u2026","date":"2020-08-05 08:59","category":"头条","author_name":"虫子爱搞笑","url":"https://mini.eastday.com/mobile/200805085912471.html","thumbnail_pic_s":"https://03imgmini.eastday.com/mobile/20200805/20200805085912_f8aa2b1a1c8f3a25bc2863bf5f6dc5de_2_mwpm_03200403.jpg","thumbnail_pic_s02":"http://03imgmini.eastday.com/mobile/20200805/20200805085912_f8aa2b1a1c8f3a25bc2863bf5f6dc5de_3_mwpm_03200403.jpg","thumbnail_pic_s03":"http://03imgmini.eastday.com/mobile/20200805/20200805085912_f8aa2b1a1c8f3a25bc2863bf5f6dc5de_1_mwpm_03200403.jpg"},{"uniquekey":"d4ecee72b8617befe738668cbfb760bf","title":"华为手机虽好但不能乱买，这四款机型选择价值走低！","date":"2020-08-05 08:58","category":"头条","author_name":"每天都吃饭","url":"https://mini.eastday.com/mobile/200805085856877.html","thumbnail_pic_s":"https://05imgmini.eastday.com/mobile/20200805/20200805085856_ad4472adf96a6f11bddf4d7669fa1210_1_mwpm_03200403.jpg","thumbnail_pic_s02":"http://05imgmini.eastday.com/mobile/20200805/20200805085856_ad4472adf96a6f11bddf4d7669fa1210_2_mwpm_03200403.jpg","thumbnail_pic_s03":"http://05imgmini.eastday.com/mobile/20200805/20200805085856_ad4472adf96a6f11bddf4d7669fa1210_3_mwpm_03200403.jpg"},{"uniquekey":"06fa81ff9a35a3c6b0b7816f47eedbd2","title":"癌症太可怕！医生：生活中想要不得癌，这5种食物要远离！","date":"2020-08-05 08:58","category":"头条","author_name":"美妙健康来","url":"https://mini.eastday.com/mobile/200805085855736.html","thumbnail_pic_s":"https://08imgmini.eastday.com/mobile/20200805/20200805085855_e2dcb8d95f5deac4fe3071a6694e7eba_4_mwpm_03200403.jpg","thumbnail_pic_s02":"http://08imgmini.eastday.com/mobile/20200805/20200805085855_e2dcb8d95f5deac4fe3071a6694e7eba_1_mwpm_03200403.jpg","thumbnail_pic_s03":"http://08imgmini.eastday.com/mobile/20200805/20200805085855_e2dcb8d95f5deac4fe3071a6694e7eba_3_mwpm_03200403.jpg"},{"uniquekey":"fa1e52e446954bdfb59f5430f2278d5a","title":"粤港澳大湾区城际铁路建设规划获国家发改委批复","date":"2020-08-05 08:58","category":"头条","author_name":"人民网","url":"https://mini.eastday.com/mobile/200805085818372.html","thumbnail_pic_s":"https://02imgmini.eastday.com/mobile/20200805/20200805085818_634b0cfef582473e83ac033da843137b_1_mwpm_03200403.jpg"},{"uniquekey":"a12d2dcf3d77979c92f13a16e430c597","title":"热带风暴\u201c伊萨亚斯\u201d登陆纽约 掀起狂风暴雨","date":"2020-08-05 08:58","category":"头条","author_name":"环球网","url":"https://mini.eastday.com/mobile/200805085803653.html","thumbnail_pic_s":"https://08imgmini.eastday.com/mobile/20200805/20200805085803_b7ce4ffd9022323e6849f81e6a5aaa4c_1_mwpm_03200403.jpg","thumbnail_pic_s02":"http://08imgmini.eastday.com/mobile/20200805/20200805085803_b7ce4ffd9022323e6849f81e6a5aaa4c_4_mwpm_03200403.jpg","thumbnail_pic_s03":"http://08imgmini.eastday.com/mobile/20200805/20200805085803_b7ce4ffd9022323e6849f81e6a5aaa4c_2_mwpm_03200403.jpg"},{"uniquekey":"6e4eab4bb3ed03b40481301c1dacb161","title":"雍正继位不合法？诏书重现光明，当年的康熙可一点也不傻","date":"2020-08-05 08:57","category":"头条","author_name":"一思悠然","url":"https://mini.eastday.com/mobile/200805085751006.html","thumbnail_pic_s":"https://07imgmini.eastday.com/mobile/20200805/20200805085751_f7db361f59dce009398fbc2eb6cb4616_5_mwpm_03200403.jpg","thumbnail_pic_s02":"http://07imgmini.eastday.com/mobile/20200805/20200805085751_f7db361f59dce009398fbc2eb6cb4616_3_mwpm_03200403.jpg","thumbnail_pic_s03":"http://07imgmini.eastday.com/mobile/20200805/20200805085751_f7db361f59dce009398fbc2eb6cb4616_2_mwpm_03200403.jpg"},{"uniquekey":"b10b9d56383f75650fc54566112cf161","title":"驻美大使崔天凯：中方不希望与美紧张关系在互关领馆后进一步升级","date":"2020-08-05 08:53","category":"头条","author_name":"纵相新闻","url":"https://mini.eastday.com/mobile/200805085347691.html","thumbnail_pic_s":"https://05imgmini.eastday.com/mobile/20200805/20200805085347_b7d58fbffd228bbb23a30b011b7c629a_1_mwpm_03200403.jpg"},{"uniquekey":"2954e669cdbacdd24459930048659296","title":"肖战是如何一步步断送自己前程的","date":"2020-08-05 08:52","category":"头条","author_name":"柠重呀","url":"https://mini.eastday.com/mobile/200805085259948.html","thumbnail_pic_s":"https://06imgmini.eastday.com/mobile/20200805/20200805085259_9bb88533829c145da76a295a0c43b1ae_4_mwpm_03200403.jpg","thumbnail_pic_s02":"http://06imgmini.eastday.com/mobile/20200805/20200805085259_9bb88533829c145da76a295a0c43b1ae_2_mwpm_03200403.jpg","thumbnail_pic_s03":"http://06imgmini.eastday.com/mobile/20200805/20200805085259_9bb88533829c145da76a295a0c43b1ae_9_mwpm_03200403.jpg"},{"uniquekey":"459393584fc39a39b54ed1765dcb7288","title":"被李云龙说中了，雷军的10周年演讲将决定小米的未来道路","date":"2020-08-05 08:46","category":"头条","author_name":"科技浪子","url":"https://mini.eastday.com/mobile/200805084625427.html","thumbnail_pic_s":"https://01imgmini.eastday.com/mobile/20200805/20200805084625_909cf28c10fccdd999c2aeba6452dbf5_1_mwpm_03200403.jpg","thumbnail_pic_s02":"http://01imgmini.eastday.com/mobile/20200805/20200805084625_909cf28c10fccdd999c2aeba6452dbf5_2_mwpm_03200403.jpg","thumbnail_pic_s03":"http://01imgmini.eastday.com/mobile/20200805/20200805084625_909cf28c10fccdd999c2aeba6452dbf5_3_mwpm_03200403.jpg"},{"uniquekey":"9c28973a85b37cdde75616cfe2db89d1","title":"新证据浮出水面 通用要求恢复对FCA诉讼","date":"2020-08-05 08:43","category":"头条","author_name":"中国经济网","url":"https://mini.eastday.com/mobile/200805084319640.html","thumbnail_pic_s":"https://01imgmini.eastday.com/mobile/20200805/20200805084319_6b2a20ad3e63d740b6cce83481d297ff_1_mwpm_03200403.jpg"},{"uniquekey":"bde32719f96833e3b6bcc4959be3b62e","title":"梅溪派出所开展第七次人口普查宣传活动","date":"2020-08-05 08:43","category":"头条","author_name":"平安梅溪","url":"https://mini.eastday.com/mobile/200805084303228.html","thumbnail_pic_s":"https://06imgmini.eastday.com/mobile/20200805/20200805084303_e59f574c64056faf8a5a13c4b50adf18_1_mwpm_03200403.jpg"}]
     */

    private String stat;
    private List<DataBean> data;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * uniquekey : 7ec7bccfd79a56702bc9fb1f6d94a5c2
         * title : 幽默笑话：小明至今单身，这和他奇葩的搭讪方式不无关系
         * date : 2020-08-05 10:05
         * category : 头条
         * author_name : 幽默笑话段子集锦
         * url : https://mini.eastday.com/mobile/200805100513953.html
         * thumbnail_pic_s : https://06imgmini.eastday.com/mobile/20200805/2020080510_a4ff0ab99e154125970f95bbb5d88dff_1752_cover_mwpm_03200403.jpg
         * thumbnail_pic_s02 : http://06imgmini.eastday.com/mobile/20200805/2020080510_886cd6148c7d439d8964d8dbd2639534_0492_cover_mwpm_03200403.jpg
         * thumbnail_pic_s03 : http://06imgmini.eastday.com/mobile/20200805/2020080510_440e4cded56343e4a05a8679741ee141_8166_cover_mwpm_03200403.jpg
         */

        private String uniquekey;
        private String title;
        private String date;
        private String category;
        private String author_name;
        private String url;
        private String thumbnail_pic_s;
        private String thumbnail_pic_s02;
        private String thumbnail_pic_s03;

        public String getUniquekey() {
            return uniquekey;
        }

        public void setUniquekey(String uniquekey) {
            this.uniquekey = uniquekey;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getAuthor_name() {
            return author_name;
        }

        public void setAuthor_name(String author_name) {
            this.author_name = author_name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getThumbnail_pic_s() {
            return thumbnail_pic_s;
        }

        public void setThumbnail_pic_s(String thumbnail_pic_s) {
            this.thumbnail_pic_s = thumbnail_pic_s;
        }

        public String getThumbnail_pic_s02() {
            return thumbnail_pic_s02;
        }

        public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
            this.thumbnail_pic_s02 = thumbnail_pic_s02;
        }

        public String getThumbnail_pic_s03() {
            return thumbnail_pic_s03;
        }

        public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
            this.thumbnail_pic_s03 = thumbnail_pic_s03;
        }
    }
}
