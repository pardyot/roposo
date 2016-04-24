package com.example.pardyot.roposo;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
    TextView likeAndComment;
//    TextView likesCount;
//    TextView commentCount;
    ImageView authorImage;
//    TextView username;
//    TextView handle;
//    TextView createdOn;
    TextView followInfo;

    SharedPreferences mPref;
    boolean isfollowed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_description);
        Typeface robotoRegular = Typeface.createFromAsset(getAssets(), "RobotoTTF/Roboto-Regular.ttf");
        Typeface robotoLight = Typeface.createFromAsset(getAssets(), "RobotoTTF/Roboto-Light.ttf");
        storyImage = (ImageView) findViewById(R.id.story_image);
        title = (TextView) findViewById(R.id.title);
        author = (TextView) findViewById(R.id.author);
        follow = (Button) findViewById(R.id.follow);
        verb = (TextView) findViewById(R.id.verb);
        description = (TextView) findViewById(R.id.description);
        type = (TextView) findViewById(R.id.type);
        website = (Button) findViewById(R.id.website);
        likeAndComment = (TextView) findViewById(R.id.like_and_comment);
//        likesCount = (TextView) findViewById(R.id.like_count);
//        commentCount = (TextView) findViewById(R.id.comment_count);
        authorImage = (ImageView) findViewById(R.id.author_image);
//        username = (TextView) findViewById(R.id.username);
//        handle = (TextView) findViewById(R.id.handle);
//        createdOn = (TextView) findViewById(R.id.createdOn);
        followInfo = (TextView) findViewById(R.id.follow_info);



        Intent in = getIntent();
        final StoryBean story = (StoryBean) in.getSerializableExtra("story");
        final UserBean user = (UserBean) in.getSerializableExtra("user");

        mPref = getSharedPreferences("app", MODE_PRIVATE);

        String pref_follow = mPref.getString(story.getId(), null);
        isfollowed=false;

        if(pref_follow!=null){
            if(pref_follow.equals("yes")){
                isfollowed=true;
            } else{
                isfollowed=false;
            }
        }

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        Picasso.with(this).load(story.getStoryImage()).resize(width,dpToPx(380)).centerCrop().into(storyImage);
        title.setText(story.getTitle());
        title.setTypeface(robotoRegular);
        author.setText("by- " + user.getUsername());
        author.setTypeface(robotoLight);
        verb.setText(story.getVerb());
        verb.setTypeface(robotoLight);
        description.setText(story.getDescription());
        description.setTypeface(robotoLight);
        type.setText("Type: " + story.getType());
        type.setTypeface(robotoLight);
        likeAndComment.setText(story.getLikesCount() + " likes and " + story.getCommentCount() + " Comments");
        likeAndComment.setTypeface(robotoLight);
        follow.setTypeface(robotoLight);
        website.setTypeface(robotoLight);
//        likesCount.setText(String.valueOf(story.getLikesCount()));
//        commentCount.setText(String.valueOf(story.getCommentCount()));

        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(story.getStoryUrl());
                Intent openStoryWebsite = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(openStoryWebsite);
            }
        });

        if(isfollowed){
            follow.setText("Following");
            follow.setBackgroundDrawable( getResources().getDrawable(R.drawable.custom_button) );
        } else{
            follow.setText("Follow");
            follow.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.btn_default) );
        }

        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isfollowed) {
                    follow.setText("Follow");
                    follow.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.btn_default));
                    isfollowed = !isfollowed;
                    SharedPreferences.Editor edit = mPref.edit();
                    edit.putString(story.getId(), "no");
                    edit.commit();
                } else {
                    isfollowed = !isfollowed;
                    follow.setText("Following");
                    follow.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button));
                    SharedPreferences.Editor edit = mPref.edit();
                    edit.putString(story.getId(), "yes");
                    edit.commit();
                }
            }
        });


        Picasso.with(this).load(user.getImageUrl()).into(authorImage);
        authorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(StoryDescription.this);
                dialog.setContentView(R.layout.author_dialog);
                dialog.setTitle("About the Author");
                ImageView authorImage = (ImageView) dialog.findViewById(R.id.author_image);
                TextView username = (TextView) dialog.findViewById(R.id.username);
                TextView handle = (TextView) dialog.findViewById(R.id.handle);
                TextView createdOn = (TextView) dialog.findViewById(R.id.created_on);
                TextView followInfo = (TextView) dialog.findViewById(R.id.follow_info);
                Picasso.with(StoryDescription.this).load(user.getImageUrl()).into(authorImage);
                username.setText(user.getUsername());
                handle.setText(user.getHandle());
                String followers = String.valueOf(user.getFollowers());
                String following = String.valueOf(user.getFollowing());
                followInfo.setText("Followers:" + followers + ", Following: " + following);
                createdOn.setText(getDateCurrentTimeZone(user.getCreatedOn()));
                handle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse(user.getUrl());
                        Intent openAuthor = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(openAuthor);
                    }
                });

                dialog.show();
            }
        });
//        username.setText(user.getUsername());
//        handle.setText(user.getHandle());
        String followers = String.valueOf(user.getFollowers());
        String following = String.valueOf(user.getFollowing());
        followInfo.setText("Followers:" + followers + ", Following: " + following);
        followInfo.setTypeface(robotoLight);
//        handle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Uri uri = Uri.parse(user.getUrl());
//                Intent openAuthor = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(openAuthor);
//            }
//        });
//        createdOn.setText(getDateCurrentTimeZone(user.getCreatedOn()));

    }



    public  String getDateCurrentTimeZone(long timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        }catch (Exception e) {
        }
        return "";
    }

    private int dpToPx(int dp)
    {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }
}
