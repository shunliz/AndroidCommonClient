package com.zsl.slidingmenu.fragments;

import java.util.ArrayList;
import java.util.List;

import com.zsl.entity.ContentBean;
import com.zsl.xue8.MainActivity;
import com.zsl.xue8.R;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class MainPagerContentFragment extends Fragment {
	private int mPos = -1;
	private int mImgRes;
	
	private ViewPager mViewPager;
	private static final String[] titles = {"One","Two","Three","Four","Five"};
	private List<ContentBean> list = new ArrayList<ContentBean>();
	private ContentFragmentPagerAdapter mAdapter;
	
	public MainPagerContentFragment() { }
	public MainPagerContentFragment(int pos) {
		mPos = pos;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mPos == -1 && savedInstanceState != null)
			mPos = savedInstanceState.getInt("mPos");
		View rootView = inflater.inflate(R.layout.fragment_pager_main, container, false);
        
        initData();
		switch (mPos) {
		    //left menu index
			case 1:
			case 2:
			case 3:
			default:
				mViewPager = (ViewPager) rootView.findViewById(R.id.mViewPager);
				
				PagerTabStrip mPagerTabStrip = (PagerTabStrip) rootView.findViewById(R.id.mPagerTabStrip);
				mPagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.select_text_color)); 
				
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						mAdapter = new ContentFragmentPagerAdapter(getActivity().getSupportFragmentManager(),list);
						mViewPager.setAdapter(mAdapter);
					}
				}, 100);
		}
				
		/*if (mPos == -1 && savedInstanceState != null)
			mPos = savedInstanceState.getInt("mPos");
		TypedArray imgs = getResources().obtainTypedArray(R.array.person_img);
		mImgRes = imgs.getResourceId(mPos, -1);
		
		GridView gv = (GridView) inflater.inflate(R.layout.list_grid, null);
		gv.setBackgroundResource(android.R.color.black);
		gv.setAdapter(new GridAdapter());
		gv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				if (getActivity() == null)
					return;
				MainActivity activity = (MainActivity) getActivity();
				activity.onPersonPressed(mPos);
			}			
		});*/
		return rootView;
	}
	
	@Override
	public void onStart() {
		
		if(mAdapter!=null){
			mAdapter.notifyDataSetChanged();
		}
		
		super.onStart();
	}
	
	private void initData() {
		
		for(int i=0;i<titles.length;i++){
			
			ContentBean cb = new ContentBean();
			cb.setTitle(titles[i]);
			cb.setContent(titles[i]+"_"+(i+1));
			
			list.add(cb);
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("mPos", mPos);
	}
	
	private class GridAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 30;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.grid_item, null);
			}
			ImageView img = (ImageView) convertView.findViewById(R.id.grid_item_img);
			img.setImageResource(mImgRes);
			return convertView;
		}
		
	}
	
	public class ContentFragmentPagerAdapter extends FragmentPagerAdapter {

		private List<ContentBean> list;
		public ContentFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}
		
		public ContentFragmentPagerAdapter(FragmentManager fm,List<ContentBean> list) {
			super(fm);
			this.list = list;
		}

		@Override
		public Fragment getItem(int arg0) {
			
			return MainContentFragment.newInstance(list.get(arg0).getContent());
			
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return list.get(position).getTitle();
		}

	}
}
