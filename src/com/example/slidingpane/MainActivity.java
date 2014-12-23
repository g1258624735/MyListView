/**   
 * Copyright © 2014 All rights reserved.
 * 
 * @Title: SlidingPaneMainActivity.java 
 * @Prject: SlidingPane
 * @Package: com.example.slidingpane 
 * @Description: TODO
 * @author: raot  719055805@qq.com
 * @date: 2014年9月5日 上午10:39:59 
 * @version: V1.0   
 */
package com.example.slidingpane;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;

import com.example.slidingpane.SlidingPaneLayout_g.PanelSlideListener;
/**
 * @ClassName: SlidingPaneMainActivity
 * @Description: TODO
 * @author: raot 719055805@qq.com
 * @date: 2014年9月5日 上午10:39:59
 */
public class MainActivity extends Activity {
	private SlidingPaneLayout_g slidingPaneLayout;
	private MenuFragment menuFragment;
	private ContentFragment contentFragment;
	private DisplayMetrics displayMetrics = new DisplayMetrics();
	private int maxMargin = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		setContentView(R.layout.slidingpane_main_layout);
		slidingPaneLayout = (SlidingPaneLayout_g) findViewById(R.id.slidingpanellayout);
		menuFragment = new MenuFragment();
		contentFragment = new ContentFragment();
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.slidingpane_menu, menuFragment);
		transaction.replace(R.id.slidingpane_content, contentFragment);
		transaction.commit();
		maxMargin = displayMetrics.heightPixels / 10;
		slidingPaneLayout.setPanelSlideListener(new PanelSlideListener() {

			@Override
			public void onPanelSlide(View panel, float slideOffset) {
				// TODO Auto-generated method stub
				int contentMargin = (int) (slideOffset * maxMargin);
				FrameLayout.LayoutParams contentParams = contentFragment
						.getCurrentViewParams();
				contentParams.setMargins(0, contentMargin, 0, contentMargin);
				contentFragment.setCurrentViewPararms(contentParams);

				float scale = 1 - ((1 - slideOffset) * maxMargin * 2)
						/ (float) displayMetrics.heightPixels;
				menuFragment.getCurrentView().setScaleX(scale);// 设置缩放的基准点
				menuFragment.getCurrentView().setScaleY(scale);// 设置缩放的基准点
				menuFragment.getCurrentView().setPivotX(0);// 设置缩放和选择的点
				menuFragment.getCurrentView().setPivotY(
						displayMetrics.heightPixels / 2);
				menuFragment.getCurrentView().setAlpha(slideOffset);
			}

			@Override
			public void onPanelOpened(View panel) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPanelClosed(View panel) {
				// TODO Auto-generated method stub

			}
		});

	}

	/**
	 * @return the slidingPaneLayout
	 */
	public SlidingPaneLayout_g getSlidingPaneLayout() {
		return slidingPaneLayout;
	}

}
