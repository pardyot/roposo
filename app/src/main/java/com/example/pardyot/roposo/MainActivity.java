package com.example.pardyot.roposo;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView storyListView;
    SwipeRefreshLayout storyRefresh;
    List<UserBean> userList = new ArrayList<>();
    List<StoryBean> storyList = new ArrayList<>();
    StoryAdapter storyAdapter;
    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        storyListView = (ListView) findViewById(R.id.story_list);
        storyRefresh = (SwipeRefreshLayout) findViewById(R.id.refresh_story);
        json = loadJSONFromAsset();
        try {
            userList = fillUserList();
            storyList = fillStoryList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        storyAdapter = new StoryAdapter(storyList , this);
        storyListView.setAdapter(storyAdapter);
        storyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StoryBean story = storyList.get(position);
                String authorId = story.getAuthor();
                UserBean user = new UserBean();
                if(authorId.equals(userList.get(0).getId())) {
                    user = userList.get(0);
                } else {
                    user = userList.get(1);
                }
                Intent openStoryDescription = new Intent(MainActivity.this, StoryDescription.class);
                openStoryDescription.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                openStoryDescription.putExtra("story", story);
                openStoryDescription.putExtra("user" , user);
                startActivity(openStoryDescription);
            }
        });

    }

    /**
     * The function is implemented using the code from http://stackoverflow.com/questions/13814503/reading-a-json-file-in-android.
     * @return The json from the data.json file.
     */
    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("data.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    private List<StoryBean> fillStoryList() throws JSONException {
        List<StoryBean> storyList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i=2 ; i < jsonArray.length() ; ++i) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            StoryBean story = new StoryBean();
            story.setDescription(jsonObject.getString("description"));
            story.setId(jsonObject.getString("id"));
            story.setVerb(jsonObject.getString("verb"));
            story.setAuthor(jsonObject.getString("db"));
            story.setStoryUrl(jsonObject.getString("url"));
            story.setStoryImage(jsonObject.getString("si"));
            story.setType(jsonObject.getString("type"));
            story.setTitle(jsonObject.getString("title"));
            story.setLikeFlag(jsonObject.getBoolean("like_flag"));
            story.setLikesCount(jsonObject.getInt("likes_count"));
            story.setCommentCount(jsonObject.getInt("comment_count"));
            storyList.add(story);
        }
        return storyList;
    }

    private List<UserBean> fillUserList() throws JSONException {
        List<UserBean> userList = new ArrayList<>();
        UserBean user = new UserBean();
        JSONArray jsonArray = new JSONArray(json);
        JSONObject jsonObject = (JSONObject) jsonArray.get(0);
        user.setAbout(jsonObject.getString("about"));
        user.setId(jsonObject.getString("id"));
        user.setUsername(jsonObject.getString("username"));
        user.setFollowers(jsonObject.getInt("followers"));
        user.setFollowing(jsonObject.getInt("following"));
        user.setImageUrl(jsonObject.getString("image"));
        user.setUrl(jsonObject.getString("url"));
        user.setHandle(jsonObject.getString("handle"));
        user.setIsFollowing(jsonObject.getBoolean("is_following"));
        user.setCreatedOn(jsonObject.getLong("createdOn"));
        userList.add(user);


        jsonObject = (JSONObject) jsonArray.get(1);
        user.setAbout(jsonObject.getString("about"));
        user.setId(jsonObject.getString("id"));
        user.setUsername(jsonObject.getString("username"));
        user.setFollowers(jsonObject.getInt("followers"));
        user.setFollowing(jsonObject.getInt("following"));
        user.setImageUrl(jsonObject.getString("image"));
        user.setUrl(jsonObject.getString("url"));
        user.setHandle(jsonObject.getString("handle"));
        user.setIsFollowing(jsonObject.getBoolean("is_following"));
        user.setCreatedOn(jsonObject.getLong("createdOn"));
        userList.add(user);
        return userList;
    }
}
