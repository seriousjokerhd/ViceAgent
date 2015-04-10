package mainui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.appyvet.rangebar.RangeBar;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import viceagent.com.viceagent.ParseConstants;
import viceagent.com.viceagent.R;

public class FragmentBuy extends Fragment implements SearchView.OnQueryTextListener{

    int mMinPrice;
    int mMaxPrice;

    String mSelectedCity;

    String key  = "";
    String key1 = "";
    String key2 = "";

    String mPropertyType;
    String mTransactionType;
    String mAvailability;


    List<ParseObject> mCity;
    List<ParseObject> mLocality;
    ArrayAdapter<String> adapter;
    AutoCompleteTextView mAutoCompleteCity;

    private SearchView mSearchView;
    private ListView mListView;

    TextView priceRange;

    Button mDoneButton;

    public FragmentBuy() {
    }

    public static FragmentBuy newInstance(int position) {

        FragmentBuy fragmentCercanos = new FragmentBuy();
        Bundle extraArguments = new Bundle();

        fragmentCercanos.setArguments(extraArguments);
        return fragmentCercanos;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_buy, container, false);


        mAutoCompleteCity = (AutoCompleteTextView) v.findViewById(R.id.searchCity);

//        autoCompleteLocality = (AutoCompleteTextView) v.findViewById(R.id.searchLocality);

       mDoneButton = (Button) v.findViewById(R.id.doneButton);
       mDoneButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               mListView.setVisibility(View.GONE);
               mDoneButton.setVisibility(View.GONE);
           }
       });


        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.CLASS_PROPERTY);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    //Success
                    mCity = parseObjects;
                    String[] cityName = new String[mCity.size()];
                    int i = 0;
                    for (ParseObject city : mCity) {

                        cityName[i] = city.getString(ParseConstants.KEY_CITY);
                        i++;
                    }

                    adapter = new ArrayAdapter<String>(getActivity()
                            .getApplicationContext(), R.layout.auto_complete_adapter_white_bg, cityName);

                    mAutoCompleteCity.setThreshold(0);
                    mAutoCompleteCity.setAdapter(adapter);

                } else {
                }
            }
        });



        mAutoCompleteCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mSearchView.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.VISIBLE);
                mDoneButton.setVisibility(View.VISIBLE);
                mSelectedCity = mAutoCompleteCity.getText().toString();
                Log.i("City List: ", mSelectedCity);




                ParseQuery<ParseObject> locationQuery = ParseQuery.getQuery(ParseConstants.CLASS_PROPERTY);
                locationQuery.whereContains(ParseConstants.KEY_CITY, mSelectedCity);
                locationQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> parseObjects1, ParseException e) {
                        if (e == null) {
                            //Success
                            mLocality = parseObjects1;
                            String[] locality = new String[mLocality.size()];
                            int i = 0;
                            for (ParseObject loc : mLocality) {

                                locality[i] = loc.getString(ParseConstants.KEY_LOCALITY);
                                i++;
                            }


                            mListView.setAdapter(new ArrayAdapter<String>(getActivity(),
                                    R.layout.auto_complete_adapter_white_bg,
                                    locality));
                            mListView.setTextFilterEnabled(true);
                            setupSearchView();

                        } else {
                        }
                    }
                });




            }
        });





        mSearchView = (SearchView) v.findViewById(R.id.searchLocality);
        mListView = (ListView) v.findViewById(R.id.list_view);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedFromList = (String) mListView.getItemAtPosition(i);


                if (key.isEmpty()) {
                    key = selectedFromList;

                } else if (key1.isEmpty()) {
                    key1 = selectedFromList;
                } else if (key2.isEmpty()) {
                    key2 = selectedFromList;
                } else {
                    mListView.setVisibility(View.GONE);

                }


                mSearchView.setQueryHint(key + " " + key1 + " " + key2);
            }
        });




        priceRange = (TextView) v.findViewById(R.id.priceRange);

        RangeBar rangebar;
        rangebar = (RangeBar) v.findViewById(R.id.rangebar);
        rangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex,
                                              String leftPinValue, String rightPinValue)

            {

                String lacCrores = "Lac";
                String lacCrores1 = "Lac";
                Integer minPriceLable = Integer.parseInt(leftPinValue);
                Integer maxPriceLable = Integer.parseInt(rightPinValue);

                int minPrice = minPriceLable;
                int maxPrice = maxPriceLable;
                mMaxPrice = maxPrice;
                mMinPrice = minPrice;
                if (minPriceLable >= 100) {

                    minPriceLable = minPriceLable / 100;
                    lacCrores = "Crores";
                    if (maxPriceLable >= 100) {

                        maxPriceLable = maxPriceLable / 100;
                        lacCrores1 = "Crores";
                    }
                } else if (maxPriceLable >= 100) {

                    maxPriceLable = maxPriceLable / 100;
                    lacCrores1 = "Crores";
                }
                priceRange.setText("Price Range From " + minPriceLable + " " + lacCrores + " to " + maxPriceLable + " " + lacCrores1);

            }
        });

        Button searchButton = (Button) v.findViewById(R.id.Search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Value Range bar: ", mMaxPrice + "");

            }
        });


        return v;
    }


    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setQueryHint("Search Here");
    }

    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            mListView.clearTextFilter();
        } else {
            mListView.setFilterText(newText.toString());
        }
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }

}
