package ru.skaldytskiyvladislav.delivery.repositories;

import java.util.List;

import ru.skaldytskiyvladislav.delivery.models.entities.Product;

public interface ProductRepository {

    Product findById(Integer productId);

    List<Product> findAllByCategoryName(String categoryName, int offset, int size);

}
