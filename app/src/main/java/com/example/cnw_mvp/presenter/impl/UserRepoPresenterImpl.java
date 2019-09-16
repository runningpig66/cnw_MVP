package com.example.cnw_mvp.presenter.impl;

import com.example.cnw_mvp.model.GithubService;
import com.example.cnw_mvp.model.Repository;
import com.example.cnw_mvp.presenter.UserRepoPresenter;
import com.example.cnw_mvp.view.UserRepoBaseView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserRepoPresenterImpl implements UserRepoPresenter {
    private UserRepoBaseView userRepoBaseView;
    private Disposable disposable;//TODO Q1
    private List<Repository> repositoryList;

    @Override
    public void attachView(UserRepoBaseView view) {
        userRepoBaseView = view;
    }

    @Override
    public void detachView() {
        userRepoBaseView = null;
        disposable.dispose();//TODO Q1
    }

    @Override
    public void loadGitHubUserRepo(String userName) {
        userRepoBaseView.showProgress();
        GithubService.Factory.create().publicRepositories(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Repository>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(List<Repository> repositories) {
                        repositoryList = repositories;
                    }

                    @Override
                    public void onError(Throwable e) {
                        userRepoBaseView.showErrorMessage();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        if (repositoryList != null) {
                            userRepoBaseView.showRecyclerView(repositoryList);
                        }
                    }
                });
    }
}
