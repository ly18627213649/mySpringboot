package com.example.xhlang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {

    protected static Logger log = LoggerFactory.getLogger(PropertiesUtil.class);

    public PropertiesUtil() {
    }

    public static Properties loadProperties(String configFilePath) throws IOException {
        return CommUtil.null2String(configFilePath).equals("") ? null : loadProperties((InputStream) (new FileInputStream(new File(configFilePath))));
    }

    public static Properties loadProperties(Map<String, Object> configMap) {
        return null;
    }

    public static Properties loadProperties(InputStream inputStream) throws IOException {
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }
}
