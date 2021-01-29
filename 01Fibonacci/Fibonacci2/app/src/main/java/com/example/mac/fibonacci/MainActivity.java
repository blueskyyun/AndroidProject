package com.example.mac.fibonacci;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private EditText et1;
    private Button btn1;
    private TextView tv2;
    private String ith;
    private int ith_transf;
    private RelativeLayout rl1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText)findViewById(R.id.et_1);


        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ith = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn1 = (Button)findViewById(R.id.btn_1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et1.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
               imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                ith_transf = Integer.parseInt(ith);
                if(ith_transf <= 0){
                    Toast.makeText(MainActivity.this,"阶数必须大于0，请重新输入",Toast.LENGTH_LONG).show();
                    tv2.setText("  ");
                }else {
                    tv2.setText(getResources().getString(R.string.tv2str, ith_transf, calculateFibonacci(ith_transf)));
                }

            }
        });
        tv2 = (TextView)findViewById(R.id.tv_2);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN && getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    private int calculateFibonacci(int i){
        if(i==1 || i==2){
            return 1;
        }else {
            if (i <= 0) {
                return -1;
            } else {
                return calculateFibonacci(i - 1) + calculateFibonacci(i - 2);
            }
        }
    }
}
