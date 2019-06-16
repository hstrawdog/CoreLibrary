[![](https://jitpack.io/v/huangqiqiang/CoreApp.svg)](https://jitpack.io/#huangqiqiang/CoreApp)

[javac](https://javadoc.jitpack.io/com/github/huangqiqiang/CoreApp/1.0.52/javadoc/)
##### 规范
1. 接口可以使用IXxx开头
2. 接口实现可以参考 XxxImpl
2. 基类统一参考使用BaseXxx

1. 尽量使用单个单词 如果无法表用小写单词_小写单词 如core_app
 所有封装的最上层都是接口 提高封装的可拓展性

1. 遵循设计模式七大原则
    - 单一职责
    - 接口隔离
    - 开放封闭
    - 依赖倒转
    - 迪米特法则
    - 里氏代换
1. 设计模式
    - 单利模式
    - builder模式/构建者模式
    - 工厂模式
    - 外观模式/门面模式
    - 代理模式
    - 命令模式
1. 类的结构
    - 常量
    - 变量
    - 静态方法
    - 构造方法
    - 父类方法生命周期
    - 重载/重写
    - 实现方法
    - 自定义方法

#### 参考的开源库
  [权限适配](https://github.com/soulqw/SoulPermission)

  [状态栏适配]( https://github.com/gyf-dev/ImmersionBar)


application 可与通过 ContentProvider来获取(不知道算不算是ipc 的机制)
这样可以避免需要在application中执行init方法