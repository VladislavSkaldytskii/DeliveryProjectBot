package ru.vladislavskaldytskiy.delivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vladislavskaldytskiy.delivery.models.entities.Client;
import ru.vladislavskaldytskiy.delivery.models.entities.Courier;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourierRepository  extends JpaRepository<Courier, Long> {
    List<Courier> findAllByActive(boolean active);
    Optional<Courier> findByClient(Client client);

    void deleteById(Integer id);
}
