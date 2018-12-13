package ua.i74.movieapp.ui.adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.i74.movieapp.R;
import ua.i74.movieapp.data.network.ServiceData;
import ua.i74.movieapp.model.MovieModel;
import ua.i74.movieapp.ui.listener.OnViewHolderClickListener;

public class LandscapeMovieViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_movie_list_layout)
    ConstraintLayout layout;
    @BindView(R.id.item_poster_image_view)
    ImageView posterImageView;
    @BindView(R.id.item_title_text_view)
    TextView titleTextView;
    @BindView(R.id.item_rating_text_view)
    TextView ratingTextView;
    @BindView(R.id.item_release_date_text_view)
    TextView releaseDateTextView;
    @BindView(R.id.item_category_text_view)
    TextView categoryTextView;

    private OnViewHolderClickListener listener;
    private int position;

    public LandscapeMovieViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindPosition(int position) {
        this.position = position;
    }

    public void bindListener(OnViewHolderClickListener listener) {
        this.listener = listener;
    }

    public void bindModel(MovieModel movie) {
        if (position >= 0 && listener != null) {
            titleTextView.setText(movie.getTitle());
            releaseDateTextView.setText(movie.getReleaseDate());
            ratingTextView.setText(String.valueOf(movie.getRating()));

            if (movie.getGenreList() == null || movie.getGenreList().isEmpty())
                categoryTextView.setText("");
            else {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < movie.getGenreList().size(); i++) {
                    builder.append(movie.getGenreList().get(i).getName());
                    if (i < movie.getGenreList().size()-1)
                        builder.append(", ");
                }
                categoryTextView.setText(builder.toString());
            }

            Picasso.get()
                    .load(ServiceData.BACKDROP_IMAGE_URL + movie.getBackdropPath())
                    .centerInside()
                    .error(R.drawable.emoticon_devil_outline)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .resizeDimen(R.dimen.backdrop_width, R.dimen.backdrop_height)
                    .into(posterImageView);
        }
    }

    @OnClick(R.id.item_movie_list_layout)
    public void onClick() {
        listener.onViewHolderClick(position);
    }
}
