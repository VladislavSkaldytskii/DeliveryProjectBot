package ua.ivanzaitsev.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.ivanzaitsev.admin.models.entities.Client;
import ua.ivanzaitsev.admin.models.entities.Courier;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourierRepository  extends JpaRepository<Courier, Long> {
    List<Courier> findAllByActive(boolean active);
    Optional<Courier> findByClient(Client client);

    void deleteById(Integer id);
}
