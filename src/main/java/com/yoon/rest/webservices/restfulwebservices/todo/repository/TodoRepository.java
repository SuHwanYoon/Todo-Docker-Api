package com.yoon.rest.webservices.restfulwebservices.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yoon.rest.webservices.restfulwebservices.todo.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer> {

	//username 으로 Todo리스트 반환하는 메서드 정의
	List<Todo> findByUsername(String username);
}
