package com.littlejie.gankio.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.littlejie.core.ui.BaseImageView;
import com.littlejie.gankio.Constant;
import com.littlejie.gankio.R;
import com.littlejie.gankio.entity.DataInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Gank IO 普通类目：Android | iOS | 前端 | 拓展资源
 * Created by littlejie on 2017/3/12.
 */

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final int TYPE_IMAGE = 1;
    private static final int TYPE_VIDEO = 2;
    private static final int TYPE_NORMAL = 3;

    private List<DataInfo> mDataList;
    private List<Integer> mHeightList;

    private OnItemClickListener mOnItemClickListener;

    public CategoryAdapter() {
        mHeightList = new ArrayList<>();
    }

    public void setDataList(List<DataInfo> dataList) {
        if (dataList == null || dataList.size() == 0) {
            return;
        }
        mDataList = dataList;
        notifyDataSetChanged();
    }

    public void addDataList(List<DataInfo> dataList) {
        if (dataList == null || dataList.size() == 0) {
            return;
        }
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public List<DataInfo> getDataList() {
        return mDataList;
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataList == null || mDataList.size() == 0) {
            return TYPE_NORMAL;
        }
        String type = mDataList.get(position).getType();
        if (TextUtils.isEmpty(type)) {
            return TYPE_NORMAL;
        }
        if (Constant.Category.IMAGE.equals(type)) {
            return TYPE_IMAGE;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_IMAGE) {
            return new Image(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false));
        } else {
            return new Normal(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this);
        int type = getItemViewType(position);
        if (type == TYPE_IMAGE) {
            bindImageViewHolder((Image) holder, position);
        } else {
            bindNormalViewHolder((Normal) holder, position);
        }
    }

    private void bindNormalViewHolder(Normal holder, int position) {
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

    private void bindImageViewHolder(Image holder, int position) {
        String url = mDataList.get(position).getUrl();

        //随机生成图片高度
        if (mHeightList.size() <= position) {
            mHeightList.add((int) (Math.random() * 300 + 500));
        }
        ViewGroup.LayoutParams lp = holder.mImageView.getLayoutParams();
        lp.height = mHeightList.get(position);
        holder.mImageView.setLayoutParams(lp);

        if (!TextUtils.isEmpty(url)) {
            holder.mImageView.setImage(url);
        } else {
            holder.mImageView.setImage(R.mipmap.ic_launcher);
        }
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    static class Normal extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image)
        BaseImageView ivImage;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_publish_time)
        TextView tvPublish;
        @BindView(R.id.tv_desc)
        TextView tvDesc;

        Normal(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class Image extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image)
        BaseImageView mImageView;

        Image(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
