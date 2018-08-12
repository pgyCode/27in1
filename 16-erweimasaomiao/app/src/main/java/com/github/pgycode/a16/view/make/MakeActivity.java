package com.github.pgycode.a16.view.make;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.github.pgycode.a16.R;

public class MakeActivity extends Activity implements View.OnClickListener {

    private EditText edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make);

        edt = findViewById(R.id.edt);

        findViewById(R.id.btn_sure).setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_back){
            finish();
        } else if (view.getId() == R.id.btn_sure){
            Intent intent = new Intent(this, ImageActivity.class);
            intent.putExtra("content", edt.getText().toString());
            startActivity(intent);
        }
    }
}
