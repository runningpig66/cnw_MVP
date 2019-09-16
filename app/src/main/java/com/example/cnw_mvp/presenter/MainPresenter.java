package com.example.cnw_mvp.presenter;

import com.example.cnw_mvp.view.MainBaseView;

/**
 * 处理MainActivity当中的业务逻辑,只为MainActivity服务
 */
public interface MainPresenter extends BasePresenter<MainBaseView> {
    //添加MainPresenter中特有的业务逻辑
    void loadGitHubJava();
}
