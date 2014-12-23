/**   
 * Copyright © 2014 All rights reserved.
 * 
 * @Title: SlidingPaneContentFragment.java 
 * @Prject: SlidingPane
 * @Package: com.example.slidingpane 
 * @Description: TODO
 * @author: raot  719055805@qq.com
 * @date: 2014年9月5日 上午10:44:01 
 * @version: V1.0   
 */
package com.example.slidingpane;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.Toast;

import com.example.slidingpane.swiperefresh.ScrollViewX;
import com.example.slidingpane.swiperefresh.SwipeRefreshLayout_scrollView_3;
import com.example.slidingpane.swiperefresh.SwipeRefreshLayout_scrollView_3.OnRefreshListener;
import com.example.slidingpane.swiperefresh.SwipeRefreshLayout_scrollView_3.loadMoreInface;

/**
 * scrollview 上下拉刷新
 */
public class ScrollViewFragment extends Fragment implements OnRefreshListener {
	private SwipeRefreshLayout_scrollView_3<Object> mSwipeRefreshWidget;

	public void setCurrentViewPararms(FrameLayout.LayoutParams layoutParams) {
		mSwipeRefreshWidget.setLayoutParams(layoutParams);
	}

	public FrameLayout.LayoutParams getCurrentViewParams() {
		return (LayoutParams) mSwipeRefreshWidget.getLayoutParams();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mScrollView = (ScrollViewX) inflater.inflate(R.layout.layout_scrollview,
				container, false);
		mSwipeRefreshWidget = new SwipeRefreshLayout_scrollView_3<Object>(getActivity());
		mSwipeRefreshWidget.addScrollView(mScrollView);
		mSwipeRefreshWidget.setLoadMore(new loadMoreInface() {
			@Override
			public void loadMore() {
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						mSwipeRefreshWidget.stopLoadMore();
					}
				}, 2000);
				
			}
		});
		mSwipeRefreshWidget.setColorScheme(R.color.color1, R.color.color2,
				R.color.color3, R.color.color4);
		mSwipeRefreshWidget.setOnRefreshListener(this);
		return mSwipeRefreshWidget;
	}

	@Override
	public void onRefresh() {
		refresh();
	}

	private Handler mHandler = new Handler();
	private final Runnable mRefreshDone = new Runnable() {

		@Override
		public void run() {
			mSwipeRefreshWidget.setRefreshing(false);
//			adpter.notifyDataSetChanged();
		}

	};
	private ScrollViewX mScrollView;
	private ArrayAdapter<String> adpter;
	private List<String> list;

	private void refresh() {
		mHandler.removeCallbacks(mRefreshDone);
		mHandler.postDelayed(mRefreshDone, 1000);
	}

}
