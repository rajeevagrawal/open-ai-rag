package com.raj.rag;



// import lombok.AllArgsConstructor;  // replaced by explicit constructor
// import lombok.extern.slf4j.Slf4j;  // replaced by explicit logger
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import java.util.List;
import org.springframework.web.client.RestTemplate;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.document.Document;

@Component
public class IngestionService  {
    private static final Logger log = LoggerFactory.getLogger(IngestionService.class);
    private final VectorStore vectorStore;
    public IngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

//    @Override
//implements CommandLineRunner
//    public void run(String... args) throws Exception{
//        Resource pdf = new ClassPathResource("/doc/text.pdf");
//        ingestPdf(pdf);
//
//    }
    public void ingestPdf(Resource pdf) {
        try {
            var pdfReader = new PagePdfDocumentReader(pdf);
            TextSplitter textSplitter = new TokenTextSplitter();
            vectorStore.accept(textSplitter.apply(pdfReader.get()));
            log.info("Vector DB is updated ->{}", pdf.getFilename());
        }catch (Exception e){
            log.error("Vector DB is not updated with ->{}", pdf.getFilename());
        }
    }
    /**
     * Ingests JSON data from the given URL into the vector store.
     * Fetches the raw JSON, splits into text chunks, and stores embeddings.
     * @param url the HTTP URL returning JSON content
     */
    public void ingestJsonFromUrl(String url) {
    try {
        RestTemplate restTemplate = new RestTemplate();
        String json = restTemplate.getForObject(url, String.class);
        // Wrap JSON into a Document for splitting
        Document document = Document.builder().content(json).build();
        TextSplitter splitter = new TokenTextSplitter();
        List<Document> chunks = splitter.apply(List.of(document));
        vectorStore.accept(chunks);
        log.info("Vector DB updated from JSON URL -> {}", url);
    } catch (Exception e) {
        log.error("Failed to ingest JSON from URL -> {}", url, e);
    }
    }
}
