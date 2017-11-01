package com.fbsum.android.experiment.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public final class BitmapUtils {

    private BitmapUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Bitmap 质量压缩（Bitmap.CompressFormat.JPEG）
     */
    public static Bitmap compress(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        // <code>quality = 100</code> 表示不压缩，压缩后的数据存放到 baos
        for (int quality = 90; quality > 50; quality = quality - 10) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            if (baos.toByteArray().length / 1024 <= 150) {// 循环判断如果压缩后图片是否小于 150kb，小于则退出压缩
                break;
            }
            baos.reset();//重置 baos,继续压缩
        }
        ByteArrayInputStream is = new ByteArrayInputStream(baos.toByteArray());
        return BitmapFactory.decodeStream(is, null, null);
    }

    /**
     * Bitmap 质量压缩（Bitmap.CompressFormat.JPEG）
     */
    public static byte[] compressToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // <code>quality = 100</code> 表示不压缩，压缩后的数据存放到 baos
        for (int quality = 100; quality > 50; quality = quality - 10) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            if (baos.toByteArray().length / 1024 <= 150) {// 循环判断如果压缩后图片是否小于 150kb，小于则退出压缩
                break;
            }
            baos.reset();//重置baos,继续压缩
        }
        return baos.toByteArray();
    }

}
