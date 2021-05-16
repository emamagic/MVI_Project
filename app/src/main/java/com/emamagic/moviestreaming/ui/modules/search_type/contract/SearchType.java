package com.emamagic.moviestreaming.ui.modules.search_type.contract;


import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({
        SearchType.TOP,
        SearchType.NEW,
        SearchType.SERIES,
        SearchType.ANIMATION
})
public @interface SearchType {
    String TOP = "top_movie_imdb";
    String NEW = "movie_new";
    String SERIES = "series";
    String ANIMATION = "animation";
}
