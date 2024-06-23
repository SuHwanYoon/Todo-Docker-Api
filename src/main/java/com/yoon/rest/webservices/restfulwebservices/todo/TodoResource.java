package com.yoon.rest.webservices.restfulwebservices.todo;

import java.util.List;

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
	public List<Todo> getTodos(@PathVariable String username) {
		return todoService.findByUsername(username);
	}
				
}
