package ua.ivanzaitsev.admin.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ivanzaitsev.admin.exceptions.ValidationException;

import ua.ivanzaitsev.admin.models.entities.Client;
import ua.ivanzaitsev.admin.models.entities.Courier;
import ua.ivanzaitsev.admin.repositories.ClientRepository;
import ua.ivanzaitsev.admin.repositories.CourierRepository;
import ua.ivanzaitsev.admin.services.CourierService;

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

        // 1. Загружаем существующего курьера (PERSISTENT)
        Courier courier = courierRepository.findById(Long.valueOf(incoming.getId()))
            .orElseThrow(() -> new EntityNotFoundException("Courier not found"));

        // 2. Обновляем ТОЛЬКО разрешённые поля
        courier.setName(incoming.getName());
        courier.setPhoneNumber(incoming.getPhoneNumber());
        courier.setCity(incoming.getCity());
        courier.setAddress(incoming.getAddress());
        courier.setActive(incoming.isActive());

        // ❗ client НЕ ТРОГАЕМ

        // 3. save() не обязателен, но можно
        return courier;
    }

    @Override
    public List<Courier> findAllByActive(boolean active) {
        return courierRepository.findAllByActive(active);
    }

    public Courier save(Long chatId) {

        // 1. Найти клиента
        Client client = clientRepository.findByChatId(chatId)
            .orElseThrow(() ->
                new IllegalArgumentException("Client with chatId not found: " + chatId)
            );

        // 2. Проверить, не существует ли курьер уже
        Optional<Courier> existingCourier =
            courierRepository.findByClient(client);

        if (!existingCourier.isPresent()) {// 3. Создать нового курьера
            Courier courier = new Courier();
            courier.setClient(client);
            courier.setActive(true);

            // 4. Скопировать данные
            courier.setName(client.getName());
            courier.setPhoneNumber(client.getPhoneNumber());
            courier.setCity(client.getCity());
            courier.setAddress(client.getAddress());

            // 5. Сохранить
            return courierRepository.save(courier);
        } else {
            Courier courier = existingCourier.get();
            courier.setActive(true); // просто активируем
            return courier;
        }

    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Courier courier = courierRepository.findById(Long.valueOf(id))
            .orElseThrow();

        courierRepository.delete(courier);

    }






}

