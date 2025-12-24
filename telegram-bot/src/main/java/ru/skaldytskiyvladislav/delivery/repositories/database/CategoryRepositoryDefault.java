package ru.skaldytskiyvladislav.delivery.repositories.database;

import static ru.skaldytskiyvladislav.delivery.repositories.hibernate.HibernateTransactionFactory.inTransaction;

import java.util.List;

import org.springframework.stereotype.Repository;
import ru.skaldytskiyvladislav.delivery.models.entities.Category;
import ru.skaldytskiyvladislav.delivery.repositories.CategoryRepository;

@Repository
public class CategoryRepositoryDefault implements CategoryRepository {

    @Override
    public List<Category> findAll() {
        String query = "from Category";

        return inTransaction(session -> session.createQuery(query, Category.class).getResultList());
    }

}
