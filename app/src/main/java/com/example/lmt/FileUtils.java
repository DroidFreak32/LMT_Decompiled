package com.example.lmt;

import java.io.File;

class FileUtils {
    FileUtils() {
    }

    static boolean isFileAvailable(String path, String fileName) {
        return new File(path, fileName).exists();
    }

    static boolean deleteFile(String path, String fileName) {
        return new File(path, fileName).delete();
    }

    static void createFolder(String path) {
        new File(path).mkdirs();
    }
}