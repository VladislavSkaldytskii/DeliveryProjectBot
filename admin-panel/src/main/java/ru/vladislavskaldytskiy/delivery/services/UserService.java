package ru.vladislavskaldytskiy.delivery.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import ru.vladislavskaldytskiy.delivery.models.entities.User;

public interface UserService extends UserDetailsService {

    User findById(Integer id);

    List<User> findAll();

    User save(User user);

    User update(User user);

    void deleteById(Integer id);

}
