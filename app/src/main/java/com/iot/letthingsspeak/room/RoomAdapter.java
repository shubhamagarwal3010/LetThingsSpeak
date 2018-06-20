package com.iot.letthingsspeak.room;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.iot.letthingsspeak.R;
import com.iot.letthingsspeak.aws.db.RoomDO;
import com.iot.letthingsspeak.device.DeviceActivity;

import java.util.List;


public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
    public static final String TITLE_KEY = "letthingsspeak.constants.title";
    public static final String ROOM_NAME = "";
    public static final int ACTIVITY_REQUEST_CODE = 202;
    public int ROOM_DETAILS_REQUEST_CODE = 9283;
    private Context mContext;
    private List<RoomDO> roomDetails;

    public RoomAdapter(Context mContext, List<RoomDO> roomDetails) {
        this.mContext = mContext;
        this.roomDetails = roomDetails;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_home, parent, false);
        RoomViewHolder roomViewHolder = new RoomViewHolder(view);
        return roomViewHolder;
    }

    @Override
    public void onBindViewHolder(final RoomViewHolder holder, final int position) {
        RoomDO roomDetails = this.roomDetails.get(position);
        holder.roomTitle.setText(roomDetails.getName());

        Integer image = (roomDetails.getImageId()).intValue();
        if (image == 0) {
            image = 2131492873;
        }
        try {
            Glide.with(mContext).load(image).into(holder.roomImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }


    @Override
    public int getItemCount() {
        return roomDetails.size();
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView roomImage, overflow;
        TextView roomTitle;

        public RoomViewHolder(View itemView) {
            super(itemView);
            roomImage = itemView.findViewById(R.id.card_news_image);
            roomTitle = itemView.findViewById(R.id.room_category);
            overflow = itemView.findViewById(R.id.overflow);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context.getApplicationContext(), DeviceActivity.class);
            RoomDO roomDetail = roomDetails.get(getAdapterPosition());
            intent.putExtra(TITLE_KEY, roomDetail.getName());
            ((Activity) context).startActivityForResult(intent, ROOM_DETAILS_REQUEST_CODE);

        }
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.room_settings:
                    Intent intent = new Intent(mContext, UpdateRoom.class);
                    ((Activity) mContext).startActivityForResult(intent, ACTIVITY_REQUEST_CODE);
                    Toast.makeText(mContext, "Settings", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
}
