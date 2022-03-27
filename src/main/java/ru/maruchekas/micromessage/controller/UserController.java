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
import ru.maruchekas.micromessage.api.request.AuthRequest;
import ru.maruchekas.micromessage.api.response.AuthResponse;
import ru.maruchekas.micromessage.api.response.UserData;
import ru.maruchekas.micromessage.exception.AccessDeniedException;
import ru.maruchekas.micromessage.exception.NotSuchUserException;
import ru.maruchekas.micromessage.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Контроллер аутентификации")
@RequestMapping("/api/auth")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "Вход через логин/пароль")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest)
            throws NotSuchUserException, AccessDeniedException {

        logger.info("Пользователь {} входит в приложение", authRequest.getEmail());
        return new ResponseEntity<>(userService.loginUser(authRequest), HttpStatus.OK);
    }

    @GetMapping("/users")
    @Operation(summary = "Получение списка всех пользователей")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<List<UserData>> getUsers(Principal principal){
        logger.info("Пользователь {} запросил список пользовталей", principal.getName());
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
}
