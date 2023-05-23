/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.hqq.album.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.hqq.album.R;
import com.hqq.album.entity.LocalMedia;
import com.hqq.album.entity.LocalMediaFolder;
import com.hqq.album.utils.LoadUtils;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: cn.huangqiqiang.halbum.Adapter.AlbumDirectoryAdapter.java
 * @author: 黄其强
 * @date: 2017-05-05 16:10
 */
public class AlbumDirectoryAdapter extends RecyclerView.Adapter<AlbumDirectoryAdapter.ViewHolder> {
    private Context mContext;
    private List<LocalMediaFolder> folders = new ArrayList<>();

    public AlbumDirectoryAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    public void bindFolderData(List<LocalMediaFolder> folders) {
        this.folders = folders;
        notifyDataSetChanged();
    }

    public List<LocalMediaFolder> getFolderData() {
        return folders;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.picture_album_folder_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final LocalMediaFolder folder = folders.get(position);
        String name = folder.getName();
        int imageNum = folder.getImageNum();
        if (folder.isChecked()) {
            holder.mTvImgNum.setVisibility(View.VISIBLE);
            holder.mTvImgNum.setText(folder.getCheckedNum() + "");
        } else {
            holder.mTvImgNum.setVisibility(View.INVISIBLE);
        }
        LoadUtils.loadLocalMediaPath(folder.getType(), folder.getFirstImageUri(), holder.mFirstImage);

        holder.mImageNum.setText("(" + imageNum + ")");
        holder.mTvFolderName.setText(name);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    notifyDataSetChanged();
                }
                onItemClickListener.onItemClick(folder.getName(), folder.getImages());
            }
        });
    }

    @Override
    public int getItemCount() {
        return folders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mFirstImage;
        TextView mTvFolderName, mImageNum, mTvImgNum;

        public ViewHolder(View itemView) {
            super(itemView);
            mFirstImage = (ImageView) itemView.findViewById(R.id.first_image);
            mTvFolderName = (TextView) itemView.findViewById(R.id.tv_folder_name);
            mImageNum = (TextView) itemView.findViewById(R.id.image_num);
            mTvImgNum = (TextView) itemView.findViewById(R.id.tv_img_num);
        }
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(String folderName, List<LocalMedia> images);
    }
}
