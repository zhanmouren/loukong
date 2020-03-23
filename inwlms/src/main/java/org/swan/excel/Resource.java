package org.swan.excel;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

public class Resource {

    public static InputStream getResourceInputStream(String path) {
        try {
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            org.springframework.core.io.Resource resource = resourceLoader.getResource("classpath:" + path);
            InputStream inputStream = resource.getInputStream();
            return inputStream;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
