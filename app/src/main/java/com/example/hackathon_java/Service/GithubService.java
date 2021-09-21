package com.example.hackathon_java.Service;

import com.example.hackathon_java.user.GithubUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {
    @GET("/users/{user}")
    Call<GithubUser> getPosts(@Path("user") String user);
}
