package com.example.pardyot.roposo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class StoryDescription extends AppCompatActivity {
    ImageView storyImage;
    TextView title;
    TextView author;
    Button follow;
    TextView verb;
    TextView description;
    TextView type;
    Button website;
    TextView likesCount;
    TextView commentCount;
    ImageView authorImage;
    TextView username;
    TextView handle;
    TextView createdOn;
    TextView followInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_description);
        storyImage = (ImageView) findViewById(R.id.story_image);
        title = (TextView) findViewById(R.id.title);
        author = (TextView) findViewById(R.id.author);
        follow = (Button) findViewById(R.id.follow);
        verb = (TextView) findViewById(R.id.verb);
        description = (TextView) findViewById(R.id.description);
        type = (TextView) findViewById(R.id.type);
        website = (Button) findViewById(R.id.website);
        likesCount = (TextView) findViewById(R.id.like_count);
        commentCount = (TextView) findViewById(R.id.comment_count);
        authorImage = (ImageView) findViewById(R.id.author_image);
        username = (TextView) findViewById(R.id.username);
        handle = (TextView) findViewById(R.id.handle);
        createdOn = (TextView) findViewById(R.id.createdOn);
        followInfo = (TextView) findViewById(R.id.follow_info);

        Intent in = getIntent();
        final StoryBean story = (StoryBean) in.getSerializableExtra("story");
        final UserBean user = (UserBean) in.getSerializableExtra("user");


        Picasso.with(this).load(story.getStoryImage()).into(storyImage);
        title.setText(story.getTitle());
        author.setText(user.getUsername());
        verb.setText(story.getVerb());
        description.setText(story.getDescription());
        type.setText(story.getType());
        likesCount.setText(String.valueOf(story.getLikesCount()));
        commentCount.setText(String.valueOf(story.getCommentCount()));

        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(story.getStoryUrl());
                Intent openStoryWebsite = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(openStoryWebsite);
            }
        });

        Picasso.with(this).load(user.getImageUrl()).into(authorImage);
        username.setText(user.getUsername());
        handle.setText(user.getHandle());
        String followers = String.valueOf(user.getFollowers());
        String following = String.valueOf(user.getFollowing());
        followInfo.setText("Followers:" + followers + ", Following: " + following);
        handle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(user.getUrl());
                Intent openAuthor = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(openAuthor);
            }
        });
        createdOn.setText(getDateCurrentTimeZone(user.getCreatedOn()));

    }

    public  String getDateCurrentTimeZone(long timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        }catch (Exception e) {
        }
        return "";
    }
}
