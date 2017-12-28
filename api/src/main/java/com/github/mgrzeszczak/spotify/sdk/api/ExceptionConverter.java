package com.github.mgrzeszczak.spotify.sdk.api;

import java.io.IOException;

import com.github.mgrzeszczak.spotify.sdk.api.exception.ApiException;
import com.github.mgrzeszczak.spotify.sdk.model.ErrorHolder;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.HttpException;

public class ExceptionConverter {

    private final Converter<ResponseBody, ErrorHolder> errorHolderConverter;

    public ExceptionConverter(Converter<ResponseBody, ErrorHolder> errorHolderConverter) {
        this.errorHolderConverter = errorHolderConverter;
    }

    public <T> SingleSource<T> convertSingle(Throwable throwable) throws IOException {
        return Single.error(createApiException(throwable));
    }

    public <T> Observable<T> convertObservable(Throwable throwable) throws IOException {
        return Observable.error(createApiException(throwable));
    }

    public <T> Flowable<T> convertFlowable(Throwable throwable) throws IOException {
        return Flowable.error(createApiException(throwable));
    }

    public <T> Maybe<T> convertMaybe(Throwable throwable) throws IOException {
        return Maybe.error(createApiException(throwable));
    }

    public Completable convertCompletable(Throwable throwable) throws IOException {
        return Completable.error(createApiException(throwable));
    }

    private ApiException createApiException(Throwable throwable) throws IOException {
        return new ApiException(throwable, errorHolderConverter.convert(((HttpException) throwable).response().errorBody()).getError());
    }

}
