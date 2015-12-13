package com.mentor.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.mentor.R;
import com.mentor.core.Extension;
import com.mentor.core.FileManager;
import com.mentor.core.MediaType;
import com.mentor.core.PreferenceManager;
import com.mentor.listeners.FragmentToolbarListener;
import com.mentor.ui.fragment.WakieFragment;
import com.mentor.util.GeneralUtils;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.File;

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

    private void setupDrawer() {
        ProfileDrawerItem profile;
        File profilePic = FileManager.get("profile", this, MediaType.ProfilePic, Extension.JPEG);

        if (profilePic == null) {
            profile = new ProfileDrawerItem().withName(preferenceManager.getName());

        } else {
            Bitmap bitmap = GeneralUtils.getBitmapFromFile(profilePic);

            profile = new ProfileDrawerItem().withName(preferenceManager.getName()).withIcon(bitmap);

        }
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.nav)
                .addProfiles(
                        profile
                )

                .build();

        PrimaryDrawerItem wakies = new PrimaryDrawerItem().withName(R.string.drawer_item_wakies)
                .withIcon(CommunityMaterial.Icon.cmd_clock)
                .withIdentifier(1);

        PrimaryDrawerItem notifications = new PrimaryDrawerItem().withName(R.string.drawer_item_notifications)
                .withIcon(CommunityMaterial.Icon.cmd_comment_alert)
                .withIdentifier(2);
        ;
        SecondaryDrawerItem settings = new SecondaryDrawerItem().withName(R.string.drawer_item_settings)
                .withIcon(CommunityMaterial.Icon.cmd_settings)
                .withIdentifier(3);
        SecondaryDrawerItem logout = new SecondaryDrawerItem().withName(R.string.drawer_item_logout)
                .withIcon(CommunityMaterial.Icon.cmd_logout)
                .withIdentifier(4);

        result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(wakies, notifications, new DividerDrawerItem(), settings, logout)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        switch (drawerItem.getIdentifier())
                        {
                            case 1:


                                break;

                            case 2:

                                break;

                            case 3:

                                break;
                            case 4:
                                logout();
                                break;
                        }
                        return false;
                    }
                })
                .build();
    }

    private void initialFragment() {
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
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        result.setToolbar(this, toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.getItem(0)
                .setIcon(new IconicsDrawable(this, CommunityMaterial.Icon.cmd_magnify)
                        .colorRes(android.R.color.white).sizeDp(20));
        return true;
    }


}
