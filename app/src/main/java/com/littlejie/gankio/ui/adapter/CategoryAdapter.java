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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Gank IO 普通类目：Android | iOS | 前端 | 拓展资源
 * Created by littlejie on 2017/3/12.
 */

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final int TYPE_DATE = 0;
    private static final int TYPE_IMAGE = 1;
    private static final int TYPE_VIDEO = 2;
    private static final int TYPE_NORMAL = 3;
    private static final String TAG = CategoryAdapter.class.getSimpleName();

    private List<DataInfo> mDataList;
    private Map<Integer, Integer> mHeightMap;
    //是否为每日精选
    private boolean isDayPublish;
    private OnItemClickListener mOnItemClickListener;

    public CategoryAdapter() {
        mDataList = new ArrayList<>();
        mHeightMap = new HashMap<>();
    }

    public void setDayPublish(boolean dayPublish) {
        isDayPublish = dayPublish;
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
        Log.d(TAG, "type = " + type);
        if (TextUtils.isEmpty(type)) {
            return TYPE_DATE;
        }
        if (Constant.Category.IMAGE.equals(type)) {
            return TYPE_IMAGE;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_DATE) {
            return new Date(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date, parent, false));
        } else if (viewType == TYPE_IMAGE) {
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
        if (type == TYPE_DATE) {
            bingDateViewHolder((Date) holder, position);
        } else if (type == TYPE_IMAGE) {
            bindImageViewHolder((Image) holder, position);
        } else {
            bindNormalViewHolder((Normal) holder, position);
        }
    }

    private void bingDateViewHolder(Date holder, int position) {
        String time = mDataList.get(position).getPublishedTime();
        holder.tvDate.setText(time);
    }

    private void bindNormalViewHolder(Normal holder, int position) {
        DataInfo data = mDataList.get(position);
        List<String> images = data.getImages();
        if (images == null || images.size() == 0) {
            holder.ivImage.setVisibility(View.GONE);
        } else {
            holder.ivImage.setVisibility(View.VISIBLE);
            holder.ivImage.setImage(images.get(0));
        }
        holder.tvAuthor.setText(data.getWho());
        //如果为每日精选，则不显示发布时间
        holder.tvPublish.setText(isDayPublish ? data.getType() : data.getPublishedTime());
        holder.tvDesc.setText(data.getDesc());
    }

    private void bindImageViewHolder(Image holder, int position) {
        String url = mDataList.get(position).getUrl();

        //随机生成图片高度
        if (mHeightMap.size() <= position) {
            mHeightMap.put(position, generaHeight());
        }
        ViewGroup.LayoutParams lp = holder.mImageView.getLayoutParams();
        //防止产生height为空
        Integer height = mHeightMap.get(position);
        if (height == null) {
            height = generaHeight();
            mHeightMap.put(position, height);
        }
        lp.height = height;
        holder.mImageView.setLayoutParams(lp);

        if (!TextUtils.isEmpty(url)) {
            holder.mImageView.setImage(url);
        } else {
            holder.mImageView.setImage(R.mipmap.ic_launcher);
        }
    }

    private int generaHeight() {
        return (int) (Math.random() * 300 + 500);
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            int position = (int) v.getTag();
            //date类型的type不响应点击事件
            if (getItemViewType(position) == TYPE_DATE) {
                return;
            }
            mOnItemClickListener.onItemClick(v, position);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    static class Date extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_date)
        TextView tvDate;

        Date(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
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
