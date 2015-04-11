package viceagent.com.viceagent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class DetailPropertyActivity extends ActionBarActivity {

    ParseFile imageFile = null;
    ParseFile imageFile1 = null;
    ParseFile imageFile2 = null;
    String imageUrl = "";
    private String mPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_property);


        Intent getIntent = getIntent();
        String objectId = getIntent.getStringExtra(PropertyResultActivity.TAG_OBJECT_ID);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_PROPERTY);
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {

                    ParseImageView image = (ParseImageView) findViewById(R.id.detailImage);
                    imageFile = parseObject.getParseFile(ParseConstants.KEY_PHOTO);
                    if (imageFile != null) {
                        image.setParseFile(imageFile);
                        image.loadInBackground();
                    }

                    ParseImageView image1 = (ParseImageView) findViewById(R.id.detailImage1);
                    imageFile1 = parseObject.getParseFile(ParseConstants.KEY_PHOTO1);
                    if (imageFile1 != null) {
                        image1.setParseFile(imageFile1);
                        image1.loadInBackground();
                    }
                    ParseImageView image2 = (ParseImageView) findViewById(R.id.detailImage2);
                    imageFile2 = parseObject.getParseFile(ParseConstants.KEY_PHOTO2);
                    if (imageFile2 != null) {
                        image2.setParseFile(imageFile2);
                        image2.loadInBackground();
                    }

                    TextView propetyTypeTextView = (TextView) findViewById(R.id.propertyTypeTextView);
                    String propertyType = parseObject.getString(ParseConstants.KEY_PROPERTY_TYPE);
                    propetyTypeTextView.setText(propertyType);

                    TextView priceTextView = (TextView) findViewById(R.id.priceTextView);
                    Number price = parseObject.getNumber(ParseConstants.KEY_PRICE);
                    priceTextView.setText("Rs. " + price);

                    TextView bedroomTextView = (TextView) findViewById(R.id.bedroomTextView);
                    String bedroom = parseObject.getString(ParseConstants.KEY_BEDROOM);
                    bedroomTextView.setText(bedroom);

                    TextView bathroomTextView = (TextView) findViewById(R.id.bathroomTextView);
                    String bathroom = parseObject.getString(ParseConstants.KEY_BATHROOM);
                    bathroomTextView.setText(bathroom);

                    TextView plotAreaTextView = (TextView) findViewById(R.id.plotAreaTextView);
                    String plotArea = parseObject.getString(ParseConstants.KEY_PLOT_AREA);
                    plotAreaTextView.setText(plotArea + " sq.ft.");


                    TextView addressTextView = (TextView) findViewById(R.id.addressTextView);
                    String address = parseObject.getString(ParseConstants.KEY_ADDRESS);
                    String locality = parseObject.getString(ParseConstants.KEY_LOCALITY);
                    String city = parseObject.getString(ParseConstants.KEY_CITY);
                    addressTextView.setText(address + ", " + locality + ", " + city);


                    TextView transactionTypeTextView = (TextView) findViewById(R.id.transactionTypeTextView);
                    String transactionType = parseObject.getString(ParseConstants.KEY_TRANSACTION_TYPE);
                    transactionTypeTextView.setText(transactionType);

                    TextView availabilityTextView = (TextView) findViewById(R.id.availabilityTextView);
                    String availability = parseObject.getString(ParseConstants.KEY_AVAILABILITY);
                    availabilityTextView.setText(availability);

                    TextView dealerNameTextView = (TextView) findViewById(R.id.dealerNameTextView);
                    String dealerName = parseObject.getString(ParseConstants.KEY_DEALER_NAME);
                    dealerNameTextView.setText(dealerName);

                    TextView descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
                    String description = parseObject.getString(ParseConstants.KEY_DESCRIPTION);
                    descriptionTextView.setText(description);

                    mPhoneNumber = parseObject.getString(ParseConstants.KEY_PHONE_NUMBER);
                } else {
                    Log.e("Detail Error: ", "", e);
                }
            }
        });
    }

    protected void makeCall() {
        Log.i("Make call", mPhoneNumber);

        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        phoneIntent.setData(Uri.parse("tel:" + mPhoneNumber));

        try {
            startActivity(phoneIntent);
            finish();
            Log.i("Finished...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(DetailPropertyActivity.this,
                    "Call faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_property, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_call) {
            makeCall();
        }

        return super.onOptionsItemSelected(item);
    }
}
