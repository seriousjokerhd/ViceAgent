package viceagent.com.viceagent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


public class YourListingActivity extends ActionBarActivity {
    private static final String TAG = YourListingActivity.class.getSimpleName();

    public static final String TAG_OBJECT_ID = "objectId";

    private ListView mListView;
    private YourListingAdapterAdapter mAdapter;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_result);




    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter = new YourListingAdapterAdapter(this);
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(mAdapter);
        mAdapter.loadObjects();



        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // getting values from selected ListItem
                String objectId = ((TextView) view.findViewById(R.id.objectId)).getText()
                        .toString();
                Intent intent = new Intent
                        (YourListingActivity.this, DetailPropertyActivity.class);
                intent.putExtra(TAG_OBJECT_ID, objectId);
                startActivity(intent);
            }
        });

    }
}
