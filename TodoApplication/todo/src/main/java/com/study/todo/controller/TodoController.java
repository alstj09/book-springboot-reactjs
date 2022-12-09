package com.study.todo.controller;

import com.study.todo.dto.ResponseDTO;
import com.study.todo.dto.TodoDTO;
import com.study.todo.model.TodoEntity;
import com.study.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/test")
    public ResponseEntity<?> testTodo() {
        String str = todoService.testService();
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .data(list)
                .build();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
        try {
            String temporaryUserId = "temporary-user";
            TodoEntity entity = TodoDTO.todoEntity(dto);
            entity.setId(null);
            entity.setUserId(temporaryUserId);

            List<TodoEntity> entities = todoService.create(entity);
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder()
                    .data(dtos)
                    .build();
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder()
                    .error(error)
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList() {
        String temporaryUserId = "temporary-user";

        List<TodoEntity> entities = todoService.retrieve(temporaryUserId);
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder()
                .data(dtos)
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping
    public ResponseEntity<?> updateTodoList(@RequestBody TodoDTO dto) {
        String temporaryUserId = "temporary-user";
        TodoEntity entity = TodoDTO.todoEntity(dto);
        entity.setUserId(temporaryUserId);


        List<TodoEntity> entities = todoService.update(entity);
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder()
                .data(dtos)
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto) {
        try {
            String temporaryUserId = "temporary-user";
            TodoEntity entity = TodoDTO.todoEntity(dto);
            entity.setUserId(temporaryUserId);

            List<TodoEntity> entities = todoService.delete(entity);
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder()
                    .data(dtos)
                    .build();
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder()
                    .error(error)
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
