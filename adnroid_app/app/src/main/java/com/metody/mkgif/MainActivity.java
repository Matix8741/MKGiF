package com.metody.mkgif;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.metody.mkgif.data.tools.Data;
import com.metody.mkgif.data.tools.DataCategory;
import com.metody.mkgif.data.tools.DataItem;
import com.metody.mkgif.data.tools.DataType;
import com.metody.mkgif.data.tools.EditItemTouchHelperCallback;
import com.metody.mkgif.data.tools.MyAdapter;
import com.metody.mkgif.data.tools.intefaces.OnStartDragListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnStartDragListener {

    ItemTouchHelper mItemTouchHelper;
    private RecyclerView.Adapter mAdapter;
    List<Data> myDataSet = new ArrayList<>();
    Context context;
    String username = "";
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView mRecyclerView = findViewById(R.id.recycler_view1);
        mRecyclerView.setHasFixedSize(true);
        Intent intent = getIntent();
        if (intent != null) {
            username = intent.getStringExtra("username");
            password = intent.getStringExtra("password");
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        context = this;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddingActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(myDataSet, this, this);
        ItemTouchHelper.Callback callback =
                new EditItemTouchHelperCallback((MyAdapter) mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        createRecyclerBaseView();
        fetchData();
    }

    private void createRecyclerBaseView(){

        String[] themes = getResources().getStringArray(R.array.types);
        String[] statuses = getResources().getStringArray(R.array.status);
        for (String theme : themes) {
            Data itemTheme = new DataCategory(theme, DataType.Theme);
            myDataSet.add(itemTheme);
            for (String status : statuses) {
                Data item = new DataCategory(status, DataType.status);
                myDataSet.add(item);
            }
        }
    }
    public void fetchData() {
        final Map<String, String> stringMap = new HashMap<>();
        final Map<String, String> stringMap2 = new HashMap<>();
        final String[] themesNotPolishCharacter = {"Ksiazka", "Gra", "Film"};
        final String[] statusesNotPolishCharacter = {"W przyszlosci", "W trakcie", "Skonczone", "Przerwane"};
        final String[] themes = getResources().getStringArray(R.array.types);
        final String[] statuses = getResources().getStringArray(R.array.status);
        for (int i = 0; i < themesNotPolishCharacter.length; i++) {
            stringMap.put(themesNotPolishCharacter[i], themes[i]);
        }
        for (int i = 0; i < statusesNotPolishCharacter.length; i++) {
            stringMap2.put(statusesNotPolishCharacter[i], statuses[i]);
        }
        String url = "http://192.168.0.18:8080/MkGiF/ConnectionSwervlet?Login=" + username + "&Password=" + password;
        Ion.with(context)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            JsonObject jsonObject = result.getAsJsonObject();
                            for (String theme : themesNotPolishCharacter) {
                                JsonObject jsonObject1 = jsonObject.getAsJsonObject(theme);
                                if (jsonObject1 != null) {
                                    for (String status : statusesNotPolishCharacter) {
                                        JsonArray array = jsonObject1.getAsJsonArray(status);
                                        for (JsonElement element : array) {
                                            JsonObject object = element.getAsJsonObject();
                                            DataItem item = new DataItem(object.get("Tytul").getAsString());
                                            item.setRating(object.get("Rating").getAsFloat());
                                            item.setDate(object.get("Data").getAsString());
                                            item.setCreator(object.get("Tworca").getAsString());
                                            int startIndex = myDataSet.indexOf(new DataCategory(getThemeFromString(stringMap.get(theme)), DataType.Theme));
                                            int endIndex = myDataSet.size();
                                            int index = myDataSet.subList(startIndex, endIndex).indexOf(new DataCategory(stringMap2.get(status), DataType.status)) + 1;
                                            myDataSet.add(index, item);
                                        }
                                    }
                                }
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
        String s = data.getStringExtra("text");
        String a = data.getStringExtra("type");
        String date = data.getStringExtra("date");
        Float rating = data.getFloatExtra("rating", -1);
        String status = data.getStringExtra("status");
        DataItem item = new DataItem(s);
        item.setDate(date);
        item.setRating(rating);
        int startIndex = myDataSet.indexOf(new DataCategory(getThemeFromString(a), DataType.Theme));
        int endIndex = myDataSet.size();
        int index = myDataSet.subList(startIndex, endIndex).indexOf(new DataCategory(status, DataType.status)) + 1;
        myDataSet.add(index + startIndex, item);
        mAdapter.notifyDataSetChanged();
    }

    String getThemeFromString(String source) {
        for (String theme : getResources().getStringArray(R.array.types)) {
            if (source.contains(theme)) {
                return theme;
            }
        }
        return "";
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}