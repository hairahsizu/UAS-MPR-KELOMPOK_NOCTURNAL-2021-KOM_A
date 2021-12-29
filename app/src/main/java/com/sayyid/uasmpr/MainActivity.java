package com.sayyid.uasmpr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sayyid.uasmpr.adapter.DataAdapter;
import com.sayyid.uasmpr.models.ResponseDataItem;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DataAdapter.OnItemClickListener {
    //Deklarasi Variabel
    private ShimmerFrameLayout animationLoading;
    private DataAdapter dataAdapter;
    private RecyclerView recyclerView;
    private List<ResponseDataItem> responseDataItemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initialize();
    }

    private void initialize() {
        animationLoading.startShimmer();
    }

    private void initView() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        dataAdapter = new DataAdapter(MainActivity.this);
        animationLoading = findViewById(R.id.aninmation_loading);
        recyclerView = findViewById(R.id.recyclerView);

        responseDataItemList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(dataAdapter);

        dataAdapter.setOnItemClickListener(this);

        getData();

    }

    private void getData() {
        AndroidNetworking.get("https://gist.githubusercontent.com/erdem/8c7d26765831d0f9a8c62f02782ae00d/raw/248037cd701af0a4957cce340dabb0fd04e38f4c/countries.json")
                .setTag("getData")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        responseDataItemList = new Gson().fromJson(response.toString(),
                                new TypeToken<ArrayList<ResponseDataItem>>() {
                                }.getType());

                        if (responseDataItemList.size() != 0) {
                            animationLoading.stopShimmer();
                            animationLoading.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        } else {
                            animationLoading.stopShimmer();
                            animationLoading.setVisibility(View.GONE);
                        }

                        dataAdapter.addAll(responseDataItemList);

                    }

                    @Override
                    public void onError(ANError error) {
                        if (error.getErrorCode() != 0) {
                            Log.d("TAG", "onError errorCode : " + error.getErrorCode());
                            Log.d("TAG", "onError errorBody : " + error.getErrorBody());
                            Log.d("TAG", "onError errorDetail : " + error.getErrorDetail());
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d("TAG", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });
    }
    @Override
    public void onItemClick(View view, ResponseDataItem responseDataItem, int position) {
        Gson gson = new Gson();
        Intent activity = new Intent(this, DetailNegara.class);
        activity.putExtra("data_negara", gson.toJson(responseDataItem));
        startActivity(activity);

    }
}