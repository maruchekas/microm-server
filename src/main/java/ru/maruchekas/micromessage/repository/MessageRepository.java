package ru.maruchekas.micromessage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maruchekas.micromessage.model.Message;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Optional<Message> findByCreatedTime(LocalDateTime ctime);
}
