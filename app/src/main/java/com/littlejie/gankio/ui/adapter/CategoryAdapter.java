package com.littlejie.gankio.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.littlejie.core.ui.BaseImageView;
import com.littlejie.gankio.R;
import com.littlejie.gankio.entity.DataInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Gank IO 普通类目：Android | iOS | 前端 | 拓展资源
 * Created by littlejie on 2017/3/12.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<DataInfo> mDataList;

    public void setDataList(List<DataInfo> dataList) {
        mDataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataInfo data = mDataList.get(position);
        List<String> images = data.getImages();
        if (images == null || images.size() == 0) {
            holder.ivImage.setVisibility(View.GONE);
        } else {
            Log.d("Image", images.get(0));
            holder.ivImage.setVisibility(View.VISIBLE);
            holder.ivImage.setImage(images.get(0));
        }
        holder.tvAuthor.setText(data.getWho());
        holder.tvPublish.setText(data.getPublishedTime());
        holder.tvDesc.setText(data.getDesc());
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image)
        BaseImageView ivImage;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_publish_time)
        TextView tvPublish;
        @BindView(R.id.tv_desc)
        TextView tvDesc;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
