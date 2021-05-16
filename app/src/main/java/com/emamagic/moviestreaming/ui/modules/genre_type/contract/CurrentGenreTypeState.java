package com.emamagic.moviestreaming.ui.modules.genre_type.contract;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        CurrentGenreTypeState.NON_STATE,
        CurrentGenreTypeState.RECEIVE_GENRES
})
public @interface CurrentGenreTypeState {
    int NON_STATE = 0;
    int RECEIVE_GENRES = 1;
}
