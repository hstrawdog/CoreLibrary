package com.hqq.core.utils

import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.util.Linkify
import android.util.SparseArray
import android.view.View
import android.view.View.OnLongClickListener
import android.view.View.OnTouchListener
import android.view.animation.AlphaAnimation
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.utils
 * @FileName :   ViewHolderUtils
 * @Date : 2018/6/13 0013  上午 9:44
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 * 与旧帮帮 BRVAH是一样的  方便 HeadView 的一些操作
 */
class ViewHolderUtils(view: View, onItemChildClick: View.OnClickListener) {
    /**
     * Views indexed with their IDs
     */
    private val views: SparseArray<View?>

    /**
     * use itemView instead
     *
     * @return the ViewHolder root view
     */
    /**
     * use itemView instead
     */
    @get:Deprecated("")
    var convertView: View
    var mOnItemChildClick: View.OnClickListener

    /**
     * Will set the text of a TextView.
     *
     * @param viewId The view id.
     * @param value  The text to put in the text view.
     * @return The ViewHolderUtils for chaining.
     */
    fun setText(@IdRes viewId: Int, value: CharSequence?): ViewHolderUtils {
        val view = getView<TextView>(viewId)!!
        view.text = value
        return this
    }

    fun setText(@IdRes viewId: Int, @StringRes strId: Int): ViewHolderUtils {
        val view = getView<TextView>(viewId)!!
        view.setText(strId)
        return this
    }

    /**
     * Will set the image of an ImageView from a resource id.
     *
     * @param viewId     The view id.
     * @param imageResId The image resource id.
     * @return The ViewHolderUtils for chaining.
     */
    fun setImageResource(@IdRes viewId: Int, @DrawableRes imageResId: Int): ViewHolderUtils {
        val view = getView<ImageView>(viewId)!!
        view.setImageResource(imageResId)
        return this
    }

    /**
     * Will set background color of a view.
     *
     * @param viewId The view id.
     * @param color  A color, not a resource id.
     * @return The ViewHolderUtils for chaining.
     */
    fun setBackgroundColor(@IdRes viewId: Int, @ColorInt color: Int): ViewHolderUtils {
        val view = getView<View>(viewId)!!
        view.setBackgroundColor(color)
        return this
    }

    /**
     * Will set background of a view.
     *
     * @param viewId        The view id.
     * @param backgroundRes A resource to use as a background.
     * @return The ViewHolderUtils for chaining.
     */
    fun setBackgroundRes(@IdRes viewId: Int, @DrawableRes backgroundRes: Int): ViewHolderUtils {
        val view = getView<View>(viewId)!!
        view.setBackgroundResource(backgroundRes)
        return this
    }

    /**
     * Will set text color of a TextView.
     *
     * @param viewId    The view id.
     * @param textColor The text color (not a resource id).
     * @return The ViewHolderUtils for chaining.
     */
    fun setTextColor(@IdRes viewId: Int, @ColorInt textColor: Int): ViewHolderUtils {
        val view = getView<TextView>(viewId)!!
        view.setTextColor(textColor)
        return this
    }

    /**
     * Will set the image of an ImageView from a drawable.
     *
     * @param viewId   The view id.
     * @param drawable The image drawable.
     * @return The ViewHolderUtils for chaining.
     */
    fun setImageDrawable(@IdRes viewId: Int, drawable: Drawable?): ViewHolderUtils {
        val view = getView<ImageView>(viewId)!!
        view.setImageDrawable(drawable)
        return this
    }

    /**
     * Add an action to set the image of an image view. Can be called multiple times.
     */
    fun setImageBitmap(@IdRes viewId: Int, bitmap: Bitmap?): ViewHolderUtils {
        val view = getView<ImageView>(viewId)!!
        view.setImageBitmap(bitmap)
        return this
    }

