package mongo.demo.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mongo.demo.app.dto.GenericResponse;
import mongo.demo.app.model.ToDoDTO;
import mongo.demo.app.repository.ToDoRepository;
import mongo.demo.app.service.ToDoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(
        name = "ToDos api",
        description = "crud operations"
)
public class ToDoController {
    private final ToDoRepository toDoRepository;
    private final ToDoService toDoService;

    @GetMapping("/todos")
    @Operation(summary = "Get all todos")
    public ResponseEntity<GenericResponse> getAllTodos(){
        GenericResponse response = new GenericResponse();
        var todos = toDoService.getAllToDos();
        if(!todos.isEmpty()){
            response.addMessage("todos",todos);
        }else {
            response.addMessage("todos",todos);
            response.addMessage("todos","No ToDos found...");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/todo")
    @Operation(summary = "Save todo")
    public ResponseEntity<GenericResponse> saveToDo(@RequestBody ToDoDTO todo){
        GenericResponse response = new GenericResponse();
        try {
            var t = toDoService.createToDo(todo);
            response.addMessage("status","todo saved successfully");
            response.addMessage("todo",t);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.addMessage("status","todo saved failed");
            response.addMessage("error",e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/todo/{id}")
    @Operation(summary = "Get single todo by id")
    public ResponseEntity<GenericResponse> getSingleToDo(
            @PathVariable String id
            ){
        GenericResponse response = new GenericResponse();
        try {
            var t = toDoService.getSingleTodo(id);
            response.addMessage("status","retrieved successfully");
            response.addMessage("todo",t);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.addMessage("status","retrieved failed");
            response.addMessage("error",e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PatchMapping("/todo/{id}")
    public ResponseEntity<GenericResponse> updateToDo(
            @PathVariable String id,
            @RequestBody ToDoDTO dto
            ){
        GenericResponse response = new GenericResponse();
        try {
            var t = toDoService.updateToDo(id,dto);
            response.addMessage("status","updated successfully");
            response.addMessage("todo",t);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.addMessage("status","updated failed");
            response.addMessage("error",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @DeleteMapping("todo/{id}")
    public ResponseEntity<GenericResponse> deleteById(
            @PathVariable String id
    ){
        GenericResponse response = new GenericResponse();
        try {
            toDoService.deleteByID(id);
            response.addMessage("status","deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.addMessage("status","deleted failed");
            response.addMessage("error",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
