package com.example.cms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.model.Schedule;

public interface ScheduleRepo extends JpaRepository<Schedule, Integer>{

}
