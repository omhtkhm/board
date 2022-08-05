package com.cos.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.project1.model.Mylike;

public interface MylikeRepository extends JpaRepository<Mylike, Integer> {
	
}
