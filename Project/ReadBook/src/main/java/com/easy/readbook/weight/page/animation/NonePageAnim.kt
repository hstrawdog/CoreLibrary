package com.easy.readbook.weight.page.animation


import android.graphics.Canvas
import android.view.View

/**
 * Created by zlj
 * 无
 */

class NonePageAnim(w: Int, h: Int, view: View, listener: OnPageChangeListener) :
    HorizonPageAnim(w, h, view, listener) {

    override fun drawStatic(canvas: Canvas) {
        if (isCancel) {
            canvas.drawBitmap(mCurBitmap, 0f, 0f, null)
        } else {
            canvas.drawBitmap(mNextBitmap, 0f, 0f, null)
        }
    }

    override fun drawMove(canvas: Canvas) {
        if (isCancel) {
            canvas.drawBitmap(mCurBitmap, 0f, 0f, null)
        } else {
            canvas.drawBitmap(mNextBitmap, 0f, 0f, null)
        }
    }

    override fun startAnim() {}
}
