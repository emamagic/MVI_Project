package com.emamagic.moviestreaming.ui.modules.home.contract;

import androidx.annotation.StringDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({
        CategoryType.GENRE,
        CategoryType.TOP,
        CategoryType.NEW,
        CategoryType.SERIES,
        CategoryType.POPULAR,
        CategoryType.ANIMATION,
})

public @interface CategoryType {
    String GENRE = "genre";
    String TOP = "top_movie_imdb";
    String NEW = "movie_new";
    String SERIES = "series";
    String POPULAR = "popular_movie";
    String ANIMATION = "animation";
}
