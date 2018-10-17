package com.maxd.upload;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadProperties {
    public static Properties getProperties() {

        File file = new File(Class.class.getClass().getResource("/").getPath()
                + "properties.properties");

        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return properties;

    }
}
