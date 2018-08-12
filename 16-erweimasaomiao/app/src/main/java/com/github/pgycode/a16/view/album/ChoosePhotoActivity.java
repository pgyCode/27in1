package com.github.pgycode.a16.view.album;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.github.pgycode.a16.R;
import com.github.pgycode.a16.tool.MyImgShow;
import com.github.pgycode.a16.tool.OnWeakTaskListener;

import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;


public class ChoosePhotoActivity extends Activity {

    private GridView grid;

    private List<String> photos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_choose_photo);

        grid = findViewById(R.id.grid);


        photos = (List<String>) getIntent().getSerializableExtra("photos");

        grid.setAdapter(new MyAdapter());

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChoosePhotoActivity.this, ChooseResultActivity.class);
                startActivity(intent.putExtra("path", photos.get(position)));
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
            if (photos == null)
                return 0;
            return photos.size();
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
            if (convertView == null) {
                convertView = LayoutInflater.from(ChoosePhotoActivity.this).inflate(R.layout.own_photo_item, grid, false);
                viewHolder = new ViewHolder();
                viewHolder.img = convertView.findViewById(R.id.img);
                convertView.setTag(viewHolder);
            } else
                viewHolder = (ViewHolder) convertView.getTag();

            MyImgShow.showLocalImgSquare(ChoosePhotoActivity.this, "file://" + photos.get(position), viewHolder.img);
            return convertView;
        }

        public class ViewHolder {
            ImageView img;
        }
    }
}
