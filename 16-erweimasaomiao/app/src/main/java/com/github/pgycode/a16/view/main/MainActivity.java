package com.github.pgycode.a16.view.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import com.github.pgycode.a16.R;
import com.github.pgycode.a16.tool.permission.OnAskAppearListener;
import com.github.pgycode.a16.tool.permission.PermissionAsker;
import com.github.pgycode.a16.view.album.ChooseAlbumActivity;
import com.github.pgycode.a16.view.make.MakeActivity;
import com.github.pgycode.a16.view.scan.ScanActivity;

/**
 * Created by D'Russel on 2017/7/31.
 */


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private PermissionAsker fileAsker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileAsker = new PermissionAsker(this, new OnAskAppearListener() {
            @Override
            public void onAppear() {
                startActivity(new Intent(MainActivity.this, ChooseAlbumActivity.class));
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE, 2, 2, "为了能够保存图片，需要使用你的文件权限", true);

        findViewById(R.id.btn_get).setOnClickListener(this);
        findViewById(R.id.btn_set).setOnClickListener(this);
        findViewById(R.id.btn_album).setOnClickListener(this);
        findViewById(R.id.btn_jianyi).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_get){
            startActivity(new Intent(this, ScanActivity.class));
        } else if (view.getId() == R.id.btn_set){
            startActivity(new Intent(this, MakeActivity.class));
        } else if (view.getId() == R.id.btn_album){
            fileAsker.onAsk();
        } else if (view.getId() == R.id.btn_jianyi){
            startActivity(new Intent(this, JianyiActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fileAsker.onSet(requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        fileAsker.onChoose(requestCode, grantResults);
    }
}