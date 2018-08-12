package com.github.pgycode.a16.view.album;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pgycode.a16.R;
import com.github.pgycode.a16.tool.QRHelper;
import com.google.android.material.snackbar.Snackbar;

public class ChooseResultActivity extends Activity implements View.OnClickListener {

    private TextView txt;
    private TextView txtTitle;
    private Button btnSure;
    private String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        txt = findViewById(R.id.txt);

        String path = getIntent().getStringExtra("path");
//        Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
        result = QRHelper.getReult(BitmapFactory.decodeFile(path));
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
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData mClipData = ClipData.newPlainText("Label", result);
            cm.setPrimaryClip(mClipData);
            Snackbar.make(findViewById(R.id.root), "复制成功", Snackbar.LENGTH_SHORT).show();
        }
    }

}
