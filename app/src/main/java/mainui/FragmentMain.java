package mainui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import viceagent.com.viceagent.R;

/**
 * Created by Jhordan on 07/11/14.
 */


public class FragmentMain extends android.support.v4.app.Fragment {

    protected Context mContext;

    public FragmentMain() {
    }

    public static FragmentMain newInstance(int position) {

       FragmentMain home = new FragmentMain();
        Bundle extraArguments = new Bundle();
        //extraArguments.putInt(NavigationDrawerFragment.ARG_SECTION_NUMBER, position);
        home.setArguments(extraArguments);
        return home;
    }



    private List<android.support.v4.app.Fragment> listaFragments;
    private PagerAdapter mPagerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
          View v = inflater.inflate(R.layout.pager_adapter, container, false);

//        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
  //      toolbar.setTitle("Android Wear");





        listaFragments = new ArrayList<android.support.v4.app.Fragment>();
        listaFragments.add(FragmentBuy.newInstance(0));
        listaFragments.add(FragmentRent.newInstance(0));

        // Creamos nuestro Adapter
        mPagerAdapter = new PageAdapter(getActivity(), getFragmentManager(), listaFragments);


        // Initialize the ViewPager and set an adapter
        ViewPager pager = (ViewPager) v.findViewById(R.id.pager);
        pager.setAdapter(mPagerAdapter);


        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) v.findViewById(R.id.tabs);
        tabs.setIndicatorColor(getResources().getColor(R.color.accent));


        tabs.setShouldExpand(true);


//       tabs.setTextColorResource(R.color.white);
//       tabs.setDividerColor(getResources().getColor(R.color.accent));
        tabs.setViewPager(pager);




        return  v;
    }

}
