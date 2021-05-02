package com.emamagic.moviestreaming.ui.episode_list.contract;


import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        CurrentEpisodeListState.NON_STATE,
        CurrentEpisodeListState.EPISODES_RECEIVED
})

public @interface CurrentEpisodeListState {
    int NON_STATE = 0;
    int EPISODES_RECEIVED = 1;
}
