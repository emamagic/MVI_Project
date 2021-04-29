package com.emamagic.moviestreaming.ui.genre.contract;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        CurrentGenreState.NON_STATE
})
public @interface CurrentGenreState {
    int NON_STATE = 0;
}
