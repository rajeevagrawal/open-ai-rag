package com.raj.rag;

// import lombok.AllArgsConstructor;  // replaced by explicit constructor
// import lombok.extern.slf4j.Slf4j;  // logger not used
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
@Service
public class UploadVectorService {
    private final IngestionService ingestionService;
    public UploadVectorService(IngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }
    public void uploadPdfFilesInVectorDb(File dir){

        List<File> files = FindFiles.getDirectoryContents(dir,"pdf");
        files.forEach(f->ingestionService.ingestPdf(new FileSystemResource(f)));
    }
}
