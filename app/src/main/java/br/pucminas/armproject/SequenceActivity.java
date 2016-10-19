package br.pucminas.armproject;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import br.pucminas.armproject.databinding.ActivitySequenceBinding;
import br.pucminas.armproject.databinding.MoveViewBinding;

public class SequenceActivity extends AppCompatActivity {

    ActivitySequenceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Gravar Sequência");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sequence);

    }
}
