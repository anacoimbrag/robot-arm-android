package br.pucminas.armproject;

import android.widget.TextView;

/**
 * Created by anacoimbra on 12/10/16.
 */

public class ValueEventHandler {
    public int max;
    public int min;
    TextView textView;

    public ValueEventHandler(int max, int min, TextView textView) {
        this.max = max;
        this.min = min;
        this.textView = textView;
    }

    public void onIncrease() {
        int value = Integer.parseInt(textView.getText().toString());
        if ( value >= min && value < max ) value += 1;

        textView.setText(String.valueOf(value));
    }

    public void onDecrease() {
        int value = Integer.parseInt(textView.getText().toString());
        if(value > min && value <= max) value -= 1;

        textView.setText(String.valueOf(value));
    }
}
