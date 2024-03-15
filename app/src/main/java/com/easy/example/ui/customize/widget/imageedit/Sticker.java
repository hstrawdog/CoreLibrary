package com.easy.example.ui.customize.widget.imageedit;

import android.graphics.Canvas;
import android.graphics.Matrix;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.customize.widget.imageedit
 * @FileName :   Sticker
 * @Date : 2020/1/15 0015  上午 10:09
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
public abstract  class Sticker {
    protected Matrix matrix;
    private float[] matrixValues = new float[9];


    public Matrix getMatrix() {
        return matrix;
    }

    public Sticker setMatrix(Matrix matrix) {
        this.matrix = matrix;
        return this;
    }



    public boolean contains(float x, float y) {
        Matrix tempMatrix = new Matrix();
        tempMatrix.setRotate(-getCurrentAngle());
        float[] unrotatedWrapperCorner = new float[8];
        float[] unrotatedPoint = new float[2];
        tempMatrix.mapPoints(unrotatedWrapperCorner, getMappedBoundPoints());
        tempMatrix.mapPoints(unrotatedPoint, new float[] { x, y });
        return StickerUtils.trapToRect(unrotatedWrapperCorner)
                .contains(unrotatedPoint[0], unrotatedPoint[1]);
    }

    public float[] getMappedBoundPoints() {
        float[] dst = new float[8];
        matrix.mapPoints(dst, getBoundPoints());
        return dst;
    }

    public abstract void draw(Canvas canvas);

    public abstract int getWidth();

    public abstract int getHeight();


    public float[] getBoundPoints() {
                return new float[] {
                        0f, 0f, getWidth(), 0f, 0f, getHeight(), getWidth(), getHeight()
                };
    }


    private float getMatrixValue(@NonNull Matrix matrix, @IntRange(from = 0, to = 9) int valueIndex) {
        matrix.getValues(matrixValues);

        return matrixValues[valueIndex];
    }


    /**
     * This method calculates rotation angle for given Matrix object.
     */
    private float getMatrixAngle(@NonNull Matrix matrix) {
        return (float) -(Math.atan2(getMatrixValue(matrix, Matrix.MSKEW_X),
                getMatrixValue(matrix, Matrix.MSCALE_X)) * (180 / Math.PI));
    }



    /**
     * @return - current image rotation angle.
     */
    public float getCurrentAngle() {
        return getMatrixAngle(matrix);
    }


}
