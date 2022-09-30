[![](https://jitpack.io/v/huangqiqiang/CoreApp.svg)](https://jitpack.io/#huangqiqiang/CoreApp)

[javac](https://javadoc.jitpack.io/com/github/huangqiqiang/CoreApp/1.0.52/javadoc/)

#### 参考的开源库

[权限适配](https://github.com/soulqw/SoulPermission)

[状态栏适配]( https://github.com/gyf-dev/ImmersionBar)

application 可与通过 ContentProvider来获取(不知道算不算是ipc 的机制)
这样可以避免需要在application中执行init方法

# 状态设置

1. 设置状态栏模式 application 中 CoreConfig.Companion.get().setStatusMode(ToolBarMode.DARK_MODE);

2. 设置 状态栏信息

    1. 重写color
       <color name="toolbar_status_color">#D92129</color>
       <color name="toolbar_bg_color">#D92129</color>
       <color name="toolbar_status_left_image_color">@color/white</color>
       <color name="toolbar_text_color">@color/white</color>