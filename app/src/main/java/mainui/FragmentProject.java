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
public class FragmentProject extends Fragment {

    public FragmentProject() {
    }

    public static FragmentProject newInstance(int position) {

       FragmentProject fragmentCercanos = new FragmentProject();
        Bundle extraArguments = new Bundle();

        fragmentCercanos.setArguments(extraArguments);
        return fragmentCercanos;
    }
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_project, container, false);


        return v;
    }
}
