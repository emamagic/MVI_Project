package com.emamagic.moviestreaming.ui.genre.contract;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        CurrentGenreState.NON_STATE,
        CurrentGenreState.RECEIVE_GENRES
})
public @interface CurrentGenreState {
    int NON_STATE = 0;
    int RECEIVE_GENRES = 1;
}
