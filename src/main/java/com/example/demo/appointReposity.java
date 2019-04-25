package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface appointReposity extends JpaRepository<Appointment, Integer> {

    @Query(nativeQuery = true, value = "select distinct count(*) as drive_num from appointment where is_drive = 1 and status = 1 and a_time like %?1%")
    Integer getDriveNum(String date);

    @Query(nativeQuery = true, value = "select count(*) from appointment where status = 1 and a_time like %?1%")
    Integer getUnderAppointNum(String date);

    @Query(nativeQuery = true, value = "select distinct count(*) as drive_num from appointment where is_drive = 1 and status != 2 and a_time like %?1%")
    Integer getDriveNumStatics(String date);

    @Query(nativeQuery = true, value = "select count(*) from appointment where status != 2 and a_time like %?1%")
    Integer getUnderAppointNumStatics(String date);

    @Query(nativeQuery = true, value = "select * from appointment where u_name = ?1")
    List<Appointment> getAppointByUser(String userName);

    @Query(nativeQuery = true, value = "select * from appointment where c_name like %?1%")
    List<Appointment> getAppointByContact(String conName);

    @Query(nativeQuery = true, value = "select * from appointment where id_num like %?1%")
    List<Appointment> getAppointById(String idNum);

    @Query(nativeQuery = true, value = "select * from appointment where car_num like %?1%")
    List<Appointment> getAppointByCar(String carNum);

    @Query(nativeQuery = true, value = "select * from appointment where a_time like %?1%")
    List<Appointment> getAppointByDate(String date);

    @Query(nativeQuery = true, value = "select * from appointment where phone like %?1%")
    List<Appointment> getAppointByPhone(String phone);

    @Query(nativeQuery = true, value = "select * from appointment where u_name = ?1 and a_time = ?2 and status = 0")
    List<Appointment> getUnderAppointByUserAndDate(String userName, String date);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(nativeQuery = true, value = "delete from appointment where u_name = ?1 and status = 0")
    void delAppointByUser(String userName);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(nativeQuery = true, value = "delete from appointment where u_name = ?1 and a_time = ?2 and status = 0")
    void delAppointByDate(String userName, String date);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(nativeQuery = true, value = "delete from appointment where u_name = ?1 and c_name = ?2 and a_time = ?3 and status = 0")
    void delAppointContact(String userName, String contact, String date);
}
