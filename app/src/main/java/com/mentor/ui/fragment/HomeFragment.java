package com.mentor.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mentor.R;
import com.mentor.listeners.FragmentToolbarListener;
import com.mentor.ui.adapters.TabFragmentAdapter;
import com.mentor.util.ViewUtils;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    private FragmentToolbarListener listener;
    private TabFragmentAdapter fragmentAdapter;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (FragmentToolbarListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listener.onToolbarLoaded(toolbar);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }
        fab.setImageDrawable(ViewUtils.getMaterialIcon(getActivity(), CommunityMaterial.Icon.cmd_video));
        tabs.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
         fragmentAdapter = new TabFragmentAdapter(getChildFragmentManager());
        fragmentAdapter.addFragment(new TimelineFragment(), "Timeline");
        fragmentAdapter.addFragment(new DiscoverFragment(), "Discover");
        viewPager.setAdapter(fragmentAdapter);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
