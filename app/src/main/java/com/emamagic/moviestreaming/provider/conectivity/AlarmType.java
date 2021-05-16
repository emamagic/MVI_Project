package com.emamagic.moviestreaming.provider.conectivity;


import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        AlarmType.INFO,
        AlarmType.WARNING,
        AlarmType.ERROR
})
public @interface AlarmType {
    int INFO = 1;
    int WARNING = 2;
    int ERROR = 3;
}
