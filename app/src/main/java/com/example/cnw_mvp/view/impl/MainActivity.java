package com.example.cnw_mvp.view.impl;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnw_mvp.R;
import com.example.cnw_mvp.adapter.RepoListAdapter;
import com.example.cnw_mvp.model.Repo;
import com.example.cnw_mvp.presenter.impl.MainPresenterImpl;
import com.example.cnw_mvp.view.MainBaseView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainBaseView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_description)
    TextView textDescription;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.text_info)
    TextView textInfo;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    private MainPresenterImpl mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        textDescription.setText(R.string.github_java);

        mainPresenter = new MainPresenterImpl();
        mainPresenter.attachView(this);
        mainPresenter.loadGitHubJava();
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
        textInfo.setVisibility(View.GONE);
        recycler.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMessage() {
        progress.setVisibility(View.GONE);
        textInfo.setVisibility(View.VISIBLE);
        recycler.setVisibility(View.GONE);
    }

    @Override
    public void showRecyclerView(List<Repo> repos) {
        progress.setVisibility(View.GONE);
        textInfo.setVisibility(View.GONE);
        recycler.setVisibility(View.VISIBLE);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        RepoListAdapter adapter = new RepoListAdapter(this, repos);
        recycler.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }
}
