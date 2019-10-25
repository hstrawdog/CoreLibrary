package com.hqq.core.recycler.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hqq.core.R;
import com.hqq.core.glide.ImageLoadUtils;
import com.hqq.core.recycler.RecycleViewBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.banner
 * @FileName :   BaseBannerAdapter
 * @Date : 2018/6/15 0015  下午 5:26
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
public class RecycleBannerAdapter<T> extends RecyclerView.Adapter {
    private List<T> mData = new ArrayList<>();
    private RecycleViewBanner.RecycleViewBannerClickListener onRvBannerClickListener;
    private RecycleViewBanner.RecycleViewBannerChangeListener mRecycleViewBannerChangeListener;
    private boolean mIsShowTip;
    private boolean mIsUnlimited = true;

    public void setUnlimited(boolean unlimited) {
        mIsUnlimited = unlimited;
    }

    public void setData(List<T> data) {
        mData = data;
    }

    public void setOnRvBannerClickListener(RecycleViewBanner.RecycleViewBannerClickListener onRvBannerClickListener) {
        this.onRvBannerClickListener = onRvBannerClickListener;
    }

    public RecycleBannerAdapter setRecycleViewBannerChangeListener(RecycleViewBanner.RecycleViewBannerChangeListener recycleViewBannerChangeListener) {
        mRecycleViewBannerChangeListener = recycleViewBannerChangeListener;
        return this;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = onCreateView(parent);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        onBindView(holder, position);

    }


    @Override
    public int getItemCount() {
        if (mIsUnlimited) {
            return mData == null ? 0 : mData.size() < 2 ? mData.size() : Integer.MAX_VALUE;
        } else {
            return mData == null ? 0 : mData.size();
        }
    }

    public void setIsShowTip(boolean isShowTip) {
        mIsShowTip = isShowTip;
    }

    public boolean isShowTip() {
        return mIsShowTip;
    }

    public void setShowTip(boolean isShowTip) {
        mIsShowTip = isShowTip;
    }


    private View onCreateView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, parent, false);
    }

    private void onBindView(RecyclerView.ViewHolder holder, int position) {
        final int finalPosition = position;
        ImageView img = holder.itemView.findViewById(R.id.iv_banner);
        TextView tv = holder.itemView.findViewById(R.id.tv_code_banner);
        T data = mData.get(position % mData.size());
        if (data instanceof String) {
            tv.setVisibility(View.GONE);
            ImageLoadUtils.with((String) data, img);
        } else if (data instanceof Integer) {
            tv.setVisibility(View.GONE);
            ImageLoadUtils.with((Integer) data, img);
        } else if (null != mRecycleViewBannerChangeListener) {
            ImageLoadUtils.with(mRecycleViewBannerChangeListener.getUrl(data), img);
            if (mIsShowTip) {
                tv.setText(mRecycleViewBannerChangeListener.getTitle(data));
                tv.setVisibility(View.VISIBLE);
            } else {
                tv.setVisibility(View.GONE);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRvBannerClickListener != null) {
                    onRvBannerClickListener.onBannerClick(finalPosition % mData.size());
                }
            }
        });
    }

}