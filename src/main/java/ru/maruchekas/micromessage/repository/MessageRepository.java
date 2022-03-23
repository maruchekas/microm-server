package ru.maruchekas.micromessage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.maruchekas.micromessage.model.Message;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m " +
            "FROM Message m " +
            "WHERE m.createdTime < current_timestamp ")
    List<Message> findByCreatedTime(LocalDateTime ctime);
}
