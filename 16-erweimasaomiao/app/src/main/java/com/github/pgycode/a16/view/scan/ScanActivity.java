package com.github.pgycode.a16.view.scan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pgycode.a16.R;

import androidx.appcompat.app.AppCompatActivity;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * Created by D'Russel on 2017/7/31.
 */


public class ScanActivity extends AppCompatActivity implements View.OnClickListener {

    private ZXingView zxingview;
    private TextView txtTitle;
    private ImageButton btnLight;

    private boolean isLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        btnLight = findViewById(R.id.btn_light);
        txtTitle = findViewById(R.id.txt_title);
        txtTitle.setText("扫描二维码");
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_sure).setVisibility(View.GONE);
        btnLight.setOnClickListener(this);

        zxingview = findViewById(R.id.zxingview);
        zxingview.setDelegate(new QRCodeView.Delegate() {
            @Override
            public void onScanQRCodeSuccess(String result) {
                Intent intent = new Intent(ScanActivity.this, ResultActivity.class);
                intent.putExtra("result", result);
                startActivity(intent);
            }

            @Override
            public void onScanQRCodeOpenCameraError() {
                Toast.makeText(ScanActivity.this, "识别错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        zxingview.closeFlashlight();
        isLight = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isLight){
            btnLight.setImageResource(R.drawable.green_oval);
        } else {
            btnLight.setImageResource(R.drawable.red_oval);
        }
    }

    protected void onStart() {
        super.onStart();
        //打开后置摄像头预览,但并没有开始扫描
        zxingview.startCamera();
        //开启扫描框
        zxingview.showScanRect();
        zxingview.startSpot();
    }

    @Override
    protected void onStop() {
        zxingview.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        zxingview.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_back) {
            finish();
        } else if (view.getId() == R.id.btn_light){
            isLight = !isLight;
            if (isLight){
                zxingview.openFlashlight();
                btnLight.setImageResource(R.drawable.green_oval);
            } else {
                zxingview.closeFlashlight();
                btnLight.setImageResource(R.drawable.red_oval);
            }
        }
    }
}