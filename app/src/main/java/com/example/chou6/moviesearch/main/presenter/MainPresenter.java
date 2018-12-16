package com.example.chou6.moviesearch.main.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.chou6.moviesearch.main.MVP_Main;
import com.example.chou6.moviesearch.main.view.recycler.MovieHolder;
import com.example.chou6.moviesearch.json.movie.MovieJson;
import com.example.chou6.moviesearch.model.MovieModel;
import com.example.chou6.moviesearch.R;
import com.example.chou6.moviesearch.json.movie.MovieItem;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


import java.util.ArrayList;

import static com.example.chou6.moviesearch.main.view.MainActivity.TAG;


public class MainPresenter implements MVP_Main.ProvidedPresenter, MVP_Main.RequiredPresenter {
    //View reference
    private MVP_Main.RequiredView mView;
    // Model reference
    private MVP_Main.ProvidedModel mModel;
    // AdapterModel reference
    private MVP_Main.ProvidedAdapterModel aModel;

    CompositeDisposable compositeDisposable;

    public MainPresenter(MVP_Main.RequiredView view, MVP_Main.ProvidedAdapterModel adapterModel) {
        mView = view;
        aModel = adapterModel;
        compositeDisposable = new CompositeDisposable();
    }

    public void onDestroy(boolean isChangingConfiguration) {
        // View show be null every time onDestroy is called
        mView = null;
        // Inform Model about the event

        mModel.onDestroy(isChangingConfiguration);
        // Activity destroyed
        if ( !isChangingConfiguration ) {
            // Nulls Model when the Activity destruction is permanent
            mModel = null;
        }
    }

    public void setView(MVP_Main.RequiredView view) {
        mView = view ;
    }
    public void setModel(MVP_Main.ProvidedModel model) {
        mModel = model;
    }

    @Override
    public void search(String query) {
        if(aModel.isLoading())
            return;
        aModel.setLoading(true);
        mView.showDialog();
        aModel.setMoreDataAvailable(false);
        aModel.clear();
        aModel.setQuery(query);
        Disposable response = mModel.getMovie(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(json->{

                    refreshView(json);
                });
        compositeDisposable.add(response);
    }

    @Override
    public void findMore() {

        if(aModel.isLoading() | !aModel.isMoreDataAvailable())
            return;
        aModel.setLoading(true);
        Disposable response = mModel.getMovie(aModel.getQuery(),aModel.getItemCount()+1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(json->{
                    updateView(json);
                });
        compositeDisposable.add(response);

    }


    @Override
    public boolean isMoreDataAvailable() {
        return aModel.isMoreDataAvailable();
    }

    @Override
    public MovieHolder createViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(getAppContext());
        return new MovieHolder(inflater.inflate(R.layout.row_movie,parent,false));
    }

    @Override
    public void bindViewHolder(MovieHolder holder, int position) {
        MovieModel movie = aModel.getItem(position);
        holder.rmb.setMovie(movie);
        holder.rmb.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!"".equals(movie.getLink())){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(movie.getLink()));
                    getAppContext().startActivity(browserIntent);
                }
            }
        });
        if(!"".equals(movie.getUrl())){
//            mModel.getImg(movie.getUrl(), holder.rmb.image);
            Disposable response  = mModel.getImg(movie.getUrl()).subscribe(bitmap -> {
                holder.rmb.image.setImageBitmap(bitmap);
            });
            compositeDisposable.add(response);
        }else{
            holder.rmb.image.setImageBitmap(null);

        }
    }

    @Override
    public Context getAppContext() {
        try {
            return mView.getAppContext();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Context getActivityContext() {
        try {
            return mView.getActivityContext();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public void refreshView(MovieJson json) {

        ArrayList<MovieItem> item = json.items;
        for (MovieItem mi : item){
            aModel.addMovie(new MovieModel(mi));
        }
        aModel.setTotal(Integer.parseInt(json.total));
        aModel.setLoadThreshold(aModel.getItemCount());
        if(aModel.hasMoreData()){
            aModel.setMoreDataAvailable(true);
        }else{
            aModel.setMoreDataAvailable(false);
        }
        mView.notifyDataSetChanged();
        mView.hideDialog();
        aModel.setLoading(false);

    }

    @Override
    public void updateView(MovieJson json) {
        int lastindex = aModel.getItemCount();
        ArrayList<MovieItem> item = json.items;
        for (MovieItem mi : item){
            aModel.addMovie(new MovieModel(mi));
        }
        aModel.setLoadThreshold(aModel.getItemCount());
        if(aModel.hasMoreData()){
            aModel.setMoreDataAvailable(true);
        }else{
            aModel.setMoreDataAvailable(false);
        }
        mView.notifyItemRangeInserted(lastindex, aModel.getItemCount()-lastindex);
        aModel.setLoading(false);
    }


}

