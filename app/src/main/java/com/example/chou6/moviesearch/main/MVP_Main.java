package com.example.chou6.moviesearch.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.chou6.moviesearch.main.view.recycler.MovieHolder;
import com.example.chou6.moviesearch.model.MovieModel;
import com.example.chou6.moviesearch.json.movie.MovieJson;
import io.reactivex.Observable;

public interface MVP_Main {

        interface RequiredView{
            // View operations permitted to Presenter

            Context getAppContext();
            Context getActivityContext();

            void notifyDataSetChanged();
            void notifyItemRangeInserted(int newindex, int i);
            void showDialog();
            void hideDialog();
            void makeToast(String message);

        }

        interface RequiredPresenter{
            // Presenter operations permitted to Model


            Context getAppContext();
            Context getActivityContext();

            void refreshView(MovieJson json);
            void updateView(MovieJson json);

        }

        interface ProvidedPresenter{
            // Presenter operations permitted to View
            boolean isMoreDataAvailable();

            MovieHolder createViewHolder(ViewGroup parent, int viewType);

            void search(String query);
            void findMore();
            void onDestroy(boolean isChangingConfiguration);
            void bindViewHolder(MovieHolder holder, int position);

        }
        interface ProvidedModel{

            // Presenter operations permitted to Presenter

            void onDestroy(boolean isChangingConfiguration);

            Observable<MovieJson> getMovie(String query);
            Observable<MovieJson>getMovie(String query, int start);
            Observable<Bitmap> getImg(String url);

        }

        interface ProvidedAdapterView{

            int getLoadThreshold();
            void notifyDataSetChanged();
            void notifyItemRangeInserted(int newindex, int i);

        }
        interface ProvidedAdapterModel{
            boolean hasMoreData();
            boolean isMoreDataAvailable();
            boolean isLoading();

            int getItemCount();
            int getTotal();

            String getQuery();

            void setLoading(boolean flag);
            void setQuery(String query);
            void setTotal(int total);
            void setLoadThreshold(int itemCount);
            void setMoreDataAvailable(boolean flag);
            void addMovie(MovieModel data);
            void clear();

            MovieModel getItem(int position);

        }
}
