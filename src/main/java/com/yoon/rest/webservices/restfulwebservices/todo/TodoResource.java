package com.yoon.rest.webservices.restfulwebservices.todo;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoResource {
	
	private TodoService todoService;
	
	public TodoResource(TodoService todoService) {
		super();
		this.todoService = todoService;
	}


	//URL의  username 값으로 todos를 가져오는 메서드
	@GetMapping(path = "/users/{username}/todos")
	public List<Todo> getTodosByUsername(@PathVariable String username) {
		return todoService.findByUsername(username);
	}

	//URL의  id 값으로  특정 Todo를 가져오는 메서드
	@GetMapping(path = "/users/{username}/todos/{id}")
	public Todo getSpecificTodo(@PathVariable String username, @PathVariable int id) {
		return todoService.findById(id);
	}

	//URL의  id 값으로  특정 Todo를 삭제하는 메서드
	@DeleteMapping(path = "/users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteSpecificTodo(@PathVariable String username, @PathVariable int id) {
		 todoService.deleteById(id);
		 // http 200을 반환하는 대신 204(내용없음) 상태를 반환하기
		 return ResponseEntity.noContent().build();
		 
	}
	
}
