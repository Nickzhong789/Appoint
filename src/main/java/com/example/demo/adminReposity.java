package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface adminReposity extends JpaRepository<Admin, Integer> {
    @Query(nativeQuery = true, value = "select * from Admin where a_name = ?1")
    public List<Admin> getByAdminName(String adName);
}
