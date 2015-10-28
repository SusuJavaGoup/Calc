package ru.susu.itschool.calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainScreen extends AppCompatActivity {

    private CalcCore calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        calc = new CalcCore();
        addActionButton(
                R.id.button2,
                R.id.button3,
                R.id.button4,
                R.id.button5,
                R.id.button6,
                R.id.button7,
                R.id.button8,
                R.id.button9,
                R.id.button10,
                R.id.button11,
                R.id.button12,
                R.id.button13
        );

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView) findViewById(R.id.textView)).setText("");
                updateOutput();
            }
        });
        updateOutput();
    }

    private void addActionButton(int... ids) {
        for (int id : ids) {
            try {
                final Button b = (Button) findViewById(id);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final TextView tw = (TextView) findViewById(R.id.textView);
                        tw.setText(tw.getText().toString() + b.getText());
                        updateOutput();
                    }
                });
            } catch (Throwable t) {
                System.out.println(t.getMessage() + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
        }
    }

    private void updateOutput() {
        final String query = ((TextView) findViewById(R.id.textView)).getText().toString(); // Знаю,что сохранять всякие поля в визуальной части программы это плохо, но я в андроиде новичок так, что можно))
        String response = null;
        final TextView tw = (TextView) findViewById(R.id.textView2);

        try {
            response = String.valueOf(calc.calculateResult(calc.getOPN(query)));
        } catch (Throwable t) {
        }

        if (response != null) {
            //tw.setTextColor(333333);
            tw.setText(response);
        } else {
            //tw.setTextColor(11111111); //как нормально поставить цвет я хз
        }
    }
}
