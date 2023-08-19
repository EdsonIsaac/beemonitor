package io.github.edsonisaac.beemonitor.utils;

import jakarta.servlet.ServletContext;
import org.springframework.http.MediaType;

public class MediaTypeUtils {

    public static MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {
        String mineType = servletContext.getMimeType(fileName);
        return MediaType.parseMediaType(mineType != null ? mineType : MediaType.APPLICATION_OCTET_STREAM_VALUE);
    }   
}