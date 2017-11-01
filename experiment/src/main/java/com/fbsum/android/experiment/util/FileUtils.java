package com.fbsum.android.experiment.util;

import java.io.File;

/**
 * Created by xin on 9/28/17.
 */

public final class FileUtils {

    private FileUtils() {
        throw new UnsupportedOperationException();
    }

    public String getParentPath(String filePath) {
        int index = filePath.lastIndexOf(File.pathSeparator);
        return filePath.substring(0, index);
    }
}
