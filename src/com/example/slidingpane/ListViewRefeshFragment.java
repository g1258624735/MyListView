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

import java.util.ArrayList;
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
import android.widget.ListView;
import android.widget.Toast;

import com.example.slidingpane.swiperefresh.SwipeRefreshLayout_listview_2;
import com.example.slidingpane.swiperefresh.SwipeRefreshLayout_listview_2.OnRefreshListener;
import com.example.slidingpane.swiperefresh.SwipeRefreshLayout_listview_2.loadMoreInface;
/**
 * 
 * listview 上下拉刷新
 */
public class ListViewRefeshFragment extends Fragment implements OnRefreshListener {
	private SwipeRefreshLayout_listview_2<Object> mSwipeRefreshWidget;

	public void setCurrentViewPararms(FrameLayout.LayoutParams layoutParams) {
		mSwipeRefreshWidget.setLayoutParams(layoutParams);
	}

	public FrameLayout.LayoutParams getCurrentViewParams() {
		return (LayoutParams) mSwipeRefreshWidget.getLayoutParams();
	}
	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mlistview = (ListView) inflater.inflate(R.layout.slidingpane_content_layout,
				container, false);
		mSwipeRefreshWidget = new SwipeRefreshLayout_listview_2<Object>(getActivity());
		mSwipeRefreshWidget.addView(mlistview);
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
		list = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			list.add("我是程序员!");
		}
		adpter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_dropdown_item_1line, list);
		mlistview.setAdapter(adpter);
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
		}
	};
	private ListView mlistview;
	private ArrayAdapter<String> adpter;
	private List<String> list;

	private void refresh() {
		mHandler.removeCallbacks(mRefreshDone);
		mHandler.postDelayed(mRefreshDone, 1000);
	}

}
