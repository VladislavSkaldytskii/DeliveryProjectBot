package ru.vladislavskaldytskiy.delivery.services;

import java.util.List;

import ru.vladislavskaldytskiy.delivery.models.entities.Category;

public interface CategoryService {

    Category findById(Integer id);

    List<Category> findAll();

    Category save(Category category);

    Category update(Category category);

    void deleteById(Integer id);

}
