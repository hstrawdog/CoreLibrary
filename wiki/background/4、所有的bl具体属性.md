## 属性菜单
### [shape类](#jump1)
### [selector类](#jump2)
### [textColor的selector状态](#jump3)
### [buttonDrawable状态](#jump4)
### [动画属性](#jump5)
### [其他属性(sdk21及以上的手机才支持)](#jump6)
### [bl_multi_*属性特别说明](#jump7)
### [textView颜色渐变](#jump8)

## 具体属性内容
### <h2 id="jump1">shape类</h2>
支持shape的所有属性。  

| 名称 | 类型 |
|---|---|
|bl_shape|rectangle、oval、line、ring(暂时不支持)|
|bl_shape_alpha|drawable透明度，取值范围0-1|
|bl_solid_color|color|
|bl_corners_radius|dimension|
|bl_corners_bottomLeftRadius|dimension|
|bl_corners_bottomRightRadius|dimension|
|bl_corners_topLeftRadius|dimension|
|bl_corners_topRightRadius|dimension|
|bl_corners_leftRadius|dimension|
|bl_corners_topRadius|dimension|
|bl_corners_rightRadius|dimension|
|bl_corners_bottomRadius|dimension|
|bl_gradient_angle|integer|
|bl_gradient_centerX|float|
|bl_gradient_centerY|float|
|bl_gradient_centerColor|color|
|bl_gradient_endColor|color|
|bl_gradient_startColor|color|
|bl_gradient_gradientRadius|dimension|
|bl_gradient_type|linear、radial、sweep|
|bl_gradient_useLevel|boolean|
|bl_size_width|dimension|
|bl_size_height|dimension|
|bl_stroke_width|dimension|
|bl_stroke_color|color|
|bl_stroke_dashWidth|dimension|
|bl_stroke_dashGap|dimension|
|bl_position|left、right、top、bottom 设置drawable位于view的指定位置|
|bl_stroke_position|left、right、top、bottom 指定某个方向的边框进行显示|
|bl_function|增加默认点击事件，参数为方法名，支持父类方法，暂时只支持无参方法|
|bl_checkable_gradient_angle|integer|
|bl_checkable_gradient_centerX|float|
|bl_checkable_gradient_centerY|float|
|bl_checkable_gradient_centerColor|color|
|bl_checkable_gradient_endColor|color|
|bl_checkable_gradient_startColor|color|
|bl_checkable_gradient_gradientRadius|dimension|
|bl_unCheckable_gradient_type|linear、radial、sweep|
|bl_unCheckable_gradient_useLevel|boolean|
|bl_unCheckable_gradient_angle|integer|
|bl_unCheckable_gradient_centerX|float|
|bl_unCheckable_gradient_centerY|float|
|bl_unCheckable_gradient_centerColor|color|
|bl_unCheckable_gradient_endColor|color|
|bl_unCheckable_gradient_startColor|color|
|bl_unCheckable_gradient_gradientRadius|dimension|
|bl_unCheckable_gradient_type|linear、radial、sweep|
|bl_unCheckable_gradient_useLevel|boolean|
|bl_checked_gradient_angle|integer|
|bl_checked_gradient_centerX|float|
|bl_checked_gradient_centerY|float|
|bl_checked_gradient_centerColor|color|
|bl_checked_gradient_endColor|color|
|bl_checked_gradient_startColor|color|
|bl_checked_gradient_gradientRadius|dimension|
|bl_checked_gradient_type|linear、radial、sweep|
|bl_checked_gradient_useLevel|boolean|
|bl_unChecked_gradient_angle|integer|
|bl_unChecked_gradient_centerX|float|
|bl_unChecked_gradient_centerY|float|
|bl_unChecked_gradient_centerColor|color|
|bl_unChecked_gradient_endColor|color|
|bl_unChecked_gradient_startColor|color|
|bl_unChecked_gradient_gradientRadius|dimension|
|bl_unChecked_gradient_type|linear、radial、sweep|
|bl_unChecked_gradient_useLevel|boolean|
|bl_enabled_gradient_angle|integer|
|bl_enabled_gradient_centerX|float|
|bl_enabled_gradient_centerY|float|
|bl_enabled_gradient_centerColor|color|
|bl_enabled_gradient_endColor|color|
|bl_enabled_gradient_startColor|color|
|bl_enabled_gradient_gradientRadius|dimension|
|bl_enabled_gradient_type|linear、radial、sweep|
|bl_checked_gradient_useLevel|boolean|
|bl_unEnabled_gradient_angle|integer|
|bl_unEnabled_gradient_centerX|float|
|bl_unEnabled_gradient_centerY|float|
|bl_unEnabled_gradient_centerColor|color|
|bl_unEnabled_gradient_endColor|color|
|bl_unEnabled_gradient_startColor|color|
|bl_unEnabled_gradient_gradientRadius|dimension|
|bl_unEnabled_gradient_type|linear、radial、sweep|
|bl_unEnabled_gradient_useLevel|boolean|
|bl_selected_gradient_angle|integer|
|bl_selected_gradient_centerX|float|
|bl_selected_gradient_centerY|float|
|bl_selected_gradient_centerColor|color|
|bl_selected_gradient_endColor|color|
|bl_selected_gradient_startColor|color|
|bl_selected_gradient_gradientRadius|dimension|
|bl_selected_gradient_type|linear、radial、sweep|
|bl_selected_gradient_useLevel|boolean|
|bl_unSelected_gradient_angle|integer|
|bl_unSelected_gradient_centerX|float|
|bl_unSelected_gradient_centerY|float|
|bl_unSelected_gradient_centerColor|color|
|bl_unSelected_gradient_endColor|color|
|bl_unSelected_gradient_startColor|color|
|bl_unSelected_gradient_gradientRadius|dimension|
|bl_unSelected_gradient_type|linear、radial、sweep|
|bl_unSelected_gradient_useLevel|boolean|
|bl_pressed_gradient_angle|integer|
|bl_pressed_gradient_centerX|float|
|bl_pressed_gradient_centerY|float|
|bl_pressed_gradient_centerColor|color|
|bl_pressed_gradient_endColor|color|
|bl_pressed_gradient_startColor|color|
|bl_pressed_gradient_gradientRadius|dimension|
|bl_pressed_gradient_type|linear、radial、sweep|
|bl_pressed_gradient_useLevel|boolean|
|bl_unPressed_gradient_angle|integer|
|bl_unPressed_gradient_centerX|float|
|bl_unPressed_gradient_centerY|float|
|bl_unPressed_gradient_centerColor|color|
|bl_unPressed_gradient_endColor|color|
|bl_unPressed_gradient_startColor|color|
|bl_unPressed_gradient_gradientRadius|dimension|
|bl_unPressed_gradient_type|linear、radial、sweep|
|bl_unPressed_gradient_useLevel|boolean|
|bl_focused_gradient_angle|integer|
|bl_focused_gradient_centerX|float|
|bl_focused_gradient_centerY|float|
|bl_focused_gradient_centerColor|color|
|bl_focused_gradient_endColor|color|
|bl_focused_gradient_startColor|color|
|bl_focused_gradient_gradientRadius|dimension|
|bl_focused_gradient_type|linear、radial、sweep|
|bl_focused_gradient_useLevel|boolean|
|bl_unFocused_gradient_angle|integer|
|bl_unFocused_gradient_centerX|float|
|bl_unFocused_gradient_centerY|float|
|bl_unFocused_gradient_centerColor|color|
|bl_unFocused_gradient_endColor|color|
|bl_unFocused_gradient_startColor|color|
|bl_unFocused_gradient_gradientRadius|dimension|
|bl_unFocused_gradient_type|linear、radial、sweep|
|bl_unFocused_gradient_useLevel|boolean|. 

