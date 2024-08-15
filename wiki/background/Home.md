## BackgroundLibrary
A framework for directly generating shape through Tags, no need to write shape.xml again（通过标签直接生成shape，无需再写shape.xml）  


![](https://user-gold-cdn.xitu.io/2018/9/11/165c6e5c0cff0548?w=681&h=233&f=png&s=31240)


[English.md](https://github.com/JavaNoober/BackgroundLibrary/blob/master/README-EN.md)

作为一个android程序员，对于shape、selector这两个标签一定不陌生。每当UI设计师给我们设计出一个个button背景的时候，我们就需要去drawable文件夹下去新建一个bg_xxx.xml，然后很多时候区别仅仅是一个边框的颜色或者填充的颜色。这就导致了很多非常相似的.xml文件产生。
网上之前也有了一种通过自定义View，在xml中通过设置属性达到shape效果的控件。但是这种自定义的控件不太灵活，归根到底是一个自定义的button，如果我想改造项目的话就得去替换原有的button或者textView。接下来就给大家提供一种更加简单的方式：  
**无需自定义View，直接添加属性便可以实现shape、selector效果**。

