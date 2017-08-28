package com.gdj.myview.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.gdj.myview.R;
import com.gdj.myview.ui.fragment.NewsDetailFragment;

/**TabLayout 和viewpager**/
public class CVActivity extends AppCompatActivity {

	private TabLayout tabLayout;
	private String[] title = {
		"1",
		"2",
		"3",
		"4",
		"5",
		"6",
		"7",
		"9",
		"8",
		"0"
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cv);
		
		final ViewPager viewPager = (ViewPager) findViewById(R.id.vp);
		tabLayout = (TabLayout)findViewById(R.id.tablayout);
		MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
		//1.TabLayout��Viewpager����
		tabLayout.setOnTabSelectedListener(new OnTabSelectedListener() {
			
			@Override
			public void onTabUnselected(Tab arg0) {

			}
			
			@Override
			public void onTabSelected(Tab tab) {
				// ��ѡ�е�ʱ��ص�
				viewPager.setCurrentItem(tab.getPosition(),true);
			}
			
			@Override
			public void onTabReselected(Tab arg0) {

			}
		});
		//2.ViewPager��������tabLayout
		viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
		
		
		//����tabLayout�ı�ǩ������PagerAdapter
		tabLayout.setTabsFromPagerAdapter(adapter);
		
		viewPager.setAdapter(adapter);
	}
	
	class MyPagerAdapter extends FragmentPagerAdapter{

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return title[position];
		}

		@Override
		public Fragment getItem(int position) {
			Fragment f = new NewsDetailFragment();
			Bundle bundle = new Bundle();
			bundle.putString("title", title[position]);
			f.setArguments(bundle);
			return f;
		}

		@Override
		public int getCount() {
			return title.length;
		}
		
	}
	
	
	
}
