package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface certiReposity extends JpaRepository<Certification, Integer> {
    @Query(nativeQuery = true, value = "select certification.id, certification.c_name, certification.id_num, " +
            "certification.phone  from Certification, Contacts where certification.id_num = contacts.id_num and u_name = ?1")
    List<Certification> getCertificationByUser(String userName);

    @Query(nativeQuery = true, value = "select * from Certification where id_num = ?1")
    List<Certification> getById(String id_num);

    @Query(nativeQuery = true, value = "select * from Certification where c_name = ?1")
    List<Certification> getCertificationByCerName(String cerName);
}
