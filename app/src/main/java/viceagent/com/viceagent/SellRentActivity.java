package viceagent.com.viceagent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class SellRentActivity extends ActionBarActivity {

    public static final String TAG = SellRentActivity.class.getSimpleName();

    private NumberPicker mBathroomNumberPicker;
    private NumberPicker mBedroomroomNumberPicker;

    private Button mSellPropertyButton;
    private Button mRentPropertyButton;

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


    private Button mPostAd;

    private AutoCompleteTextView mCityAutoCompleteTextView;
    private EditText mLocalityEditText;
    private EditText mAddressEditText;

    private EditText mAskingPriceEditText;

    private EditText mPlotAreaEditText;

    private EditText mDescription;

    private String mSellRentText = "";
    private String mPropertyType = "";
    private String mTransactionType = "";
    private String mAvailability = "";


    private Button mTakePhoto;
    private Button mTakePhoto1;
    private Button mTakePhoto2;

    public static final int TAKE_PHOTO_REQUEST = 0;
    public static final int CHOOSE_PHOTO_REQUEST = 1;

    public static final int TAKE_PHOTO_REQUEST1 = 2;
    public static final int CHOOSE_PHOTO_REQUEST1 = 3;


    public static final int TAKE_PHOTO_REQUEST2 = 4;
    public static final int CHOOSE_PHOTO_REQUEST2 = 5;

    public static final int MEDIA_TYPE_IMAGE = 7;
    protected Uri mMediaUri;
    protected Uri mMediaUri1;
    protected Uri mMediaUri2;

    protected ParseFile mFile;
    protected ParseFile mFile1;
    protected ParseFile mFile2;



    protected DialogInterface.OnClickListener mDialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

            switch (i) {
                case 0:
                    Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    mMediaUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                    if (mMediaUri == null) {

                        Toast.makeText(SellRentActivity.this, getString(R.string.error_external_storage), Toast.LENGTH_LONG).show();
                    } else {

                        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);

                        startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST);
                        break;


                    }

                case 1:
                    Intent choosePhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    choosePhotoIntent.setType("image/*");
                    startActivityForResult(choosePhotoIntent, CHOOSE_PHOTO_REQUEST);
                    break;


            }
        }

    };

    protected DialogInterface.OnClickListener mDialogListener1 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

            switch (i) {
                case 0:
                    Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    mMediaUri1 = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                    if (mMediaUri1 == null) {

                        Toast.makeText(SellRentActivity.this, getString(R.string.error_external_storage), Toast.LENGTH_LONG).show();
                    } else {

                        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri1);

                        startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST1);
                        break;


                    }

                case 1:
                    Intent choosePhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    choosePhotoIntent.setType("image/*");
                    startActivityForResult(choosePhotoIntent, CHOOSE_PHOTO_REQUEST1);
                    break;


            }
        }

    };

    protected DialogInterface.OnClickListener mDialogListener2 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

            switch (i) {
                case 0:
                    Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    mMediaUri2 = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                    if (mMediaUri2 == null) {

                        Toast.makeText(SellRentActivity.this, getString(R.string.error_external_storage), Toast.LENGTH_LONG).show();
                    } else {

                        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri2);

                        startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST2);
                        break;


                    }

                case 1:
                    Intent choosePhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    choosePhotoIntent.setType("image/*");
                    startActivityForResult(choosePhotoIntent, CHOOSE_PHOTO_REQUEST2);
                    break;


            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_rent);


        PostAdForm();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == CHOOSE_PHOTO_REQUEST || requestCode == TAKE_PHOTO_REQUEST) {

                if (requestCode == CHOOSE_PHOTO_REQUEST) {

                    if (data == null) {

                        Toast.makeText(this, getString(R.string.general_error), Toast.LENGTH_LONG).show();
                    } else {

                        mMediaUri = data.getData();

                        Log.i(TAG, mMediaUri.toString());

                    }
                }


                // Add it to the galary
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanIntent.setData(mMediaUri);
                sendBroadcast(mediaScanIntent);


                Bitmap bitmap = null;
                try {
                    GetImageThumbnail getImageThumbnail = new GetImageThumbnail();
                    bitmap = getImageThumbnail.getThumbnail(mMediaUri, this);
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                // Setting image image icon on the imageview

                ImageView imageView = (ImageView) this
                        .findViewById(R.id.photoView);
                imageView.setVisibility(View.VISIBLE);
                mTakePhoto.setVisibility(View.GONE);

                imageView.setImageBitmap(bitmap);
                mTakePhoto1.setVisibility(View.VISIBLE);


                //Photo to byte array

                byte[] filebytes = FileHelper.getByteArrayFromFile(SellRentActivity.this, mMediaUri);
                if (filebytes == null){

                    Toast.makeText(SellRentActivity.this, getString(R.string.general_error), Toast.LENGTH_LONG).show();
                }
                else {
                    filebytes = FileHelper.reduceImageForUpload(filebytes);

                    String fileName = FileHelper.getFileName(SellRentActivity.this, mMediaUri, ParseConstants.TYPE_IMAGE);

                    mFile = new ParseFile(fileName, filebytes);
                }


            } else if (requestCode == CHOOSE_PHOTO_REQUEST1 || requestCode == TAKE_PHOTO_REQUEST1) {

                if (requestCode == CHOOSE_PHOTO_REQUEST1) {

                    if (data == null) {

                        Toast.makeText(this, getString(R.string.general_error), Toast.LENGTH_LONG).show();
                    } else {

                        mMediaUri1 = data.getData();

                        Log.i(TAG, mMediaUri1.toString());

                    }
                }


                // Add it to the galary
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanIntent.setData(mMediaUri1);
                sendBroadcast(mediaScanIntent);


                Bitmap bitmap = null;
                try {
                    GetImageThumbnail getImageThumbnail = new GetImageThumbnail();
                    bitmap = getImageThumbnail.getThumbnail(mMediaUri1, this);
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                // Setting image image icon on the imageview

                ImageView imageView1 = (ImageView) this
                        .findViewById(R.id.photoView1);
                imageView1.setVisibility(View.VISIBLE);
                mTakePhoto1.setVisibility(View.GONE);

                imageView1.setImageBitmap(bitmap);
                mTakePhoto2.setVisibility(View.VISIBLE);


                byte[] filebytes1 = FileHelper.getByteArrayFromFile(SellRentActivity.this, mMediaUri1);
                if (filebytes1 == null){

                    Toast.makeText(SellRentActivity.this, getString(R.string.general_error), Toast.LENGTH_LONG).show();
                }
                else {
                    filebytes1 = FileHelper.reduceImageForUpload(filebytes1);

                    String fileName1 = FileHelper.getFileName(SellRentActivity.this, mMediaUri1, ParseConstants.TYPE_IMAGE);

                    mFile1 = new ParseFile(fileName1, filebytes1);
                }



            } else if (requestCode == CHOOSE_PHOTO_REQUEST2 || requestCode == TAKE_PHOTO_REQUEST2) {

                if (requestCode == CHOOSE_PHOTO_REQUEST2) {

                    if (data == null) {

                        Toast.makeText(this, getString(R.string.general_error), Toast.LENGTH_LONG).show();
                    } else {

                        mMediaUri2 = data.getData();

                        Log.i(TAG, mMediaUri2.toString());

                    }
                }


                // Add it to the galary
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanIntent.setData(mMediaUri2);
                sendBroadcast(mediaScanIntent);


                Bitmap bitmap = null;
                try {
                    GetImageThumbnail getImageThumbnail = new GetImageThumbnail();
                    bitmap = getImageThumbnail.getThumbnail(mMediaUri2, this);
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                // Setting image image icon on the imageview

                ImageView imageView2 = (ImageView) this
                        .findViewById(R.id.photoView2);
                imageView2.setVisibility(View.VISIBLE);
                mTakePhoto2.setVisibility(View.GONE);

                imageView2.setImageBitmap(bitmap);



                byte[] filebytes2 = FileHelper.getByteArrayFromFile(SellRentActivity.this, mMediaUri2);
                if (filebytes2 == null){

                    Toast.makeText(SellRentActivity.this, getString(R.string.general_error), Toast.LENGTH_LONG).show();
                }
                else {
                    filebytes2 = FileHelper.reduceImageForUpload(filebytes2);

                    String fileName2 = FileHelper.getFileName(SellRentActivity.this, mMediaUri2, ParseConstants.TYPE_IMAGE);

                    mFile2 = new ParseFile(fileName2, filebytes2);
                }


            }

        } else if (resultCode != RESULT_CANCELED) {

            Toast.makeText(this, getString(R.string.general_error), Toast.LENGTH_LONG).show();

        }


    }


    private Uri getOutputMediaFileUri(int mediaType) {
        if (isExternalStorageAvailable()) {

            //1. Get the External Storage directory

            File mediaStorageDirs = new File
                    (Environment.getExternalStoragePublicDirectory
                            (Environment.DIRECTORY_PICTURES),
                            getString(R.string.app_name));

            //2. Create our sub directory
            if (!mediaStorageDirs.exists()) {
                if (!mediaStorageDirs.mkdirs()) {
                    Log.e(TAG, "Failed to create the directory");
                    return null;
                }
            }
            //3. Create a file name

            File mediaFile;
            Date now = new Date();

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
                    .format(now);

            String path = mediaStorageDirs.getPath() + File.separator;
            if (mediaType == MEDIA_TYPE_IMAGE) {
                mediaFile = new File(path, "IMG_" + timeStamp + ".jpg");
            } else {
                return null;
            }
            //4. Create the file
            //5. Return the file's Uri
            Log.i(TAG, "File: " + Uri.fromFile(mediaFile));
            return Uri.fromFile(mediaFile);


        } else {
            return null;
        }
    }

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();

        if (state.equals(Environment.MEDIA_MOUNTED)) {


            return true;
        } else {

            return false;
        }

    }

    public void PostAdForm() {

        //Take Photo

        mTakePhoto = (Button) findViewById(R.id.takePhotoButton);
        mTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SellRentActivity.this);
                builder.setItems(R.array.camera_dailogs, mDialogListener);

                AlertDialog dialog;
                dialog = builder.create();
                dialog.show();


            }
        });
        mTakePhoto1 = (Button) findViewById(R.id.takePhoto1);
        mTakePhoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SellRentActivity.this);
                builder.setItems(R.array.camera_dailogs, mDialogListener1);

                AlertDialog dialog;
                dialog = builder.create();
                dialog.show();
            }
        });

        mTakePhoto2 = (Button) findViewById(R.id.takePhoto2);
        mTakePhoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SellRentActivity.this);
                builder.setItems(R.array.camera_dailogs, mDialogListener2);

                AlertDialog dialog;
                dialog = builder.create();
                dialog.show();
            }
        });




        // Text Fields

        mLocalityEditText = (EditText) findViewById(R.id.localityEditText);
        mAddressEditText = (EditText) findViewById(R.id.addressEditText);
        mAskingPriceEditText = (EditText) findViewById(R.id.askingPriceEditText);
        mPlotAreaEditText = (EditText) findViewById(R.id.plotAreaEditText);
        mDescription = (EditText) findViewById(R.id.descriptionEditText);


        // Number Pickers

        mBathroomNumberPicker = (NumberPicker) findViewById(R.id.bathroomsNumberPicker);
        mBathroomNumberPicker.setMaxValue(100);
        mBathroomNumberPicker.setMinValue(1);
        mBathroomNumberPicker.setValue(1);

        mBedroomroomNumberPicker = (NumberPicker) findViewById(R.id.bedroomsNumberPicker);
        mBedroomroomNumberPicker.setMaxValue(100);
        mBedroomroomNumberPicker.setMinValue(1);
        mBedroomroomNumberPicker.setValue(1);


        // City Autocomplete

        final String[] mCities = getResources().getStringArray(R.array.cities);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.auto_complete_adapter, mCities);
        mCityAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.citiesAutoCompleteTextView);
        mCityAutoCompleteTextView.setThreshold(2);
        mCityAutoCompleteTextView.setAdapter(adapter);


        // SellOrRent

        mSellPropertyButton = (Button) findViewById(R.id.sellPropertyButton);
        mSellPropertyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSellRentText = getResources().getString(R.string.sell_property);

                mSellPropertyButton.setSelected(true);
                mRentPropertyButton.setSelected(false);
                Log.i("What the check", mSellRentText);
            }
        });
        mRentPropertyButton = (Button) findViewById(R.id.rentPropertyButton);
        mRentPropertyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRentPropertyButton.setSelected(true);
                mSellPropertyButton.setSelected(false);

                mSellRentText = getResources().getString(R.string.rent_property);
                Log.i("What the check", mSellRentText);
            }
        });


        // Property Type

        mAppartmentButton = (Button) findViewById(R.id.appartmentButton);
        mAppartmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPropertyType = getResources().getString(R.string.apt);
                mAppartmentButton.setSelected(true);
                mBuilderFloorButton.setSelected(false);
                mPlotLandButton.setSelected(false);
                mFarmhouseButton.setSelected(false);
                mStudioApartmentButton.setSelected(false);
                mFarmhouseButton.setSelected(false);
                mServicedApartmentButton.setSelected(false);
                Log.i("Property Type: ", mPropertyType);

            }
        });
        mBuilderFloorButton = (Button) findViewById(R.id.builderFloorButton);
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
        mPlotLandButton = (Button) findViewById(R.id.plotLandButton);
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
        mHouseButton = (Button) findViewById(R.id.houseButton);
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
        mStudioApartmentButton = (Button) findViewById(R.id.studioApartmentButton);
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
        mFarmhouseButton = (Button) findViewById(R.id.farmHouseButton);
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
        mServicedApartmentButton = (Button) findViewById(R.id.servicedApartmentButton);
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

        mResaleButton = (Button) findViewById(R.id.resaleButton);
        mResaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTransactionType = getResources().getString(R.string.resale);
                mResaleButton.setSelected(true);
                mNewBookingButton.setSelected(false);
                Log.i("Transaction Type: ", mTransactionType);

            }
        });
        mNewBookingButton = (Button) findViewById(R.id.newBookingButton);
        mNewBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTransactionType = getResources().getString(R.string.new_booking);
                mNewBookingButton.setSelected(true);
                mResaleButton.setSelected(false);
                Log.i("Transaction Type: ", mTransactionType);
            }
        });

        mUnderConstructionButton = (Button) findViewById(R.id.underConstructionButton);
        mUnderConstructionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAvailability = getResources().getString(R.string.under_construction);
                mUnderConstructionButton.setSelected(true);
                mReadyToMoveButton.setSelected(false);
                Log.i("Availability: ", mAvailability);
            }
        });

        mReadyToMoveButton = (Button) findViewById(R.id.readyToMovebutton);
        mReadyToMoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAvailability = getResources().getString(R.string.ready_to_move);
                mReadyToMoveButton.setSelected(true);
                mUnderConstructionButton.setSelected(false);
                Log.i("Availability: ", mAvailability);
            }
        });

        mPostAd = (Button) findViewById(R.id.postAdButton);
        mPostAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                // Get values from Text fields
                String locality = mLocalityEditText.getText().toString();
                String address = mAddressEditText.getText().toString();
                String askingPrice = mAskingPriceEditText.getText().toString();
                String plotArea = mPlotAreaEditText.getText().toString();
                String description = mDescription.getText().toString();
                String city = mCityAutoCompleteTextView.getText().toString();

                // Get Value from Number Picker

                String bathroom = String.valueOf(mBathroomNumberPicker.getValue());
                String bedroom = String.valueOf(mBedroomroomNumberPicker.getValue());


                // Check for Errors

                // Sell Or Rent
                if (mSellRentText.isEmpty()) {

                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(SellRentActivity.this);
                    builder.setTitle(getString(R.string.error_title))
                            .setMessage("Please Choose if you want to Rent or Sell")
                            .setPositiveButton(getString(android.R.string.ok), null);

                    AlertDialog dialog = builder.create();

                    dialog.show();
                }

                //Property Type

                else if (mPropertyType.isEmpty()) {

                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(SellRentActivity.this);
                    builder.setTitle(getString(R.string.error_title))
                            .setMessage("Please Choose atleast one property type")
                            .setPositiveButton(getString(android.R.string.ok), null);

                    AlertDialog dialog = builder.create();

                    dialog.show();
                }
                //Transaction Type

                else if (mTransactionType.isEmpty()) {

                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(SellRentActivity.this);
                    builder.setTitle(getString(R.string.error_title))
                            .setMessage("Please Choose atleast one Transaction type")
                            .setPositiveButton(getString(android.R.string.ok), null);

                    AlertDialog dialog = builder.create();

                    dialog.show();
                }

                //Property Type

                else if (mAvailability.isEmpty()) {

                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(SellRentActivity.this);
                    builder.setTitle(getString(R.string.error_title))
                            .setMessage("Please Choose atleast one Availability type")
                            .setPositiveButton(getString(android.R.string.ok), null);

                    AlertDialog dialog = builder.create();

                    dialog.show();
                }


                //Text Fields

                else if (city.isEmpty()) {

                    mCityAutoCompleteTextView.setError("Please select your city");
                } else if (locality.isEmpty()) {

                    mLocalityEditText.setError("Please enter your Locality... e.g. SG Road ... Maninagar ... Satelite ");
                } else if (address.isEmpty()) {

                    mAddressEditText.setError("Please enter your Address... e.g. 400, Mendel Ave");
                } else if (askingPrice.isEmpty()) {

                    mAskingPriceEditText.setError("Please choose a Asking Price");
                } else if (plotArea.isEmpty()) {

                    mPlotAreaEditText.setError("Please enter Plot Area");
                } else if (description.isEmpty()) {

                    mDescription.setError("Please write a short description about your property");
                } else {




                    Log.i("City: ", city);
                    Log.i("Bathroom: ", bathroom);

                    ParseObject Property = new ParseObject(ParseConstants.CLASS_PROPERTY);
                    Property.put(ParseConstants.KEY_SELL_RENT, mSellRentText);
                    Property.put(ParseConstants.KEY_PROPERTY_TYPE, mPropertyType);
                    Property.put(ParseConstants.KEY_CITY, city);
                    Property.put(ParseConstants.KEY_LOCALITY, locality);
                    Property.put(ParseConstants.KEY_ADDRESS, address);
                    Property.put(ParseConstants.KEY_PRICE, askingPrice);
                    Property.put(ParseConstants.KEY_BATHROOM, bathroom);
                    Property.put(ParseConstants.KEY_BEDROOM, bedroom);
                    Property.put(ParseConstants.KEY_PLOT_AREA, plotArea);
                    Property.put(ParseConstants.KEY_TRANSACTION_TYPE, mTransactionType);
                    Property.put(ParseConstants.KEY_AVAILABILITY, mAvailability);
                    Property.put(ParseConstants.KEY_DESCRIPTION, description);
                    Property.put(ParseConstants.KEY_PHONE_NUMBER, ParseUser.getCurrentUser().getUsername());
                    Property.put(ParseConstants.KEY_DEALER_NAME, ParseUser.getCurrentUser().getString("name"));

                    if (mFile != null) {
                        Property.put(ParseConstants.KEY_PHOTO, mFile);
                    }
                    if (mFile1 != null){
                        Property.put(ParseConstants.KEY_PHOTO1, mFile1);
                    }

                    if (mFile2 != null){
                        Property.put(ParseConstants.KEY_PHOTO2, mFile2);
                    }

                    Property.saveInBackground();

                    Intent intent = new Intent(SellRentActivity.this,
                            PropertyResultActivity.class);
                    startActivity(intent);

                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sell_rent, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
