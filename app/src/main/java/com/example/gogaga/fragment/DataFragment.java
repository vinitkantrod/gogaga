package com.example.gogaga.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gogaga.Post;
import com.example.gogaga.PostAdapter;
import com.example.gogaga.R;
import com.example.gogaga.apiinterface.PostApiInterface;
import com.example.gogaga.retrofit.PostRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataFragment extends Fragment {

    private static final String TAG = "DataFragment";
    private Integer START = 0;
    private Integer LIMIT = 15;

    PostApiInterface postApiInterface;
    RecyclerView recyclerView;
    PostAdapter postAdapter;
    List<Post> postList = new ArrayList<>();
    ProgressBar progressBar;
    LinearLayoutManager linearLayoutManager;

    Boolean isScrolling = false;
    int currentItem, totalItems, scrollOutItems;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_data, container, false);
        PostRetrofit postRetrofit = new PostRetrofit();
        postApiInterface = postRetrofit.getPostsInterface();

        progressBar = rootView.findViewById(R.id.progress_bar);
        recyclerView = rootView.findViewById(R.id.data_recycler_view);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        postAdapter = new PostAdapter(getContext(), postList);
        recyclerView.setAdapter(postAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.i(TAG, "onScrollStateChanged: newState: " + newState + ", SCROLL_STATE_TOUCH_SCROLL: " + AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItem = linearLayoutManager.getChildCount();
                totalItems = linearLayoutManager.getItemCount();
                scrollOutItems = linearLayoutManager.findFirstVisibleItemPosition();
                Log.i(TAG, "onScrolled: currentItem: " + currentItem + ", total: " + totalItems + ", scrolled: " + scrollOutItems);
                if (isScrolling && (currentItem + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    START = START + LIMIT;
                    fetchData();
                }
            }
        });

        fetchData();
        return rootView;
    }

    public void fetchData() {
        progressBar.setVisibility(View.VISIBLE);
        postApiInterface.getPosts(String.valueOf(START), String.valueOf(LIMIT)).enqueue(callback);
    }


    Callback<List<Post>> callback = new Callback<List<Post>>() {
        @Override
        public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
            if (response.isSuccessful()) {
                List<Post> tempList = response.body();
                Log.i(TAG, "onResponse: " + postList);
                postAdapter.addData(tempList);
                progressBar.setVisibility(View.GONE);
            }
        }

        @Override
        public void onFailure(Call<List<Post>> call, Throwable t) {
            t.printStackTrace();
        }
    };
}