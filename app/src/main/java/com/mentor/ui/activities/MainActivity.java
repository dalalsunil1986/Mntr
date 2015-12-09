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
import android.view.View;
import android.widget.FrameLayout;

import com.mentor.R;
import com.mentor.core.PreferenceManager;
import com.mentor.listeners.FragmentToolbarListener;
import com.mentor.ui.fragment.ActivityFragment;
import com.mentor.ui.fragment.HomeFragment;
import com.mentor.ui.fragment.WakieFragment;
import com.mentor.util.GeneralUtils;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements FragmentToolbarListener {
    @Bind(R.id.dashboard_content)
    FrameLayout dashboardContent;

    @Inject
    PreferenceManager preferenceManager;

    Drawer result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        applicationComponent().inject(this);
        setupDrawer();
        initialFragment();
    }

    private void setupDrawer()
    {
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.nav)
                .addProfiles(
                        new ProfileDrawerItem().withName(preferenceManager.getName()).withIcon(GeneralUtils.facebookPicture("605305661"))
                )

                .build();

        PrimaryDrawerItem wakies = new PrimaryDrawerItem().withName(R.string.drawer_item_wakies).withIcon(CommunityMaterial.Icon.cmd_clock);
        PrimaryDrawerItem notifications = new PrimaryDrawerItem().withName(R.string.drawer_item_notifications).withIcon(CommunityMaterial.Icon.cmd_comment_alert);
        SecondaryDrawerItem settings = new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(CommunityMaterial.Icon.cmd_settings);
        SecondaryDrawerItem logout = new SecondaryDrawerItem().withName(R.string.drawer_item_logout).withIcon(CommunityMaterial.Icon.cmd_logout);

        result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(wakies, notifications,new DividerDrawerItem(), settings, logout)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        return false;
                    }
                })
                .build();
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
