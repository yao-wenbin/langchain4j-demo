package com.github.yaowenbin.rag;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {
    public static Path toPath(String relativePath) {
        try {
            URL fileUrl = EasyRagExample.class.getClassLoader().getResource(relativePath);
            return Paths.get(fileUrl.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    static interface Assistant {

        String chat(String userMessage);
    }
}
