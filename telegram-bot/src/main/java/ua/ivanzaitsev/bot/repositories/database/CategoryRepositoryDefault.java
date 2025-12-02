package ua.ivanzaitsev.bot.repositories.database;

import static ua.ivanzaitsev.bot.repositories.hibernate.HibernateTransactionFactory.inTransaction;

import java.util.List;

import org.springframework.stereotype.Repository;
import ua.ivanzaitsev.bot.models.entities.Category;
import ua.ivanzaitsev.bot.repositories.CategoryRepository;

@Repository
public class CategoryRepositoryDefault implements CategoryRepository {

    @Override
    public List<Category> findAll() {
        String query = "from Category";

        return inTransaction(session -> session.createQuery(query, Category.class).getResultList());
    }

}
