package com.fbsum.android.commons.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.annotation.DrawableRes;

public final class BitmapUtils {

    private BitmapUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 解码
     */
    public static Bitmap decode(Resources res, @DrawableRes int resId) {
        return BitmapFactory.decodeResource(res, resId);
    }

    /**
     * 解码
     */
    public static Bitmap decode(String path) {
        return BitmapFactory.decodeFile(path);
    }

    /**
     * 解码（根据宽高在读取时尽量缩小图片，适合从大图读取小图）
     */
    public static Bitmap decode(Resources res, @DrawableRes int resId, int width, int height) {
        // First decode with <code>inJustDecodeBounds = true</code> to check dimensions.
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, width, height);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 解码（根据宽高在读取时尽量缩小图片，适合从大图读取小图）
     */
    public static Bitmap decode(String path, int width, int height) {
        // First decode with <code>inJustDecodeBounds = true</code> to check dimensions.
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, width, height);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * 解码读取图片的宽高
     */
    public static int[] decodeSize(String path) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        return new int[]{options.outWidth, options.outHeight};
    }

    /**
     * 解码读取图片的宽高
     */
    public static int[] decodeSize(Resources res, @DrawableRes int resId) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        return new int[]{options.outWidth, options.outHeight};
    }

    /**
     * 根据宽高计算可以缩小读图的倍数
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (reqWidth == 0 && reqHeight == 0) {
            return inSampleSize;
        }

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both height and width larger than the requested height and
            // width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * center crop to target size.
     */
    public static Bitmap centerCrop(Bitmap srcBmp, int dstSize) {
        int srcWidth = srcBmp.getWidth();
        int srcHeight = srcBmp.getHeight();
        if (srcWidth >= srcHeight) {
            dstSize = dstSize <= srcHeight ? dstSize : srcHeight;
        } else {
            dstSize = dstSize <= srcWidth ? dstSize : srcWidth;
        }
        return Bitmap.createBitmap(
                srcBmp, srcWidth / 2 - dstSize / 2, srcHeight / 2 - dstSize / 2, dstSize, dstSize
        );
    }

    /**
     * Bitmap 宽高缩放
     */
    public static Bitmap scale(Bitmap bitmap, int dstWidth, int dstHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleX = dstWidth * 1.0f / width;
        float scaleY = dstHeight * 1.0f / height;
        float scale = Math.min(scaleX, scaleY);
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale); // 长和宽放大缩小的比例
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

}
