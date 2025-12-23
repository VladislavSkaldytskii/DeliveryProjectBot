package ru.vladislavskaldytskiy.delivery.services;

import org.springframework.transaction.annotation.Transactional;
import ru.vladislavskaldytskiy.delivery.models.entities.Courier;

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
