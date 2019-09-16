package com.example.cnw_mvp.view;

public interface BaseView<T> {
    void showProgress();

    void showErrorMessage();

    void showRecyclerView(T t);
}
