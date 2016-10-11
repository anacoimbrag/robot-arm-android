package br.pucminas.armproject;

import android.databinding.DataBindingUtil;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.SeekBar;

import br.pucminas.armproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

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

        int radioId = binding.velocity.getCheckedRadioButtonId();
        if(radioId == R.id.rb_1){
            velocidade = 1;
        } else if (radioId == R.id.rb_2) {
            velocidade = 2;
        } else if (radioId == R.id.rb_3) {
            velocidade = 3;
        }

        binding.valueGarra.setMinValue(90);
        binding.valueGarra.setMaxValue(140);
        binding.valueGarra.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                garra = i;
            }
        });

        binding.valuePulsoSd.setMinValue(30);
        binding.valuePulsoSd.setMaxValue(120);
        binding.valuePulsoSd.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                pulsoSd = i;
            }
        });

        binding.valuePulsoGira.setMinValue(60);
        binding.valuePulsoGira.setMaxValue(150);
        binding.valuePulsoGira.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                pulsoGira = i;
            }
        });

        binding.valueCotovelo.setMinValue(40);
        binding.valueCotovelo.setMaxValue(130);
        binding.valueCotovelo.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                cotovelo = i;
            }
        });

        binding.valueOmbro.setMinValue(50);
        binding.valueOmbro.setMaxValue(110);
        binding.valueOmbro.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                ombro = i;
            }
        });

        binding.valueCintura.setMinValue(10);
        binding.valueCintura.setMaxValue(110);
        binding.valueCintura.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                cintura = i;
            }
        });
    }
}
