package com.example.cnw_mvp.view.impl;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnw_mvp.R;
import com.example.cnw_mvp.adapter.RepositoryAdapter;
import com.example.cnw_mvp.model.Repository;
import com.example.cnw_mvp.presenter.impl.UserRepoPresenterImpl;
import com.example.cnw_mvp.view.UserRepoBaseView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserRepoActivity extends AppCompatActivity implements UserRepoBaseView, View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.button_search)
    ImageButton buttonSearch;
    @BindView(R.id.edit_text_username)
    EditText editTextUsername;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.text_info)
    TextView textInfo;
    @BindView(R.id.repos_recycler_view)
    RecyclerView reposRecyclerView;
    private UserRepoPresenterImpl userRepoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_repo);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        buttonSearch.setOnClickListener(this);
        String username = getIntent().getStringExtra("username");
        editTextUsername.setText(username);
        addTextListener();

        userRepoPresenter = new UserRepoPresenterImpl();
        userRepoPresenter.attachView(this);
        userRepoPresenter.loadGitHubUserRepo(username);
    }

    private void addTextListener() {
        editTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                buttonSearch.setVisibility(charSequence.length() > 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
        textInfo.setVisibility(View.GONE);
        reposRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMessage() {
        progress.setVisibility(View.GONE);
        textInfo.setVisibility(View.VISIBLE);
        reposRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showRecyclerView(List<Repository> repositories) {
        progress.setVisibility(View.GONE);
        textInfo.setVisibility(View.GONE);
        reposRecyclerView.setVisibility(View.VISIBLE);

        reposRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RepositoryAdapter adapter = new RepositoryAdapter(this, repositories);
        reposRecyclerView.setAdapter(adapter);
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(editTextUsername.getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_search:
                hideSoftKeyboard();
                userRepoPresenter.loadGitHubUserRepo(editTextUsername.getText().toString());
                break;
        }
    }
}