    /**
     * Add an action to set the alpha of a view. Can be called multiple times.
     * Alpha between 0-1.
     */
    fun setAlpha(@IdRes viewId: Int, value: Float): ViewHolderUtils {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView<View>(viewId)!!.alpha = value
        } else {
            // Pre-honeycomb hack to set Alpha value
            val alpha = AlphaAnimation(value, value)
            alpha.duration = 0
            alpha.fillAfter = true
            getView<View>(viewId)!!.startAnimation(alpha)
        }
        return this
    }

    /**
     * Set a view visibility to VISIBLE (true) or GONE (false).
     *
     * @param viewId  The view id.
     * @param visible True for VISIBLE, false for GONE.
     * @return The ViewHolderUtils for chaining.
     */
    fun setGone(@IdRes viewId: Int, visible: Boolean): ViewHolderUtils {
        val view = getView<View>(viewId)!!
        view.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    /**
     * Set a view visibility to VISIBLE (true) or INVISIBLE (false).
     *
     * @param viewId  The view id.
     * @param visible True for VISIBLE, false for INVISIBLE.
     * @return The ViewHolderUtils for chaining.
     */
    fun setVisible(@IdRes viewId: Int, visible: Boolean): ViewHolderUtils {
        val view = getView<View>(viewId)!!
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
        return this
    }

    /**
     * Add links into a TextView.
     *
     * @param viewId The id of the TextView to linkify.
     * @return The ViewHolderUtils for chaining.
     */
    fun linkify(@IdRes viewId: Int): ViewHolderUtils {
        val view = getView<TextView>(viewId)!!
        Linkify.addLinks(view, Linkify.ALL)
        return this
    }

    /**
     * Apply the typeface to the given viewId, and enable subpixel rendering.
     */
    fun setTypeface(@IdRes viewId: Int, typeface: Typeface?): ViewHolderUtils {
        val view = getView<TextView>(viewId)!!
        view.typeface = typeface
        view.paintFlags = view.paintFlags or Paint.SUBPIXEL_TEXT_FLAG
        return this
    }

    /**
     * Apply the typeface to all the given viewIds, and enable subpixel rendering.
     */
    fun setTypeface(typeface: Typeface?, vararg viewIds: Int): ViewHolderUtils {
        for (viewId in viewIds) {
            val view = getView<TextView>(viewId)!!
            view.typeface = typeface
            view.paintFlags = view.paintFlags or Paint.SUBPIXEL_TEXT_FLAG
        }
        return this
    }

    /**
     * Sets the progress of a ProgressBar.
     *
     * @param viewId   The view id.
     * @param progress The progress.
     * @return The ViewHolderUtils for chaining.
     */
    fun setProgress(@IdRes viewId: Int, progress: Int): ViewHolderUtils {
        val view = getView<ProgressBar>(viewId)!!
        view.progress = progress
        return this
    }

    /**
     * Sets the progress and max of a ProgressBar.
     *
     * @param viewId   The view id.
     * @param progress The progress.
     * @param max      The max value of a ProgressBar.
     * @return The ViewHolderUtils for chaining.
     */
    fun setProgress(@IdRes viewId: Int, progress: Int, max: Int): ViewHolderUtils {
        val view = getView<ProgressBar>(viewId)!!
        view.max = max
        view.progress = progress
        return this
    }

    /**
     * Sets the range of a ProgressBar to 0...max.
     *
     * @param viewId The view id.
     * @param max    The max value of a ProgressBar.
     * @return The ViewHolderUtils for chaining.
     */
    fun setMax(@IdRes viewId: Int, max: Int): ViewHolderUtils {
        val view = getView<ProgressBar>(viewId)!!
        view.max = max
        return this
    }

    /**
     * Sets the rating (the number of stars filled) of a RatingBar.
     *
     * @param viewId The view id.
     * @param rating The rating.
     * @return The ViewHolderUtils for chaining.
     */
    fun setRating(@IdRes viewId: Int, rating: Float): ViewHolderUtils {
        val view = getView<RatingBar>(viewId)!!
        view.rating = rating
        return this
    }

    /**
     * Sets the rating (the number of stars filled) and max of a RatingBar.
     *
     * @param viewId The view id.
     * @param rating The rating.
     * @param max    The range of the RatingBar to 0...max.
     * @return The ViewHolderUtils for chaining.
     */
    fun setRating(@IdRes viewId: Int, rating: Float, max: Int): ViewHolderUtils {
        val view = getView<RatingBar>(viewId)!!
        view.max = max
        view.rating = rating
        return this
    }

    /**
     * Sets the on click listener of the view.
     *
     * @param viewId   The view id.
     * @param listener The on click listener;
     * @return The ViewHolderUtils for chaining.
     */
    @Deprecated("")
    fun setOnClickListener(@IdRes viewId: Int, listener: View.OnClickListener?): ViewHolderUtils {
        val view = getView<View>(viewId)!!
        view.setOnClickListener(listener)
        return this
    }

    /**
     * Sets the on touch listener of the view.
     *
     * @param viewId   The view id.
     * @param listener The on touch listener;
     * @return The ViewHolderUtils for chaining.
     */
    @Deprecated("")
    fun setOnTouchListener(@IdRes viewId: Int, listener: OnTouchListener?): ViewHolderUtils {
        val view = getView<View>(viewId)!!
        view.setOnTouchListener(listener)
        return this
    }

    /**
     * Sets the on long click listener of the view.
     *
     * @param viewId   The view id.
     * @param listener The on long click listener;
     * @return The ViewHolderUtils for chaining.
     * Please use [.] (adapter.setOnItemChildLongClickListener(listener))}
     */
    @Deprecated("")
    fun setOnLongClickListener(@IdRes viewId: Int, listener: OnLongClickListener?): ViewHolderUtils {
        val view = getView<View>(viewId)!!
        view.setOnLongClickListener(listener)
        return this
    }

    /**
     * Sets the listview or gridview's item click listener of the view
     *
     * @param viewId   The view id.
     * @param listener The item on click listener;
     * @return The ViewHolderUtils for chaining.
     * Please use [.addOnClickListener] (int)} (adapter.setOnItemChildClickListener(listener))}
     */
    @Deprecated("")
    fun setOnItemClickListener(@IdRes viewId: Int, listener: AdapterView.OnItemClickListener?): ViewHolderUtils {
        val view = getView<AdapterView<*>>(viewId)!!
        view.onItemClickListener = listener
        return this
    }

    /**
     * Sets the listview or gridview's item long click listener of the view
     *
     * @param viewId   The view id.
     * @param listener The item long click listener;
     * @return The ViewHolderUtils for chaining.
     */
    fun setOnItemLongClickListener(@IdRes viewId: Int, listener: AdapterView.OnItemLongClickListener?): ViewHolderUtils {
        val view = getView<AdapterView<*>>(viewId)!!
        view.onItemLongClickListener = listener
        return this
    }

    /**
     * Sets the listview or gridview's item selected click listener of the view
     *
     * @param viewId   The view id.
     * @param listener The item selected click listener;
     * @return The ViewHolderUtils for chaining.
     */
    fun setOnItemSelectedClickListener(@IdRes viewId: Int, listener: OnItemSelectedListener?): ViewHolderUtils {
        val view = getView<AdapterView<*>>(viewId)!!
        view.onItemSelectedListener = listener
        return this
    }

    /**
     * Sets the on checked change listener of the view.
     *
     * @param viewId   The view id.
     * @param listener The checked change listener of compound button.
     * @return The ViewHolderUtils for chaining.
     */
    fun setOnCheckedChangeListener(@IdRes viewId: Int, listener: CompoundButton.OnCheckedChangeListener?): ViewHolderUtils {
        val view = getView<CompoundButton>(viewId)!!
        view.setOnCheckedChangeListener(listener)
        return this
    }

    /**
     * Sets the tag of the view.
     *
     * @param viewId The view id.
     * @param tag    The tag;
     * @return The ViewHolderUtils for chaining.
     */
    fun setTag(@IdRes viewId: Int, tag: Any?): ViewHolderUtils {
        val view = getView<View>(viewId)!!
        view.tag = tag
        return this
    }

    /**
     * Sets the tag of the view.
     *
     * @param viewId The view id.
     * @param key    The key of tag;
     * @param tag    The tag;
     * @return The ViewHolderUtils for chaining.
     */
    fun setTag(@IdRes viewId: Int, key: Int, tag: Any?): ViewHolderUtils {
        val view = getView<View>(viewId)!!
        view.setTag(key, tag)
        return this
    }

    /**
     * Sets the checked status of a checkable.
     *
     * @param viewId  The view id.
     * @param checked The checked status;
     * @return The ViewHolderUtils for chaining.
     */
    fun setChecked(@IdRes viewId: Int, checked: Boolean): ViewHolderUtils {
        val view = getView<View>(viewId)!!
        // View unable cast to Checkable
        if (view is Checkable) {
            (view as Checkable).isChecked = checked
        }
        return this
    }

    /**
     * Sets the adapter of a adapter view.
     *
     * @param viewId  The view id.
     * @param adapter The adapter;
     * @return The ViewHolderUtils for chaining.
     */
    fun setAdapter(@IdRes viewId: Int, adapter: Adapter?): ViewHolderUtils {
        val view = getView<AdapterView<Adapter>>(viewId)!!
        view.setAdapter(adapter)
        return this
    }

    fun <T : View?> getView(@IdRes viewId: Int): T? {
        var view = views[viewId]
        if (view == null) {
            view = convertView.findViewById(viewId)
            views.put(viewId, view)
        }
        return view as T?
    }

    /**
     * add childView id
     *
     * @param viewId add the child view id   can support childview click
     * @return if you use adapter bind listener
     * @link {(adapter.setOnItemChildClickListener(listener))}
     *
     *
     * or if you can use  recyclerView.addOnItemTouch(listerer)  wo also support this menthod
     */
    fun addOnClickListener(@IdRes viewId: Int): ViewHolderUtils {
        val view = getView<View>(viewId)
        if (view != null) {
            if (!view.isClickable) {
                view.isClickable = true
            }
            view.setOnClickListener(mOnItemChildClick)
        }
        return this
    }

    init {
        views = SparseArray()
        convertView = view
        mOnItemChildClick = onItemChildClick
    }
}