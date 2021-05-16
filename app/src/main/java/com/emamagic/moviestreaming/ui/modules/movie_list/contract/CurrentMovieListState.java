package com.emamagic.moviestreaming.ui.modules.movie_list.contract;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        CurrentMovieListState.NON_STATE,
        CurrentMovieListState.RECEIVE_MOVIES
})

public @interface CurrentMovieListState {
    int NON_STATE = 0;
    int RECEIVE_MOVIES = 1;
}
