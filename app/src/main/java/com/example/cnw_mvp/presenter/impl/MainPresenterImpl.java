package com.example.cnw_mvp.presenter.impl;

import com.example.cnw_mvp.model.GithubService;
import com.example.cnw_mvp.model.Repo;
import com.example.cnw_mvp.presenter.MainPresenter;
import com.example.cnw_mvp.view.MainBaseView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenterImpl implements MainPresenter {
    private MainBaseView mainBaseView;
    private Disposable disposable;//TODO Q1
    private List<Repo> repoList;

    @Override
    public void attachView(MainBaseView view) {
        mainBaseView = view;
    }

    @Override
    public void detachView() {
        mainBaseView = null;
        disposable.dispose();//TODO Q1
    }

    @Override
    public void loadGitHubJava() {
        mainBaseView.showProgress();
        String url = "https://github-trending-api.now.sh/repositories";
        GithubService.Factory.create().javaRepositories(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Repo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(List<Repo> repos) {
                        repoList = repos;
                    }

                    @Override
                    public void onError(Throwable e) {
                        mainBaseView.showErrorMessage();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        if (repoList != null) {
                            mainBaseView.showRecyclerView(repoList);
                        }
                    }
                });
    }
}
