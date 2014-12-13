package com.zsl.slidingmenu.fragments;

import com.zsl.xue8.MainActivity;
import com.zsl.xue8.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PersonMenuFragment extends ListFragment {
	
	
	public class MyListAdapter extends ArrayAdapter<String> {
		private Context myContext = null;
		
		public MyListAdapter(Context context, int textViewResourceId, 
				String[] objects) {
		   super(context, textViewResourceId, objects);
		   myContext = context;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// return super.getView(position, convertView, parent);

			String[] pepole = getResources().getStringArray(R.array.pepole);
			LayoutInflater inflater = (LayoutInflater) myContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row = inflater.inflate(R.layout.menu_left_row, parent, false);
			TextView label = (TextView) row.findViewById(R.id.left_menu_row);
			label.setText(pepole[position]);
			TextView suffix = (TextView) row.findViewById(R.id.left_menu_row_suffix);
			suffix.setText(position%2==0?"50+":"");
			ImageView icon = (ImageView) row.findViewById(R.id.icon);

			// Customize your icon here
			icon.setImageResource(R.drawable.ic_launcher);

			return row;
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		String[] pepole = getResources().getStringArray(R.array.pepole);
		MyListAdapter myListAdapter = new MyListAdapter(getActivity(),
				R.layout.menu_left_row, pepole);
		setListAdapter(myListAdapter);
	}
	
	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Fragment newContent = new MainPagerContentFragment(position);
		if (newContent != null)
			switchFragment(newContent);
	}
	
	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof MainActivity) {
			MainActivity ra = (MainActivity) getActivity();
			ra.switchContent(fragment);
		}
	}
}
