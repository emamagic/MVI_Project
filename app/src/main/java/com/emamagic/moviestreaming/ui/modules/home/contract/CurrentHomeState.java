package com.emamagic.moviestreaming.ui.modules.home.contract;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        CurrentHomeState.SLIDER_RECEIVED,
        CurrentHomeState.MOVIE_RECEIVED,
        CurrentHomeState.GENRE_RECEIVE,
        CurrentHomeState.NON_STATE,
        CurrentHomeState.CLOSE_APP
})
public @interface CurrentHomeState {
    int NON_STATE = 0;
    int SLIDER_RECEIVED = 1;
    int MOVIE_RECEIVED = 2;
    int GENRE_RECEIVE = 3;
    int CLOSE_APP = 4;
}
