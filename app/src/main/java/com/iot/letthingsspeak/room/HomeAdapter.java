package com.iot.letthingsspeak.room;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iot.letthingsspeak.R;
import com.iot.letthingsspeak.constants.Constants;
import com.iot.letthingsspeak.device.DeviceActivity;

import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeNewsViewHolder> {
    private List<RoomDetails> roomDetails;
    public int ROOM_DETAILS_REQUEST_CODE = 9283;

    public HomeAdapter(List<RoomDetails> newsArticles) {
        this.roomDetails = newsArticles;
    }

    @NonNull
    @Override
    public HomeNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_news, parent, false);
        HomeNewsViewHolder homeNewsViewHolder = new HomeNewsViewHolder(view);
        return homeNewsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeNewsViewHolder holder, final int position) {
        RoomDetails newsArticle = roomDetails.get(position);
        holder.roomTitle.setText(newsArticle.getRoomType());
    }


    @Override
    public int getItemCount() {
        return roomDetails.size();
    }

    public class HomeNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView roomImage;
        TextView roomTitle;

        public HomeNewsViewHolder(View itemView) {
            super(itemView);
            roomImage = (ImageView) itemView.findViewById(R.id.card_news_image);
            roomTitle = (TextView) itemView.findViewById(R.id.room_category);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context.getApplicationContext(), DeviceActivity.class);
            RoomDetails roomDetail = roomDetails.get(getAdapterPosition());
            intent.putExtra(Constants.TITLE_KEY, roomDetail.getRoomType());
            ((Activity)context).startActivityForResult(intent, ROOM_DETAILS_REQUEST_CODE);

        }
    }
}
