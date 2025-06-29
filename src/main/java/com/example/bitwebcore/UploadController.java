package com.example.bitwebcore;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UploadController {

    private final RabbitMQSender rabbitMQSender;
    private final UploadMetadataRepository metadataRepository;

    public UploadController(RabbitMQSender rabbitMQSender, UploadMetadataRepository metadataRepository) {
        this.rabbitMQSender = rabbitMQSender;
        this.metadataRepository = metadataRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<UUID> upload(@RequestParam("file") MultipartFile file) throws IOException {
        UUID id = UUID.randomUUID();
        String filename = file.getOriginalFilename();
        String content = new String(file.getBytes(), StandardCharsets.UTF_8);

        System.out.println("Received file from frontend: " + id + "(" + filename + ")");

        // Save
        UploadMetadata metadata = new UploadMetadata(id, filename, "processing");
        metadataRepository.save(metadata);

        TextUploadMessage message = new TextUploadMessage(id, filename, content);
        rabbitMQSender.send(message);  // <-- Send to RabbitMQ

        return ResponseEntity.ok(id);
    }

    @GetMapping("/upload/{id}")
    public ResponseEntity<UploadMetadata> getUpload(@PathVariable UUID id) {
        Optional<UploadMetadata> result = metadataRepository.findById(id);
        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
