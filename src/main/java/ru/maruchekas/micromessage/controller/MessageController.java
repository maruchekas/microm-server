package ru.maruchekas.micromessage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.maruchekas.micromessage.api.request.CreateMessageRequest;
import ru.maruchekas.micromessage.api.response.ListMessageResponse;
import ru.maruchekas.micromessage.api.response.MessageResponse;
import ru.maruchekas.micromessage.exception.InvalidRequestDataException;
import ru.maruchekas.micromessage.service.MessageService;

import java.time.LocalDateTime;

@RestController
@Tag(name = "Контроллер сервера для работы с сообщениями")
@RequestMapping("/api")
@RequiredArgsConstructor
public class MessageController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MessageService messageService;

    @Operation(summary = "Отправка/сохранение сообщения")
    @PostMapping("/message")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<MessageResponse> postMessage(@RequestBody CreateMessageRequest createMessageRequest) {
        logger.info("Получено сообщение: \"{}\", дата создания {}",
                createMessageRequest.getText(),
                LocalDateTime.now());
        return new ResponseEntity<>(messageService.postMessage(createMessageRequest), HttpStatus.OK);
    }

    @Operation(summary = "Получение сообщения по айди")
    @GetMapping("/message/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<MessageResponse> getMessage(@PathVariable("id") Long id) throws InvalidRequestDataException {
        return new ResponseEntity<>(messageService.getMessage(id), HttpStatus.OK);
    }

    @Operation(summary = "Получение списка сообщений в диапазоне дат")
    @GetMapping("/message/from/{from}/to/{to}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<ListMessageResponse> getMessages(@PathVariable("from") String from,
                                                           @PathVariable("to") String to) throws InvalidRequestDataException {
        ListMessageResponse listMessageResponse = messageService.getMessages(from, to);
        logger.info("Запрошен список сообщений в диапазоне дат {} {}. Сообщений по запросу отдано: {}",
                from, to, listMessageResponse.getTotal());
        return new ResponseEntity<>(listMessageResponse, HttpStatus.OK);
    }
}
