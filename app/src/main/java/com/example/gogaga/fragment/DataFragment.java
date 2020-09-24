package com.example.gogaga.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gogaga.Post;
import com.example.gogaga.PostAdapter;
import com.example.gogaga.R;
import com.example.gogaga.apiinterface.PostApiInterface;
import com.example.gogaga.retrofit.PostRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataFragment extends Fragment {

    private static final String TAG = "DataFragment";

    PostApiInterface postApiInterface;
    RecyclerView recyclerView;
    PostAdapter postAdapter;
    List<Post> postList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_data, container, false);
        PostRetrofit postRetrofit = new PostRetrofit();
        postApiInterface = postRetrofit.getPostsInterface();

        recyclerView = rootView.findViewById(R.id.data_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        postAdapter = new PostAdapter(getContext(), postList);
        recyclerView.setAdapter(postAdapter);

        postApiInterface.getPosts().enqueue(callback);
        return rootView;
    }

    Callback<List<Post>> callback = new Callback<List<Post>>() {
        @Override
        public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
            if (response.isSuccessful()) {
                postList = response.body();
                Log.i(TAG, "onResponse: " + postList);
                postAdapter.setData(postList.subList(0, 15));
            }
        }

        @Override
        public void onFailure(Call<List<Post>> call, Throwable t) {
            t.printStackTrace();
        }
    };
}