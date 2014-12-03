package com.hand.srm.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class MyLayout extends LinearLayout {

	public MyLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyLayout(Context context) {
		super(context);
	}

	private OnSoftKeyboardListener onSoftKeyboardListener;

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO 自动生成的方法存根
		super.onSizeChanged(w, h, oldw, oldh);
	}
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO 自动生成的方法存根
		super.onLayout(changed, l, t, r, b);
	}
	@Override
	protected void onMeasure(final int widthMeasureSpec,
			final int heightMeasureSpec) {
		if (onSoftKeyboardListener != null) {
			int newSpec = MeasureSpec.getSize(heightMeasureSpec);
			int oldSpec = getMeasuredHeight();
			if (oldSpec > newSpec) {
				onSoftKeyboardListener.onShown();
			} else {
				onSoftKeyboardListener.onHidden();
			}
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public final void setOnSoftKeyboardListener(
			final OnSoftKeyboardListener listener) {
		this.onSoftKeyboardListener = listener;
	}

	public interface OnSoftKeyboardListener {
		public void onShown();

		public void onHidden();
	}

}