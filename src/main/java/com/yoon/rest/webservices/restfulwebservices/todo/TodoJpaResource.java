package com.yoon.rest.webservices.restfulwebservices.todo;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yoon.rest.webservices.restfulwebservices.todo.repository.TodoRepository;

@RestController
public class TodoJpaResource {
	
	private TodoService todoService;
	
	private TodoRepository todoRepository;
	
	public TodoJpaResource(TodoService todoService, TodoRepository todoRepository) {
		super();
		this.todoService = todoService;
		this.todoRepository = todoRepository;
	}


	//URL의  username 값으로 todos를 가져오는 메서드
	@GetMapping(path = "/users/{username}/todos")
	public List<Todo> getTodosByUsername(@PathVariable String username) {
		//return todoService.findByUsername(username);
		//Jpa 전환
		return todoRepository.findByUsername(username);
	}

	//URL의  id 값으로  특정 Todo를 가져오는 메서드
	@GetMapping(path = "/users/{username}/todos/{id}")
	public Todo getSpecificTodo(@PathVariable String username, @PathVariable int id) {
		//return todoService.findById(id);
		// Jpa전환 Optional<Todo>.get()
		Todo specificTodo = todoRepository.findById(id).get();
		return specificTodo;
	}

	//URL의  id 값으로  특정 Todo를 삭제하는 메서드
	@DeleteMapping(path = "/users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteSpecificTodo(@PathVariable String username, @PathVariable int id) {
		 //todoService.deleteById(id);
		//Jpa전환
		todoRepository.deleteById(id); 
		// http 200을 반환하는 대신 204(내용없음) 상태를 반환하기
		 return ResponseEntity.noContent().build();
	}

	//URL의  id 값으로  특정 Todo를 update 하는 메서드
	@PutMapping(path = "/users/{username}/todos/{id}")
	public Todo updateTodo(@PathVariable String username, @PathVariable int id ,@RequestBody Todo todo) {
		 //todoService.updateTodo(todo);
		//Jpa 전환 save는   주어진 엔티티의 id값이 DB에 null이면 insert 있으면 update
		//기존 Todo 엔티티를 내용을 update
		todoRepository.save(todo);
		 // update 한 todo 를 반환 성공하면 200 return
		 return todo;
	}

	//URL의  id 값으로  특정 Todo를 생성 하는 메서드
	@PostMapping(path = "/users/{username}/todos")
	public Todo createTodo(@PathVariable String username,@RequestBody Todo todo) {
		//로그인한 username 쿼리 스트링을 설정
		todo.setUsername(username);
		//새로 등록하는 Todo엔티티는(Post) 엔티티 id값이 null 이어야한다
		todo.setId(null);
		//Jpa 전환 - 로그인한 username 과 id= null 값이 세팅된 Todo엔티티 insert
		Todo createdTodo = todoRepository.save(todo);
		 //Todo createdTodo = todoService.addTodo(username, todo.getDescription(), todo.getTargetDate(), todo.isDone());
		 // update 한 todo 를 반환 성공하면 200 return
		 return createdTodo;
	}
	
}
