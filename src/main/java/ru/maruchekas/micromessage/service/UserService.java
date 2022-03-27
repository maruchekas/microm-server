package ru.maruchekas.micromessage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.maruchekas.micromessage.api.request.AuthRequest;
import ru.maruchekas.micromessage.api.response.AuthResponse;
import ru.maruchekas.micromessage.api.response.UserData;
import ru.maruchekas.micromessage.config.security.JwtGenerator;
import ru.maruchekas.micromessage.exception.AccessDeniedException;
import ru.maruchekas.micromessage.exception.NoSuchElementException;
import ru.maruchekas.micromessage.model.User;
import ru.maruchekas.micromessage.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.maruchekas.micromessage.config.Constants.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtGenerator jwtGenerator;

    public AuthResponse loginUser(AuthRequest authRequest) throws NoSuchElementException, AccessDeniedException {

        User user = getUserByEmail(authRequest.getEmail());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword()))
            throw new AccessDeniedException();
        String token = jwtGenerator.generateToken(user.getEmail());

        return new AuthResponse()
                .setUserId(user.getId())
                .setToken(token);
    }

    public List<UserData> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapUserToUserData).collect(Collectors.toList());
    }

    private User getUserByEmail(String email) throws NoSuchElementException {
        return userRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));
    }

    private UserData mapUserToUserData(User user){
        return new UserData()
                .setId(user.getId())
                .setEmail(user.getEmail())
                .setType(user.getUserType());
    }


}
