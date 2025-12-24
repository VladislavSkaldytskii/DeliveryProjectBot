package ru.skaldytskiyvladislav.delivery.repositories.database;

import static ru.skaldytskiyvladislav.delivery.repositories.hibernate.HibernateTransactionFactory.inTransaction;

import java.util.List;

import org.springframework.stereotype.Repository;
import ru.skaldytskiyvladislav.delivery.models.entities.Product;
import ru.skaldytskiyvladislav.delivery.repositories.ProductRepository;

@Repository
public class ProductRepositoryDefault implements ProductRepository {

    @Override
    public Product findById(Integer productId) {
        return inTransaction(session -> session.get(Product.class, productId));
    }

    @Override
    public List<Product> findAllByCategoryName(String categoryName, int offset, int size) {
        String query = "from Product where category.name = :categoryName";

        return inTransaction(session ->
                session.createQuery(query, Product.class)
                        .setParameter("categoryName", categoryName)
                        .setFirstResult(offset)
                        .setMaxResults(size)
                        .getResultList()
        );
    }

}
