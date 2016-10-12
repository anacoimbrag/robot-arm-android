package br.pucminas.armproject;

import android.bluetooth.BluetoothDevice;
import android.databinding.DataBindingUtil;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.SeekBar;

import br.pucminas.armproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    BluetoothDevice device;

    int velocidade;

    int garra;
    int pulsoSd;
    int pulsoGira;
    int cotovelo;
    int ombro;
    int cintura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        device = getIntent().getParcelableExtra("device");
        binding.setDevice(device);

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
    }
}
