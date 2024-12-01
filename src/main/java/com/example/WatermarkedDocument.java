package com.example;

public class WatermarkedDocument extends DocumentDecorator {
    private final String watermark;

    public WatermarkedDocument(Document document, String watermark) {
        super(document);
        this.watermark = watermark;
    }

    @Override
    public String getContent() {
        return document.getContent() + " [Watermark: " + watermark + "]";
    }

    public String getWatermark() {
        return watermark;
    }
}

