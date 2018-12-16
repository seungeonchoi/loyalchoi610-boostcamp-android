package com.example.chou6.moviesearch.main.model;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.chou6.moviesearch.json.movie.MovieJson;
import com.example.chou6.moviesearch.network.AppHelper;
import com.example.chou6.moviesearch.network.NaverApi;
import com.example.chou6.moviesearch.main.MVP_Main;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainModel implements MVP_Main.ProvidedModel {

    Retrofit retrofit;
    NaverApi napi;
    MVP_Main.RequiredPresenter presenter;
    public MainModel(MVP_Main.RequiredPresenter presenter) {
        this.presenter = presenter;
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://openapi.naver.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        napi = retrofit.create(NaverApi.class);
    }

    public void onDestroy(boolean isChangingConfiguration) {
        if (!isChangingConfiguration) {
            retrofit=null;
            napi = null;
        }
    }

    public Observable<MovieJson> getMovie(String query) {
         return napi.getmovies(query);

    }
    public Observable getMovie(String query, int start) {
        return napi.getmovies(query,start);

    }

    public Observable<Bitmap> getImg(String url){
        Observable<Bitmap> obm = Observable.create(emitter -> {
            ImageRequest request = new ImageRequest(url,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            emitter.onNext(bitmap);
                            emitter.onComplete();
                        }
                    }, 0,0,ImageView.ScaleType.FIT_START,null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Image -",error.getMessage());
                        }
                    });
            AppHelper.requestQueue.add(request);
        });
        return obm;
    }

}
