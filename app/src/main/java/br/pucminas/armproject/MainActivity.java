package br.pucminas.armproject;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.generated.callback.OnClickListener;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.List;
import java.util.UUID;

import br.pucminas.armproject.databinding.ActivityMainBinding;
import io.palaima.smoothbluetooth.Device;
import io.palaima.smoothbluetooth.SmoothBluetooth;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    String address;

    static ActivityMainBinding binding;

    BluetoothDevice device;
    BluetoothAdapter adapter;
    private SmoothBluetooth mSmoothBluetooth;

    int velocidade;

    int garra;
    int pulsoSd;
    int pulsoGira;
    int cotovelo;
    int ombro;
    int cintura;

    /**
     *
     * Comandos:
     * 0 - posicao
     * 1 - sequencia
     * 2 - iniciar
     * 3 - parar
     *
     * Formato de envio da mensagem:
     *
     * c/p (0)
     * c/np/p/p/p (1)
     * c (2,3)
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Robot Arm");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        device = getIntent().getParcelableExtra("device");
        binding.setDevice(device);

        final Device device = new Device(this.device.getName(), this.device.getAddress(), true);

        int radioId = binding.velocity.getCheckedRadioButtonId();
        if(radioId == R.id.rb_1){
            velocidade = 1;
        } else if (radioId == R.id.rb_2) {
            velocidade = 2;
        } else if (radioId == R.id.rb_3) {
            velocidade = 3;
        }

        ValueEventHandler handler;
        handler = new ValueEventHandler(140, 90, binding.valueGarra.valueGarra);
        binding.setGarra(handler);
        handler = new ValueEventHandler(120, 30, binding.valuePulsoSd.valueGarra);
        binding.setPulsoSD(handler);
        handler = new ValueEventHandler(150, 60, binding.valuePulsoGira.valueGarra);
        binding.setPulsoGira(handler);
        handler = new ValueEventHandler(130, 40, binding.valueCotovelo.valueGarra);
        binding.setCotovelo(handler);
        handler = new ValueEventHandler(110, 50, binding.valueOmbro.valueGarra);
        binding.setOmbro(handler);
        handler = new ValueEventHandler(110, 10, binding.valueCintura.valueGarra);
        binding.setCintura(handler);

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
                binding.instructions.setText("Conectando a");
            }

            @Override
            public void onConnected(Device device) {
                binding.instructions.setText("Você está conectado a ");
            }

            @Override
            public void onDisconnected() {

            }

            @Override
            public void onConnectionFailed(Device device) {
                binding.instructions.setText("Problema para conectar no dispositivo");
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

        binding.sendPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (binding.velocity.getCheckedRadioButtonId()) {
                    case R.id.rb_1:
                        velocidade = 100;
                        break;
                    case R.id.rb_2:
                        velocidade = 50;
                        break;
                    case R.id.rb_3:
                        velocidade = 10;
                        break;
                }
                garra = Integer.parseInt(binding.valueGarra.valueGarra.getText().toString());
                pulsoSd = Integer.parseInt(binding.valuePulsoSd.valueGarra.getText().toString());
                pulsoGira = Integer.parseInt(binding.valuePulsoGira.valueGarra.getText().toString());
                cotovelo = Integer.parseInt(binding.valueCotovelo.valueGarra.getText().toString());
                ombro = Integer.parseInt(binding.valueOmbro.valueGarra.getText().toString());
                cintura = Integer.parseInt(binding.valueCintura.valueGarra.getText().toString());

                mSmoothBluetooth.send("0/" + velocidade + "," + garra + "," + pulsoSd +
                        ","  + pulsoGira + "," + cotovelo + ","  + ombro + ","  + cintura);
            }
        });

        binding.initSequence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSmoothBluetooth.send("1");
            }
        });

        binding.stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSmoothBluetooth.send("3");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSmoothBluetooth.stop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.sequence) {
            Intent intent = new Intent(this, SequenceActivity.class);
            intent.putExtra("device", device);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(menuItem);
    }
}
