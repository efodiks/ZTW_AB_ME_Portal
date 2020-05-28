package com.abme.portal.bootstrap;

import lombok.Data;

@Data
public class PicsumImage {
    int id;
    String author;
    int width;
    int height;
    String url;
    String download_url;
}
