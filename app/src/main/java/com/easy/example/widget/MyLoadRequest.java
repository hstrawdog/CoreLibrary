package com.easy.example.widget;

import android.graphics.RectF;
import android.net.Uri;

import com.isseiaoki.simplecropview.callback.LoadCallback;

import io.reactivex.Completable;

public class MyLoadRequest {

  private float initialFrameScale;
  private RectF initialFrameRect;
  private boolean useThumbnail;
  private MyCropImageView cropImageView;
  private Uri sourceUri;

  public MyLoadRequest(MyCropImageView cropImageView, Uri sourceUri) {
    this.cropImageView = cropImageView;
    this.sourceUri = sourceUri;
  }

  public MyLoadRequest initialFrameScale(float initialFrameScale) {
    this.initialFrameScale = initialFrameScale;
    return this;
  }

  public MyLoadRequest initialFrameRect(RectF initialFrameRect) {
    this.initialFrameRect = initialFrameRect;
    return this;
  }

  public MyLoadRequest useThumbnail(boolean useThumbnail) {
    this.useThumbnail = useThumbnail;
    return this;
  }

  public void execute(LoadCallback callback) {
    if (initialFrameRect == null) {
      cropImageView.setInitialFrameScale(initialFrameScale);
    }
    cropImageView.loadAsync(sourceUri, useThumbnail, initialFrameRect, callback);
  }

  public Completable executeAsCompletable() {
    if (initialFrameRect == null) {
      cropImageView.setInitialFrameScale(initialFrameScale);
    }
    return cropImageView.loadAsCompletable(sourceUri, useThumbnail, initialFrameRect);
  }
}
