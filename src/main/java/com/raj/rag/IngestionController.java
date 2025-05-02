package com.raj.rag;

// import lombok.AllArgsConstructor;  // replaced by explicit constructor
// import lombok.extern.slf4j.Slf4j;  // replaced by explicit logger
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/data")
public class IngestionController {
    private static final Logger log = LoggerFactory.getLogger(IngestionController.class);
    private final IngestionService ingestionService;
    public IngestionController(IngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }
    @PostMapping
    public ResponseEntity<String> ingest(){
        log.info("data load is called ");
        File currentDir = new File("/Users/rajeevkumar/Documents/PersonalDocuments/273MagnetBuying");
        List<File> files = FindFiles.getDirectoryContents(currentDir,"pdf");
        files.forEach(f->ingestionService.ingestPdf(new FileSystemResource(f)));
        return ResponseEntity.ok("All files loaded");

    }
    /**
     * Endpoint to ingest JSON data from a remote URL into the vector store.
     * Example: POST /data/json?url=https://example.com/data.json
     * @param url the URL returning JSON content
     * @return status message
     */
    @PostMapping("/json")
    public ResponseEntity<String> ingestJson(@RequestParam String url) {
        log.info("JSON ingestion called for URL {}", url);
        ingestionService.ingestJsonFromUrl(url);
        return ResponseEntity.ok("JSON ingestion completed for URL: " + url);
    }
}
