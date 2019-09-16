package com.example.cnw_mvp.presenter;

import com.example.cnw_mvp.view.UserRepoBaseView;

/**
 * 处理UserRepoActivity当中的业务逻辑,只为UserRepoActivity服务
 */
public interface UserRepoPresenter extends BasePresenter<UserRepoBaseView> {
    //添加UserRepoPresenter中特有的业务逻辑
    void loadGitHubUserRepo(String userName);
}
