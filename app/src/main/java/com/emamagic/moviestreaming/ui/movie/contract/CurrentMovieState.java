package com.emamagic.moviestreaming.ui.movie.contract;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        CurrentMovieState.NON_STATE,
        CurrentMovieState.RECEIVE_MOVIES
})

public @interface CurrentMovieState {
    int NON_STATE = 0;
    int RECEIVE_MOVIES = 1;
}
