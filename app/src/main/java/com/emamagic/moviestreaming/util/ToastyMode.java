package com.emamagic.moviestreaming.util;


import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        ToastyMode.MODE_TOAST_SUCCESS,
        ToastyMode.MODE_TOAST_WARNING,
        ToastyMode.MODE_TOAST_ERROR
})
public @interface ToastyMode {
    int MODE_TOAST_SUCCESS = 1;
    int MODE_TOAST_WARNING = 2;
    int MODE_TOAST_ERROR = 3;

}
