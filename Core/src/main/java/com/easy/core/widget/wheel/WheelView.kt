package com.easy.core.widget.wheel

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import  com.easy.core.utils.ScreenUtils
import com.easy.core.widget.wheel.WheelView.DividerConfig
import com.easy.core.widget.wheel.WheelView.OnItemSelectListener
import com.easy.core.widget.wheel.model.WheelItem
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

/**
 * https://github.com/gzu-liyujiang/AndroidPicker commits 335
 * 3D滚轮控件，参阅：http://blog.csdn.net/qq_22393017/article/details/59488906
 *
 *
 * Author:李玉江[QQ:1032694760]
 * DateTime:2015/12/15 09:45 基于ScrollView，参见https://github.com/wangjiegulu/WheelView
 * DateTime:2017/01/07 21:37 基于ListView，参见https://github.com/venshine/WheelView
 * DateTime:2017/04/28 21:10 基于View，参见https://github.com/weidongjian/androidWheelView
 * Builder:Android Studio
 *
 * @see WheelItem
 *
 * @see DividerConfig
 *
 * @see OnItemSelectListener
 */
class WheelView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private var handler: MessageHandler? = null
    private var gestureDetector: GestureDetector? = null
    private var onItemSelectListener: OnItemSelectListener? = null
    private var onWheelListener: OnWheelListener? = null

    //附加单位是否仅仅只显示在选中项后面
    private var onlyShowCenterLabel = true
    private var mFuture: ScheduledFuture<*>? = null

    //未选项画笔
    private var paintOuterText: Paint? = null

    //选中项画笔
    private var paintCenterText: Paint? = null

    //分割线画笔
    private var paintIndicator: Paint? = null

    //阴影画笔
    private var paintShadow: Paint? = null

    //所有选项
    private val items: MutableList<WheelItem>? = ArrayList()

    //附加单位
    private var label: String? = null

    //最大的文字宽
    private var maxTextWidth = 0

    //最大的文字高
    private var maxTextHeight = 0

    //文字倾斜度
    private var textSkewXOffset = 0

    //文字大小，单位为sp
    private var textSize = TEXT_SIZE

    //每行高度
    private var itemHeight = 0f

    //字体样式
    private var typeface = Typeface.DEFAULT

    //未选项文字颜色
    private var textColorOuter = TEXT_COLOR_NORMAL

    //选中项文字颜色
    private var textColorCenter = TEXT_COLOR_FOCUS
    private var dividerConfig = DividerConfig()

    //条目间距倍数，可用来设置上下间距
    private var lineSpaceMultiplier = LINE_SPACE_MULTIPLIER

    //文字的左右边距,单位为px
    private var textPadding = TEXT_PADDING

    //循环滚动
    private var isLoop = true

    //第一条线Y坐标值
    private var firstLineY = 0f

    //第二条线Y坐标
    private var secondLineY = 0f

    //滚动总高度y值
    private var totalScrollY = 0f

    //初始化默认选中项
    private var initPosition = -1

    //选中项的索引
    private var selectedIndex = 0
    private var preCurrentIndex = 0

    //绘制几个条目
    private var visibleItemCount = ITEM_OFF_SET * 2 + 1

    //控件高度
    private var measuredHeight = 0.0

    //控件宽度
    private var measuredWidth = 0.0

    //半径
    private var radius = 0
    private var offset = 0
    private var previousY = 0f
    private var startTime: Long = 0
    private var widthMeasureSpec = 0
    private var gravity = Gravity.CENTER

    //中间选中文字开始绘制位置
    private var drawCenterContentStart = 0

    //非中间文字开始绘制位置
    private var drawOutContentStart = 0

    //偏移量
    private var centerContentOffset = 0f

    //使用比重还是包裹内容？
    private var useWeight = true

    //条目内容过长时是否自动减少字号来适配
    private var textSizeAutoFit = true

    /**
     * 设置显示的选项个数，必须是奇数
     */
    fun setVisibleItemCount(count: Int) {
        require(count % 2 != 0) { "must be odd" }
        if (count != visibleItemCount) {
            visibleItemCount = count
        }
    }

    /**
     * 设置是否禁用循环滚动
     */
    fun setCycleDisable(cycleDisable: Boolean) {
        isLoop = !cycleDisable
    }

    /**
     * 设置滚轮个数偏移量
     */
    fun setOffset(@IntRange(from = 1, to = 5) offset: Int) {
        require(!(offset < 1 || offset > 5)) { "must between 1 and 5" }
        var count = offset * 2 + 1
        count += if (offset % 2 == 0) {
            offset
        } else {
            offset - 1
        }
        setVisibleItemCount(count)
    }

    fun getSelectedIndex(): Int {
        return selectedIndex
    }

    fun setSelectedIndex(index: Int) {
        if (items == null || items.isEmpty()) {
            return
        }
        val size = items.size
        if (index == 0 || index > 0 && index < size && index != selectedIndex) {
            initPosition = index
            totalScrollY = 0f //回归顶部，不然重设索引的话位置会偏移，就会显示出不对位置的数据
            offset = 0
            invalidate()
        }
    }

    fun setOnItemSelectListener(onItemSelectListener: OnItemSelectListener?) {
        this.onItemSelectListener = onItemSelectListener
    }

    @Deprecated("use {@link #setOnItemSelectListener(OnItemSelectListener)} instead")
    fun setOnWheelListener(listener: OnWheelListener?) {
        onWheelListener = listener
    }

    fun setItems(items: List<*>) {
        this.items!!.clear()
        for (item in items) {
            if (item is WheelItem) {
                this.items.add(item)
            } else if (item is CharSequence || item is Number) {
                this.items.add(StringItem(item.toString()))
            } else {
                throw IllegalArgumentException("please implements " + WheelItem::class.java.name)
            }
        }
        remeasure()
        invalidate()
    }

    fun setItems(items: List<*>, index: Int) {
        setItems(items)
        setSelectedIndex(index)
    }

    fun setItems(list: Array<String>) {
        setItems(Arrays.asList(*list))
    }

    fun setItems(list: List<String>, item: String?) {
        var index = list.indexOf(item)
        if (index == -1) {
            index = 0
        }
        setItems(list, index)
    }

    fun setItems(list: Array<String?>, index: Int) {
        setItems(Arrays.asList<String>(*list), index)
    }

    fun setItems(items: Array<String?>, item: String?) {
        setItems(Arrays.asList<String>(*items), item)
    }

    /**
     * 附加在右边的单位字符串
     */
    fun setLabel(label: String?, onlyShowCenterLabel: Boolean) {
        this.label = label
        this.onlyShowCenterLabel = onlyShowCenterLabel
    }

    fun setLabel(label: String?) {
        setLabel(label, true)
    }

    fun setGravity(gravity: Int) {
        this.gravity = gravity
    }

    fun setTextColor(@ColorInt colorNormal: Int, @ColorInt colorFocus: Int) {
        textColorOuter = colorNormal
        textColorCenter = colorFocus
        paintOuterText!!.color = colorNormal
        paintCenterText!!.color = colorFocus
    }

    fun setTextColor(@ColorInt color: Int) {
        textColorOuter = color
        textColorCenter = color
        paintOuterText!!.color = color
        paintCenterText!!.color = color
    }

    fun setTypeface(font: Typeface) {
        typeface = font
        paintOuterText!!.typeface = typeface
        paintCenterText!!.typeface = typeface
    }

    fun setTextSize(size: Float) {
        if (size > 0.0f) {
            textSize = (context.resources.displayMetrics.density * size).toInt()
            paintOuterText!!.textSize = textSize.toFloat()
            paintCenterText!!.textSize = textSize.toFloat()
        }
    }

    fun setTextSkewXOffset(textSkewXOffset: Int) {
        this.textSkewXOffset = textSkewXOffset
        if (textSkewXOffset != 0) {
            paintCenterText!!.textScaleX = 1.0f
        }
    }

    fun setDividerColor(@ColorInt color: Int) {
        dividerConfig.setColor(color)
        paintIndicator!!.color = color
    }

    @Deprecated("use {{@link #setDividerConfig(DividerConfig)} instead")
    fun setLineConfig(config: DividerConfig?) {
        setDividerConfig(config)
    }

    fun setDividerConfig(config: DividerConfig?) {
        if (null == config) {
            dividerConfig.setVisible(false)
            dividerConfig.setShadowVisible(false)
            return
        }
        dividerConfig = config
        paintIndicator!!.color = config.color
        paintIndicator!!.strokeWidth = config.thick
        paintIndicator!!.alpha = config.alpha
        paintShadow!!.color = config.shadowColor
        paintShadow!!.alpha = config.shadowAlpha
    }

    fun setLineSpaceMultiplier(@FloatRange(from = 2.0, to = 4.0) multiplier: Float) {
        lineSpaceMultiplier = multiplier
        judgeLineSpace()
    }

    /**
     * Use [.setTextPadding] instead
     */
    @Deprecated("")
    fun setPadding(padding: Int) {
        setTextPadding(padding)
    }

    fun setTextPadding(textPadding: Int) {
        this.textPadding = ScreenUtils.dip2px(context, textPadding.toFloat())
    }

    fun setUseWeight(useWeight: Boolean) {
        this.useWeight = useWeight
    }

    fun setTextSizeAutoFit(textSizeAutoFit: Boolean) {
        this.textSizeAutoFit = textSizeAutoFit
    }

    /**
     * 判断间距是否在有效范围内
     */
    private fun judgeLineSpace() {
        if (lineSpaceMultiplier < 1.5f) {
            lineSpaceMultiplier = 1.5f
        } else if (lineSpaceMultiplier > 4.0f) {
            lineSpaceMultiplier = 4.0f
        }
    }

    private fun initView(context: Context) {
        handler = MessageHandler(this)
        gestureDetector = GestureDetector(context, object : SimpleOnGestureListener() {
            override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
                scrollBy(velocityY)
                return true
            }
        })
        gestureDetector!!.setIsLongpressEnabled(false)
        initPaints()
        initDataForIDE()
    }

    private fun initPaints() {
        paintOuterText = Paint()
        paintOuterText!!.isAntiAlias = true
        paintOuterText!!.color = textColorOuter
        paintOuterText!!.typeface = typeface
        paintOuterText!!.textSize = textSize.toFloat()
        paintCenterText = Paint()
        paintCenterText!!.isAntiAlias = true
        paintCenterText!!.color = textColorCenter
        paintCenterText!!.textScaleX = 1.0f
        paintCenterText!!.typeface = typeface
        paintCenterText!!.textSize = textSize.toFloat()
        paintIndicator = Paint()
        paintIndicator!!.isAntiAlias = true
        paintIndicator!!.color = dividerConfig.color
        paintIndicator!!.strokeWidth = dividerConfig.thick
        paintIndicator!!.alpha = dividerConfig.alpha
        paintShadow = Paint()
        paintShadow!!.isAntiAlias = true
        paintShadow!!.color = dividerConfig.shadowColor
        paintShadow!!.alpha = dividerConfig.shadowAlpha
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    private fun initDataForIDE() {
        if (isInEditMode) {
            setItems(arrayOf("李玉江", "男", "贵州", "穿青人"))
        }
    }

    /**
     * 重新测量
     */
    private fun remeasure() {
        if (items == null) {
            return
        }
        measureTextWidthHeight()
        //半圆的周长
        val halfCircumference = (itemHeight * (visibleItemCount - 1)).toInt()
        //整个圆的周长除以PI得到直径，这个直径用作控件的总高度
        measuredHeight = (halfCircumference * 2 / Math.PI).toInt().toDouble()
        //求出半径
        radius = (halfCircumference / Math.PI).toInt()
        val params = layoutParams
        //控件宽度
        if (useWeight) {
            measuredWidth = MeasureSpec.getSize(widthMeasureSpec).toDouble()
        } else if (params != null && params.width > 0) {
            measuredWidth = params.width.toDouble()
        } else {
            measuredWidth = maxTextWidth.toDouble()
            if (textPadding < 0) {
                textPadding = ScreenUtils.dip2px(context, ITEM_PADDING)
            }
            measuredWidth += textPadding * 2
            if (!TextUtils.isEmpty(label)) {
                measuredWidth += obtainTextWidth(paintCenterText, label)
            }
        }
        //计算两条横线 和 选中项画笔的基线Y位置
        firstLineY = ((measuredHeight - itemHeight) / 2.0f).toFloat()
        secondLineY = ((measuredHeight + itemHeight) / 2.0f).toFloat()
        //初始化显示的item的position
        if (initPosition == -1) {
            initPosition = if (isLoop) {
                (items.size + 1) / 2
            } else {
                0
            }
        }
        preCurrentIndex = initPosition
    }

    /**
     * 计算最大length的Text的宽高度
     */
    private fun measureTextWidthHeight() {
        val rect = Rect()
        for (i in items!!.indices) {
            val s1 = obtainContentText(items[i])
            paintCenterText!!.getTextBounds(s1, 0, s1!!.length, rect)
            val textWidth = rect.width()
            if (textWidth > maxTextWidth) {
                maxTextWidth = textWidth
            }
            paintCenterText!!.getTextBounds("测试", 0, 2, rect)
            maxTextHeight = rect.height() + 2
        }
        itemHeight = lineSpaceMultiplier * maxTextHeight
    }

    /**
     * 平滑滚动的实现
     */
    private fun smoothScroll(actionType: Int) {
        cancelFuture()
        if (actionType == ACTION_FLING || actionType == ACTION_DRAG) {
            offset = ((totalScrollY % itemHeight + itemHeight) % itemHeight).toInt()
            offset = if (offset.toFloat() > itemHeight / 2.0f) { //如果超过Item高度的一半，滚动到下一个Item去
                (itemHeight - offset.toFloat()).toInt()
            } else {
                -offset
            }
        }
        //停止的时候，位置有偏移，不是全部都能正确停止到中间位置的，这里把文字位置挪回中间去
        val command = SmoothScrollTimerTask(this, offset)
        mFuture = Executors.newSingleThreadScheduledExecutor()
                .scheduleWithFixedDelay(command, 0, 10, TimeUnit.MILLISECONDS)
    }

    /**
     * 滚动惯性的实现
     */
    private fun scrollBy(velocityY: Float) {
        cancelFuture()
        val command = InertiaTimerTask(this, velocityY)
        mFuture = Executors.newSingleThreadScheduledExecutor()
                .scheduleWithFixedDelay(command, 0, VELOCITY_FLING.toLong(), TimeUnit.MILLISECONDS)
    }

    private fun cancelFuture() {
        if (mFuture != null && !mFuture!!.isCancelled) {
            mFuture!!.cancel(true)
            mFuture = null
        }
    }

    private fun itemSelectedCallback() {
        if (onItemSelectListener == null && onWheelListener == null) {
            return
        }
        postDelayed({
            if (onItemSelectListener != null) {
                onItemSelectListener!!.onSelected(selectedIndex)
            }
            if (onWheelListener != null) {
                onWheelListener!!.onSelected(true, selectedIndex, items!![selectedIndex].name)
            }
        }, 200L)
    }

    override fun onDraw(canvas: Canvas) {
        if (items == null || items.size == 0) {
            return
        }
        //可见项的数组
        @SuppressLint("DrawAllocation") val visibleItemStrings = arrayOfNulls<String>(visibleItemCount)
        //滚动的Y值高度除去每行的高度，得到滚动了多少个项，即change数
        val change = (totalScrollY / itemHeight).toInt()
        //滚动中实际的预选中的item(即经过了中间位置的item) ＝ 滑动前的位置 ＋ 滑动相对位置
        preCurrentIndex = initPosition + change % items.size
        if (!isLoop) { //不循环的情况
            if (preCurrentIndex < 0) {
                preCurrentIndex = 0
            }
            if (preCurrentIndex > items.size - 1) {
                preCurrentIndex = items.size - 1
            }
        } else { //循环
            if (preCurrentIndex < 0) { //举个例子：如果总数是5，preCurrentIndex ＝ －1，那么preCurrentIndex按循环来说，其实是0的上面，也就是4的位置
                preCurrentIndex = items.size + preCurrentIndex
            }
            if (preCurrentIndex > items.size - 1) { //同理上面,自己脑补一下
                preCurrentIndex = preCurrentIndex - items.size
            }
        }
        //跟滚动流畅度有关，总滑动距离与每个item高度取余，即并不是一格格的滚动，每个item不一定滚到对应Rect里的，这个item对应格子的偏移值
        val itemHeightOffset = totalScrollY % itemHeight
        // 设置数组中每个元素的值
        var counter = 0
        while (counter < visibleItemCount) {
            var index = preCurrentIndex - (visibleItemCount / 2 - counter) //索引值，即当前在控件中间的item看作数据源的中间，计算出相对源数据源的index值
            //判断是否循环，如果是循环数据源也使用相对循环的position获取对应的item值，如果不是循环则超出数据源范围使用""空白字符串填充，在界面上形成空白无数据的item项
            if (isLoop) {
                index = getLoopMappingIndex(index)
                visibleItemStrings[counter] = items[index].name
            } else if (index < 0) {
                visibleItemStrings[counter] = ""
            } else if (index > items.size - 1) {
                visibleItemStrings[counter] = ""
            } else {
                visibleItemStrings[counter] = items[index].name
            }
            counter++
        }
        //绘制中间两条横线
        if (dividerConfig.visible) {
            val ratio = dividerConfig.ratio
            canvas.drawLine((measuredWidth * ratio).toFloat(), firstLineY, (measuredWidth * (1 - ratio)).toFloat(), this.firstLineY, paintIndicator!!)
            canvas.drawLine((measuredWidth * ratio).toFloat(), secondLineY, (measuredWidth * (1 - ratio)).toFloat(), secondLineY, paintIndicator!!)
        }
        if (dividerConfig.shadowVisible) {
            paintShadow!!.color = dividerConfig.shadowColor
            paintShadow!!.alpha = dividerConfig.shadowAlpha
            canvas.drawRect(0.0f, firstLineY, measuredWidth.toFloat(), secondLineY, paintShadow!!)
        }
        counter = 0
        while (counter < visibleItemCount) {
            canvas.save()
            // 弧长 L = itemHeight * counter - itemHeightOffset
            // 求弧度 α = L / r  (弧长/半径) [0,π]
            val radian = (itemHeight * counter - itemHeightOffset) / radius.toDouble()
            // 弧度转换成角度(把半圆以Y轴为轴心向右转90度，使其处于第一象限及第四象限
            // angle [-90°,90°]
            val angle = (90.0 - radian / Math.PI * 180.0).toFloat() //item第一项,从90度开始，逐渐递减到 -90度
            // 计算取值可能有细微偏差，保证负90°到90°以外的不绘制
            if (angle >= 90f || angle <= -90f) {
                canvas.restore()
            } else {
                //获取内容文字
                var contentText: String?
                //如果是label每项都显示的模式，并且item内容不为空、label也不为空
                val tempStr = obtainContentText(visibleItemStrings[counter])
                contentText = if (!onlyShowCenterLabel && !TextUtils.isEmpty(label) && !TextUtils.isEmpty(tempStr)) {
                    tempStr + label
                } else {
                    tempStr
                }
                gravity = if (textSizeAutoFit) {
                    remeasureTextSize(contentText)
                    Gravity.CENTER
                } else {
                    Gravity.START
                }
                //计算开始绘制的位置
                measuredCenterContentStart(contentText)
                measuredOutContentStart(contentText)
                val translateY = (radius - Math.cos(radian) * radius - Math.sin(radian) * maxTextHeight / 2.0).toFloat()
                canvas.translate(0.0f, translateY)
                if (translateY <= firstLineY && maxTextHeight + translateY >= firstLineY) {
                    // 条目经过第一条线
                    canvas.save()
                    canvas.clipRect(0f, 0f, measuredWidth.toFloat(), firstLineY - translateY)
                    canvas.scale(1.0f, Math.sin(radian).toFloat() * SCALE_CONTENT)
                    canvas.drawText(contentText!!, drawOutContentStart.toFloat(), maxTextHeight.toFloat(), paintOuterText!!)
                    canvas.restore()
                    canvas.save()
                    canvas.clipRect(0f, firstLineY - translateY, measuredWidth.toFloat(), itemHeight)
                    canvas.scale(1.0f, Math.sin(radian).toFloat() * 1.0f)
                    canvas.drawText(contentText, drawCenterContentStart.toFloat(), maxTextHeight - centerContentOffset, paintCenterText!!)
                    canvas.restore()
                } else if (translateY <= secondLineY && maxTextHeight + translateY >= secondLineY) {
                    // 条目经过第二条线
                    canvas.save()
                    canvas.clipRect(0f, 0f, measuredWidth.toFloat(), secondLineY - translateY)
                    canvas.scale(1.0f, Math.sin(radian).toFloat() * 1.0f)
                    canvas.drawText(contentText!!, drawCenterContentStart.toFloat(), maxTextHeight - centerContentOffset, paintCenterText!!)
                    canvas.restore()
                    canvas.save()
                    canvas.clipRect(0f, secondLineY - translateY, measuredWidth.toFloat(), itemHeight )
                    canvas.scale(1.0f, Math.sin(radian).toFloat() * SCALE_CONTENT)
                    canvas.drawText(contentText, drawOutContentStart.toFloat(), maxTextHeight.toFloat(), paintOuterText!!)
                    canvas.restore()
                } else if (translateY >= firstLineY && maxTextHeight + translateY <= secondLineY) {
                    // 中间条目
                    canvas.clipRect(0, 0, measuredWidth.toInt(), maxTextHeight)
                    //让文字居中
                    val y = maxTextHeight - centerContentOffset //因为圆弧角换算的向下取值，导致角度稍微有点偏差，加上画笔的基线会偏上，因此需要偏移量修正一下
                    var i = 0
                    for (item in items) {
                        if (item.name == tempStr) {
                            selectedIndex = i
                            break
                        }
                        i++
                    }
                    if (onlyShowCenterLabel && !TextUtils.isEmpty(label)) {
                        contentText += label
                    }
                    canvas.drawText(contentText!!, drawCenterContentStart.toFloat(), y, paintCenterText!!)
                } else {
                    // 其他条目
                    canvas.save()
                    canvas.clipRect(0f, 0f, measuredWidth.toFloat(), itemHeight)
                    canvas.scale(1.0f, Math.sin(radian).toFloat() * SCALE_CONTENT)
                    // 根据当前角度计算出偏差系数，用以在绘制时控制文字的 水平移动 透明度 倾斜程度
                    val offsetCoefficient = Math.pow(Math.abs(angle) / 90f.toDouble(), 2.2).toFloat()
                    if (textSkewXOffset != 0) {
                        //控制文字倾斜度
                        paintOuterText!!.textSkewX = (if (textSkewXOffset > 0) 1 else -1) * (if (angle > 0) -1 else 1) * 0.5f * offsetCoefficient
                        // 控制透明度
                        paintOuterText!!.alpha = ((1 - offsetCoefficient) * 255).toInt()
                    }
                    // 控制文字水平偏移距离
                    canvas.drawText(contentText!!, drawOutContentStart + textSkewXOffset * offsetCoefficient, maxTextHeight.toFloat(), paintOuterText!!)
                    canvas.restore()
                }
                canvas.restore()
                paintCenterText!!.textSize = textSize.toFloat()
            }
            counter++
        }
    }

    /**
     * 根据文字的长度 重新设置文字的大小 让其能完全显示
     */
    private fun remeasureTextSize(contentText: String?) {
        val rect = Rect()
        paintCenterText!!.getTextBounds(contentText, 0, contentText!!.length, rect)
        var width = rect.width()
        var size = textSize
        while (width > measuredWidth) {
            size--
            //设置2条横线中间的文字大小
            paintCenterText!!.textSize = size.toFloat()
            paintCenterText!!.getTextBounds(contentText, 0, contentText.length, rect)
            width = rect.width()
        }
        //设置2条横线外面的文字大小
        paintOuterText!!.textSize = size.toFloat()
    }

    /**
     * 递归计算出对应的索引
     */
    private fun getLoopMappingIndex(index: Int): Int {
        var index = index
        if (index < 0) {
            index = index + items!!.size
            index = getLoopMappingIndex(index)
        } else if (index > items!!.size - 1) {
            index = index - items.size
            index = getLoopMappingIndex(index)
        }
        return index
    }

    /**
     * 根据传进来的对象来获取需要显示的值
     *
     * @param item 数据源的项
     * @return 对应显示的字符串
     */
    private fun obtainContentText(item: Any?): String? {
        if (item == null) {
            return ""
        } else if (item is WheelItem) {
            return item.name
        } else if (item is Int) {
            //如果为整形则最少保留两位数.
            return String.format(Locale.getDefault(), "%02d", item)
        }
        return item.toString()
    }

    private fun measuredCenterContentStart(content: String?) {
        val rect = Rect()
        paintCenterText!!.getTextBounds(content, 0, content!!.length, rect)
        when (gravity) {
            Gravity.CENTER -> drawCenterContentStart = ((measuredWidth - rect.width()) * 0.5).toInt()
            Gravity.LEFT -> drawCenterContentStart = ScreenUtils.dip2px(context, 8f)
            Gravity.RIGHT -> drawCenterContentStart = (measuredWidth - rect.width() - centerContentOffset.toInt()).toInt()
            else -> {
            }
        }
    }

    private fun measuredOutContentStart(content: String?) {
        val rect = Rect()
        paintOuterText!!.getTextBounds(content, 0, content!!.length, rect)
        when (gravity) {
            Gravity.CENTER -> drawOutContentStart = ((measuredWidth - rect.width()) * 0.5).toInt()
            Gravity.LEFT -> drawOutContentStart = ScreenUtils.dip2px(context, 8f)
            Gravity.RIGHT -> drawOutContentStart = (measuredWidth - rect.width() - centerContentOffset.toInt()).toInt()
            else -> {
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        this.widthMeasureSpec = widthMeasureSpec
        remeasure()
        setMeasuredDimension(measuredWidth.toInt(), measuredHeight.toInt())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val eventConsumed = gestureDetector!!.onTouchEvent(event)
        val parent = parent
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startTime = System.currentTimeMillis()
                cancelFuture()
                previousY = event.rawY
                parent?.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                val dy = previousY - event.rawY
                previousY = event.rawY
                totalScrollY = totalScrollY + dy
                // 边界处理。
                if (!isLoop) {
                    var top = -initPosition * itemHeight
                    var bottom = (items!!.size - 1 - initPosition) * itemHeight
                    if (totalScrollY - itemHeight * 0.25 < top) {
                        top = totalScrollY - dy
                    } else if (totalScrollY + itemHeight * 0.25 > bottom) {
                        bottom = totalScrollY - dy
                    }
                    if (totalScrollY < top) {
                        totalScrollY = top
                    } else if (totalScrollY > bottom) {
                        totalScrollY = bottom
                    }
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (!eventConsumed) { //未消费掉事件
                    /*
                     * 关于弧长的计算
                     *
                     * 弧长公式： L = α*R
                     * 反余弦公式：arccos(cosα) = α
                     * 由于之前是有顺时针偏移90度，
                     * 所以实际弧度范围α2的值 ：α2 = π/2-α    （α=[0,π] α2 = [-π/2,π/2]）
                     * 根据正弦余弦转换公式 cosα = sin(π/2-α)
                     * 代入，得： cosα = sin(π/2-α) = sinα2 = (R - y) / R
                     * 所以弧长 L = arccos(cosα)*R = arccos((R - y) / R)*R
                     */
                    val y = event.y
                    val L = Math.acos((radius - y) / radius.toDouble()) * radius
                    //item0 有一半是在不可见区域，所以需要加上 itemHeight / 2
                    val circlePosition = ((L + itemHeight / 2) / itemHeight).toInt()
                    val extraOffset = (totalScrollY % itemHeight + itemHeight) % itemHeight
                    //已滑动的弧长值
                    offset = ((circlePosition - visibleItemCount / 2) * itemHeight - extraOffset).toInt()
                    if (System.currentTimeMillis() - startTime > 120) {
                        // 处理拖拽事件
                        smoothScroll(ACTION_DRAG)
                    } else {
                        // 处理条目点击事件
                        smoothScroll(ACTION_CLICK)
                    }
                }
                parent?.requestDisallowInterceptTouchEvent(false)
            }
            else -> {
                if (!eventConsumed) {
                    val y = event.y
                    val L = Math.acos((radius - y) / radius.toDouble()) * radius
                    val circlePosition = ((L + itemHeight / 2) / itemHeight).toInt()
                    val extraOffset = (totalScrollY % itemHeight + itemHeight) % itemHeight
                    offset = ((circlePosition - visibleItemCount / 2) * itemHeight - extraOffset).toInt()
                    if (System.currentTimeMillis() - startTime > 120) {
                        smoothScroll(ACTION_DRAG)
                    } else {
                        smoothScroll(ACTION_CLICK)
                    }
                }
                parent?.requestDisallowInterceptTouchEvent(false)
            }
        }
        if (event.action != MotionEvent.ACTION_DOWN) {
            invalidate()
        }
        return true
    }

    /**
     * 获取选项个数
     */
    protected val itemCount: Int
        protected get() = items?.size ?: 0

    private fun obtainTextWidth(paint: Paint?, str: String?): Int {
        var iRet = 0
        if (str != null && str.length > 0) {
            val len = str.length
            val widths = FloatArray(len)
            paint!!.getTextWidths(str, widths)
            for (j in 0 until len) {
                iRet += Math.ceil(widths[j].toDouble()).toInt()
            }
        }
        return iRet
    }

    /**
     * 选中项的分割线
     */
    open class DividerConfig {
        var visible = true
        var shadowVisible = false
        var color = DIVIDER_COLOR
        var shadowColor = TEXT_COLOR_NORMAL
        var shadowAlpha = 100
        var alpha = DIVIDER_ALPHA
        var ratio = 0.1f
        var thick = DIVIDER_THICK

        constructor() : super() {}
        constructor(@FloatRange(from = 0.0, to = 1.0) ratio: Float) {
            this.ratio = ratio
        }

        /**
         * 线是否可见
         */
        fun setVisible(visible: Boolean): DividerConfig {
            this.visible = visible
            return this
        }

        /**
         * 阴影是否可见
         */
        fun setShadowVisible(shadowVisible: Boolean): DividerConfig {
            this.shadowVisible = shadowVisible
            if (shadowVisible && color == DIVIDER_COLOR) {
                color = shadowColor
                alpha = 255
            }
            return this
        }

        /**
         * 阴影颜色
         */
        fun setShadowColor(@ColorInt color: Int): DividerConfig {
            shadowVisible = true
            shadowColor = color
            return this
        }

        /**
         * 阴影透明度
         */
        fun setShadowAlpha(@IntRange(from = 1, to = 255) alpha: Int): DividerConfig {
            shadowAlpha = alpha
            return this
        }

        /**
         * 线颜色
         */
        fun setColor(@ColorInt color: Int): DividerConfig {
            this.color = color
            return this
        }

        /**
         * 线透明度
         */
        fun setAlpha(@IntRange(from = 1, to = 255) alpha: Int): DividerConfig {
            this.alpha = alpha
            return this
        }

        /**
         * 线比例，范围为0-1,0表示最长，1表示最短
         */
        fun setRatio(@FloatRange(from = 0.0, to = 1.0) ratio: Float): DividerConfig {
            this.ratio = ratio
            return this
        }

        /**
         * 线粗
         */
        fun setThick(thick: Float): DividerConfig {
            this.thick = thick
            return this
        }

        override fun toString(): String {
            return "visible=$visible,color=$color,alpha=$alpha,thick=$thick"
        }

        companion object {
            const val FILL = 0f
            const val WRAP = 1f
        }
    }

    @Deprecated("使用{@link #DividerConfig}代替")
    class LineConfig : DividerConfig()

    /**
     * 用于兼容旧版本的纯字符串条目
     */
    private class StringItem(override val name: String) : WheelItem

    interface OnItemSelectListener {
        /**
         * 滑动选择回调
         *
         * @param index 当前选择项的索引
         */
        fun onSelected(index: Int)
    }

    /**
     * 兼容旧版本API
     *
     */
    @Deprecated("use {@link OnItemSelectListener} instead")
    interface OnWheelListener {
        fun onSelected(isUserScroll: Boolean, index: Int, item: String?)
    }

    @Deprecated("use {@link OnItemSelectListener} instead")
    interface OnWheelViewListener : OnWheelListener
    private class MessageHandler internal constructor(val view: WheelView) : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                WHAT_INVALIDATE -> view.invalidate()
                WHAT_SMOOTH_SCROLL -> view.smoothScroll(ACTION_FLING)
                WHAT_ITEM_SELECTED -> view.itemSelectedCallback()
                else -> {
                }
            }
        }

        companion object {
            const val WHAT_INVALIDATE = 1000
            const val WHAT_SMOOTH_SCROLL = 2000
            const val WHAT_ITEM_SELECTED = 3000
        }

    }

    private class SmoothScrollTimerTask internal constructor(val view: WheelView, var offset: Int) : TimerTask() {
        var realTotalOffset = Int.MAX_VALUE
        var realOffset = 0
        override fun run() {
            if (realTotalOffset == Int.MAX_VALUE) {
                realTotalOffset = offset
            }
            //把要滚动的范围细分成10小份，按10小份单位来重绘
            realOffset = (realTotalOffset.toFloat() * 0.1f).toInt()
            if (realOffset == 0) {
                realOffset = if (realTotalOffset < 0) {
                    -1
                } else {
                    1
                }
            }
            if (Math.abs(realTotalOffset) <= 1) {
                view.cancelFuture()
                view.handler!!.sendEmptyMessage(MessageHandler.WHAT_ITEM_SELECTED)
            } else {
                view.totalScrollY = view.totalScrollY + realOffset
                //这里如果不是循环模式，则点击空白位置需要回滚，不然就会出现选到－1 item的情况
                if (!view.isLoop) {
                    val itemHeight = view.itemHeight
                    val top = (-view.initPosition).toFloat() * itemHeight
                    val bottom = (view.itemCount - 1 - view.initPosition).toFloat() * itemHeight
                    if (view.totalScrollY <= top || view.totalScrollY >= bottom) {
                        view.totalScrollY = view.totalScrollY - realOffset
                        view.cancelFuture()
                        view.handler!!.sendEmptyMessage(MessageHandler.WHAT_ITEM_SELECTED)
                        return
                    }
                }
                view.handler!!.sendEmptyMessage(MessageHandler.WHAT_INVALIDATE)
                realTotalOffset = realTotalOffset - realOffset
            }
        }

    }

    private class InertiaTimerTask internal constructor(val view: WheelView, val velocityY: Float) : TimerTask() {
        var a = Int.MAX_VALUE.toFloat()
        override fun run() {
            if (a == Int.MAX_VALUE.toFloat()) {
                a = if (Math.abs(velocityY) > 2000f) {
                    if (velocityY > 0.0f) {
                        2000f
                    } else {
                        -2000f
                    }
                } else {
                    velocityY
                }
            }
            if (Math.abs(a) >= 0.0f && Math.abs(a) <= 20f) {
                view.cancelFuture()
                view.handler!!.sendEmptyMessage(MessageHandler.WHAT_SMOOTH_SCROLL)
                return
            }
            val i = (a * 10f / 1000f).toInt()
            view.totalScrollY = view.totalScrollY - i
            if (!view.isLoop) {
                val itemHeight = view.itemHeight
                var top = -view.initPosition * itemHeight
                var bottom = (view.itemCount - 1 - view.initPosition) * itemHeight
                if (view.totalScrollY - itemHeight * 0.25 < top) {
                    top = view.totalScrollY + i
                } else if (view.totalScrollY + itemHeight * 0.25 > bottom) {
                    bottom = view.totalScrollY + i
                }
                if (view.totalScrollY <= top) {
                    a = 40f
                    view.totalScrollY = top
                } else if (view.totalScrollY >= bottom) {
                    view.totalScrollY = bottom
                    a = -40f
                }
            }
            a = if (a < 0.0f) {
                a + 20f
            } else {
                a - 20f
            }
            view.handler!!.sendEmptyMessage(MessageHandler.WHAT_INVALIDATE)
        }

    }

    companion object {
        const val LINE_SPACE_MULTIPLIER = 2.0f
        const val TEXT_PADDING = -1

        /**
         * 单位为sp
         */
        const val TEXT_SIZE = 16
        const val TEXT_COLOR_FOCUS = -0xfd7732
        const val TEXT_COLOR_NORMAL = -0x444445
        const val DIVIDER_COLOR = -0x7c321a
        const val DIVIDER_ALPHA = 220

        //单位为px
        const val DIVIDER_THICK = 2f
        const val ITEM_OFF_SET = 3

        /**
         * 单位为px,480X800的手机边距不能太大
         */
        private const val ITEM_PADDING = 13f

        //点击
        private const val ACTION_CLICK = 1

        //滑翔
        private const val ACTION_FLING = 2

        //拖拽
        private const val ACTION_DRAG = 3

        //修改这个值可以改变滑行速度
        private const val VELOCITY_FLING = 5

        //非中间文字用此控制高度，压扁形成3D错觉
        private const val SCALE_CONTENT = 0.8f
    }

    init {
        //屏幕密度：0.75、1.0、1.5、2.0、3.0，根据密度不同进行适配
        val density = resources.displayMetrics.density
        if (density < 1) {
            centerContentOffset = 2.4f
        } else if (1 <= density && density < 2) {
            centerContentOffset = 3.6f
        } else if (1 <= density && density < 2) {
            centerContentOffset = 4.5f
        } else if (2 <= density && density < 3) {
            centerContentOffset = 6.0f
        } else if (density >= 3) {
            centerContentOffset = density * 2.5f
        }
        judgeLineSpace()
        initView(context)
    }
}