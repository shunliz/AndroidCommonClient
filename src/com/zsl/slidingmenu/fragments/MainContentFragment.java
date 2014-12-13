package com.zsl.slidingmenu.fragments;

import com.zsl.xue8.R;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;


public class MainContentFragment extends Fragment {
	private static final String TAG = MainContentFragment.class.getSimpleName();
	private String title = "Hello";
	private ViewFlipper viewFlipper = null;
	
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
		
		TextView txtLabel = (TextView) rootView.findViewById(R.id.txtLabel);
		txtLabel.setText(title);
		
		final Animation in = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.in_alpha);

		final Animation out = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.out_alpha);

		viewFlipper = (ViewFlipper) rootView.findViewById(R.id.viewFlipper);
		
		new CountDownTimer(10000,100000){

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
	}
}
