package viceagent.com.viceagent;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class PropertyResultActivity extends ActionBarActivity {
    private static final String TAG = PropertyResultActivity.class.getSimpleName();

    public static final String TAG_OBJECT_ID = "objectId";

    private ListView mListView;
    private PropertyResultAdapter mAdapter;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_result);




    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter = new PropertyResultAdapter(this);
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
                        (PropertyResultActivity.this, DetailPropertyActivity.class);
                intent.putExtra(TAG_OBJECT_ID, objectId);
                startActivity(intent);
            }
        });

    }
}
