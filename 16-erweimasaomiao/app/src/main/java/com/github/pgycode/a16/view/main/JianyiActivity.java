package com.github.pgycode.a16.view.main;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.pgycode.a16.R;
import com.google.android.material.snackbar.Snackbar;

public class JianyiActivity extends Activity implements View.OnClickListener {

    private TextView txtTitle;
    private Button btnSure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jianyi);

        txtTitle = findViewById(R.id.txt_title);
        btnSure = findViewById(R.id.btn_sure);
        btnSure.setText("复制");
        txtTitle.setText("我的邮箱");
        findViewById(R.id.btn_back).setOnClickListener(this);
        btnSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_sure){
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData mClipData = ClipData.newPlainText("Label", "2363887871@qq.com");
            cm.setPrimaryClip(mClipData);
            Snackbar.make(findViewById(R.id.root), "复制成功", Snackbar.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.btn_back){
            finish();
        }
    }
}
