package ru.maruchekas.micromessage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.maruchekas.micromessage.api.request.CreateMessageRequest;
import ru.maruchekas.micromessage.api.response.ListMessageResponse;
import ru.maruchekas.micromessage.api.response.MessageResponse;
import ru.maruchekas.micromessage.exception.InvalidRequestDataException;
import ru.maruchekas.micromessage.model.Message;
import ru.maruchekas.micromessage.repository.MessageRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.maruchekas.micromessage.config.Constants.*;


@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageResponse postMessage(CreateMessageRequest request) {
        Message message = createMessage(request);
        return mapMessageToMessageResponse(message);
    }

    public ListMessageResponse getMessages(String from, String to) throws InvalidRequestDataException {

        LocalDateTime fromTime;
        LocalDateTime toTime;
        try {
        fromTime = LocalDateTime.parse(from);
        toTime = LocalDateTime.parse(to);
        } catch (RuntimeException e){
            throw new InvalidRequestDataException(INVALID_DATEFORMAT);
        }

        if (fromTime.isAfter(toTime)){
            throw new InvalidRequestDataException(INCORRECT_PERIOD);
        }

        List<Message> messages = messageRepository.findByCreatedTime(fromTime, toTime);
        List<MessageResponse> messageResponseList = messages.stream().map(this::mapMessageToMessageResponse)
                .collect(Collectors.toList());
        return new ListMessageResponse()
                .setMessageList(messageResponseList)
                .setTotal((long) messages.size());
    }

    public MessageResponse getMessage(Long id) throws InvalidRequestDataException {
        Message message = messageRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return mapMessageToMessageResponse(message);

    }

    private Message createMessage(CreateMessageRequest request) {
        Message message = new Message()
                .setCreatedTime(LocalDateTime.now())
                .setText(request.getText());

        messageRepository.save(message);

        return message;
    }

    private MessageResponse mapMessageToMessageResponse(Message message) {
        return new MessageResponse()
                .setId(message.getId())
                .setCreatedTime(message.getCreatedTime())
                .setText(message.getText());
    }
}
