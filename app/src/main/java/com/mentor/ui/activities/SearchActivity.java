package com.mentor.ui.activities;

import android.os.Bundle;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.mentor.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity {


    @Bind(R.id.floating_search_view)
    FloatingSearchView floatingSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        floatingSearchView.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {
                onBackPressed();
            }
        });

        floatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {

            }
        });


    }
}
