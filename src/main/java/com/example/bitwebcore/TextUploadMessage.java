package com.example.bitwebcore;

import java.util.UUID;

public class TextUploadMessage {
    private UUID uuid;
    private String fileName;
    private String content;

    public TextUploadMessage(UUID uuid, String fileName, String content) {
        this.uuid = uuid;
        this.fileName = fileName;
        this.content = content;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContent() {
        return content;
    }
}
