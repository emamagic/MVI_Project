package com.emamagic.moviestreaming.ui.modules.movie.contract;


import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        CurrentMovieState.NON_STATE,
        CurrentMovieState.MOVIE_RECEIVED,
        CurrentMovieState.CASTS_RECEIVED,
        CurrentMovieState.SEASONS_RECEIVED
})
public @interface CurrentMovieState {
    int NON_STATE = 0;
    int MOVIE_RECEIVED = 1;
    int CASTS_RECEIVED = 2;
    int SEASONS_RECEIVED = 3;
}
