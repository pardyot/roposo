package com.example.pardyot.roposo;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pardyot on 22/4/16.
 */
public class StoryAdapter extends BaseAdapter{
    List<StoryBean> storyList = new ArrayList<>();
    Context context;

    public StoryAdapter(List<StoryBean> storyList, Context context) {
        this.storyList = storyList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return storyList.size();
    }

    @Override
    public Object getItem(int position) {
        return storyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.story_list_item, parent, false);
        } else{
            row = convertView;
        }
        Typeface robotoBold = Typeface.createFromAsset(context.getAssets(), "RobotoTTF/Roboto-Bold.ttf");

        ImageView storyImage = (ImageView) row.findViewById(R.id.story_image);
        TextView title = (TextView) row.findViewById(R.id.title);
        title.setTypeface(robotoBold);
        TextView description = (TextView) row.findViewById(R.id.description);
        TextView likesCount = (TextView) row.findViewById(R.id.like_count);
        TextView commentCount = (TextView) row.findViewById(R.id.comment_count);
        Picasso.with(context).load(storyList.get(position).getStoryImage()).resize(dpToPx(88), dpToPx(88)).centerCrop().into(storyImage);
        title.setText(storyList.get(position).getTitle());
        description.setText(storyList.get(position).getDescription());
        likesCount.setText(String.valueOf(storyList.get(position).getLikesCount()));
        commentCount.setText(String.valueOf(storyList.get(position).getCommentCount()));
        return row;
    }

    private int dpToPx(int dp)
    {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }
}
