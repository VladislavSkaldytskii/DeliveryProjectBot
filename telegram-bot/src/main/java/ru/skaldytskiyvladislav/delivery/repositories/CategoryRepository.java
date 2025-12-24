package ru.skaldytskiyvladislav.delivery.repositories;

import java.util.List;

import ru.skaldytskiyvladislav.delivery.models.entities.Category;

public interface CategoryRepository {

    List<Category> findAll();

}
