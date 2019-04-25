package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface userReposity extends JpaRepository<User, Integer> {
    @Query(nativeQuery = true, value = "select count(*) as userNum from user")
    Integer getUserNum();

    @Query(nativeQuery = true, value = "select * from User where u_name = ?1")
    public List<User> getByUserName(String userName);

    @Query(nativeQuery = true, value = "select * from User where id_num = ?1")
    public List<User> getByUserId(String id_num);

    @Query(nativeQuery = true, value = "select * from user where c_name like %?1%")
    List<User> getUserByContact(String conName);

    @Query(nativeQuery = true, value = "select * from user where id_num like %?1%")
    List<User> getUserByIdNum(String idNum);

    @Query(nativeQuery = true, value = "select * from user where phone like %?1%")
    List<User> getUserByPhone(String phone);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(nativeQuery = true, value = "delete from User where u_name = ?1")
    void delUserByName(String userName);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(nativeQuery = true, value = "update User set u_pass = ?2 where u_name = ?1")
    void updatePassByName(String userName, String password);
}
