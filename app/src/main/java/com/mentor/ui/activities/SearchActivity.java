package com.mentor.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.mentor.R;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity {

    @Bind(R.id.results)
    RecyclerView results;
    @Bind(R.id.search_view)
    MaterialSearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        searchView.showSearch(false);
        searchView.findViewById(com.miguelcatalan.materialsearchview.R.id.action_up_btn)
        .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        searchView.setVoiceSearch(true);
        searchView.showVoice(true);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.showSearch(false);
                searchView.setQuery(query,false);


                Toast.makeText(SearchActivity.this,query,Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }


}
