


utils层：工具类层，通用的、与业务无关的，可以独立出来，可供其他项目使用；

tools层：与某些业务有关，通用性只限于某几个业务类之间；

manager层：通用业务处理层，它有如下特征，对第三方平台封装的层，预处理返回结果及转化异常信息； 对 Service 层通用能力的下沉，如缓存方案、中间件通用处理；与 DAO 层交互，对多个 DAO 的组合复用。



service层：业务处理层，在大系统中，该层比较复杂，故可抽取出通用处理层（manager层），并且一个service层可以对应多个manager层，但小系统的话，往往没必要抽取出manager层，一个service层足够了。


helper层：辅助类层，一般是一些功能辅助，如SqlHelper封装数据库连接操作提供数据库操作对象，ConfigHelper帮助创建配置信息用于模块初始化构建，其实作用与工具类很像，但没有工具类通用性好。





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





