package com.alex788.hibernate.springdatajpa.message.repository;

import com.alex788.hibernate.springdatajpa.message.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> findAll();
}
