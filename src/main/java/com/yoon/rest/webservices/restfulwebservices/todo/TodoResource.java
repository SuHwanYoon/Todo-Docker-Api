package com.yoon.rest.webservices.restfulwebservices.todo;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	//URL의  id 값으로  특정 Todo를 update 하는 메서드
	@PutMapping(path = "/users/{username}/todos/{id}")
	public Todo updateTodo(@PathVariable String username, @PathVariable int id ,@RequestBody Todo todo) {
		 todoService.updateTodo(todo);
		 // update 한 todo 를 반환 성공하면 200 return
		 return todo;
	}

	//URL의  id 값으로  특정 Todo를 생성 하는 메서드
	@PostMapping(path = "/users/{username}/todos")
	public Todo createTodo(@PathVariable String username,@RequestBody Todo todo) {
		 Todo createdTodo = todoService.addTodo(username, todo.getDescription(), todo.getTargetDate(), todo.isDone());
		 // update 한 todo 를 반환 성공하면 200 return
		 return createdTodo;
	}
	
}
