package com.gdj.myview.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TranslateFragment extends Fragment {
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		Bundle bundle = getArguments();
		int layoutId = bundle.getInt("layoutId");
		int pageIndex = bundle.getInt("pageIndex");
		
		View view = inflater.inflate(layoutId, null);
		view.setTag(pageIndex);
		return view;
	}

}
