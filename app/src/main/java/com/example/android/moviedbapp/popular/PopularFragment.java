package com.example.android.moviedbapp.popular;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.moviedbapp.NetworkSourceData;
import com.example.android.moviedbapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopularFragment extends Fragment implements PopularPresenter.View {

    @BindView(R.id.recycleView) RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private View view;
    private PopularPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PopularPresenter(NetworkSourceData.getInstance().getRetrofit());
        presenter.setView(this);
        presenter.getPopularMovies();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.popular_fragment, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        return view;
    }

    @Override
    public void displayPopularMovies(List<PopularResult> moviePopularResults) {
        adapter = new PopularMovieAdapter(getActivity(), moviePopularResults);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void displayError() {
        Toast.makeText(getActivity(), getString(R.string.problem_with_loading), Toast.LENGTH_SHORT).show();
    }
}
