package com.example.demo.util;

import java.io.File;

public class AppUtil {
    public static String getUploadPath(String fileName) {
        File uploadDir = new File("src/main/resources/static/uploads");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // create the uploads folder if it doesn't exist
        }
        return new File(uploadDir, fileName).getAbsolutePath();
    }
}
