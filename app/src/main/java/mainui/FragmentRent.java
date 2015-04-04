package mainui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import viceagent.com.viceagent.R;


/**
 * Created by Jhordan on 07/11/14.
 */
public class FragmentRent extends Fragment {

    public FragmentRent() {
    }

    public static FragmentRent newInstance(int position) {

        FragmentRent fragmentCercanos = new FragmentRent();
        Bundle extraArguments = new Bundle();

        String h = Integer.toString(position);
        Log.i("position", h);
        fragmentCercanos.setArguments(extraArguments);
        return fragmentCercanos;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rent, container, false);



        return v;
    }
}

