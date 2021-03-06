/*
 *
 *
 *  * Copyright © 2016, Mobilyte Inc. and/or its affiliates. All rights reserved.
 *  *
 *  * Redistribution and use in source and binary forms, with or without
 *  * modification, are permitted provided that the following conditions are met:
 *  *
 *  * - Redistributions of source code must retain the above copyright
 *  *    notice, this list of conditions and the following disclaimer.
 *  *
 *  * - Redistributions in binary form must reproduce the above copyright
 *  * notice, this list of conditions and the following disclaimer in the
 *  * documentation and/or other materials provided with the distribution.
 *
 * /
 */

package com.android.dezi.views.passenger.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dezi.BaseActivity;
import com.android.dezi.R;
import com.android.dezi.adapters.TripHistoryAdapter;
import com.android.dezi.beans.TripHistoryBean;
import com.android.dezi.utility.CommonMethods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anuj.sharma on 3/29/2016.
 */
public class PassengerTripHistoryFragment extends Fragment implements View.OnClickListener{
    Context mContext;
    View rootView;

    LinearLayout mParent;
    RecyclerView mRecyclerView;
    TextView mSearchTV;
    TripHistoryAdapter mAdapter = null;

    LinearLayoutManager llm;
    private boolean loading = true;
    int count = 20;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_trip_history, container, false);
        mContext = getActivity();
        ((BaseActivity) mContext).mTitle.setText("TRIP HISTORY");
        ((BaseActivity) mContext).mTitleImageview.setVisibility(View.GONE);
        initViews();
        initRecyclerView();
        loadData();

        return rootView;
    }

    /*
    Initialize Views
     */
    private void initViews(){
        mParent = (LinearLayout)rootView.findViewById(R.id.parent);
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.list_trip_history);
        mSearchTV = (TextView)rootView.findViewById(R.id.search_tv);

        mSearchTV.setOnClickListener(this);
    }
    /*
    Initialize RecyclerView
     */
    private void initRecyclerView(){
        mRecyclerView.setHasFixedSize(true);
        llm = new LinearLayoutManager(mContext);
//        llm.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(llm);
       /*
        Add Scrolling Listener in RecyclerView
         */
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = llm.getChildCount();
                totalItemCount = llm.getItemCount();
                pastVisiblesItems = llm.findFirstVisibleItemPosition();
                if (loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;
                        CommonMethods.getInstance().e("...", "Last Item Wow !");
                        count = count + 20;
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    /*
    Load Data
     */
    private void loadData(){
        List<TripHistoryBean> listdata = new ArrayList<>();
        for(int i=0;i<10;i++){
            TripHistoryBean obj = new TripHistoryBean();
            obj.setId(i + "");
            obj.setDate("03/15");
            obj.setTime("8:47 PM");
            obj.setMappic("");
            obj.setPrice("Rs 120");
            listdata.add(obj);
        }
        mAdapter = new TripHistoryAdapter(mContext,listdata, PassengerTripHistoryFragment.this);
        mRecyclerView.setAdapter(mAdapter);
    }

    /*
    On Click history Items
     */
    public void onTripHistoryItemClick(){
        ((BaseActivity)mContext).navigateTo(new PassengerTripDetailFragment());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_tv:
                ((BaseActivity)mContext).navigateTo(new PassengerSearchTripFragment());
                break;
        }
    }
}
