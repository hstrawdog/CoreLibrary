package com.easy.core.widget

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.customize.view
 * @Date  : 18:29
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.easy.core.R

/**
 *
 * @property progressWidth Float
 * @property progressColor Int
 * @property progressBgColor Int
 * @property startPoint Int
 * @property orientation Int
 * @property textColor Int
 * @property textSize Float
 * @property currentProgress Int
 * @property progressBgPaint Paint
 * @property progressPaint Paint
 * @property textView TextView?
 * @constructor
 */
class RecProgressLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
//) : RelativeLayout(context, attrs, defStyleAttr) {
) : CardView(context, attrs, defStyleAttr) {

    companion object {
        //起点位置
        const val LEFT_TOP = 0
        const val RIGHT_TOP = 1
        const val RIGHT_BOTTOM = 2
        const val LEFT_BOTTOM = 3

        //方向
        const val CLOCKWISE = 0
        const val ANTI_CLOCKWISE = 1
    }

    /**
     * 进度条宽度
     */
    var progressWidth = 0f

    /**
     * 进度条颜色
     */
    var progressColor = Color.BLUE

    /**
     * 进度条背景
     */
    var progressBgColor = Color.TRANSPARENT

    /**
     * 进度条开始点
     */
    var startPoint = LEFT_TOP

    /**
     * 进度条进度方向
     */
    var orientation = CLOCKWISE

    /**
     * 文字颜色
     */
    var textColor = Color.BLACK

    /**
     * 文字大小
     */
    var textSize = 0f

    /**
     * 当前进度
     */
    var currentProgress = 0

    private var progressBgPaint: Paint
    private var progressPaint: Paint
    var textView: TextView? = null
//    var layoutParams: LayoutParams

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.RecProgressLayout)
        progressWidth = a.getDimension(R.styleable.RecProgressLayout_rpl_progress_width, 1f)
        progressColor = a.getColor(R.styleable.RecProgressLayout_rpl_progress_color, Color.GREEN)
        progressBgColor = a.getColor(R.styleable.RecProgressLayout_rpl_progress_bg_color, Color.TRANSPARENT)
        startPoint = a.getInteger(R.styleable.RecProgressLayout_rpl_start_point, LEFT_TOP)
        orientation = a.getInteger(R.styleable.RecProgressLayout_rpl_orientation, CLOCKWISE)
        textColor = a.getColor(R.styleable.RecProgressLayout_rpl_text_color, Color.BLACK)
        textSize = a.getDimension(R.styleable.RecProgressLayout_rpl_text_size, 0f)
        currentProgress = a.getInteger(R.styleable.RecProgressLayout_rpl_current_progress, 0)
        a.recycle()

        progressBgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        progressBgPaint.color = progressBgColor
        progressBgPaint.strokeWidth = progressWidth
        progressBgPaint.style = Paint.Style.STROKE

        progressBgPaint.strokeCap = Paint.Cap.ROUND

        progressPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        progressPaint.strokeCap = Paint.Cap.ROUND
        progressPaint.color = progressColor
        progressPaint.strokeWidth = 50f
        progressPaint.style = Paint.Style.STROKE
        progressPaint.strokeJoin = Paint.Join.ROUND

        if (textSize != 0f) {
            textView = TextView(context)
            textView?.setTextColor(textColor)
            textView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        }

