package com.mentor.ui.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.mentor.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.sahildave.widget.SearchViewLayout;

public class SearchActivity extends BaseActivity {


    @Bind(R.id.search_view_container)
    SearchViewLayout searchViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        searchViewContainer.expand(true);
        searchViewContainer.setExpandedContentFragment(this, new Fragment());
        searchViewContainer.setSearchListener(new SearchViewLayout.SearchListener() {
            @Override
            public void onFinished(String searchKeyword) {

            }
        });

        ViewGroup mExpanded = (ViewGroup) searchViewContainer.findViewById(xyz.sahildave.widget.R.id.search_expanded_root);
        mExpanded.findViewById(xyz.sahildave.widget.R.id.search_expanded_back_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });



    }
}
