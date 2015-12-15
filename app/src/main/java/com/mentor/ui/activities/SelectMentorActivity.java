package com.mentor.ui.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import com.algolia.search.saas.APIClient;
import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.algolia.search.saas.listeners.SearchListener;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.mentor.R;

import org.json.JSONObject;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectMentorActivity extends BaseActivity implements SearchListener {

    @Bind(R.id.floating_search_view)
    FloatingSearchView floatingSearchView;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Inject
    APIClient algoliaClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mentor);
        ButterKnife.bind(this);
        applicationComponent().inject(this);

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

        Index index=algoliaClient.initIndex("mentor_name");
        index.searchASync(new Query(""),this);


    }

    @Override
    public void searchResult(Index index, Query query, JSONObject results) {



    }

    @Override
    public void searchError(Index index, Query query, AlgoliaException e) {



    }
}
