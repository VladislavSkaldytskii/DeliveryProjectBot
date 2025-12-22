package ua.ivanzaitsev.bot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.ivanzaitsev.bot.models.entities.Courier;


import java.util.List;

@Repository
public interface CourierRepository  extends JpaRepository<Courier, Long> {
    List<Courier> findAllByActive(boolean active);


}
