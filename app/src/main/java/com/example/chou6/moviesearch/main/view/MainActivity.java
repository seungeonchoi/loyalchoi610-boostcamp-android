package com.example.chou6.moviesearch.main.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.chou6.moviesearch.*;
import com.example.chou6.moviesearch.databinding.DataLoadingBinding;
import com.example.chou6.moviesearch.main.MVP_Main;
import com.example.chou6.moviesearch.main.model.MainModel;
import com.example.chou6.moviesearch.main.presenter.MainPresenter;
import com.example.chou6.moviesearch.main.view.recycler.MoviesAdapter;
import com.example.chou6.moviesearch.network.AppHelper;
import com.example.chou6.moviesearch.model.MovieModel;
import com.example.chou6.moviesearch.databinding.ActivityMainBinding;


import java.util.*;

import android.databinding.DataBindingUtil;


public class MainActivity extends AppCompatActivity implements MVP_Main.RequiredView {
    List<MovieModel> movies;
    MoviesAdapter adapter;
    public static String TAG = "MainActivity - ";
    static int firstVisibleInListview;
    ActivityMainBinding amb;
    DataLoadingBinding dlb;
    AppCompatDialog progressDialog;


    private MVP_Main.ProvidedPresenter mPresenter;
    private MVP_Main.ProvidedAdapterView aView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupView();
        setupMVP();

        if(AppHelper.requestQueue ==null){
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy(isChangingConfigurations());

    }
    public void setupView(){
        movies = new ArrayList<>();
        adapter = new MoviesAdapter(getAppContext(), movies);
        aView = adapter;

        amb = DataBindingUtil.setContentView(this,R.layout.activity_main);
        final LinearLayoutManager lm = new LinearLayoutManager(this);
        amb.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.isLoading())
                    return;
                String str = amb.query.getText().toString();
                if (str == null || "".equals(str)){

                }else{
                    mPresenter.search(str);
                }
            }
        });
        amb.recyclerView.setHasFixedSize(true);
        amb.recyclerView.setLayoutManager(lm);
        amb.recyclerView.setAdapter(adapter);
        amb.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(amb.recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(amb.recyclerView, dx, dy);
                int currentFirstVisible = lm.findFirstVisibleItemPosition();
                if(currentFirstVisible > aView.getLoadThreshold() && mPresenter.isMoreDataAvailable()){
                    mPresenter.findMore();
                }

                firstVisibleInListview = currentFirstVisible;
            }
        });
        firstVisibleInListview = lm.findFirstVisibleItemPosition();


    }

    public void setupMVP(){
        // Create the Presenter
        MainPresenter presenter = new MainPresenter(this,adapter);
        // Set Adapter presenter
        adapter.setPresenter(presenter);
        // Create the Model
        MainModel model = new MainModel(presenter);
        // Set Presenter model
        presenter.setModel(model);
        mPresenter = presenter;
    }
    public void showDialog(){
        if(progressDialog == null){
            progressDialog = new AppCompatDialog(this);
            progressDialog.setCancelable(false);
            dlb = DataLoadingBinding.inflate(LayoutInflater.from(progressDialog.getContext()));
            Glide.with(progressDialog.getContext()).load(R.drawable.loading).into(dlb.ivFrameLoading);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setContentView(dlb.getRoot());
            progressDialog.show();

        }



    }
    public void hideDialog(){
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;

        }

    }

    @Override
    public void notifyDataSetChanged() {
        aView.notifyDataSetChanged();
    }

    @Override
    public void notifyItemRangeInserted(int newindex, int i) {
        aView.notifyItemRangeInserted(newindex,i);
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }



}