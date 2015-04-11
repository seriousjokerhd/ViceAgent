package viceagent.com.viceagent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.Arrays;

import mainui.FragmentBuy;

public class PropertyResultAdapter extends ParseQueryAdapter<ParseObject> {

    public PropertyResultAdapter(final Context context) {
        // Use the QueryFactory to construct a PQA that will only show
        // Todos marked as high-pri
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                Intent getIntent = ((Activity) context).getIntent();
                String selectedCity = getIntent.getStringExtra(FragmentBuy.TAG_CITY);
                String loc = getIntent.getStringExtra(FragmentBuy.TAG_LOC);
                String loc1 = getIntent.getStringExtra(FragmentBuy.TAG_LOC1);
                String loc2 = getIntent.getStringExtra(FragmentBuy.TAG_LOC2);
                String mPropertyType = getIntent.getStringExtra(FragmentBuy.TAG_PROP_TYPE);
                String mTransactionType = getIntent.getStringExtra(FragmentBuy.TAG_TRANC_TYPE);
                String mAvailability = getIntent.getStringExtra(FragmentBuy.TAG_AVAIL);
                int minPrice = getIntent.getIntExtra(FragmentBuy.TAG_MIN, 0);
                int maxPrice = getIntent.getIntExtra(FragmentBuy.TAG_MAX, 100000000);

                String sellRent = getIntent.getStringExtra(ParseConstants.KEY_SELL_RENT);

                Log.i("Value Range bar: ", minPrice + "and  Max Price: " + maxPrice);


                ParseQuery query = new ParseQuery(ParseConstants.CLASS_PROPERTY);

                if (loc.isEmpty() && loc1.isEmpty() && loc2.isEmpty()) {

                    Log.d("Locality: ", " No Match Found");
                } else if (loc1.isEmpty() && loc2.isEmpty()) {
                    Log.d("Locality: ", " 1 Match Found");
                    String[] locality = {loc};
                    query.whereContainedIn(ParseConstants.KEY_LOCALITY, Arrays.asList(locality));
                } else if (loc2.isEmpty()) {
                    Log.d("Locality: ", " 2 Match Found");

                    String[] locality = {loc, loc1};
                    query.whereContainedIn(ParseConstants.KEY_LOCALITY, Arrays.asList(locality));

                } else {

                    Log.d("Locality: ", " 3 Match Found");
                    String[] locality = {loc, loc1, loc2};
                    query.whereContainedIn(ParseConstants.KEY_LOCALITY, Arrays.asList(locality));

                }

                if (sellRent != null) {
                    Log.i("Rent Or Sell: ", sellRent);
                    query.whereContains(ParseConstants.KEY_SELL_RENT, sellRent);
                }


                if (selectedCity != null) {
                    query.whereContains(ParseConstants.KEY_CITY, selectedCity);
                }
//                if (loc != null) {
//                    query.whereContains(ParseConstants.KEY_LOCALITY, loc);
//                }
/*                if (loc1 != null) {
                    query.whereContains(ParseConstants.KEY_LOCALITY, loc1);
                }
                if (loc2 != null) {
                    query.whereContains(ParseConstants.KEY_LOCALITY, loc2);
                }
                */
                query.whereLessThanOrEqualTo(ParseConstants.KEY_PRICE, maxPrice);
                query.whereGreaterThanOrEqualTo(ParseConstants.KEY_PRICE, minPrice);

                if (mPropertyType != null) {
                    query.whereContains(ParseConstants.KEY_PROPERTY_TYPE, mPropertyType);
                }

                if (mTransactionType != null) {
                    query.whereContains(ParseConstants.KEY_TRANSACTION_TYPE, mTransactionType);
                }
                if (mAvailability != null) {
                    query.whereContains(ParseConstants.KEY_AVAILABILITY, mAvailability);
                }

                return query;
            }
        });
    }

    // Customize the layout by overriding getItemView
    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.propery_result_adapter, null);
        }

        super.getItemView(object, v, parent);
        final String TAG_objectId = ((TextView) v.findViewById(R.id.objectId)).getText()
                .toString();

        // Add and download the image
        ParseImageView image = (ParseImageView) v.findViewById(R.id.resultImageView);
        ParseFile imageFile = object.getParseFile(ParseConstants.KEY_PHOTO);
        if (imageFile != null) {
            image.setParseFile(imageFile);
            image.loadInBackground();
        }
        // Price
        TextView priceTextView = (TextView) v.findViewById(R.id.resultPriceTextView);
        priceTextView.setText("Rs. " + object.getNumber(ParseConstants.KEY_PRICE));

        // Add the title view
        TextView titleTextView = (TextView) v.findViewById(R.id.resultTitleTextView);
        titleTextView.setText(object.getString(ParseConstants.KEY_BEDROOM) +
                " BHK " + object.getString(ParseConstants.KEY_PROPERTY_TYPE));

        // Address

        TextView addressTextView = (TextView) v.findViewById(R.id.resultAddressTextView);
        addressTextView.setText(object.getString(ParseConstants.KEY_ADDRESS));

        // Locality

        TextView localityTextView = (TextView) v.findViewById(R.id.resultLocalityTextView);
        localityTextView.setText(object.getString(ParseConstants.KEY_LOCALITY));

        // Plot Area

        TextView plotAreaTextView = (TextView) v.findViewById(R.id.resultPlotAreaTextView);
        plotAreaTextView.setText(object.getString(ParseConstants.KEY_PLOT_AREA) + " sq. ft.");

        // Bathroom

        TextView bathroomTextView = (TextView) v.findViewById(R.id.resultBathroomTextView);
        bathroomTextView.setText(object.getString(ParseConstants.KEY_BATHROOM) + " Bathrooms");

        // Object Id
        TextView objectId = (TextView) v.findViewById(R.id.objectId);
        objectId.setText(object.getObjectId());


        return v;
    }

}
