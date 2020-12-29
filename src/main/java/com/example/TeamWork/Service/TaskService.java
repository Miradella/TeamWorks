package com.example.TeamWork.Service;

import com.example.TeamWork.Repository.TaskRepository;
import com.example.TeamWork.entity.Task;
import com.example.TeamWork.exception.BadResourceException;
import com.example.TeamWork.exception.ResourceAlreadyExistsException;
import com.example.TeamWork.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
  @Autowired
  private TaskRepository taskRepository;

  private boolean existsById(Integer id) {
    return taskRepository.existsById(id);
  }

  public Task findById(Integer id) throws ResourceNotFoundException {
    Task task = taskRepository.findById(id).orElse(null);
    if (task == null) {
      throw new ResourceNotFoundException("Cannot find Contact with id: " + id);
    } else return task;
  }
  public List<Task> findAll(int pageNumber, int rowPerPage) {
    List<Task> tasks = new ArrayList<>();
    taskRepository.findAll(PageRequest.of(pageNumber - 1, rowPerPage)).forEach(tasks::add);
    return tasks;
  }

  public Task save(Task task) throws BadResourceException, ResourceAlreadyExistsException {
    if (task != null && existsById(task.getTaskId())) {
      throw new ResourceAlreadyExistsException("Contact with id: " + task.getTaskId() +
        " already exists");
    }
    return taskRepository.save(task);
  }


  public void update(Task task)
    throws BadResourceException, ResourceNotFoundException {
    if (!existsById(task.getTaskId())) {
      throw new ResourceNotFoundException("Cannot find Contact with id: " + task.getTaskId());
    }
    taskRepository.save(task);
  }


  public void deleteById(Integer id) throws ResourceNotFoundException {
    if (!existsById(id)) {
      throw new ResourceNotFoundException("Cannot find contact with id: " + id);
    } else {
      taskRepository.deleteById(id);
    }
  }

  public Long count() {
    return taskRepository.count();
  }
}
