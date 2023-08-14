package com.easy.core.widget.ptr

import `in`.srain.cube.views.ptr.PtrFrameLayout
import `in`.srain.cube.views.ptr.PtrUIHandler
import `in`.srain.cube.views.ptr.indicator.PtrIndicator
import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.FrameLayout
import android.widget.TextView
import  com.easy.core.R
import java.text.SimpleDateFormat
import java.util.Date

/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.widget.ptr
 * @FileName :   PtrClassicDefaultHeader
 * @Date  : 2019/2/14 0014
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class PtrClassicDefaultHeader : FrameLayout, PtrUIHandler {
    private var mRotateAniTime = 150
    private var mFlipAnimation: RotateAnimation? = null
    private var mReverseFlipAnimation: RotateAnimation? = null
    private var mTitleTextView: TextView? = null
    private var mRotateView: View? = null
    private var mProgressBar: View? = null
    private var mLastUpdateTime: Long = -1
    private var mLastUpdateTextView: TextView? = null
    private var mLastUpdateTimeKey: String? = null
    private var mShouldShowLastUpdate = false
    private val mLastUpdateTimeUpdater: LastUpdateTimeUpdater? = LastUpdateTimeUpdater()

    constructor(context: Context?) : super(context!!) {
        initViews(null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        initViews(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context!!, attrs, defStyle) {
        initViews(attrs)
    }

    protected fun initViews(attrs: AttributeSet?) {
        val arr = context.obtainStyledAttributes(attrs, `in`.srain.cube.views.ptr.R.styleable.PtrClassicHeader, 0, 0)
        mRotateAniTime = arr.getInt(`in`.srain.cube.views.ptr.R.styleable.PtrClassicHeader_ptr_rotate_ani_time, mRotateAniTime)
        buildAnimation()
        val header = LayoutInflater.from(context).inflate(R.layout.cube_ptr_classic_default_header, this)
        mRotateView = header.findViewById(R.id.ptr_classic_header_rotate_view)
        mTitleTextView = header.findViewById<View>(R.id.ptr_classic_header_rotate_view_header_title) as TextView
        mLastUpdateTextView = header.findViewById<View>(R.id.ptr_classic_header_rotate_view_header_last_update) as TextView
        mProgressBar = header.findViewById(R.id.ptr_classic_header_rotate_view_progressbar)
        resetView()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mLastUpdateTimeUpdater?.stop()
    }

    fun setRotateAniTime(time: Int) {
        if (time == mRotateAniTime || time == 0) {
            return
        }
        mRotateAniTime = time
        buildAnimation()
    }

    /**
     * Specify the last update time by this key string
     *
     * @param key
     */
    fun setLastUpdateTimeKey(key: String?) {
        if (TextUtils.isEmpty(key)) {
            return
        }
        mLastUpdateTimeKey = key
    }

    /**
     * Using an object to specify the last update time.
     *
     * @param object
     */
    fun setLastUpdateTimeRelateObject(`object`: Any) {
        setLastUpdateTimeKey(`object`.javaClass.name)
    }

    private fun buildAnimation() {
        mFlipAnimation = RotateAnimation(0f, (-180).toFloat(), RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f)
        mFlipAnimation!!.interpolator = LinearInterpolator()
        mFlipAnimation!!.duration = mRotateAniTime.toLong()
        mFlipAnimation!!.fillAfter = true
        mReverseFlipAnimation = RotateAnimation((-180).toFloat(), 0f, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f)
        mReverseFlipAnimation!!.interpolator = LinearInterpolator()
        mReverseFlipAnimation!!.duration = mRotateAniTime.toLong()
        mReverseFlipAnimation!!.fillAfter = true
    }

    private fun resetView() {
        hideRotateView()
        mProgressBar!!.visibility = View.INVISIBLE
    }

    private fun hideRotateView() {
        mRotateView!!.clearAnimation()
        mRotateView!!.visibility = View.INVISIBLE
    }

    override fun onUIReset(frame: PtrFrameLayout) {
        resetView()
        mShouldShowLastUpdate = true
        tryUpdateLastUpdateTime()
    }

    override fun onUIRefreshPrepare(frame: PtrFrameLayout) {
        mShouldShowLastUpdate = true
        tryUpdateLastUpdateTime()
        mLastUpdateTimeUpdater!!.start()
        mProgressBar!!.visibility = View.INVISIBLE
        mRotateView!!.visibility = View.VISIBLE
        mTitleTextView!!.visibility = View.VISIBLE
        if (frame.isPullToRefresh()) {
            mTitleTextView!!.text = resources.getString(`in`.srain.cube.views.ptr.R.string.cube_ptr_pull_down_to_refresh)
        } else {
            mTitleTextView!!.text = resources.getString(`in`.srain.cube.views.ptr.R.string.cube_ptr_pull_down)
        }
    }

    override fun onUIRefreshBegin(frame: PtrFrameLayout) {
        mShouldShowLastUpdate = false
        hideRotateView()
        mProgressBar!!.visibility = View.VISIBLE
        mTitleTextView!!.visibility = View.VISIBLE
        mTitleTextView!!.setText(`in`.srain.cube.views.ptr.R.string.cube_ptr_refreshing)
        tryUpdateLastUpdateTime()
        mLastUpdateTimeUpdater!!.stop()
    }

    override fun onUIRefreshComplete(frame: PtrFrameLayout) {
        hideRotateView()
        mProgressBar!!.visibility = View.INVISIBLE
        mTitleTextView!!.visibility = View.VISIBLE
        mTitleTextView!!.text = resources.getString(`in`.srain.cube.views.ptr.R.string.cube_ptr_refresh_complete)

        // update last update time
        val sharedPreferences = context.getSharedPreferences(KEY_SharedPreferences, 0)
        if (!TextUtils.isEmpty(mLastUpdateTimeKey)) {
            mLastUpdateTime = System.currentTimeMillis()
            sharedPreferences.edit().putLong(mLastUpdateTimeKey, mLastUpdateTime).commit()
        }
    }

    private fun tryUpdateLastUpdateTime() {
        if (TextUtils.isEmpty(mLastUpdateTimeKey) || !mShouldShowLastUpdate) {
            mLastUpdateTextView!!.visibility = View.GONE
        } else {
            val time = lastUpdateTime
            if (TextUtils.isEmpty(time)) {
                mLastUpdateTextView!!.visibility = View.GONE
            } else {
                mLastUpdateTextView!!.visibility = View.VISIBLE
                mLastUpdateTextView!!.text = time
            }
        }
    }

    private val lastUpdateTime: String?
        private get() {
            if (mLastUpdateTime == -1L && !TextUtils.isEmpty(mLastUpdateTimeKey)) {
                mLastUpdateTime = context.getSharedPreferences(KEY_SharedPreferences, 0).getLong(mLastUpdateTimeKey, -1)
            }
            if (mLastUpdateTime == -1L) {
                return null
            }
            val diffTime = System.currentTimeMillis() - mLastUpdateTime
            val seconds = (diffTime / 1000).toInt()
            if (diffTime < 0) {
                return null
            }
            if (seconds <= 0) {
                return null
            }
            val sb = StringBuilder()
            sb.append(context.getString(`in`.srain.cube.views.ptr.R.string.cube_ptr_last_update))
            if (seconds < 60) {
                sb.append(seconds.toString() + context.getString(`in`.srain.cube.views.ptr.R.string.cube_ptr_seconds_ago))
            } else {
                val minutes = seconds / 60
                if (minutes > 60) {
                    val hours = minutes / 60
                    if (hours > 24) {
                        val date = Date(mLastUpdateTime)
                        sb.append(sDataFormat.format(date))
                    } else {
                        sb.append(hours.toString() + context.getString(`in`.srain.cube.views.ptr.R.string.cube_ptr_hours_ago))
                    }
                } else {
                    sb.append(minutes.toString() + context.getString(`in`.srain.cube.views.ptr.R.string.cube_ptr_minutes_ago))
                }
            }
            return sb.toString()
        }

    override fun onUIPositionChange(frame: PtrFrameLayout, isUnderTouch: Boolean, status: Byte, ptrIndicator: PtrIndicator) {
        val mOffsetToRefresh: Int = frame.getOffsetToRefresh()
        val currentPos: Int = ptrIndicator.getCurrentPosY()
        val lastPos: Int = ptrIndicator.getLastPosY()
        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromBottomUnderTouch(frame)
                if (mRotateView != null) {
                    mRotateView!!.clearAnimation()
                    mRotateView!!.startAnimation(mReverseFlipAnimation)
                }
            }
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromTopUnderTouch(frame)
                if (mRotateView != null) {
                    mRotateView!!.clearAnimation()
                    mRotateView!!.startAnimation(mFlipAnimation)
                }
            }
        }
    }

    private fun crossRotateLineFromTopUnderTouch(frame: PtrFrameLayout) {
        if (!frame.isPullToRefresh()) {
            mTitleTextView!!.visibility = View.VISIBLE
            mTitleTextView!!.setText(`in`.srain.cube.views.ptr.R.string.cube_ptr_release_to_refresh)
        }
    }

    private fun crossRotateLineFromBottomUnderTouch(frame: PtrFrameLayout) {
        mTitleTextView!!.visibility = View.VISIBLE
        if (frame.isPullToRefresh()) {
            mTitleTextView!!.text = resources.getString(`in`.srain.cube.views.ptr.R.string.cube_ptr_pull_down_to_refresh)
        } else {
            mTitleTextView!!.text = resources.getString(`in`.srain.cube.views.ptr.R.string.cube_ptr_pull_down)
        }
    }

    private inner class LastUpdateTimeUpdater : Runnable {
        private var mRunning = false
        fun start() {
            if (TextUtils.isEmpty(mLastUpdateTimeKey)) {
                return
            }
            mRunning = true
            run()
        }

        fun stop() {
            mRunning = false
            removeCallbacks(this)
        }

        override fun run() {
            tryUpdateLastUpdateTime()
            if (mRunning) {
                postDelayed(this, 1000)
            }
        }
    }

    companion object {
        private const val KEY_SharedPreferences = "cube_ptr_classic_last_update"
        private val sDataFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    }
}