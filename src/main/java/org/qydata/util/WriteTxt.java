package org.qydata.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by jonhn on 2017/9/4.
 */
public class WriteTxt {

    /**
     *写文件
     */
    public static void writeTxt(String fileName,String fileContent) throws IOException {
        File file = new File(fileName);
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(fileContent + "\r\n");
        bw.close();
    }

}
