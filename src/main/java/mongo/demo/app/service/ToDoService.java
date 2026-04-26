package mongo.demo.app.service;

import lombok.RequiredArgsConstructor;
import mongo.demo.app.exception.ToDoCollectionException;
import mongo.demo.app.model.ToDoDTO;
import mongo.demo.app.repository.ToDoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoRepository toDoRepository;

    public ToDoDTO createToDo(ToDoDTO toDoDTO) throws Exception{
        Optional<ToDoDTO> existingToDo = toDoRepository.findByTodo(toDoDTO.getTodo());
        if (existingToDo.isPresent()) {
            throw new ToDoCollectionException(ToDoCollectionException.ToDoAlreadyExistsException(toDoDTO.getTodo()));
        }
        toDoDTO.setCreatedAt(new Date());
        toDoDTO.setUpdatedAt(new Date());
        return toDoRepository.save(toDoDTO);
    }

    public List<ToDoDTO> getAllToDos() {
        List<ToDoDTO> todos = toDoRepository.findAll();
        if(todos.isEmpty()){
            return new ArrayList<>();
        }
        return todos;
    }

    public ToDoDTO getSingleTodo(String id) throws Exception{
        Optional<ToDoDTO> optionalToDo = toDoRepository.findById(id);
        if(!optionalToDo.isPresent()){
            throw new ToDoCollectionException(ToDoCollectionException.NotFoundException(id));
        }else {
            return optionalToDo.get();
        }
    }

    public ToDoDTO updateToDo(String id, ToDoDTO toDoDTO) throws Exception{
        Optional<ToDoDTO> todoWithId = toDoRepository.findById(id);
        if(!todoWithId.isPresent()){
            throw new ToDoCollectionException(ToDoCollectionException.NotFoundException(id));
        }
        Optional<ToDoDTO> existingToDo = toDoRepository.findByTodo(toDoDTO.getTodo());
        if (existingToDo.isPresent() && !existingToDo.get().getId().equals(id)) {
            throw new ToDoCollectionException(ToDoCollectionException.ToDoAlreadyExistsException(toDoDTO.getTodo()));
        }

        ToDoDTO updated = todoWithId.get();
        updated.setTodo(toDoDTO.getTodo());
        updated.setDescription(toDoDTO.getDescription());
        updated.setCompleted(toDoDTO.getCompleted());
        updated.setUpdatedAt(new Date());
        return toDoRepository.save(updated);

    }

    public void deleteByID(String id) throws Exception{
        Optional<ToDoDTO> todoWithId = toDoRepository.findById(id);
        if(!todoWithId.isPresent()){
            throw new ToDoCollectionException(ToDoCollectionException.NotFoundException(id));
        }
        toDoRepository.deleteById(id);
    }

}
