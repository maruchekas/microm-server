package ru.maruchekas.micromessage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.maruchekas.micromessage.api.request.MessageRequest;
import ru.maruchekas.micromessage.api.response.ListMessageResponse;
import ru.maruchekas.micromessage.api.response.MessageResponse;
import ru.maruchekas.micromessage.model.Message;
import ru.maruchekas.micromessage.repository.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageResponse postMessage(MessageRequest request) {
        Message message = createMessage(request);
        return mapMessageToMessageResponse(message);
    }

    public ListMessageResponse getMessages() {
        List<Message> messages = messageRepository.findByCreatedTime(LocalDateTime.now());
        List<MessageResponse> messageResponseList = messages.stream().map(this::mapMessageToMessageResponse)
                .collect(Collectors.toList());
        return new ListMessageResponse()
                .setMessageResponseList(messageResponseList)
                .setTotal((long) messages.size());
    }

    private Message createMessage(MessageRequest request) {
        Message message = new Message()
                .setCreatedTime(LocalDateTime.now())
                .setText(request.getText());

        messageRepository.save(message);

        return message;
    }

    private MessageResponse mapMessageToMessageResponse(Message message) {
        return new MessageResponse()
                .setId(message.getId())
                .setCreatedTime(LocalDateTime.now())
                .setText(message.getText());
    }
}
