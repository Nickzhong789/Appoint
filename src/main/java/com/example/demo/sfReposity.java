package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface sfReposity extends JpaRepository<Schoolfellow, Integer> {
    @Query(nativeQuery = true, value = "select * from schoolfellow where is_pass = 0")
    List<Schoolfellow> getSfOfNotPass();

    @Query(nativeQuery = true, value = "select * from schoolfellow where is_pass = 1")
    List<Schoolfellow> getSfOfPass();

    @Query(nativeQuery = true, value = "select * from schoolfellow where s_name like %?1%")
    List<Schoolfellow> getSfBySfName(String sfName);

    @Query(nativeQuery = true, value = "select * from schoolfellow where s_num like %?1%")
    List<Schoolfellow> getSfBySfNum(String sfNum);

    @Query(nativeQuery = true, value = "select * from schoolfellow where id_num like %?1%")
    List<Schoolfellow> getSfBySfIdNum(String idNum);

    @Query(nativeQuery = true, value = "select * from schoolfellow where phone like %?1%")
    List<Schoolfellow> getSfBySfPhone(String phone);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE schoolfellow set is_pass = 1 WHERE s_num = ?1")
    void authenSfBySnum(String sNum);
}
