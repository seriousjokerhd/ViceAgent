package mainui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;


import java.util.List;

import viceagent.com.viceagent.R;

/**
 * Created by Jhordan on 20/10/14.
 */
public class PageAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;
    protected Context mContext;


    public PageAdapter(Context context, android.support.v4.app.FragmentManager fm, List<Fragment> fragments) {

        super(fm);
        mContext = context;
        // TODO Auto-generated constructor stub
        this.fragments = fragments;


    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return mContext.getString(R.string.buy);
        } else if (position == 1) {
            return mContext.getString(R.string.rent);
        }
        else if (position == 2) {
            return mContext.getString(R.string.project);
        }

        return "";
    }

    @Override
    public Fragment getItem(int position) {


        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


}
