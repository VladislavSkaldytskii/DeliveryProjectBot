package ru.vladislavskaldytskiy.delivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.vladislavskaldytskiy.delivery.models.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

}
