package br.pucminas.armproject;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Set;

import br.pucminas.armproject.databinding.DeviceItemViewBinding;

/**
 * Created by anacoimbra on 11/10/16.
 */

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder>{

    Set<BluetoothDevice> devices;
    Activity activity;

    public DeviceAdapter(Set<BluetoothDevice> devices, Activity activity) {
        this.devices = devices;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        DeviceItemViewBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.device_item_view, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        BluetoothDevice[] d = new BluetoothDevice[devices.size()];
        d = devices.toArray(d);
        holder.binding.name.setText(d[position].getName());
        holder.binding.address.setText(d[position].getAddress());
        final BluetoothDevice[] finalD = d;
        holder.binding.device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, MainActivity.class);
                intent.putExtra("device", finalD[position]);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        DeviceItemViewBinding binding;

        ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
