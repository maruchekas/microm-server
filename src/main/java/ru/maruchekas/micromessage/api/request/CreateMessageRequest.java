package ru.maruchekas.micromessage.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CreateMessageRequest {

    private String text;
    private Long timestamp;
}
