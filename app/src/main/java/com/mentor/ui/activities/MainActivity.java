package com.mentor.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.mentor.R;
import com.mentor.listeners.FragmentToolbarListener;
import com.mentor.ui.fragment.ActivityFragment;
import com.mentor.ui.fragment.HomeFragment;
import com.mentor.ui.fragment.WakieFragment;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements FragmentToolbarListener {
    @Bind(R.id.dashboard_content)
    FrameLayout dashboardContent;

    Drawer result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        applicationComponent().inject(this);
        result = new DrawerBuilder().withActivity(this).build();
        initialFragment();
    }

    private void initialFragment()
    {
        WakieFragment wakieFragment = new WakieFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.dashboard_content, wakieFragment);
        transaction.commit();
    }






    @Override
    public void onToolbarLoaded(Toolbar toolbar) {
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        result.setToolbar(this,toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_search:
                Intent intent = new Intent(this,SearchActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
