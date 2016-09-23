package com.otassistant.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.BaseSliderView.OnSliderClickListener;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.innodroid.expandablerecycler.ExpandableRecyclerAdapter;
import com.otassistant.R;
import com.otassistant.adapter.Dashboard_category;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ankit on 19/09/16.
 */
public class DashboardFragment extends android.support.v4.app.Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private View parentView;
    Dashboard_category adapter;
    @Bind(R.id.main_recycler)
    RecyclerView recycler;
    @Bind(R.id.slider)
    SliderLayout mDemoSlider;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        populate();
        return parentView;
    }

    private void populate() {
        ButterKnife.bind(this, parentView);
        adapter = new Dashboard_category(getContext());
        adapter.setMode(ExpandableRecyclerAdapter.MODE_ACCORDION);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);
        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Stay Healthy Stay Wow !", "http://www.pomgen.gov.pg/wp-content/uploads/2015/06/Screen-Shot-2015-06-19-at-10.48.37-am.png");
        url_maps.put("Plan Your Day", "https://healthyridepgh.com/wp-content/uploads/sites/3/2015/08/HPC-Friends-01.png");
        url_maps.put("Emergency Calling", "http://www.standupforwomenssafety.org/images/stand-up-for-womens-safety.png");
        url_maps.put("Daily Tips", "http://whysolution.com/wp-content/uploads/2016/07/morning-walk-qiw.png");
/*
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Paper Notes", R.drawable.logo2);
        file_maps.put("Grab Mobiles", R.drawable.logo3);
        file_maps.put("Grab Accessories", R.drawable.logo4);
        file_maps.put("Grab Living Accessories", R.drawable.logo5);
        file_maps.put("Grab Laptops", R.drawable.logo1); */

        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
