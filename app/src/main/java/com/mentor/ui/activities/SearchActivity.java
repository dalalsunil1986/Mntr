package com.mentor.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.mentor.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity {

    @Bind(R.id.floating_search_view)
    FloatingSearchView floatingSearchView;
    @Bind(R.id.results)
    RecyclerView results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

    }

}
