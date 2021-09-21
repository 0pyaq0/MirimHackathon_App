# MirimHackathon_App (Java) 💬
<br>
🔗 Retrofit과 GitHub API를 이용한 GitHub 사용자 검색 앱
<br>
<br>
<br>

## Screen
<img src = "https://user-images.githubusercontent.com/72568433/134142509-fc327fc4-9a1b-425b-ab5c-0daf712c32a6.PNG"/>

## Code
<br>
<br>

1. Retrofit과 관련된 라이브러리를 build.gradle 파일에 추가
<br>

   ```java
     // retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.3.0'
    
    //Json
    implementation 'com.squareup.retrofit2:converter-gson:2.3.0'
    
    //Glide(GitHub Profile용)
    implementation 'com.github.bumptech.glide:glide:4.11.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'
   ```
 <br>
 <br>
 <br>

2. Manifests 파일에 인터넷 권한 추가
<br>

   ```java
     <uses-permission android:name="android.permission.INTERNET" />
   ```
   
 <br>
 <br>
 <br>

3. Retrofit Builder 생성
<br>

baseUrl에는 사용할 깃허브 api 주소를 넣는다. (주소 길이가 길면 잘려질 수 있음!)

   ```java
     Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final GithubService githubService = retrofit.create(GithubService.class);
   ```
addConverterFactory에는 GsonConverter를 추가하여 JSON 형식을 *DTO Class 형식으로 자동변환해준다.

<br>

*DTO Class (Data Transfer Object) : 데이터 전송 객체

  
   
 <br>
 <br>
 <br>

4. API Interface 생성
<br>

   ```java
    public interface GithubService {
    @GET("/users/{user}")  //baseUrl + "/users/{user}"
    Call<GithubUser> getPosts(@Path("user") String user);
    }
  ```

 <br>
 <br>
 <br>

5. 표시할 정보 설정
<br>

GitHub API를 참고하여 자신이 표시할 정보를 고른 후 클래스를 생성
- https://api.github.com/users/0pyaq0


<img src = "https://user-images.githubusercontent.com/72568433/134142527-73f98b09-dd69-495a-8411-5720fcd13071.PNG"/>



   ```java
    public class GithubUser {
    private String name;
    private String company;
    private String blog;
    private String location;
    private String bio;
    private int public_repos;
    private int public_gists;
    private int followers;
    private int following;
    private String avatar_url;
  ```

`<br>
 <br>
 <br>

6. 요청 보내기
<br>

   ```java
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
  ```
  
  setText로 값을 설정한다.
  
<br>
<br>
<br>

## Reference
- https://docs.github.com/en/rest/reference/users#get-a-user
- https://github.com/HwangEunmi/Retrofit-Sample
- https://kumgo1d.tistory.com/21
  
  
<br>
<br>
<br>

## Tools
- Android Studio ( Java )