### <h2 id="jump2">selector类</h2>
支持selector的所有属性：

| 名称 | 类型 |
|---|---|
|bl_checkable_drawable|color、reference|
|bl_checked_drawable|color、reference|
|bl_enabled_drawable|color、reference|
|bl_selected_drawable|color、reference|
|bl_pressed_drawable|color、reference|
|bl_focused_drawable|color、reference|
|bl_focused_hovered|color、reference|
|bl_focused_activated|color、reference|
|bl_unCheckable_drawable|color、reference|
|bl_unChecked_drawable|color、reference|
|bl_unEnabled_drawable|color、reference|
|bl_unSelected_drawable|color、reference|
|bl_unPressed_drawable|color、reference|
|bl_unFocused_drawable|color、reference|
|bl_unFocused_hovered|color、reference|
|bl_unFocused_activated|color、reference|
|bl_multi_selector1| String|
|bl_multi_selector2| String|
|bl_multi_selector3| String|
|bl_multi_selector4| String|
|bl_multi_selector5| String|
|bl_multi_selector6| String|

### <h2 id="jump3">textColor的selector状态</h2>
类似于background的selector，可以直接设置文字各个点击状态的颜色。  

| 名称 | 类型 |
|---|---|
|bl_checkable_textColor|color|
|bl_checked_textColor|color|
|bl_enabled_textColor|color|
|bl_selected_textColor|color|
|bl_pressed_textColor|color|
|bl_focused_textColor|color|
|bl_activated_textColor|color|
|bl_active_textColor|color|
|bl_expanded_textColor|color|
|bl_unCheckable_textColor|color|
|bl_unChecked_textColor|color|
|bl_unEnabled_textColor|color|
|bl_unSelected_textColor|color|
|bl_unPressed_textColor|color|
|bl_unFocused_textColor|color|
|bl_unActivated_textColor|color|
|bl_unActive_textColor|color|
|bl_unExpanded_textColor|color|
|bl_multi_text_selector1| String|
|bl_multi_text_selector2| String|
|bl_multi_text_selector3| String|
|bl_multi_text_selector4| String|
|bl_multi_text_selector5| String|
|bl_multi_text_selector6| String|


