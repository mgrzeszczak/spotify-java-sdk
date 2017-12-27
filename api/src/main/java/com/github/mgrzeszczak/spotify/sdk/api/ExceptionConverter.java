package com.github.mgrzeszczak.spotify.sdk.api;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;

class ExceptionConverter {

    private final ThrowableHandler throwableHandler;

    ExceptionConverter(ThrowableHandler throwableHandler) {
        this.throwableHandler = throwableHandler;
    }

    <T> SingleSource<T> convertSingle(Throwable throwable) throws Exception {
        return Single.error(throwableHandler.handle(throwable));
    }

    <T> Observable<T> convertObservable(Throwable throwable) throws Exception {
        return Observable.error(throwableHandler.handle(throwable));
    }

    <T> Flowable<T> convertFlowable(Throwable throwable) throws Exception {
        return Flowable.error(throwableHandler.handle(throwable));
    }

    <T> Maybe<T> convertMaybe(Throwable throwable) throws Exception {
        return Maybe.error(throwableHandler.handle(throwable));
    }

    Completable convertCompletable(Throwable throwable) throws Exception {
        return Completable.error(throwableHandler.handle(throwable));
    }

}
