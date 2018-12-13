package ua.i74.movieapp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ua.i74.movieapp.R;
import ua.i74.movieapp.model.MovieModel;
import ua.i74.movieapp.ui.listener.OnRecyclerItemClickListener;
import ua.i74.movieapp.ui.listener.OnViewHolderClickListener;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements OnViewHolderClickListener {
    private static final String TAG = "MovieRecyclerAdapter";
    private static final int PORTRAIT_TYPE_VIEW = 0;
    private static final int LANDSCAPE_TYPE_VIEW = 1;

    private boolean isPortraitOrientation;
    private OnRecyclerItemClickListener listener;

    private List<MovieModel> movies;

    public MovieRecyclerAdapter(boolean isPortraitOrientation, OnRecyclerItemClickListener listener) {
        this.isPortraitOrientation = isPortraitOrientation;
        this.listener = listener;
        movies = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == PORTRAIT_TYPE_VIEW)
            return new PortraitMovieViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_list, parent, false)
            );
        else
            return new LandscapeMovieViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_list, parent, false)
            );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.i(TAG, position + "");
        switch (holder.getItemViewType()) {
            case PORTRAIT_TYPE_VIEW:
                PortraitMovieViewHolder portraitMovieViewHolder = (PortraitMovieViewHolder) holder;
                portraitMovieViewHolder.bindListener(this);
                portraitMovieViewHolder.bindPosition(position);
                portraitMovieViewHolder.bindModel(movies.get(position));
                break;
            case LANDSCAPE_TYPE_VIEW:
                LandscapeMovieViewHolder landscapeMovieViewHolder = (LandscapeMovieViewHolder) holder;
                landscapeMovieViewHolder.bindListener(this);
                landscapeMovieViewHolder.bindPosition(position);
                landscapeMovieViewHolder.bindModel(movies.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, movies.size() + "");
        return movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPortraitOrientation)
            return PORTRAIT_TYPE_VIEW;
        else
            return LANDSCAPE_TYPE_VIEW;
    }

    @Override
    public void onViewHolderClick(int position) {
        listener.onRecyclerItemClick(movies.get(position));
    }

    public void setData(List<MovieModel> movies) {
        Log.i(TAG, movies.size() + "");
        this.movies = movies;
        notifyDataSetChanged();
    }
}
