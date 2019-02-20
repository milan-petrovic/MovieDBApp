package com.example.android.moviedbapp.upcoming;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.moviedbapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpcomingFragment extends Fragment implements UpcomingPresenter.View {
    @BindView(R.id.upRecycleView) RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private UpcomingPresenter presenter;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new UpcomingPresenter();
        presenter.setView(this);
        presenter.getUpcomingMovies();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.upcoming_fragment, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        return  view;
    }

    @Override
    public void displayUpcomingMovies(List<UpcomingResult> upcomingResults) {
        adapter = new UpcomingMovieAdapter(getActivity(), upcomingResults);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void displayError() {
        Toast.makeText(getActivity(), getString(R.string.problem_with_loading), Toast.LENGTH_SHORT).show();
    }
}
