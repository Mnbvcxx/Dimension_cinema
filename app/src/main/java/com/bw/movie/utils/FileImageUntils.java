package com.bw.movie.utils;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author: pengbo
 * @date:2019/1/27 desc:图片转换成文件
 */
public class FileImageUntils {
    public static void setBitmap(Bitmap bitmap, String path, int quality) {
        String substring = path.substring(0, path.lastIndexOf("/"));
        File file = new File(substring);
        if (!file.exists() || !file.isDirectory()) {
            try {
                if (!file.mkdirs()) {
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(path);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, quality, outputStream)) {
                outputStream.flush();
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