### <h2 id="jump4">buttonDrawable状态</h2>
checkbox、radiobutton的属性，有时不是设置在background中，而是需要设置buttonDrawble，具体如下。  

| 名称 | 类型 |
|---|---|
|bl_checked_button_drawable|color、reference|
|bl_unChecked_button_drawable|color、reference|

### <h2 id="jump5">动画属性</h2>
| 名称 | 类型 |
|---|---|
|bl_oneshot|是否只显示一次|
|bl_anim_auto_start|是否自动播放|
|bl_duration|每一帧动画时长|
|bl_duration_item0|第0帧动画时长|
|bl_duration_item1|第1帧动画时长|
|bl_duration_item2|第2帧动画时长|
|bl_duration_item3|第3帧动画时长|
|.|.|
|.|.|
|.|.|
|bl_duration_item12|第12帧动画时长|
|bl_duration_item13|第13帧动画时长|
|bl_duration_item14|第14帧动画时长|
|bl_frame_drawable_item0|第0帧动画|
|bl_frame_drawable_item1|第1帧动画|
|bl_frame_drawable_item2|第2帧动画|
|bl_frame_drawable_item3|第3帧动画|
|.|.|
|.|.|
|.|.|
|bl_frame_drawable_item12|第12帧动画|
|bl_frame_drawable_item13|第13帧动画|
|bl_frame_drawable_item14|第14帧动画|

