package com.github.pgycode.a16.view.scan;

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

public class ResultActivity extends Activity implements View.OnClickListener {

    private TextView txt;
    private TextView txtTitle;
    private Button btnSure;
    private String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        txt = findViewById(R.id.txt);

        result = getIntent().getStringExtra("result");
        txt.setText(result);

        txtTitle = findViewById(R.id.txt_title);
        txtTitle.setText("扫描结果");
        findViewById(R.id.btn_back).setOnClickListener(this);
        btnSure = findViewById(R.id.btn_sure);
        btnSure.setText("复制");
        btnSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_back){
            finish();
        } else if (view.getId() == R.id.btn_sure){
            //获取剪贴板管理器：
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", result);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            Snackbar.make(findViewById(R.id.root), "复制成功", Snackbar.LENGTH_SHORT).show();
        }
    }
}
