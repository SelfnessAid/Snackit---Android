package com.snackit.android;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class FragmentPagerMain extends AppCompatActivity {

    FragmentStatePagerAdapter adapterViewPager;
    public CustomPager vpPager;
    ApplicationSnackit snackit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        snackit = (ApplicationSnackit) getApplication();

        setContentView(R.layout.activity_fragment_pager_main);
        vpPager = (CustomPager) findViewById(R.id.viewPagerMain);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        getSupportFragmentManager().executePendingTransactions();
        vpPager.setAdapter(adapterViewPager);
        vpPager.setCurrentItem(2);
        vpPager.setOffscreenPageLimit(5);
        vpPager.setAllowedSwipeDirection(CustomPager.SwipeDirection.none);
        // Attach the page change listener inside the activity

        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                vpPager.setAllowedSwipeDirection(CustomPager.SwipeDirection.none);

                if( position == 2){

                }else{

                }
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });
    }

    public static class MyPagerAdapter extends FragmentStatePagerAdapter {
        public static int NUM_ITEMS = 5;

        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        public void setCount(int c){
            NUM_ITEMS = c;
        }

        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return new AtHomeZwei();
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return new AtHomeEins();
                case 2: // Fragment # 1 - This will show SecondFragment
                    return new MainActivity();
                case 3: // Fragment # 1 - This will show SecondFragment
                    return new ToGoEins();
                case 4: // Fragment # 1 - This will show SecondFragment
                    return new ToGoZwei();
                default:
                    return null;
            }
        }

        public int getItemPosition(Object item) {
            return POSITION_NONE;
        }
        /*
        // Returns the page title for the top indicator
        public int getItemPosition(Object object) {

            return POSITION_NONE;
        }*/

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }
        /*
        public Parcelable saveState()
        {
            return null;
        }*/
    }
}
