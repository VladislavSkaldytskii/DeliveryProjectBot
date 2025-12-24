package ru.skaldytskiyvladislav.delivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skaldytskiyvladislav.delivery.models.entities.Courier;


import java.util.List;

@Repository
public interface CourierRepository  extends JpaRepository<Courier, Long> {
    List<Courier> findAllByActive(boolean active);


}
