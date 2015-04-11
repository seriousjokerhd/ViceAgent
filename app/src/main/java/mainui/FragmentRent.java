package mainui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import login.LoginActivity;
import viceagent.com.viceagent.ParseConstants;
import viceagent.com.viceagent.PropertyResultActivity;
import viceagent.com.viceagent.R;

public class FragmentRent extends Fragment implements SearchView.OnQueryTextListener {

    public static final String TAG_CITY = "selectedCity";
    public static final String TAG_LOC = "locKey";
    public static final String TAG_LOC1 = "locKey1";
    public static final String TAG_LOC2 = "locKey2";
    public static final String TAG_MIN = "minPrice";
    public static final String TAG_MAX = "maxPrice";
    public static final String TAG_PROP_TYPE = "propertyType";
    public static final String TAG_TRANC_TYPE = "transactionType";
    public static final String TAG_AVAIL = "availability";

    int mMinPrice = 0; //Default Min Price
    int mMaxPrice = 1000000000; //Default Max Price

    String mSelectedCity;

    String loc = "";
    String loc1 = "";
    String loc2 = "";

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

    private Button mAppartmentButton;
    private Button mBuilderFloorButton;
    private Button mPlotLandButton;
    private Button mHouseButton;
    private Button mStudioApartmentButton;
    private Button mFarmhouseButton;
    private Button mServicedApartmentButton;

    private Button mResaleButton;
    private Button mNewBookingButton;

    private Button mUnderConstructionButton;
    private Button mReadyToMoveButton;


    public FragmentRent() {
    }