//        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        drawProgressBg(canvas)
        drawProgress(canvas)
        if (textView != null) drawText()
    }

    fun setProgress(progress: Int) {
        currentProgress = progress
        invalidate()
    }

    /**
     * 绘制进度条背景
     * @param canvas Canvas?
     */
    private fun drawProgressBg(canvas: Canvas?) {
        if (orientation == CLOCKWISE) {
            drawTop(canvas, progressBgPaint, width.toFloat())
            drawRight(canvas, progressBgPaint, height.toFloat())
            drawBottom(canvas, progressBgPaint, 0f)
            drawLeft(canvas, progressBgPaint, 0f)
        } else {
            drawTop(canvas, progressBgPaint, 0f)
            drawRight(canvas, progressBgPaint, 0f)
            drawBottom(canvas, progressBgPaint, width.toFloat())
            drawLeft(canvas, progressBgPaint, height.toFloat())
        }
    }

    /**
     * 绘制进度条
     * @param canvas Canvas?
     */
    private fun drawProgress(canvas: Canvas?) {
        val totalWidth = 2 * (width + height) - 4 * progressWidth
        val progress = totalWidth * (currentProgress / 100.0f)
        when (startPoint) {
            LEFT_TOP -> {
                drawAtLeftTop(canvas, totalWidth, progress)
            }
//            LEFT_BOTTOM -> {
//                drawAtLeftBottom(canvas, totalWidth, progress)
//            }
//            RIGHT_TOP -> {
//                drawAtRightTop(canvas, totalWidth, progress)
//            }
//            RIGHT_BOTTOM -> {
//                drawAtRightBottom(canvas, totalWidth, progress)
//            }
        }
    }

    @SuppressLint("NewApi")
    private fun drawText() {
        removeView(textView)
        val content = "$currentProgress%"
        textView?.text = content
//        layoutParams.addRule(CENTER_IN_PARENT)
//        textView?.layoutParams = layoutParams
        addView(textView)
    }

    /**
     * 绘制上方边进度条
     * @param canvas Canvas?
     */
    private fun drawTop(canvas: Canvas?, paint: Paint, width: Float) {
        if (orientation == CLOCKWISE) {
            canvas?.drawLine(progressWidth, progressWidth / 2.0f, width, progressWidth / 2.0f, paint)
        } else {
            canvas?.drawLine(this.width - progressWidth, progressWidth / 2.0f, width, progressWidth / 2.0f, paint)
        }
    }

    /**
     * 绘制右边进度条
     * @param canvas Canvas?
     */
    private fun drawRight(canvas: Canvas?, paint: Paint, height: Float) {
        if (orientation == CLOCKWISE) {
//            val paint = Paint().apply {
//                style = Paint.Style.STROKE
//                strokeWidth = 10f
//                color = Color.BLACK
//                strokeCap = Paint.Cap.ROUND
//            }

            canvas?.drawLine(width.toFloat() - progressWidth / 2.0f, progressWidth, width.toFloat() - progressWidth / 2.0f, height, paint)

//            var w = 500f
//            var h = 500f
//            var centerX = w/2f
//            var centerY = h/2f
//            var  start = PointF()
//            var  end =PointF()
//            var  control =PointF()
//            // 初始化数据点和控制点的位置
//            start.x = width.toFloat() - progressWidth / 2.0f
//            start.y = progressWidth
//            end.x =width.toFloat() - progressWidth / 2.0f
//            end.y = height
//            control.x = 0f
//            control.y = 700f
//            Slog.d("输出progressWidth=$progressWidth++$height")
//            // 绘制贝塞尔曲线f
//            var path = Path()
//            path.moveTo(start.x,start.y);
//            path.quadTo(control.x,control.y,end.x,end.y)
//            canvas?.drawPath(path, paint)

//            var mRectF = RectF(10f, 10f, 600f, 600f)
//            path.arcTo(mRectF, 0f, 90f)
//            canvas!!.drawPath(path, paint)

//            val p = 6f
//            paint.color = UIUtils.getColor(R.color.yellow_heart_color)
//            val rectF = RectF(p, p, width.toFloat() - p, height.toFloat() - p)
//
//            canvas?.drawRoundRect(rectF, 80f, 80f, paint)  // 绘制方形圆角边框

//            var progress2 = 60
//            var maxProgress2 = 100
//            var cornerRadius = 6f
//            var progressWidth = 0f
//            var backgroundColor2 = Color.GRAY
//            var progressColor = Color.BLUE
//            val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
//
//            val rectF2 = RectF(0f, 0f, width.toFloat(), height.toFloat())
//            canvas?.drawRoundRect(rectF2, cornerRadius, cornerRadius, backgroundPaint)
//
//            val progressWidth2 = progress2 * (width - 2 * cornerRadius) / maxProgress2.toFloat()
//            val progressRectF = RectF(cornerRadius, cornerRadius, cornerRadius + progressWidth2, height - cornerRadius)
//            canvas?.drawRoundRect(progressRectF, cornerRadius, cornerRadius, progressPaint)

            // 计算进度条的位置和宽度
//            val progressRect = RectF(
//                borderWidth / 2,       // left
//                height - progressHeight - borderWidth / 2,       // top
//                progress * (width - borderWidth) - borderWidth / 2, // right
//                height - borderWidth / 2   // bottom
//            )

            // 画进度条矩形
//            progressPaint.color = Color.RED
//            canvas?.drawRect(rectF, progressPaint)

        } else {
            canvas?.drawLine(width - progressWidth / 2.0f, this.height - progressWidth, width - progressWidth / 2.0f, height, paint)
        }
    }

    /**
     * 绘制下方进度条
     * @param canvas Canvas?
     */
    private fun drawBottom(canvas: Canvas?, paint: Paint, width: Float) {
        if (orientation == CLOCKWISE) {
            canvas?.drawLine(this.width - progressWidth, height.toFloat() - progressWidth / 2.0f, width, height.toFloat() - progressWidth / 2.0f, paint)
        } else {
            canvas?.drawLine(progressWidth, height - progressWidth / 2.0f, width, height - progressWidth / 2.0f, paint)
        }
    }

    /**
     * 绘制左边进度条
     * @param canvas Canvas?
     */
    private fun drawLeft(canvas: Canvas?, paint: Paint, height: Float) {
        if (orientation == CLOCKWISE) {
            canvas?.drawLine(progressWidth / 2.0f, this.height - progressWidth, progressWidth / 2.0f, height, paint)
        } else {
            canvas?.drawLine(progressWidth / 2.0f, progressWidth, progressWidth / 2.0f, height, paint)
        }
    }

    /**
     * 绘制起点在左上
     * @param canvas Canvas?
     * @param totalWidth Float
     * @param progress Float
     */
    private fun drawAtLeftTop(canvas: Canvas?, totalWidth: Float, progress: Float) {
        if (orientation == CLOCKWISE) {
            if (progress >= width - progressWidth) {
                drawTop(canvas, progressPaint, width.toFloat())
                if (currentProgress >= 50) {
                    drawRight(canvas, progressPaint, height.toFloat())
                    if (progress >= width * 2 + height - 3 * progressWidth) {
                        drawBottom(canvas, progressPaint, 0f)
                        if (currentProgress == 100) {
                            drawLeft(canvas, progressPaint, 0f)
                        } else {
                            drawLeft(canvas, progressPaint, totalWidth - progress)
                        }
                    } else {
                        drawBottom(canvas, progressPaint, 2 * width + height - 3 * progressWidth - progress)
                    }
                } else {
                    drawRight(canvas, progressPaint, progress - width + 2 * progressWidth)
                }
            } else {
                drawTop(canvas, progressPaint, progressWidth + progress)
            }
        } else {
            if (progress >= height - progressWidth) {
                drawLeft(canvas, progressPaint, height.toFloat())
                if (currentProgress >= 50) {
                    drawBottom(canvas, progressPaint, height.toFloat())
                    if (progress >= height * 2 + width - 3 * progressWidth) {
                        drawRight(canvas, progressPaint, 0f)
                        if (currentProgress == 100) {
                            drawTop(canvas, progressPaint, 0f)
                        } else {
                            drawTop(canvas, progressPaint, width - (totalWidth - progress))
                        }
                    } else {
                        drawRight(canvas, progressPaint, 2 * height + width - (progress + 3 * progressWidth))
                    }
                } else {
                    drawBottom(canvas, progressPaint, progress + 2 * progressWidth - height)
                }
            } else {
                drawLeft(canvas, progressPaint, progressWidth + progress)
            }
        }
    }

    /**
     * 绘制起点在左下
     * @param canvas Canvas?
     * @param totalWidth Float
     * @param progress Float
     */
    private fun drawAtLeftBottom(canvas: Canvas?, totalWidth: Float, progress: Float) {
        if (orientation == CLOCKWISE) {
            if (progress >= height - progressWidth) {
                drawLeft(canvas, progressPaint, 0f)
                if (currentProgress >= 50) {
                    drawTop(canvas, progressPaint, width.toFloat())
                    if (progress >= height * 2 + width - 3 * progressWidth) {
                        drawRight(canvas, progressPaint, height.toFloat())
                        if (currentProgress == 100) {
                            drawBottom(canvas, progressPaint, 0f)
                        } else {
                            drawBottom(canvas, progressPaint, totalWidth - progress)
                        }
                    } else {
                        drawRight(canvas, progressPaint, progress - 3 * progressWidth - height - width)
                    }
                } else {
                    drawTop(canvas, progressPaint, progress - height + 2 * progressWidth)
                }
            } else {
                drawLeft(canvas, progressPaint, height - progress)
            }
        } else {
            if (progress >= width - progressWidth) {
                drawBottom(canvas, progressPaint, width.toFloat())
                if (currentProgress >= 50) {
                    drawRight(canvas, progressPaint, 0f)
                    if (progress >= width * 2 + height - 3 * progressWidth) {
                        drawTop(canvas, progressPaint, 0f)
                        if (currentProgress == 100) {
                            drawLeft(canvas, progressPaint, height.toFloat())
                        } else {
                            drawLeft(canvas, progressPaint, height - (totalWidth - progress))
                        }
                    } else {
                        drawTop(canvas, progressPaint, 2 * width + height - (progress + 3 * progressWidth))
                    }
                } else {
                    drawRight(canvas, progressPaint, height + width - (progress + 2 * progressWidth))
                }
            } else {
                drawBottom(canvas, progressPaint, progress)
            }
        }
    }

    /**
     * 绘制起点在右上
     * @param canvas Canvas?
     * @param totalWidth Float
     * @param progress Float
     */
    private fun drawAtRightTop(canvas: Canvas?, totalWidth: Float, progress: Float) {
        if (orientation == CLOCKWISE) {
            if (progress >= height - progressWidth) {
                drawRight(canvas, progressPaint, height.toFloat())
                if (currentProgress >= 50) {
                    drawBottom(canvas, progressPaint, 0f)
                    if (progress >= height * 2 + width - 3 * progressWidth) {
                        drawLeft(canvas, progressPaint, 0f)
                        if (currentProgress == 100) {
                            drawTop(canvas, progressPaint, width.toFloat())
                        } else {
                            drawTop(canvas, progressPaint, width - (totalWidth - progress))
                        }
                    } else {
                        drawLeft(canvas, progressPaint, 2 * height + width - 3 * progressWidth - progress)
                    }
                } else {
                    drawBottom(canvas, progressPaint, width + height - progress - 2 * progressWidth)
                }
            } else {
                drawRight(canvas, progressPaint, progressWidth + progress)
            }
        } else {
            if (progress >= width - progressWidth) {
                drawTop(canvas, progressPaint, 0f)
                if (currentProgress >= 50) {
                    drawLeft(canvas, progressPaint, height.toFloat())
                    if (progress >= width * 2 + height - 3 * progressWidth) {
                        drawBottom(canvas, progressPaint, width.toFloat())
                        if (currentProgress == 100) {
                            drawRight(canvas, progressPaint, 0f)
                        } else {
                            drawRight(canvas, progressPaint, totalWidth - progress)
                        }
                    } else {
                        drawBottom(canvas, progressPaint, progress + 3 * progressWidth - width - height)
                    }
                } else {
                    drawLeft(canvas, progressPaint, progress + 2 * progressWidth - width)
                }
            } else {
                drawTop(canvas, progressPaint, width - progressWidth - progress)
            }
        }
    }

    /**
     * 绘制起点在左上
     * @param canvas Canvas?
     * @param totalWidth Float
     * @param progress Float
     */
    private fun drawAtRightBottom(canvas: Canvas?, totalWidth: Float, progress: Float) {
        if (orientation == CLOCKWISE) {
            if (progress >= width - progressWidth) {
                drawBottom(canvas, progressPaint, 0f)
                if (currentProgress >= 50) {
                    drawLeft(canvas, progressPaint, 0f)
                    if (progress >= width * 2 + height - 3 * progressWidth) {
                        drawTop(canvas, progressPaint, width.toFloat())
                        if (currentProgress == 100) {
                            drawRight(canvas, progressPaint, height.toFloat())
                        } else {
                            drawRight(canvas, progressPaint, totalWidth - progress)
                        }
                    } else {
                        drawTop(canvas, progressPaint, 3 * progressWidth + progress - width - height)
                    }
                } else {
                    drawLeft(canvas, progressPaint, width + height - progress - 2 * progressWidth)
                }
            } else {
                drawBottom(canvas, progressPaint, width - progressWidth - progress)
            }
        } else {
            if (progress >= height - progressWidth) {
                drawRight(canvas, progressPaint, 0f)
                if (currentProgress >= 50) {
                    drawTop(canvas, progressPaint, 0f)
                    if (progress >= height * 2 + width - 3 * progressWidth) {
                        drawLeft(canvas, progressPaint, height.toFloat())
                        if (currentProgress == 100) {
                            drawBottom(canvas, progressPaint, width.toFloat())
                        } else {
                            drawBottom(canvas, progressPaint, width - (totalWidth - progress))
                        }
                    } else {
                        drawLeft(canvas, progressPaint, progress + 3 * progressWidth - width - height)
                    }
                } else {
                    drawTop(canvas, progressPaint, height + width - progress - 2 * progressWidth)
                }
            } else {
                drawRight(canvas, progressPaint, height - progress - progressWidth)
            }
        }
    }
}
