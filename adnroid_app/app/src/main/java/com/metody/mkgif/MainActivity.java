package com.metody.mkgif;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.metody.mkgif.data.tools.DataItem;
import com.metody.mkgif.data.tools.DataType;
import com.metody.mkgif.data.tools.MyAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<DataItem> myDataset = new ArrayList<>();
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view1);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        FloatingActionButton fab = findViewById(R.id.fab);
        context = this;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddingActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        DataItem[] myDataset2 = new DataItem[]{new DataItem("Filmy", DataType.Thema),new DataItem("obejrzane", DataType.status),
                new DataItem( "first", DataType.item), new DataItem( "do oglądnięcia xd", DataType.status),
                new DataItem(  "second", DataType.item), new DataItem("Gry", DataType.Thema),
                new DataItem("test how long can it be for now", DataType.item),
                new DataItem("rak", DataType.status),
                new DataItem("lol", DataType.item),
                new DataItem("Książki", DataType.Thema),
                new DataItem("Not see", DataType.item),
                new DataItem("some more content",DataType.item)};
        myDataset.addAll(Arrays.asList(myDataset2));
        fetchData();
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);}
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//         Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//         Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

//        return super.onOptionsItemSelected(item);
    public void fetchData() {
        Ion.with(context)
                .load("http://192.168.0.18:8080/MKGiF_Server/ConnectionServlet")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    //TODO zmienic wczystywanie na 100% poprawne
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            JsonObject jsonObject = result.getAsJsonObject();
                            JsonObject gry = (JsonObject) jsonObject.get("gry");
                            JsonArray companyList = gry.getAsJsonArray("skonczone");
                            Log.d(companyList.toString(), "heheh");
                            for (int i = 0; i < companyList.size(); i++) {
                                myDataset.add(new DataItem(((JsonObject) companyList.get(i)).get("tytul").getAsString(), DataType.item));
                            }
                            mAdapter.notifyDataSetChanged();

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("????", "!!!");
        String s = data.getStringExtra("text");
        Log.d("??", s);
    }

}