    public static FragmentRent newInstance(int position) {

        FragmentRent fragmentCercanos = new FragmentRent();
        Bundle extraArguments = new Bundle();

        fragmentCercanos.setArguments(extraArguments);
        return fragmentCercanos;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rent, container, false);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            navigateToLogin();
        } else {
        // Property Type

        mAppartmentButton = (Button) v.findViewById(R.id.appartmentButton);
        mAppartmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPropertyType = getResources().getString(R.string.apt);
                mAppartmentButton.setSelected(true);
                mBuilderFloorButton.setSelected(false);
                mHouseButton.setSelected(false);
                mPlotLandButton.setSelected(false);
                mFarmhouseButton.setSelected(false);
                mStudioApartmentButton.setSelected(false);
                mFarmhouseButton.setSelected(false);
                mServicedApartmentButton.setSelected(false);

                Log.i("Property Type: ", mPropertyType);

            }
        });
        mBuilderFloorButton = (Button) v.findViewById(R.id.builderFloorButton);
        mBuilderFloorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPropertyType = getResources().getString(R.string.builder_floor);
                Log.i("Property Type: ", mPropertyType);
                mAppartmentButton.setSelected(false);
                mBuilderFloorButton.setSelected(true);
                mHouseButton.setSelected(false);
                mPlotLandButton.setSelected(false);
                mFarmhouseButton.setSelected(false);
                mStudioApartmentButton.setSelected(false);
                mFarmhouseButton.setSelected(false);
                mServicedApartmentButton.setSelected(false);

            }
        });
        mPlotLandButton = (Button) v.findViewById(R.id.plotLandButton);
        mPlotLandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPropertyType = getResources().getString(R.string.plot_land);
                Log.i("Property Type: ", mPropertyType);
                mAppartmentButton.setSelected(false);
                mBuilderFloorButton.setSelected(false);
                mHouseButton.setSelected(false);
                mPlotLandButton.setSelected(true);
                mFarmhouseButton.setSelected(false);
                mStudioApartmentButton.setSelected(false);
                mFarmhouseButton.setSelected(false);
                mServicedApartmentButton.setSelected(false);
            }
        });
        mHouseButton = (Button) v.findViewById(R.id.houseButton);
        mHouseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPropertyType = getResources().getString(R.string.house);
                Log.i("Property Type: ", mPropertyType);
                mAppartmentButton.setSelected(false);
                mBuilderFloorButton.setSelected(false);
                mHouseButton.setSelected(true);
                mPlotLandButton.setSelected(false);
                mFarmhouseButton.setSelected(false);
                mStudioApartmentButton.setSelected(false);
                mFarmhouseButton.setSelected(false);
                mServicedApartmentButton.setSelected(false);
            }
        });
        mStudioApartmentButton = (Button) v.findViewById(R.id.studioApartmentButton);
        mStudioApartmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPropertyType = getResources().getString(R.string.studio_apartment);
                Log.i("Property Type: ", mPropertyType);
                mAppartmentButton.setSelected(false);
                mBuilderFloorButton.setSelected(false);
                mHouseButton.setSelected(false);
                mPlotLandButton.setSelected(false);
                mFarmhouseButton.setSelected(false);
                mStudioApartmentButton.setSelected(true);
                mFarmhouseButton.setSelected(false);
                mServicedApartmentButton.setSelected(false);
            }
        });
        mFarmhouseButton = (Button) v.findViewById(R.id.farmHouseButton);
        mFarmhouseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPropertyType = getResources().getString(R.string.farmhouse);
                Log.i("Property Type: ", mPropertyType);
                mAppartmentButton.setSelected(false);
                mBuilderFloorButton.setSelected(false);
                mHouseButton.setSelected(false);
                mPlotLandButton.setSelected(false);
                mFarmhouseButton.setSelected(false);
                mStudioApartmentButton.setSelected(false);
                mFarmhouseButton.setSelected(true);
                mServicedApartmentButton.setSelected(false);
            }
        });
        mServicedApartmentButton = (Button) v.findViewById(R.id.servicedApartmentButton);
        mServicedApartmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPropertyType = getResources().getString(R.string.serviced_apt);
                Log.i("Property Type: ", mPropertyType);
                mAppartmentButton.setSelected(false);
                mBuilderFloorButton.setSelected(false);
                mHouseButton.setSelected(false);
                mPlotLandButton.setSelected(false);
                mFarmhouseButton.setSelected(false);
                mStudioApartmentButton.setSelected(false);
                mFarmhouseButton.setSelected(false);
                mServicedApartmentButton.setSelected(true);
            }
        });

        mResaleButton = (Button) v.findViewById(R.id.resaleButton);
        mResaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTransactionType = getResources().getString(R.string.resale);
                mResaleButton.setSelected(true);
                mNewBookingButton.setSelected(false);
                Log.i("Transaction Type: ", mTransactionType);

            }
        });
        mNewBookingButton = (Button) v.findViewById(R.id.newBookingButton);
        mNewBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTransactionType = getResources().getString(R.string.new_booking);
                mNewBookingButton.setSelected(true);
                mResaleButton.setSelected(false);
                Log.i("Transaction Type: ", mTransactionType);
            }
        });

        mUnderConstructionButton = (Button) v.findViewById(R.id.underConstructionButton);
        mUnderConstructionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAvailability = getResources().getString(R.string.under_construction);
                mUnderConstructionButton.setSelected(true);
                mReadyToMoveButton.setSelected(false);
                Log.i("Availability: ", mAvailability);
            }
        });

        mReadyToMoveButton = (Button) v.findViewById(R.id.readyToMovebutton);
        mReadyToMoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAvailability = getResources().getString(R.string.ready_to_move);
                mReadyToMoveButton.setSelected(true);
                mUnderConstructionButton.setSelected(false);
                Log.i("Availability: ", mAvailability);
            }
        });


        mAutoCompleteCity = (AutoCompleteTextView) v.findViewById(R.id.searchCity);

        mDoneButton = (Button) v.findViewById(R.id.doneButton);
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListView.setVisibility(View.GONE);
                mDoneButton.setVisibility(View.GONE);
            }
        });

        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.CLASS_PROPERTY);
        query.whereContains(ParseConstants.KEY_SELL_RENT, "Rent Out Property");
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
                    // To get unique Strings

                    List<String> tmpList = Arrays.asList(cityName);
                    TreeSet<String> unique = new TreeSet<String>(tmpList);
                    List<String> list = new ArrayList<String>(unique);

                        adapter = new ArrayAdapter<String>(getActivity()
                                .getApplicationContext(), R.layout.auto_complete_adapter_white_bg, list);

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
                locationQuery.whereContains(ParseConstants.KEY_SELL_RENT, "Rent Out Property");
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

                            // To get unique Strings

                            List<String> tmpList = Arrays.asList(locality);
                            TreeSet<String> unique = new TreeSet<String>(tmpList);
                            List<String> list = new ArrayList<String>(unique);

                            mListView.setAdapter(new ArrayAdapter<String>(getActivity(),
                                    R.layout.auto_complete_adapter_white_bg,
                                    list));
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

                if (loc.isEmpty()) {
                    loc = selectedFromList;

                } else if (loc1.isEmpty()) {
                    loc1 = selectedFromList;
                } else if (loc2.isEmpty()) {
                    loc2 = selectedFromList;
                } else {
                    mListView.setVisibility(View.GONE);

                }
                mSearchView.setQueryHint(loc + " " + loc1 + " " + loc2);
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
                mMaxPrice = maxPrice * 100000;
                mMinPrice = minPrice * 100000;
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

                Intent intent = new Intent(getActivity(), PropertyResultActivity.class);
                intent.putExtra(ParseConstants.KEY_SELL_RENT, "Rent Out Property");
                intent.putExtra(TAG_CITY, mSelectedCity);
                intent.putExtra(TAG_LOC, loc);
                intent.putExtra(TAG_LOC1, loc1);
                intent.putExtra(TAG_LOC2, loc2);
                intent.putExtra(TAG_MIN, mMinPrice);
                intent.putExtra(TAG_MAX, mMaxPrice);
                intent.putExtra(TAG_PROP_TYPE, mPropertyType);
                intent.putExtra(TAG_TRANC_TYPE, mTransactionType);
                intent.putExtra(TAG_AVAIL, mAvailability);

                startActivity(intent);

            }
        });}
        return v;
    }
    private void navigateToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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
