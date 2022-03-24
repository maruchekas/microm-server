package ru.maruchekas.micromessage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.maruchekas.micromessage.model.Message;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Optional<Message> findById(Long id);

    @Query("SELECT m " +
            "FROM Message m " +
            "WHERE m.createdTime BETWEEN :from AND :to")
    List<Message> findByCreatedTime(LocalDateTime from, LocalDateTime to);
}
