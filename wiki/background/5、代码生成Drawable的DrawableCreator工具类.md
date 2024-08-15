所有的属性都提供了**set方法**去通过代码设置，具体见[DrawableCreator.Build](https://github.com/JavaNoober/BackgroundLibrary/blob/develop/library/src/main/java/com/noober/background/drawable/DrawableCreator.java)
使用方法如下：

    //设置button圆角背景
    Drawable drawable = new DrawableCreator.Builder().setCornersRadius(dip2px(20))
                    .setGradientAngle(0).setGradientColor(Color.parseColor("#63B8FF"), Color.parseColor("#4F94CD")).build();
    btn.setBackground(drawable);
    //文字点击变色
    tvTest1.setClickable(true);//由于Android源码的原因，必须调用，否则不生效
    ColorStateList colors = new DrawableCreator.Builder().setPressedTextColor(Color.RED).setUnPressedTextColor(Color.BLUE).buildTextColor();
    tvTest1.setTextColor(colors);