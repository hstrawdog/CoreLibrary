更新日志





- ui层:  Activity与Fragment 或者ViewModel 对应MVVM   VM 

- utils层：工具类层，通用的、与业务无关的，可以独立出来，可供其他项目使用；

- tools层：与某些业务有关，通用性只限于某几个业务类之间；

- manager层：通用业务处理层，它有如下特征，对第三方平台封装的层，预处理返回结果及转化异常信息； 对 Service 层通用能力的下沉，如缓存方案、中间件通用处理；与 DAO 层交互，对多个 DAO 的组合复用。

- service层：业务处理层，在大系统中，该层比较复杂，故可抽取出通用处理层（manager层），并且一个service层可以对应多个manager层，但小系统的话，往往没必要抽取出manager层，一个service层足够了。

- helper层：辅助类层，一般是一些功能辅助，如SqlHelper封装数据库连接操作提供数据库操作对象，ConfigHelper帮助创建配置信息用于模块初始化构建，其实作用与工具类很像，但没有工具类通用性好。

- repository层:  数据源/模型  对应数据模型  对应 model 层

- custom层:  自定义View 



# Shape 替代方案
- 参考[https://github.com/JavaNoober/BackgroundLibrary/tree/master ]   采用 自定View方案 预览界面也能实现效果 
- 也可以参考根目录中的wiki
- xml中代码提示参考[https://github.com/JavaNoober/BackgroundLibrary/wiki/7%E3%80%81%E5%A6%82%E4%BD%95%E8%BF%9B%E8%A1%8C%E4%BB%A3%E7%A0%81%E6%8F%90%E7%A4%BA]
- 移除点击事件以及动画

# 基类
 - 默认Activity与Fragment以及Dialog 继承  BaseViewBidingActivity / BaseViewBidingFragment /  BaseDialogFragment 实现ViewBiding的使用 
 - Activity与Fragment 也支持 BaseDataBinding 
 - MVVM  继承 BaseVm


# LiveData  使用 MutableResult
解决 数据倒灌问题 参考 https://github.com/KunMinX/UnPeek-LiveData




# 工具  utils
 - ImageLoadUtils  图片请求框架  类中使用的是Glide



# 屏幕适配
 - 布局中不在使用 dp与sp  改使用dimen文件中的 如  @dimen/x1    系统会自动选取合适的大小  
 - 所有字体  统一使用    style="@style/def_text.26.333"
   
# 相册  Album模块

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

# 使用方法

## Maven引入



## 打包 aar
```kotlin
./gradlew Core:assembleRelease
```


使用aar 将[build.gradle](Core%2Fbuild.gradle) 使用的依赖复制过去


# 参考的开源库

[权限适配](https://github.com/soulqw/SoulPermission)

[状态栏适配]( https://github.com/gyf-dev/ImmersionBar)





