package com.emamagic.moviestreaming.ui.genre_list.contract;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        CurrentGenreListState.NON_STATE,
        CurrentGenreListState.GENRE_RECEIVED
})
public @interface CurrentGenreListState {
    int NON_STATE = 0;
    int GENRE_RECEIVED = 1;
}
