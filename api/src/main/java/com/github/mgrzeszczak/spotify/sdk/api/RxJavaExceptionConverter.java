package com.github.mgrzeszczak.spotify.sdk.api;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;

class RxJavaExceptionConverter {

    private final ErrorConverter errorConverter;

    RxJavaExceptionConverter(ErrorConverter errorConverter) {
        this.errorConverter = errorConverter;
    }

    <T> SingleSource<T> convertSingle(Throwable throwable) {
        return Single.error(errorConverter.convert(throwable));
    }

    <T> Observable<T> convertObservable(Throwable throwable) {
        return Observable.error(errorConverter.convert(throwable));
    }

    <T> Flowable<T> convertFlowable(Throwable throwable) {
        return Flowable.error(errorConverter.convert(throwable));
    }

    <T> Maybe<T> convertMaybe(Throwable throwable) {
        return Maybe.error(errorConverter.convert(throwable));
    }

    Completable convertCompletable(Throwable throwable) {
        return Completable.error(errorConverter.convert(throwable));
    }

}
