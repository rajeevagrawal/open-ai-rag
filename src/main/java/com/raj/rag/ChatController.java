package com.raj.rag;

// import lombok.extern.slf4j.Slf4j;  // replaced by explicit logger
// import org.springframework.ai.chat.client.ChatClient;  // no longer used
// import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
// import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    private final ChatClient client;

    public ChatController(ChatClient.Builder builder, PgVectorStore vectorStore) {
        this.client = builder
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))
                .build();
    }

//    @GetMapping(path = "/chat", produces = MediaType.APPLICATION_JSON_VALUE)
//    public String chat(@RequestParam String text) {
//        log.info("Chat request: {}", text);
//        String question = StringUtils.hasText(text) ? text : "What happened to Hardwigg";
//        // Execute chat with RAG advisors (vector store + QA)
//        // Use prompt(text) API to get a CallResponseSpec for entity mapping
//        ChatClient.CallResponseSpec spec = client
//                .prompt()
//                .user(question)
//                .call();
//        // Extract full ChatResponse (includes choices, usage, formatting)
//        log.info("Response= {}",  spec.chatResponse());
//
//        log.info("content= {}",  spec.content());
//        return spec.content();
//    }


    @GetMapping("/chat")
    public String chat(@RequestParam String text){

        log.info("Request {}",text);
        String question= StringUtils.hasText(text)? text : "What happened to Hardwigg";
        return client.prompt()
                .user(question)
                .call()
                .content();
    }


}
