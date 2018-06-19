package com.iot.letthingsspeak.room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.iot.letthingsspeak.R;

import java.util.ArrayList;

public class AddRoomAdapter extends RecyclerView.Adapter<AddRoomAdapter.AddRoomViewHolder> {

    private ArrayList<Integer> imageModelArrayList;
    private Context context;

    public AddRoomAdapter(ArrayList<Integer> horizontalList, Context context) {
        this.imageModelArrayList = horizontalList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull final AddRoomViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final int model = imageModelArrayList.get(position);

        //holder.imageView.setImageResource(model);
        try {
            Glide.with(context).load(model).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, model + " - " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    public AddRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new AddRoomViewHolder(itemView);
    }

    public class AddRoomViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        private AddRoomViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
        }
    }
}
