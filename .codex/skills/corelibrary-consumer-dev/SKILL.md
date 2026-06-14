---
name: corelibrary-consumer-dev
description: Use when developing or reviewing Android projects that depend on CoreLibrary/Core; prefer reusing Core infrastructure, keep business projects focused on business logic, and identify reusable capabilities that should be moved into Core.
---

# CoreLibrary Consumer Dev

用于依赖 `CoreLibrary/Core` 的业务项目开发与评审。

## 目标

- 优先复用 Core 通用基础能力
- 避免在业务项目重复实现工具类、UI 基座、权限、文件、图片、网络、列表、Compose 适配
- 业务项目只保留业务语义
- 发现可复用能力时，优先建议沉淀到 Core

## 适用场景

- Android 业务项目接入或使用 CoreLibrary
- 编写页面、组件、工具方法、网络、权限、图片、文件、列表、Compose UI
- 评审业务项目是否重复造轮子
- 判断代码应留在业务项目，还是沉淀到 Core

## 工作原则

1. 优先查找并复用 Core 已有能力
2. 业务项目只保留业务语义
3. 通用能力应下沉到 Core
4. 不复制 Core 已有工具
5. 不修改 Core public API，除非已评估兼容性
6. 新增通用能力时，优先考虑提交到 Core

## Core 可复用范围

- `ui`：Activity / Fragment / Dialog / ViewBinding / DataBinding / ViewModel 基座
- `ui/compose`：Compose 页面基座、RootView、Toolbar、375dp 适配、xdp
- `utils`：文件、图片、文本、加密、日志、线程、状态栏、键盘、设备、Intent、时间、SP
- `recycle` / `widget`：列表、Banner、Indicator、Divider、PTR、WheelView、通用控件
- `album`：相册选择、目录、预览、媒体实体
- `permission`：权限请求与结果封装
- `net`：OkHttp / Retrofit 基础封装、下载回调、参数兼容
- `glide`：图片加载、圆角、裁剪等封装
- `msa`：Activity result 管理
- `background`：Shape / Selector / Drawable 构建

## Compose 约束

- 设计基线：375dp
- 设计稿标注多少 `dp`，代码写多少 `xdp`
- 布局尺寸、间距、圆角优先使用 `xdp`
- 字号最终输出必须是 `sp`，并保留系统 `fontScale`
- 禁止 `750px` 心智
- 禁止手动将设计图 `px` 除以 2
- 新 Compose 页面优先复用 Core Compose 基座和适配体系

## 判断规则

新增代码前先判断：

1. 是否已有 Core 能力可用
2. 是否属于业务逻辑
3. 是否具备跨项目复用价值
4. 是否应该沉淀到 Core
5. 是否存在重复工具类 / 重复扩展函数 / 重复 UI 基座

## 处理策略

- 纯业务逻辑：留在业务项目
- 通用工具能力：优先使用 Core；没有则建议沉淀 Core
- 通用 UI 能力：优先使用 Core 组件或 Compose 基座
- 项目定制逻辑：留在业务项目，不污染 Core
- 临时 demo / 单项目 hack：禁止沉淀 Core

## 禁止

- 在业务项目重复写 Core 已有工具
- 把业务语义写进 Core
- 为单项目需求污染 Core API
- 随意修改 Core public API
- 在业务项目绕过 Core 适配体系
- Compose 中继续使用 `750px` / `px÷2` 心智
- 引入重型依赖替代 Core 已有轻量封装

## 输出要求

- 先给结论
- 再给可执行代码
- 明确说明：使用 Core / 留在业务项目 / 建议沉淀 Core
- 涉及 Core public API 时说明兼容性风险
- 输出简短，避免废话
