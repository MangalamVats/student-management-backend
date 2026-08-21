package com.example.studentmanagement.service;
import com.example.studentmanagement.entity.Student; import com.example.studentmanagement.repository.StudentRepository; import org.springframework.stereotype.Service; import java.util.List;
@Service public class StudentService {
private final StudentRepository repo; public StudentService(StudentRepository repo){this.repo=repo;}
public List<Student> getAll(){return repo.findAll();} public Student get(Long id){return repo.findById(id).orElseThrow(()->new RuntimeException("Student not found: "+id));}
public Student create(Student s){return repo.save(s);} public Student update(Long id,Student s){Student e=get(id);e.setName(s.getName());e.setEmail(s.getEmail());e.setAge(s.getAge());return repo.save(e);}
public void delete(Long id){repo.delete(get(id));}
}