package com.example.cms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.model.Publish;

public interface PublishRepo extends JpaRepository<Publish, Integer> {

}