### <h2 id="jump6">其他属性(除bl_ripple_enable，bl_ripple_color外，其他属性从1.6.1开始支持sdk21以下版本)</h2>
| 名称 | 类型 |备注|
|---|---|---|
|bl_ripple_enable|boolean|是否开启点击的水波纹效果，只支持sdk21以上版本|
|bl_ripple_color|color|水波纹颜色（如果开启，一定要有这个属性能生效，只支持sdk21以上版本）|
|bl_checkable_stroke_color| color| 边框状态的属性，如果在版本1.6.1之前，sdk21以下，会没有效果，默认固定边框色取stroke_color的值，1.6.1开始，可以生效|
|bl_checked_stroke_color| color| |
|bl_enabled_stroke_color| color| |
|bl_selected_stroke_color| color| |
|bl_pressed_stroke_color| color| |
|bl_focused_stroke_color| color| |
|bl_unCheckable_stroke_color| color| |
|bl_unChecked_stroke_color| color| |
|bl_unEnabled_stroke_color| color| |
|bl_unSelected_stroke_color| color| |
|bl_unPressed_stroke_color| color| |
|bl_unFocused_stroke_color| color| |
|bl_checkable_solid_color| color| 填充颜色的属性，如果在版本1.6.1之前，sdk21以下，会没有效果，默认固定边框色取stroke_color的值，1.6.1开始，可以生效|
|bl_checked_solid_color| color| |
|bl_enabled_solid_color| color| |
|bl_selected_solid_color| color| |
|bl_pressed_solid_color| color| |
|bl_focused_solid_color| color| |
|bl_unCheckable_solid_color| color| |
|bl_unChecked_solid_color| color| |
|bl_unEnabled_solid_color| color| |
|bl_unSelected_solid_color| color| |
|bl_unPressed_solid_color| color| |
|bl_unFocused_solid_color| color| |

### <h2 id="jump7">bl_multi_*属性特别说明</h2>
通常我们一个属性代表一个状态，但是selector使用的时候会出现一个item内有多个状态，同时设置一个属性，这就引入了bl_multi的属性。  
bl_multi内容规则为以",
"为分隔符，最后一项为资源id的名字，可选状态为:  
- state_checkable
- state_checked
- state_enabled
- state_selected
- state_pressed
- state_focused
- state_hovered
- state_activated  
默认为true，如果为false则在前面加上"-"即可，例如-state_checkable。  
bl_multi_text_selector最后一个属性只能为颜色，暂时不支持androidsdk自带颜色，必须存在于当前module的value文件夹中。  
bl_multi_selector最后一个属性可以为颜色，也可以为资源id名。  

效果例子如下：

![](https://raw.githubusercontent.com/JavaNoober/BackgroundLibrary/master/images/mulit_text.gif)

通常写法：  

    <selector xmlns:android="http://schemas.android.com/apk/res/android">
        <item android:state_pressed="true" android:state_enabled="true"
            android:color="#EE0000" />
        <item android:state_pressed="false" android:state_enabled="false"
            android:color="#DDA0DD" />
            <item android:state_pressed="false" android:state_enabled="true"
            android:color="#DDA0DD" />
    </selector>

bl_multi写法：  

        <com.noober.background.view.BLTextView
            android:padding="0dp"
            android:layout_width="300dp"
            android:layout_height="36dp"
            android:layout_marginTop="15dp"
            android:textColor="@android:color/black"
            android:clickable="true"
            android:focusable="true"
            android:gravity="center"
            android:text="textView一条属性多个状态"
            android:textSize="18dp"
            android:textStyle="bold"
            app:bl_multi_text_selector1="state_pressed,state_enabled,#EE0000"
            app:bl_multi_text_selector2="-state_pressed,-state_enabled,#DDA0DD"
            app:bl_multi_text_selector3="-state_pressed,state_enabled,#DDA0DD"/>


### <h2 id="jump8">文字颜色渐变</h2>

| 名称 | 类型 |备注|
|---|---|---|
|bl_text_gradient_startColor|int||
|bl_text_gradient_endColor|int||
|bl_text_gradient_orientation|int|vertical、horizontal|
