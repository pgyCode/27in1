package com.github.pgycode.a16.view.album;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.pgycode.a16.R;
import com.github.pgycode.a16.tool.MyImgShow;
import com.github.pgycode.a16.tool.OnWeakTaskListener;
import com.github.pgycode.a16.tool.WeakTask;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;

public class ChooseAlbumActivity extends Activity {

    private ListView lst;
    private HashMap<String, List<String>> albums;
    private MyAdapter myAdapter;

    private ViewStub loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_choose_album);

        loading = findViewById(R.id.loading);
        lst = findViewById(R.id.lst);
        lst.setAdapter(myAdapter = new MyAdapter());

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChooseAlbumActivity.this, ChoosePhotoActivity.class);
                intent.putExtra("photos", (Serializable) albums.get(albums.keySet().toArray()[position]));
                intent.putExtra("sign", getIntent().getIntExtra("sign", -1));
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (albums == null)
                return 0;
            return albums.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null){
                convertView = LayoutInflater.from(ChooseAlbumActivity.this).inflate(R.layout.own_album_item, null, false);
                viewHolder = new ViewHolder();
                viewHolder.path = convertView.findViewById(R.id.path);
                viewHolder.img = convertView.findViewById(R.id.img);
                viewHolder.size = convertView.findViewById(R.id.size);
                convertView.setTag(viewHolder);
            }
            else
                viewHolder = (ViewHolder) convertView.getTag();

            viewHolder.size.setText("此相册共有：" + albums.get(albums.keySet().toArray()[position]).size() + "张");
            viewHolder.path.setText(((String)albums.keySet().toArray()[position]).replace(Environment.getExternalStorageDirectory().getPath() + "/", ""));
            MyImgShow.showLocalImgSquare(ChooseAlbumActivity.this, "file://" + albums.get(albums.keySet().toArray()[position]).get(0), viewHolder.img);
            return convertView;
        }

        public class ViewHolder {
            TextView path;
            ImageView img;
            TextView size;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        new WeakTask<Integer>(new MyWeakTaskListener()).execute();
    }

    private HashMap<String, List<String>> getSystemPhotoList()
    {
        HashMap<String, List<String>> temps = new HashMap<String, List<String>>();

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        ContentResolver contentResolver = this.getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null || cursor.getCount() <= 0)
            return temps; // 没有图片
        while (cursor.moveToNext())
        {
            int index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            String path = cursor.getString(index); // 文件地址
            File file = new File(path);
            if (file.exists())
            {
                String fatherPath = path.substring(0, path.lastIndexOf('/'));
                if (temps.get(fatherPath) == null){
                    temps.put(fatherPath, new ArrayList<String>());
                }
                temps.get(fatherPath).add(path);
            }
        }

        Object[] keys = temps.keySet().toArray();
        for (int i = 0; i < keys.length; i++){
            Collections.reverse(temps.get(keys[i]));
        }
        return temps;
    }


    private class MyWeakTaskListener implements OnWeakTaskListener<Integer>{

        @Override
        public void before() {
            loading.setVisibility(View.VISIBLE);
        }

        @Override
        public Integer middle() {
            albums = getSystemPhotoList();
            return 2;
        }


        @Override
        public void after(Integer integer) {
            loading.setVisibility(View.GONE);
            myAdapter.notifyDataSetChanged();
        }

    }
}
