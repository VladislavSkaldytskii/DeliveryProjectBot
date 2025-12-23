package ru.vladislavskaldytskiy.delivery.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vladislavskaldytskiy.delivery.exceptions.ValidationException;

import ru.vladislavskaldytskiy.delivery.models.entities.Client;
import ru.vladislavskaldytskiy.delivery.models.entities.Courier;
import ru.vladislavskaldytskiy.delivery.repositories.ClientRepository;
import ru.vladislavskaldytskiy.delivery.repositories.CourierRepository;
import ru.vladislavskaldytskiy.delivery.services.CourierService;

import java.util.List;
import java.util.Optional;


@Service
public class CourierServiceDefault implements CourierService {

    private final CourierRepository courierRepository;
    private final ClientRepository clientRepository;


    @Autowired
    public CourierServiceDefault(CourierRepository courierRepository , ClientRepository clientService, ClientRepository clientRepository) {
        this.courierRepository = courierRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Courier findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id of Courier should not be NULL");
        }

        return courierRepository.findById(Long.valueOf((id))).orElse(null);
    }

    @Override
    public List<Courier> findAll() {
        return courierRepository.findAll();
    }

    @Transactional
    public Courier update(Courier incoming) {

        if (incoming == null) {
            throw new IllegalArgumentException("Courier should not be NULL");
        }
        if (incoming.getId() == null) {
            throw new ValidationException("Id of Courier should not be NULL");
        }

        Courier courier = courierRepository.findById(Long.valueOf(incoming.getId()))
            .orElseThrow(() -> new EntityNotFoundException("Courier not found"));
        courier.setName(incoming.getName());
        courier.setPhoneNumber(incoming.getPhoneNumber());
        courier.setCity(incoming.getCity());
        courier.setAddress(incoming.getAddress());
        courier.setActive(incoming.isActive());

        return courier;
    }

    @Override
    public List<Courier> findAllByActive(boolean active) {
        return courierRepository.findAllByActive(active);
    }

    public Courier save(Long chatId) {

        Client client = clientRepository.findByChatId(chatId)
            .orElseThrow(() ->
                new IllegalArgumentException("Client with chatId not found: " + chatId)
            );

        Optional<Courier> existingCourier =
            courierRepository.findByClient(client);
        if (!existingCourier.isPresent()) {
            Courier courier = new Courier();
            courier.setClient(client);
            courier.setActive(true);

            courier.setName(client.getName());
            courier.setPhoneNumber(client.getPhoneNumber());
            courier.setCity(client.getCity());
            courier.setAddress(client.getAddress());

            return courierRepository.save(courier);
        } else {
            Courier courier = existingCourier.get();
            courier.setActive(true);
            return courier;
        }

    }
    @Transactional
    @Override
    public  void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id of Category should not be NULL");
        }

        courierRepository.deleteById(id);
    }

}








