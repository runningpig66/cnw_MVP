package com.example.cnw_mvp.presenter;

/**
 * func: 业务逻辑处理基类
 */
public interface BasePresenter<T> {
    void attachView(T view);

    void detachView();
}
