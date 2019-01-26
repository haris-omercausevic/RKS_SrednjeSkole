package ba.fit.srednjeskole.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import ba.fit.srednjeskole.R;
import ba.fit.srednjeskole.helper.FragmentTabHost;
import ba.fit.srednjeskole.helper.MyApp;
import ba.fit.srednjeskole.helper.MySession;
import ba.fit.srednjeskole.model.OcjenaVM;
import ba.fit.srednjeskole.model.Storage;
import ba.fit.srednjeskole.model.UIKorisnik;

public class OcjeneFragment extends Fragment {
    private TabLayout tabLayout;
    //private ViewPager viewPager;
    private View view;
    private FragmentTabHost ocjeneTabs;
    private UIKorisnik korisnik;


    public OcjeneFragment() {
        // Required empty public constructor
    }

    public static OcjeneFragment newInstance() {
        OcjeneFragment fragment = new OcjeneFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        korisnik = MySession.readAccessSharedPreferences(MyApp.getContext());

        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ocjene, container, false);
        setLayout();

//        mTabHost = (FragmentTabHost) view.findViewById(R.id.tabhost);
//        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.mjestoZaTabs);
//
//        //add each tab
//        mTabHost.addTab(mTabHost.newTabSpec("first").setIndicator("first"), MaterijalOcjena.class, null);
//        mTabHost.addTab(mTabHost.newTabSpec("second").setIndicator("second"), ObavijestiFragment.class, null);
//        mTabHost.addTab(mTabHost.newTabSpec("third").setIndicator("third"), OcjeneGenericFragment.class, null);


//        mTabHost.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mTabHost.setCurrentTabByTag("second");
//            }
//        }, 5000);

//        tabLayout = view.findViewById(R.id.tabs);
//        viewPager = view.findViewById(R.id.view_pager);
//        viewPager.setAdapter(new CustomFragmentPageAdapter(getChildFragmentManager()));
//        tabLayout.setupWithViewPager(viewPager);
        //setupViewPager(viewPager);
        return view;
    }

    private void setLayout(){
        ocjeneTabs = view.findViewById(R.id.razredi_tabhost);
        ocjeneTabs.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);


        //razredi: [4,6] -- ovo su IDovi
        //razrediBrojcano: [1,2] -- ovo je brojcano
        ArrayList<String> razrediTitles = new ArrayList<String>();
        razrediTitles.add("I");
        razrediTitles.add("II");
        razrediTitles.add("III");
        razrediTitles.add("IV");

        for (int i = 0; i < korisnik.razredi.size(); i++)
        {
            Bundle tempArg = new Bundle();
            //tempArg.putInt(String.valueOf(korisnik.razrediBrojcano.get(i)), Integer.parseInt(korisnik.razredi.get(i)));
            tempArg.putInt(MyApp.RazredGenericKey, Integer.parseInt(korisnik.razredi.get(i)));
            ocjeneTabs.addTab(ocjeneTabs.newTabSpec("r" + String.valueOf(i+1)).setIndicator(razrediTitles.get(i)), OcjeneGenericFragment.class, tempArg);
        }
    }

//    public class CustomFragmentPageAdapter extends FragmentPagerAdapter{
//        private final String TAG = CustomFragmentPageAdapter.class.getSimpleName();
//        private final int FRAGMENT_COUNT = 4;
//
//        public CustomFragmentPageAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int i) {
//            return new OcjeneGenericFragment();
//        }
//
//        @Override
//        public int getCount() {
//            return FRAGMENT_COUNT;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            switch (position){
//                case 0:
//                    return "I";
//                case 1:
//                    return "II";
//                case 2:
//                    return "III";
//                case 3:
//                    return "IV";
//            }
//            return null;
//        }
//    }



    //Add Fragments to Tabs
//    private void setupViewPager(ViewPager viewPager) {
//        Adapter adapter = new Adapter(getChildFragmentManager());
//        adapter.addFragment(new (), "Today");
//        viewPager.setAdapter(adapter);
//    }
//    static class Adapter extends FragmentPagerAdapter{
//        private final List<Fragment> mFragmentList = new ArrayList<>();
//        private final List<String> mFragmentTitleList = new ArrayList<>();
//
//        public Adapter(FragmentManager manager) {
//            super(manager);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return mFragmentList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return mFragmentList.size();
//        }
//
//        public void addFragment(Fragment fragment, String title) {
//            mFragmentList.add(fragment);
//            mFragmentTitleList.add(title);
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mFragmentTitleList.get(position);
//        }
//    }


}
