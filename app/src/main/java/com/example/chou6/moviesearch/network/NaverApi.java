package com.example.chou6.moviesearch.network;

import com.example.chou6.moviesearch.json.movie.MovieJson;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface NaverApi {

    @Headers({"X-Naver-Client-Id: bC2vBV2qgM2MWKkUqIuW","X-Naver-Client-Secret: zi3Vupcv_0"})
    @GET("search/movie.json")
    Observable<MovieJson> getmovies(@Query("query") String query);

    @Headers({"X-Naver-Client-Id: bC2vBV2qgM2MWKkUqIuW","X-Naver-Client-Secret: zi3Vupcv_0"})
    @GET("search/movie.json")
    Observable<MovieJson> getmovies(@Query("query") String query, @Query("start") int start);

}
