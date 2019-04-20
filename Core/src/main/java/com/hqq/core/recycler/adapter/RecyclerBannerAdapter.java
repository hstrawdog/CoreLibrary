package com.hqq.core.recycler.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hqq.core.R;
import com.hqq.core.recycler.IBannerBean;
import com.hqq.core.recycler.OnRvBannerClickListener;
import com.hqq.core.glide.ImageLoadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.banner
 * @FileName :   RecyclerBannerAdapter
 * @Date : 2018/6/15 0015  下午 5:26
 * @Descrive : TODO
 * @Email :
 */
public class RecyclerBannerAdapter extends RecyclerView.Adapter {
    private List<IBannerBean> mData = new ArrayList<>();
    private OnRvBannerClickListener onRvBannerClickListener;
    private boolean mIsShowTip;
    private boolean mIsUnlimited = true;

    public void setUnlimited(boolean unlimited) {
        mIsUnlimited = unlimited;
    }

    public void setData(List<IBannerBean> data) {
        mData = data;
    }

    public void setOnRvBannerClickListener(OnRvBannerClickListener onRvBannerClickListener) {
        this.onRvBannerClickListener = onRvBannerClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,  int position) {
       final int finalPosition=position;
        ImageView img = holder.itemView.findViewById(R.id.iv_banner);
        TextView tv = holder.itemView.findViewById(R.id.tv_code_banner);
        ImageLoadUtils.with(mData.get(position % mData.size()).getBannerUrl(), img);

        if (mIsShowTip) {
            tv.setText(mData.get(position % mData.size()).getBannerTitle());
            tv.setVisibility(View.VISIBLE);
        } else {
            tv.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRvBannerClickListener != null) {
                    onRvBannerClickListener.onClick(finalPosition % mData.size());
                }
            }
        });

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
}