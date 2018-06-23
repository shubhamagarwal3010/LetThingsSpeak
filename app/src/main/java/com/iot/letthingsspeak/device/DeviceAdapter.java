package com.iot.letthingsspeak.device;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.iot.letthingsspeak.LetThingsSpeakApplication;
import com.iot.letthingsspeak.R;
import com.iot.letthingsspeak.aws.db.DynamoDBManager;
import com.iot.letthingsspeak.device.model.DeviceDO;

import java.util.HashMap;
import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {
    DynamoDBManager dynamoDBManager = LetThingsSpeakApplication.dynamoDBManager;
    private List<DeviceDO> deviceDetails;
    private Context context;

    public DeviceAdapter(Context context, List<DeviceDO> deviceDetails) {
        this.context = context;
        this.deviceDetails = deviceDetails;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_device, parent, false);
        DeviceViewHolder deviceViewHolder = new DeviceViewHolder(view);
        return deviceViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DeviceViewHolder holder, final int position) {
        final DeviceDO deviceDetail = this.deviceDetails.get(position);
        holder.cardTitleTextView.setText(deviceDetail.getDeviceName());
        final Integer[] state = {0};
        final Boolean[] setState = {false};
        if (deviceDetail.getState()) {
            holder.deviceState.setChecked(true);
            state[0] = R.drawable.on;
        } else {
            holder.deviceState.setChecked(false);
            state[0] = R.drawable.off;
        }

        holder.deviceState.setBackgroundResource(state[0]);

        holder.deviceState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.deviceState.isChecked()) {
                    state[0] = R.drawable.on;
                    setState[0] = true;
                } else {
                    state[0] = R.drawable.off;
                    setState[0] = false;
                }

                holder.deviceState.setBackgroundResource(state[0]);
                dynamoDBManager.insertDevice(new HashMap<String, Object>() {{
                    put("roomId", deviceDetail.getRoomId());
                    put("deviceId", deviceDetail.getDeviceId());
                    put("name", deviceDetail.getDeviceName());
                    put("state", setState[0]);
                }});
            }
        });
    }

    @Override
    public int getItemCount() {
        return deviceDetails.size();
    }

    public static class DeviceViewHolder extends RecyclerView.ViewHolder {
        ToggleButton deviceState;
        TextView cardTitleTextView;

        public DeviceViewHolder(View itemView) {
            super(itemView);
            deviceState = itemView.findViewById(R.id.device_state);
            cardTitleTextView = itemView.findViewById(R.id.device_category);
        }
    }
}
