package ru.vladislavskaldytskiy.delivery.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.vladislavskaldytskiy.delivery.models.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAllByActive(boolean active);
    Optional<Client> findByChatId(Long chatId);
}


