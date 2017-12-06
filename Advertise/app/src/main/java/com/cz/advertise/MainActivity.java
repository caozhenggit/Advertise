package com.cz.advertise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author caozheng
 * Created time on 2017/12/5
 *
 * description:
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        List<String> mDatas = new ArrayList<>();
        mDatas.add("QQ");
        mDatas.add("知乎");

        CommonAdapter mAdapter = new CommonAdapter<String>(MainActivity.this, R.layout.item_main, mDatas) {
            @Override
            protected void convert(ViewHolder holder, String text, int position) {
                holder.setText(R.id.tv_text, text);
            }
        };

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                switch (position){
                    case 0:
                        startActivity(new Intent(MainActivity.this, QqActivity.class));
                        break;

                    case 1:
                        startActivity(new Intent(MainActivity.this, ZhiHuActivity.class));
                        break;

                    default:
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }
}
