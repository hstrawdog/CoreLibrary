package com.easy.core.background.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.helper.widget.Flow;

import com.easy.core.background.BackgroundFactory;

public class BLFlow extends Flow {

	public BLFlow(Context context) {
		super(context);
	}

	public BLFlow(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public BLFlow(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		BackgroundFactory.setViewBackground(context, attrs, this);
	}
}
