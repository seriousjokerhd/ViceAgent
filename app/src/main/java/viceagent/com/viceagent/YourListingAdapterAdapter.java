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
import com.parse.ParseUser;

import java.util.Arrays;

import mainui.FragmentBuy;

public class YourListingAdapterAdapter extends ParseQueryAdapter<ParseObject> {

    public YourListingAdapterAdapter(final Context context) {
        // Use the QueryFactory to construct a PQA that will only show
        // Todos marked as high-pri
        super(context, new QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery(ParseConstants.CLASS_PROPERTY);
                query.whereContains(ParseConstants.KEY_PHONE_NUMBER, ParseUser.getCurrentUser().getUsername());
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
