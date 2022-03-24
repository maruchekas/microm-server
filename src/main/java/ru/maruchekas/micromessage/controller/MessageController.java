package ru.maruchekas.micromessage.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maruchekas.micromessage.api.request.CreateMessageRequest;
import ru.maruchekas.micromessage.api.response.ListMessageResponse;
import ru.maruchekas.micromessage.api.response.MessageResponse;
import ru.maruchekas.micromessage.exception.InvalidRequestDataException;
import ru.maruchekas.micromessage.service.MessageService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MessageController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MessageService messageService;

    @PostMapping("/message")
    public ResponseEntity<MessageResponse> postMessage(@RequestBody CreateMessageRequest createMessageRequest) {
        logger.info("Получено сообщение: \"{}\", дата создания {}",
                createMessageRequest.getText(),
                LocalDateTime.now());
        return new ResponseEntity<>(messageService.postMessage(createMessageRequest), HttpStatus.OK);
    }

    @GetMapping("/message/{id}")
    public ResponseEntity<MessageResponse> getMessage(@PathVariable("id") Long id) throws InvalidRequestDataException {
        return new ResponseEntity<>(messageService.getMessage(id), HttpStatus.OK);
    }

    @GetMapping("/message/from/{from}/to/{to}")
    public ResponseEntity<ListMessageResponse> getMessages(@PathVariable("from") String from,
                                                           @PathVariable("to") String to) throws InvalidRequestDataException {
        ListMessageResponse listMessageResponse = messageService.getMessages(from, to);
        logger.info("Запрошен список сообщений в диапазоне дат {} {}. Сообщений по запросу отдано: {}",
                from, to,listMessageResponse.getTotal());
        return new ResponseEntity<>(messageService.getMessages(from, to), HttpStatus.OK);
    }
}
