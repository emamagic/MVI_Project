package com.emamagic.moviestreaming.ui.home.contract;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        CurrentHomeState.SLIDER_RECEIVED,
        CurrentHomeState.MOVIE_RECEIVED,
        CurrentHomeState.NON_STATE
})
public @interface CurrentHomeState {
    int NON_STATE = 0;
    int SLIDER_RECEIVED = 1;
    int MOVIE_RECEIVED = 2;
}