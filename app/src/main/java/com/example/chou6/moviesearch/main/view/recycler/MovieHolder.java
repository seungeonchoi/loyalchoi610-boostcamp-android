package com.example.chou6.moviesearch.main.view.recycler;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.example.chou6.moviesearch.databinding.RowMovieBinding;

public class MovieHolder extends RecyclerView.ViewHolder{
    public RowMovieBinding rmb;
    public MovieHolder(View itemView) {
        super(itemView);
        rmb = DataBindingUtil.bind(itemView);

    }

}
