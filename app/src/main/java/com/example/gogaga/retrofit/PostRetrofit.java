package com.example.gogaga.retrofit;

import com.example.gogaga.apiinterface.PostApiInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostRetrofit {
    PostApiInterface postApiInterface;

    public void getRetrofitInstance() {
        if (postApiInterface == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(PostApiInterface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            postApiInterface = retrofit.create(PostApiInterface.class);
        }
    }

    public PostApiInterface getPostsInterface() {
        if (postApiInterface == null) {
            getRetrofitInstance();
        }
        return postApiInterface;
    }
}
