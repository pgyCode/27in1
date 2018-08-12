package com.github.pgycode.a16.view.make;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.pgycode.a16.R;
import com.github.pgycode.a16.tool.OnWeakTaskListener;
import com.github.pgycode.a16.tool.WeakTask;
import com.github.pgycode.a16.tool.ZXingUtils;
import com.github.pgycode.a16.tool.permission.OnAskAppearListener;
import com.github.pgycode.a16.tool.permission.PermissionAsker;
import com.github.pgycode.a16.view.main.MainActivity;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageActivity extends Activity implements View.OnClickListener {

    private ImageView img;

    private TextView txtTitle;
    private Button btnSure;

    private Bitmap bitmap;

    private PermissionAsker fileAsker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        txtTitle = findViewById(R.id.txt_title);
        btnSure = findViewById(R.id.btn_sure);
        txtTitle.setText("保存二维码");
        btnSure.setText("保存");

        img = findViewById(R.id.img);
        bitmap = ZXingUtils.createQRImage(getIntent().getStringExtra("content"), 400,  400);
        img.setImageBitmap(bitmap);

        btnSure.setOnClickListener(this);

        findViewById(R.id.btn_back).setOnClickListener(this);

        fileAsker = new PermissionAsker(this, new OnAskAppearListener() {
            @Override
            public void onAppear() {
                new WeakTask<Integer>(new MyWeakTaskListener(bitmap)).execute();
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE, 2, 2, "为了能够保存图片，需要使用你的文件权限", true);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_back){
            finish();
        } else if (view.getId() == R.id.btn_sure){
            fileAsker.onAsk();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fileAsker.onSet(requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        fileAsker.onChoose(requestCode, grantResults);
    }

    public int saveImageToGallery(Bitmap bmp) {
        // 首先保存图片
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();//注意小米手机必须这样获得public绝对路径
        String dirName = "erweima16";
        File appDir = new File(root , dirName);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }

        long timeStamp = System.currentTimeMillis();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(new Date(timeStamp));
        String fileName = sd + ".jpg";

        File file = new File(appDir, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            //通知系统相册刷新
            ImageActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(new File(file.getPath()))));
            return 2;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    private class MyWeakTaskListener implements OnWeakTaskListener<Integer>{

        private Bitmap bitmap;
        public MyWeakTaskListener(Bitmap bitmap){
            this.bitmap = bitmap;
        }

        @Override
        public void before() {

        }

        @Override
        public Integer middle() {
            return saveImageToGallery(bitmap);
        }

        @Override
        public void after(Integer integer) {
            if (integer == 2){
                Snackbar.make(findViewById(R.id.root), "保存成功", Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(findViewById(R.id.root), "保存失败", Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
