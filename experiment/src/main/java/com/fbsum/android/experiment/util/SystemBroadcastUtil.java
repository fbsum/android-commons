package com.fbsum.android.experiment.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by xin on 10/24/17.
 */

public class SystemBroadcastUtil {

    private SystemBroadcastUtil() {
        throw new UnsupportedOperationException();
    }

    public static void notifyGalleryRefresh(Activity activity, String path) {
        activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
    }
}
