package com.hqq.core.utils.click

import android.os.SystemClock
import android.text.TextUtils
import android.view.View
import java.util.concurrent.ConcurrentHashMap

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2020/09/01
 * desc  : utils about debouncing
 */
object DebouncingUtils {
    private const val CACHE_SIZE = 64
    private val KEY_MILLIS_MAP: MutableMap<String, Long> = ConcurrentHashMap(CACHE_SIZE)
    private const val DEBOUNCING_DEFAULT_VALUE: Long = 1000

    /**
     * Return whether the view is not in a jitter state.
     *
     * @param view The view.
     * @return `true`: yes<br></br>`false`: no
     */
    fun isValid(view: View): Boolean {
        return isValid(view, DEBOUNCING_DEFAULT_VALUE)
    }

    /**
     * Return whether the view is not in a jitter state.
     *
     * @param view     The view.
     * @param duration The duration.
     * @return `true`: yes<br></br>`false`: no
     */
    fun isValid(view: View, duration: Long): Boolean {
        return isValid(view.hashCode().toString(), duration)
    }

    /**
     * Return whether the key is not in a jitter state.
     *
     * @param key      The key.
     * @param duration The duration.
     * @return `true`: yes<br></br>`false`: no
     */
    fun isValid(key: String, duration: Long): Boolean {
        require(!TextUtils.isEmpty(key)) { "The key is null." }
        require(duration >= 0) { "The duration is less than 0." }
        val curTime = SystemClock.elapsedRealtime()
        clearIfNecessary(curTime)
        val validTime = KEY_MILLIS_MAP[key]
        if (validTime == null || curTime >= validTime) {
            KEY_MILLIS_MAP[key] = curTime + duration
            return true
        }
        return false
    }

    /**
     *
     * @param curTime Long
     */
    private fun clearIfNecessary(curTime: Long) {
        if (KEY_MILLIS_MAP.size < CACHE_SIZE) return
        val it: MutableIterator<Map.Entry<String, Long>> = KEY_MILLIS_MAP.entries.iterator()
        while (it.hasNext()) {
            val entry = it.next()
            val validTime = entry.value
            if (curTime >= validTime) {
                it.remove()
            }
        }
    }

}