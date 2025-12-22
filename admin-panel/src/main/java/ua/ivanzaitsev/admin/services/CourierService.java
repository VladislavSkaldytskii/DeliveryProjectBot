package ua.ivanzaitsev.admin.services;

import org.springframework.transaction.annotation.Transactional;
import ua.ivanzaitsev.admin.models.entities.Courier;

import java.util.List;

public interface CourierService {

    Courier findById(Integer id);

    List<Courier> findAll();

    Courier update(Courier courier);

    List<Courier> findAllByActive(boolean active);

    public Courier save(Long chatId);

    @Transactional
    void deleteById(Integer id);

}
