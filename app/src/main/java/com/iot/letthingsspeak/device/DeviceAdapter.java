package com.iot.letthingsspeak.device;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iot.letthingsspeak.R;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {
    private List<DeviceDetails> deviceDetails;

    public DeviceAdapter(List<DeviceDetails> deviceDetails) {
        this.deviceDetails = deviceDetails;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false);
        DeviceViewHolder deviceViewHolder = new DeviceViewHolder(view);
        return deviceViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, final int position) {
        DeviceDetails deviceDetail = this.deviceDetails.get(position);
        holder.cardTitleTextView.setText(deviceDetail.getDeviceName());
    }

    @Override
    public int getItemCount() {
        return deviceDetails.size();
    }

    public static class DeviceViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImageView;
        TextView cardTitleTextView;

        public DeviceViewHolder(View itemView) {
            super(itemView);
            cardImageView = (ImageView) itemView.findViewById(R.id.device_image);
            cardTitleTextView = (TextView) itemView.findViewById(R.id.device_category);
        }
    }
}
