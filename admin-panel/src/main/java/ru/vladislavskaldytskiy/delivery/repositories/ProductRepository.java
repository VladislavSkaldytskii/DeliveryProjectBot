package ru.vladislavskaldytskiy.delivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.vladislavskaldytskiy.delivery.models.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
