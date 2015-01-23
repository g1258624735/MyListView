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
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ListView;
import android.widget.Toast;

import com.example.slidingpane.swiperefresh.ScrollViewX;

/**
 * @ClassName: SlidingPaneContentFragment
 * @Description: TODO
 * @author: raot 719055805@qq.com
 * @date: 2014年9月5日 上午10:44:01
 */
public class MainFragment extends Fragment implements OnRefreshListener {
	private SwipeRefreshLayout mSwipeRefreshWidget;

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
		listview = (ListView) inflater.inflate(R.layout.slidingpane_content_layout,
				container, false);
		mSwipeRefreshWidget = new SwipeRefreshLayout(getActivity());
		mSwipeRefreshWidget.addView(listview);
		list = new ArrayList<String>();
		list.add("0-listview刷新-ListViewRefeshFragment");
		list.add("1-scrollview刷新-ScrollViewFragment");
		list.add("2-重写listview刷新-XListViewFragment");
		list.add("3-新的API21-DetailFragment");
		adpter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_dropdown_item_1line, list);
		listview.setAdapter(adpter);
		mSwipeRefreshWidget.setColorSchemeColors(R.color.color1, R.color.color2,
				R.color.color3, R.color.color4);
		mSwipeRefreshWidget.setOnRefreshListener(this);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				FragmentTransaction transaction = getFragmentManager()
						.beginTransaction();
				if(position==0){
					ListViewRefeshFragment fragmemnt = new ListViewRefeshFragment();
					transaction.replace(R.id.slidingpane_content, fragmemnt);
				}
				if(position==1){
					ScrollViewFragment fragmemnt = new ScrollViewFragment();
					transaction.replace(R.id.slidingpane_content, fragmemnt);
				}
				if(position==2){
					XListViewFragment fragmemnt = new XListViewFragment();
					transaction.replace(R.id.slidingpane_content, fragmemnt);
				}
				if(position==3){
					DetailFragment fragmemnt = new DetailFragment();
					transaction.replace(R.id.slidingpane_content, fragmemnt);
				}
				transaction.addToBackStack(null);
				transaction.commit();
			}

		});
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
	private ArrayAdapter<String> adpter;
	private List<String> list;
	private ListView listview;

	private void refresh() {
		mHandler.removeCallbacks(mRefreshDone);
		mHandler.postDelayed(mRefreshDone, 1000);
	}

}
