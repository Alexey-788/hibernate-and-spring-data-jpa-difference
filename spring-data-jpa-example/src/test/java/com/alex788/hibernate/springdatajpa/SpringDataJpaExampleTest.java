package com.alex788.hibernate.springdatajpa;

import com.alex788.hibernate.springdatajpa.message.Message;
import com.alex788.hibernate.springdatajpa.message.config.SpringDataJpaConfiguration;
import com.alex788.hibernate.springdatajpa.message.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringDataJpaConfiguration.class)
public class SpringDataJpaExampleTest {

    @Autowired
    MessageRepository messageRepository;

    @Test
    void saveAndLoadMessage() {
        Message message = new Message();
        message.setText("Some Text");
        messageRepository.save(message);

        List<Message> messagesFromDb = messageRepository.findAll();
        assertEquals(1, messagesFromDb.size());
        assertEquals("Some Text", messagesFromDb.get(0).getText());
    }
}
