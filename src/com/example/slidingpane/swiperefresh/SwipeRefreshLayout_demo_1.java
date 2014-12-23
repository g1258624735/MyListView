/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.slidingpane.swiperefresh;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.slidingpane.SwipeProgressBar_;
import com.nineoldandroids.view.ViewHelper;

/**
 * 测试 下拉刷新demo 仿官方的
 * 
 * @param <mProgressBar>
 */
public class SwipeRefreshLayout_demo_1 extends ViewGroup {
	private static final float PROGRESS_BAR_HEIGHT = 4;
	private OnRefreshListener mListener;
	private boolean mRefreshing = false;
	private boolean mReturningToStart = true;
	private MotionEvent mDownEvent;
	private float mPrevY;
	private int mProgressBarHeight;
	private SwipeProgressBar_ mProgressBar;
	private static final int[] LAYOUT_ATTRS = new int[] { android.R.attr.enabled };

	/**
	 * Simple constructor to use when creating a SwipeRefreshLayout from code
	 */
	public SwipeRefreshLayout_demo_1(Context context) {
		this(context, null);
	}

	/**
	 * Constructor that is called when inflating SwipeRefreshLayout from XML.
	 * @param context
	 * @param attrs
	 */
	public SwipeRefreshLayout_demo_1(Context context, AttributeSet attrs) {
		super(context, attrs);
		metrics = getResources().getDisplayMetrics();
		mProgressBarHeight = (int) (metrics.density * PROGRESS_BAR_HEIGHT);
		setWillNotDraw(false);
		final TypedArray a = context
				.obtainStyledAttributes(attrs, LAYOUT_ATTRS);
		setEnabled(a.getBoolean(0, true));
		a.recycle();
	}
@Override
protected void onFinishInflate() {
	super.onFinishInflate();
	listview = (ListView) getChildAt(0);
	if(listview!=null){
		Log.i("onFinishInflate", "listview初始化");
	}
	
}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mProgressBar.draw(canvas);
	}

	/**
	 * Set the listener to be notified when a refresh is triggered via the swipe
	 * gesture.
	 */
	public void setOnRefreshListener(OnRefreshListener listener) {
		mListener = listener;
	}

	/**
	 * Notify the widget that refresh state has changed. Do not call this when
	 * refresh is triggered by a swipe gesture.
	 * 
	 * @param refreshing
	 *            Whether or not the view should show refresh progress.
	 */
	public void setRefreshing(boolean refreshing) {
		if (mRefreshing != refreshing) {
			mRefreshing = refreshing;
			if (mRefreshing) {
				 mProgressBar.start();
			} else {
				 mProgressBar.stop();
			}
		}
	}

	private View mTarget;
	private DisplayMetrics metrics;
	private ListView listview;

	/**
	 * 使用此方法能进行view的上下移动
	 * @param offset
	 */
	private void setTargetOffsetTopAndBottom(int offset) {
		// Log.i("setTargetOffsetTopAndBottom-offset---", offset + "");
		mTarget.offsetTopAndBottom(offset);
	}

	/**
	 * @return Whether the SwipeRefreshWidget is actively showing refresh
	 *         progress.
	 */
	public boolean isRefreshing() {
		return mRefreshing;
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}
	 public void setColorScheme(int colorRes1, int colorRes2, int colorRes3, int colorRes4) {
//	        ensureTarget();
	        final Resources res = getResources();
	        final int color1 = res.getColor(colorRes1);
	        final int color2 = res.getColor(colorRes2);
	        final int color3 = res.getColor(colorRes3);
	        final int color4 = res.getColor(colorRes4);
	        mProgressBar.setColorScheme(color1, color2, color3,color4);
	    }
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		final int width = getMeasuredWidth();
		final int height = getMeasuredHeight();
		if (getChildCount() == 0) {
			return;
		}
		mProgressBar.setBounds(0, 0, width, mProgressBarHeight);
		mTarget = getChildAt(0);
		final View child = getChildAt(0);
		final int childLeft = getPaddingLeft();
		final int childTop = 10 + getPaddingTop();
		final int childWidth = width - getPaddingLeft() - getPaddingRight();
		final int childHeight = height - getPaddingTop() - getPaddingBottom();
		child.layout(childLeft, childTop, childLeft + childWidth, childTop
				+ childHeight);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (getChildCount() > 1 && !isInEditMode()) {
			throw new IllegalStateException(
					"SwipeRefreshLayout can host only one direct child");
		}
		if (getChildCount() > 0) {
			getChildAt(0).measure(
					MeasureSpec.makeMeasureSpec(getMeasuredWidth()
							- getPaddingLeft() - getPaddingRight(),
							MeasureSpec.EXACTLY),
					MeasureSpec.makeMeasureSpec(getMeasuredHeight()
							- getPaddingTop() - getPaddingBottom(),
							MeasureSpec.EXACTLY));
		}
	}

	/**
	 * @return Whether it is possible for the child view of this layout to
	 *         scroll up. Override this if the child view is a custom view.
	 * 
	 */
	public boolean canChildScrollUp() {
		if (android.os.Build.VERSION.SDK_INT < 14) {
			if (mTarget instanceof AbsListView) {
				final AbsListView absListView = (AbsListView) mTarget;
				return absListView.getChildCount() > 0
						&& (absListView.getFirstVisiblePosition() > 0 || absListView
								.getChildAt(0).getTop() < absListView
								.getPaddingTop());
			} else {
				return mTarget.getScrollY() > 0;
			}
		} else {
			return ViewCompat.canScrollVertically(mTarget, -1);
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		boolean handled = false;
		if (mReturningToStart && ev.getAction() == MotionEvent.ACTION_DOWN) {
			mReturningToStart = false;
		}
		if (isEnabled() && !mReturningToStart && !canChildScrollUp()) {
			handled = onTouchEvent(ev);
		}
		return !handled ? super.onInterceptTouchEvent(ev) : handled;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		boolean handled = false;
		float yDiff = 0;
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mPrevY = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float eventY = event.getY();
			yDiff = eventY - mPrevY;
			if (yDiff > 0) {
				ViewHelper.setPivotX(mTarget, mTarget.getMeasuredWidth() / 2);
				ViewHelper.setPivotY(mTarget, 0);
				ViewHelper.setTranslationY(mTarget, yDiff);
//				 mProgressBar.setTriggerPercentage((float)(yDiff/metrics.widthPixels));
				 
				
				 if(yDiff>=metrics.widthPixels){
					 startRefresh();
				 }
			}
			break;
		case MotionEvent.ACTION_UP:
			ViewHelper.setTranslationY(mTarget, yDiff);
			if(!isRefreshing()){
//				mProgressBar.setTriggerPercentage(0);
				
			}
		
			break;
		case MotionEvent.ACTION_CANCEL:
			if (mDownEvent != null) {
				mDownEvent.recycle();
				mDownEvent = null;
			}
			break;
		}
		return handled;
	}

	private void startRefresh() {
		setRefreshing(true);
		if (mListener != null)
			mListener.onRefresh();
	}

	/**
	 * Classes that wish to be notified when the swipe gesture correctly
	 * triggers a refresh should implement this interface.
	 */
	public interface OnRefreshListener {
		public void onRefresh();
	}

}