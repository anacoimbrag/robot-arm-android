package br.pucminas.armproject;

import android.bluetooth.BluetoothDevice;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import java.util.Arrays;
import java.util.List;

import br.pucminas.armproject.databinding.ActivitySequenceBinding;
import br.pucminas.armproject.databinding.MoveViewBinding;
import io.palaima.smoothbluetooth.Device;
import io.palaima.smoothbluetooth.SmoothBluetooth;

public class SequenceActivity extends AppCompatActivity {

    ActivitySequenceBinding binding;
    private SmoothBluetooth mSmoothBluetooth;
    private BluetoothDevice device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Gravar Sequência");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sequence);

        device = getIntent().getParcelableExtra("device");
        final Device device = new Device(this.device.getName(), this.device.getAddress(), true);

        mSmoothBluetooth = new SmoothBluetooth(this, SmoothBluetooth.ConnectionTo.OTHER_DEVICE,
                SmoothBluetooth.Connection.INSECURE, new SmoothBluetooth.Listener() {
            @Override
            public void onBluetoothNotSupported() {

            }

            @Override
            public void onBluetoothNotEnabled() {

            }

            @Override
            public void onConnecting(Device device) {
//                binding.instructions.setText("Conectando a");
            }

            @Override
            public void onConnected(Device device) {
//                binding.instructions.setText("Você está conectado a ");
            }

            @Override
            public void onDisconnected() {

            }

            @Override
            public void onConnectionFailed(Device device) {
//                binding.instructions.setText("Problema para conectar no dispositivo");
            }

            @Override
            public void onDiscoveryStarted() {

            }

            @Override
            public void onDiscoveryFinished() {

            }

            @Override
            public void onNoDevicesFound() {

            }

            @Override
            public void onDevicesFound(List<Device> deviceList, SmoothBluetooth.ConnectionCallback connectionCallback) {
                connectionCallback.connectTo(device);
            }

            @Override
            public void onDataReceived(int data) {

            }
        });

        mSmoothBluetooth.tryConnection();

        int numberMovments = 0;
        if(validate(binding.move1)) numberMovments++;
        if(validate(binding.move2)) numberMovments++;
        if(validate(binding.move3)) numberMovments++;
        if(validate(binding.move4)) numberMovments++;
        if(validate(binding.move5)) numberMovments++;
        if(validate(binding.move6)) numberMovments++;
        if(validate(binding.move7)) numberMovments++;
        if(validate(binding.move8)) numberMovments++;
        if(validate(binding.move9)) numberMovments++;
        if(validate(binding.move10)) numberMovments++;

        final int finalNumberMovments = numberMovments;
        binding.saveSequence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "2/" + finalNumberMovments + "/";
                msg += validate(binding.move1) ? getPosition(binding.move1) + "/" : "";
                msg += validate(binding.move2) ? getPosition(binding.move2) + "/" : "";
                msg += validate(binding.move3) ? getPosition(binding.move3) + "/" : "";
                msg += validate(binding.move4) ? getPosition(binding.move4) + "/" : "";
                msg += validate(binding.move5) ? getPosition(binding.move5) + "/" : "";
                msg += validate(binding.move6) ? getPosition(binding.move6) + "/" : "";
                msg += validate(binding.move7) ? getPosition(binding.move7) + "/" : "";
                msg += validate(binding.move8) ? getPosition(binding.move8) + "/" : "";
                msg += validate(binding.move9) ? getPosition(binding.move9) + "/" : "";
                msg += validate(binding.move10) ? getPosition(binding.move10) : "";

                mSmoothBluetooth.send(msg);
            }
        });

    }

    String getPosition(MoveViewBinding binding) {
        int velocidade;

        int garra;
        int pulsoSd;
        int pulsoGira;
        int cotovelo;
        int ombro;
        int cintura;

        velocidade = Integer.parseInt(binding.velocity.getText().toString());
        garra = Integer.parseInt(binding.garra.getText().toString());
        pulsoSd = Integer.parseInt(binding.pulsoSd.getText().toString());
        pulsoGira = Integer.parseInt(binding.pulsoGira.getText().toString());
        cotovelo = Integer.parseInt(binding.cotovelo.getText().toString());
        ombro = Integer.parseInt(binding.ombro.getText().toString());
        cintura = Integer.parseInt(binding.cintura.getText().toString());

        return velocidade + "," + garra + "," + pulsoSd + "," + pulsoGira +
                "," + cotovelo + "," + ombro + "," + cintura;
    }

    boolean validate(MoveViewBinding binding){

        if(binding.velocity.getText().length() <= 0)
            return false;
        if(binding.garra.getText().length() <= 0)
            return false;
        if(binding.pulsoSd.getText().length() <= 0)
            return false;
        if(binding.pulsoGira.getText().length() <= 0)
            return false;
        if(binding.cotovelo.getText().length() <= 0)
            return false;
        if(binding.ombro.getText().length() <= 0)
            return false;
        if(binding.cintura.getText().length() <= 0)
            return false;

        return true;
    }
}
