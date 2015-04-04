package mainui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import viceagent.com.viceagent.R;


/**
 * Created by Jhordan on 07/11/14.
 */
public class FragmentBuy extends Fragment {

    public FragmentBuy() {
    }

    public static FragmentBuy newInstance(int position) {

       FragmentBuy fragmentCercanos = new FragmentBuy();
        Bundle extraArguments = new Bundle();

        fragmentCercanos.setArguments(extraArguments);
        return fragmentCercanos;
    }
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_buy, container, false);


        return v;
    }
}
