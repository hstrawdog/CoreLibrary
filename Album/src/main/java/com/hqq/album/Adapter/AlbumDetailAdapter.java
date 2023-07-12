package com.hqq.album.Adapter;

import android.content.Context;
import android.graphics.PorterDuff;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hqq.album.R;
import com.hqq.album.annotation.LocalMediaType;
import com.hqq.album.common.SelectOptions;
import com.hqq.album.dialog.OptAnimationLoader;
import com.hqq.album.entity.LocalMedia;
import com.hqq.album.utils.AlbumUtils;
import com.hqq.album.utils.LoadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.album.Adapter
 * @FileName :   AlbumDetailAdapter
 * @Date : 2017/5/7 0001  上午 11:08
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
public class AlbumDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<LocalMedia> images = new ArrayList<>();
    private List<LocalMedia> selectImages = new ArrayList<>();
    private Context context;
    private int maxSelectNum;
    private OnPhotoSelectChangedListener imageSelectChangedListener;

    public void bindImagesData(List<LocalMedia> images) {
        this.images = images;
        selectImages = SelectOptions.getInstance().getSelectLocalMedia();
        notifyDataSetChanged();
    }

    public AlbumDetailAdapter(Context context, int maxSelectNum) {
        this.context = context;
        this.maxSelectNum = maxSelectNum;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_image_grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder contentHolder = (ViewHolder) holder;
        final LocalMedia localMedia = images.get(position);
        localMedia.position = contentHolder.getAdapterPosition();

        selectImage(contentHolder, isSelected(localMedia), false);

        LoadUtils.loadLocalMediaPath(localMedia.getLocalMediaType(), localMedia.getUri(), contentHolder.mIvPicture);

        if (localMedia.getLocalMediaType() == LocalMediaType.VALUE_TYPE_VIDEO) {
            contentHolder.mRlDuration.setVisibility(View.VISIBLE);
            contentHolder.mTvDuration.setText("时长: " + formatDuring(localMedia.getDuration()));
        } else {
            contentHolder.mRlDuration.setVisibility(View.GONE);

        }

        bindClick(position, contentHolder, localMedia);
    }

    public static String formatDuring(long mss) {
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return hours + ":" + minutes + " " + seconds + "";
    }

    /**
     * 绑定 点击事件
     *
     * @param position
     * @param contentHolder
     * @param localMedia
     */
    private void bindClick(final int position, final ViewHolder contentHolder, final LocalMedia localMedia) {
        contentHolder.mLlCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AlbumUtils.isFastDoubleClick(200)) {
                    changeCheckboxState(contentHolder, localMedia);
                }
            }
        });
        contentHolder.contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageSelectChangedListener != null) {
                    imageSelectChangedListener.onPictureClick(localMedia, position);
                } else {
                    if (!AlbumUtils.isFastDoubleClick()) {
                        changeCheckboxState(contentHolder, localMedia);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mIvPicture;
        ImageView check;
        TextView mTvDuration;
        View contentView;
        LinearLayout mLlCheck;
        RelativeLayout mRlDuration;

        public ViewHolder(View itemView) {
            super(itemView);
            contentView = itemView;
            mIvPicture = (ImageView) itemView.findViewById(R.id.iv_picture);
            check = itemView.findViewById(R.id.check);
            mLlCheck = (LinearLayout) itemView.findViewById(R.id.ll_check);
            mTvDuration = (TextView) itemView.findViewById(R.id.tv_duration);
            mRlDuration = (RelativeLayout) itemView.findViewById(R.id.rl_duration);
        }
    }

    public boolean isSelected(LocalMedia image) {
        for (LocalMedia media : selectImages) {
            if (media.getPath().equals(image.getPath())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置图片点击效果
     *
     * @param holder
     * @param isChecked
     * @param isAnim
     */
    public void selectImage(ViewHolder holder, boolean isChecked, boolean isAnim) {
        holder.check.setSelected(isChecked);
        if (isChecked) {
            if (isAnim) {
                Animation animation = OptAnimationLoader.loadAnimation(context, R.anim.modal_in);
                holder.check.startAnimation(animation);
            }
            holder.mIvPicture.setColorFilter(ContextCompat.getColor(context, R.color.image_overlay2), PorterDuff.Mode.SRC_ATOP);
        } else {
            holder.mIvPicture.setColorFilter(ContextCompat.getColor(context, R.color.image_overlay), PorterDuff.Mode.SRC_ATOP);
        }
    }

    /**
     * 改变图片选中状态
     *
     * @param contentHolder
     * @param image
     */

    private void changeCheckboxState(ViewHolder contentHolder, LocalMedia image) {
        boolean isChecked = contentHolder.check.isSelected();

        if (selectImages.size() >= maxSelectNum && !isChecked) {
            Toast.makeText(context, context.getString(R.string.picture_message_max_num, maxSelectNum + ""), Toast.LENGTH_LONG).show();
            return;
        }
        if (isChecked) {
            for (LocalMedia media : selectImages) {
                if (media.getPath().equals(image.getPath())) {
                    selectImages.remove(media);
                    subSelectPosition();
                    break;
                }
            }
        } else {
            selectImages.add(image);
        }
        //通知点击项发生了改变
        //notifyItemChanged(contentHolder.getAdapterPosition());
        selectImage(contentHolder, !isChecked, true);
        if (imageSelectChangedListener != null) {
            imageSelectChangedListener.onChange(selectImages);
        }
    }

    /**
     * 更新选择的顺序
     */
    private void subSelectPosition() {
        // if (is_checked_num) {
        for (int index = 0, len = selectImages.size(); index < len; index++) {
            LocalMedia media = selectImages.get(index);
        }
    }

    public interface OnPhotoSelectChangedListener {
        void onTakePhoto();

        void onChange(List<LocalMedia> selectImages);

        void onPictureClick(LocalMedia media, int position);
    }

    public void setOnPhotoSelectChangedListener(OnPhotoSelectChangedListener imageSelectChangedListener) {
        this.imageSelectChangedListener = imageSelectChangedListener;
    }
}
