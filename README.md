更新日志






utils层：工具类层，通用的、与业务无关的，可以独立出来，可供其他项目使用；

tools层：与某些业务有关，通用性只限于某几个业务类之间；

manager层：通用业务处理层，它有如下特征，对第三方平台封装的层，预处理返回结果及转化异常信息； 对 Service 层通用能力的下沉，如缓存方案、中间件通用处理；与 DAO 层交互，对多个 DAO 的组合复用。

service层：业务处理层，在大系统中，该层比较复杂，故可抽取出通用处理层（manager层），并且一个service层可以对应多个manager层，但小系统的话，往往没必要抽取出manager层，一个service层足够了。

helper层：辅助类层，一般是一些功能辅助，如SqlHelper封装数据库连接操作提供数据库操作对象，ConfigHelper帮助创建配置信息用于模块初始化构建，其实作用与工具类很像，但没有工具类通用性好。

repository层:  数据源/模型  




# 工具  utils 
 - ImageLoadUtils  图片请求框架  类中使用的是Glide



# 屏幕适配
 - 布局中不在使用 dp与sp  改使用dimen文件中的 如  @dimen/x1    系统会自动选取合适的大小  

    
# 相册  album 

# 请求 Net
 - OkHttpImpl 依赖OkHttp的网络请求与文件上传 


# 权限适配 
 - SysPermissionsUtils 

# 状态设置

1. 设置状态栏模式 application 中 CoreConfig.Companion.get().setStatusMode(ToolBarMode.DARK_MODE);

2. 设置 状态栏信息

    1. 重写color
       <color name="toolbar_status_color">#D92129</color>
       <color name="toolbar_bg_color">#D92129</color>
       <color name="toolbar_status_left_image_color">@color/white</color>
       <color name="toolbar_text_color">@color/white</color>

# 打包 aar

./gradlew Core:assembleRelease

使用aar 将[build.gradle](Core%2Fbuild.gradle) 使用的依赖复制过去


#### 参考的开源库

[权限适配](https://github.com/soulqw/SoulPermission)

[状态栏适配]( https://github.com/gyf-dev/ImmersionBar)






