package com.example.ankitchaturvedi.popularmovies.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ankitchaturvedi.popularmovies.ActivityMovieDetails;
import com.example.ankitchaturvedi.popularmovies.Models.Movie;
import com.example.ankitchaturvedi.popularmovies.R;
import com.example.ankitchaturvedi.popularmovies.utils.helper;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ankit.Chaturvedi on 26-Apr-17.
 */

public class MovieDataAdapter extends RecyclerView.Adapter<MovieDataAdapter.MyViewHolder> {
    private Context mcontext;
    public List<Movie> movieList;

    public MovieDataAdapter(Context mcontext, List<Movie> movieList ) {
        this.mcontext = mcontext;
        this.movieList = movieList;
    }

    @Override
    public MovieDataAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new MovieDataAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieDataAdapter.MyViewHolder holder, final int position) {

        Picasso.with(mcontext)
                .load(helper.base_url + helper.poster_sizes + movieList.get(position).poster_path)
                .into(holder.miv_detail);

        holder.mtv_ratings.setText(String.valueOf(movieList.get(position).vote_average));

        holder.miv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(mcontext, ActivityMovieDetails.class);
                in.putExtra("backdrop_path", movieList.get(position).backdrop_path);
                in.putExtra("poster_path", movieList.get(position).poster_path);
                in.putExtra("overview", movieList.get(position).overview);
                in.putExtra("release_date", movieList.get(position).release_date);
                in.putExtra("vote_average", movieList.get(position).vote_average);
//                in.putExtra("adult",movieList.get(position).get);
                in.putExtra("title", movieList.get(position).title);
                in.putExtra("id", movieList.get(position).id);
                String transitionName = mcontext.getString(R.string.title);
                ActivityOptions transitionActivityOptions = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) mcontext, view, transitionName);
                }
                if (transitionActivityOptions != null) {
                    mcontext.startActivity(in, transitionActivityOptions.toBundle());
                } else mcontext.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList != null ? movieList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView miv_detail;
        TextView mtv_ratings;
        CardView mcard_view;

        public MyViewHolder(View itemView) {
            super(itemView);

            miv_detail = (ImageView) itemView.findViewById(R.id.iv_detail);
            mtv_ratings = (TextView) itemView.findViewById(R.id.tv_ratings);
            mcard_view = (CardView) itemView.findViewById(R.id.card_view);

        }
    }
}
