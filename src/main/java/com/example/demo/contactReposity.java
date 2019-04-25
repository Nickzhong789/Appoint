package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface contactReposity extends JpaRepository<Contacts, Integer> {
    @Query(nativeQuery = true, value = "select * from Contacts where id_num = ?1 and u_name = ?2")
    List<Contacts> getContactsByIdNum(String id_num, String userName);
}
