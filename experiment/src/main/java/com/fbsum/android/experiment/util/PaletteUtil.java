package com.fbsum.android.experiment.util;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xin on 7/27/17.
 */

public final class PaletteUtil {

    private PaletteUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * observe only six primary palette swatch colors
     *
     * @param bitmap image bitmap
     * @return Observable<List<Integer>>
     */
    public static Observable<List<Integer>> findPrimaryColors(final Bitmap bitmap) {
        return findColors(bitmap, true);
    }

    /**
     * observe all palette swatch colors
     *
     * @param bitmap image bitmap
     * @return Observable<List<Integer>>
     */
    public static Observable<List<Integer>> findAllSwatchColors(final Bitmap bitmap) {
        return findColors(bitmap, false);
    }

    private static Observable<List<Integer>> findColors(final Bitmap bitmap, final boolean isOnlyPrimary) {
        return Observable
                .create(new ObservableOnSubscribe<List<Integer>>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<List<Integer>> e) throws Exception {
                        List<Palette.Swatch> swatches;
                        if (isOnlyPrimary) {
                            swatches = PaletteUtil.getPrimarySwatches(bitmap);
                        } else {
                            swatches = new ArrayList<>(PaletteUtil.getAllSwatches(bitmap));// modifiableList
                        }

                        List<Integer> colors = new ArrayList<>();
                        for (Palette.Swatch swatch : swatches) {
                            float lightness = swatch.getHsl()[2];
                            if (0.1f < lightness && lightness < 0.8f) {
                                colors.add(swatch.getRgb());
                            }
                        }

                        e.onNext(colors);
                        e.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * @return only six primary swatch colors
     */
    @NonNull
    public static List<Palette.Swatch> getPrimarySwatches(final Bitmap bitmap) {
        return getSwatches(bitmap, true);
    }

    /**
     * @return all swatch colors
     */
    @NonNull
    public static List<Palette.Swatch> getAllSwatches(final Bitmap bitmap) {
        return getSwatches(bitmap, false);
    }

    @NonNull
    private static List<Palette.Swatch> getSwatches(final Bitmap bitmap, final boolean isOnlyPrimary) {
        Palette palette = new Palette.Builder(bitmap).generate();
        List<Palette.Swatch> swatches = new ArrayList<>();
        if (isOnlyPrimary) {
            Palette.Swatch tmp;
            //
            tmp = palette.getDarkVibrantSwatch();
            if (tmp != null) swatches.add(tmp);
            //
            tmp = palette.getDarkMutedSwatch();
            if (tmp != null) swatches.add(tmp);
            //
            tmp = palette.getVibrantSwatch();
            if (tmp != null) swatches.add(tmp);
            //
            tmp = palette.getMutedSwatch();
            if (tmp != null) swatches.add(tmp);
            //
            tmp = palette.getLightVibrantSwatch();
            if (tmp != null) swatches.add(tmp);
            //
            tmp = palette.getLightMutedSwatch();
            if (tmp != null) swatches.add(tmp);
        } else {
            swatches = palette.getSwatches();
        }
        return swatches;
    }
}
