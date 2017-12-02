package com.metody.mkgif;

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
import java.util.stream.IntStream;

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
        String[] themas = getResources().getStringArray(R.array.types);
        String[] statuses = getResources().getStringArray(R.array.status);
        ArrayList<DataItem> dataSet = new ArrayList<>();
        for(String thema : themas){
            DataItem itemThema = new DataItem(thema, DataType.Thema);
            dataSet.add(itemThema);
            for(String status : statuses){
                DataItem item = new DataItem(status, DataType.status);
                dataSet.add(item);
            }
        }
        myDataset.addAll(dataSet);
        fetchData();
        mAdapter = new MyAdapter(myDataset, this);
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
                .load("http://172.16.18.44:8080/MkGiF/ConnectionSwervlet")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    //TODO zmienic wczystywanie na 100% poprawne
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            JsonObject jsonObject = result.getAsJsonObject();
                            JsonArray array = jsonObject.get("Filmy").getAsJsonArray();
                            for(int i = 0; i < array.size(); i++){
                                myDataset.add(new DataItem(array.get(i).getAsString(), DataType.item));
                            }
//                            JsonObject gry = (JsonObject) jsonObject.get("gry");
//                            JsonArray companyList = gry.getAsJsonArray("skonczone");
//                            Log.d(companyList.toString(), "heheh");
//                            for (int i = 0; i < companyList.size(); i++) {
//                                myDataset.add(new DataItem(((JsonObject) companyList.get(i)).get("tytul").getAsString(), DataType.item));
//                            }
                            mAdapter.notifyDataSetChanged();

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String s = data.getStringExtra("text");
        String a = data.getStringExtra("type");
        String date = data.getStringExtra("date");
        Float rating = data.getFloatExtra("rating", -1);
        String status = data.getStringExtra("status");
        DataItem item = new DataItem(s, DataType.item);
        item.setDate(date);
        item.setRating(rating);
        Log.d(getThemaFromString(a), status);
        int startIndex =myDataset.indexOf(new DataItem(getThemaFromString(a), DataType.Thema));
        int endIndex = myDataset.size();
        int index = myDataset.subList(startIndex, endIndex).indexOf(new DataItem(status, DataType.status))+1;
        Log.d(String.valueOf(index), String.valueOf(startIndex));
        Log.d("jak: ", myDataset.subList(startIndex, endIndex).get(0).getContent());
        myDataset.add(index+startIndex,item);
        mAdapter.notifyDataSetChanged();
    }
    String getThemaFromString(String source){
        for(String thema : getResources().getStringArray(R.array.types)){
            if(source.contains(thema)){
                return thema;
            }
        }
        return "";
    }

}