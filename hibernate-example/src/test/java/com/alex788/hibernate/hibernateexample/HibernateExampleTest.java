package com.alex788.hibernate.hibernateexample;

import com.alex788.hibernate.hibernateexample.message.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HibernateExampleTest {

    // Тяжеловесный объект, управляет пулом соединений к бд.
    // Объект SessionFactory создается единожды для подключения приложения к одной бд.
    // Расширяет JPA класс EntityMangerFactory.
    private static SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure().addAnnotatedClass(Message.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Test
    void saveAndLoadMessage() {
        try (SessionFactory sessionFactory = createSessionFactory()) {
            // Легковесный объект, использует одно соединение для общение с бд, которое бедер из пула.
            // Объект Session создается единожды для серии запросов к бд для одного потока(ex: web запроса), т.к. не потокобезопасный.
            // Расширяет JPA класс EntityManager.
            Session session = sessionFactory.openSession();

            session.beginTransaction();
            Message message = new Message();
            message.setText("Some Text");
            session.persist(message);
            session.getTransaction().commit();

            session.beginTransaction();
            // Для запроса используется язык HQL,
            //  который позволяет манипулировать таблицами как классами и обеспечивает простую миграцию на новую бд.
            // Message - НЕ название таблицы, это название класса.
            List<Message> messagesFromDb = session.createQuery(
                    "SELECT message FROM Message message",
                    Message.class
            ).getResultList();
            session.getTransaction().commit();

            assertEquals(1, messagesFromDb.size());
            assertEquals("Some Text", messagesFromDb.get(0).getText());
        }
    }
}
