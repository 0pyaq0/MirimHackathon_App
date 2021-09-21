package com.example.hackathon_java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hackathon_java.Service.GithubService;
import com.example.hackathon_java.user.GithubUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Button search;
    EditText gitID;
    ImageView profile;
    TextView name, location, bio, repos, gists, followers, following;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Mirim Hackathon_APP(JAVA.ver)");
        gitID=findViewById(R.id.gitID);
        search=findViewById(R.id.search);
        profile=findViewById(R.id.profile);
        name=findViewById(R.id.name);
        location=findViewById(R.id.location);
        bio=findViewById(R.id.bio);
        repos=findViewById(R.id.repos);
        gists=findViewById(R.id.gists);
        followers=findViewById(R.id.follower);
        following=findViewById(R.id.following);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final GithubService githubService = retrofit.create(GithubService.class);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<GithubUser>call= githubService.getPosts(gitID.getText().toString());

                call.enqueue(new Callback<GithubUser>() {
                    @Override
                    public void onResponse(Call<GithubUser> call, Response<GithubUser> response) {
                        if(response.isSuccessful()) {

                            GithubUser user = response.body();
                            name.setText(user.getName());
                            location.setText(user.getLocation());
                            bio.setText(user.getBio());

                            Glide.with(getApplicationContext()).load(user.getAvatar_url()).into(profile);

                            repos.setText(Integer.toString(user.getPublic_repos()));
                            gists.setText(Integer.toString(user.getPublic_gists()));
                            followers.setText(Integer.toString(user.getFollowers()));
                            following.setText(Integer.toString(user.getFollowing()));
                        }
                    }

                    @Override
                    public void onFailure(Call<GithubUser> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }
}