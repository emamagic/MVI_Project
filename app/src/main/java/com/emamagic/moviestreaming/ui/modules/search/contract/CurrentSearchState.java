package com.emamagic.moviestreaming.ui.modules.search.contract;


import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        CurrentSearchState.NON_STATE,
        CurrentSearchState.MOVIES_RECEIVED
})
public @interface CurrentSearchState {
    int NON_STATE = 0;
    int MOVIES_RECEIVED = 1;
}
