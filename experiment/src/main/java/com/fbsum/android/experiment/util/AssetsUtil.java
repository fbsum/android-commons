package com.fbsum.android.experiment.util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xin on 8/29/17.
 */

public class AssetsUtil {
    public static String loadJsonFromFile(Context context, String path) {
        String json = "";
        try {
            InputStream is = context.getAssets().open(path);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
        return json;
    }
}
