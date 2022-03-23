package ru.maruchekas.micromessage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.maruchekas.micromessage.api.request.MessageRequest;
import ru.maruchekas.micromessage.api.response.ListMessageResponse;
import ru.maruchekas.micromessage.api.response.MessageResponse;
import ru.maruchekas.micromessage.service.MessageService;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/api/message")
    public ResponseEntity<MessageResponse> postMessage(@RequestBody MessageRequest messageRequest){
        return new ResponseEntity<>(messageService.postMessage(messageRequest), HttpStatus.OK);
    }

    @GetMapping("/api/message")
    public ResponseEntity<ListMessageResponse> getMessages(){
        return new ResponseEntity<>(messageService.getMessages(), HttpStatus.OK);
    }
}
