package com.github.mgrzeszczak.spotify.sdk.api;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

interface ErrorConverter {

    default Throwable convert(Throwable throwable) {
        if (throwable instanceof HttpException) {
            ResponseBody body = ((HttpException) throwable).response().errorBody();
            if (body != null) {
                try {
                    return convertResponseBody(throwable, body);
                } catch (Exception e) {
                    return throwable;
                }
            }
        }
        return throwable;
    }

    Throwable convertResponseBody(Throwable throwable, ResponseBody body) throws Exception;

}
