package com.zsl.slidingmenu.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.zsl.adapter.ListViewAdapter;
import com.zsl.widget.AutoListView;
import com.zsl.widget.AutoListView.OnLoadListener;
import com.zsl.widget.AutoListView.OnRefreshListener;
import com.zsl.xue8.R;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;


public class MainContentFragment extends Fragment  implements OnRefreshListener,
			OnLoadListener {
	private static final String TAG = MainContentFragment.class.getSimpleName();
	private String title = "Hello";
	private ViewFlipper viewFlipper = null;
	
	private AutoListView lstv;
	private ListView lst;
	private ListViewAdapter adapter;
	private List<String> list = new ArrayList<String>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			List<String> result = (List<String>) msg.obj;
			switch (msg.what) {
			case AutoListView.REFRESH:
				lstv.onRefreshComplete();
				list.clear();
				list.addAll(result);
				break;
			case AutoListView.LOAD:
				lstv.onLoadComplete();
				list.addAll(result);
				break;
			}
			lstv.setResultSize(result.size());
			adapter.notifyDataSetChanged();
		};
	};
	
	
	public static MainContentFragment newInstance(String s) {
		MainContentFragment newFragment = new MainContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", s);
        newFragment.setArguments(bundle);
        
        return newFragment;

    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		Log.d(TAG, "TestContentFragment-----onCreate");
        Bundle args = getArguments();
        if(args!=null){
        	title = args.getString("title");
        }
		
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		 View rootView = inflater.inflate(R.layout.fragment_main_content, container, false);
         
		 findView(rootView);
		 
	     return rootView;
	}

	private void findView(View rootView) {
		
		final Animation in = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.in_alpha);

		final Animation out = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.out_alpha);

		viewFlipper = (ViewFlipper) rootView.findViewById(R.id.viewFlipper);
		
		new CountDownTimer(10000,50000){

			@Override

			public void onFinish() {

			viewFlipper.setInAnimation(in);

			viewFlipper.setOutAnimation(out);

			viewFlipper.showNext();

			start();

			}

			@Override

			public void onTick(long millisUntilFinished) {}

			}.start();
		
		lstv = (AutoListView) rootView.findViewById(R.id.lstv);
		adapter = new ListViewAdapter(getActivity(), list);
		lstv.setAdapter(adapter);
		lstv.setOnRefreshListener(this);
		lstv.setOnLoadListener(this);
		initListViewData();
	}
	
	private void initListViewData() {
		loadData(AutoListView.REFRESH);
	}
	
	private void loadData(final int what) {
		// ����ģ��ӷ�������ȡ���
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(700);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg = handler.obtainMessage();
				msg.what = what;
				msg.obj = getData();
				handler.sendMessage(msg);
			}
		}).start();
	}
	
	// �������
	public List<String> getData() {
		List<String> result = new ArrayList<String>();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			long l = random.nextInt(10000);
			result.add("item" + l);
		}
		return result;
	}

	@Override
	public void onLoad() {
		loadData(AutoListView.REFRESH);
	}

	@Override
	public void onRefresh() {
		loadData(AutoListView.LOAD);
	}
}
