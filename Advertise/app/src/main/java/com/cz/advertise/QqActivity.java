package com.cz.advertise;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
public class QqActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private static int ITEM = 1;
    private static int ADVERTISING = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);

        List<Integer> mData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if( i == 4){
                mData.add(ADVERTISING);
            }else {
                mData.add(ITEM);
            }
        }

        MultiItemTypeAdapter<Integer> adapter = new MultiItemTypeAdapter<Integer>(QqActivity.this, mData);
        adapter.addItemViewDelegate(ITEM, new ItemViewDelegate<Integer>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_common;
            }

            @Override
            public boolean isForViewType(Integer item, int position) {
                return item == ITEM;
            }

            @Override
            public void convert(ViewHolder holder, Integer integer, int position) {

            }
        });
        adapter.addItemViewDelegate(ADVERTISING, new ItemViewDelegate<Integer>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_qq_advertising;
            }

            @Override
            public boolean isForViewType(Integer item, int position) {
                return item == ADVERTISING;
            }

            @Override
            public void convert(ViewHolder holder, Integer integer, int position) {
                QqAdvertsView mSwitchImageView = holder.getView(R.id.image);
                mSwitchImageView.setFrontImage(R.mipmap.adverts_1);
                mSwitchImageView.setBehindImage(R.mipmap.adverts_2);
                mSwitchImageView.bindView(mRecyclerView);
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
    }
}
