package com.cz.advertise;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private static int ITEM = 1;
    private static int ADVERTISING = 2;
    private ViewGroup parent;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
        View view = null;
        if (viewType == ITEM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qq, parent, false);
        } else if (viewType == ADVERTISING) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qq_advertising, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == ADVERTISING) {
            ((QqAdvertsView) holder.view).setBehindImage(R.mipmap.waller);
            ((QqAdvertsView) holder.view).setFrontImage(R.mipmap.waller_two);
            ((QqAdvertsView) holder.view).bindView(parent);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 3 ? ADVERTISING : ITEM;
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.image);
        }
    }
}
