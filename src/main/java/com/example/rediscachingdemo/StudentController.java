package com.example.rediscachingdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/students")
@Slf4j
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    RedisTemplate<Long, Student> redisTemplate;

    @PostMapping
    public Long createStudent(@RequestBody Student student) {
        Student savedStudent = studentRepository.save(student);
        redisTemplate.opsForValue().set(savedStudent.getId(), savedStudent);
        return savedStudent.getId();
    }

    @GetMapping
    @Cacheable(cacheNames = "students")
    public Student getStudent(@RequestParam String name) {
        log.info("getStudent method called with name :{}", name);
        if (studentRepository.findByName(name).isPresent())
            return studentRepository.findByName(name).get();
        return null;
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        //First check cache
        Student student = redisTemplate.opsForValue().get(id);
        if (Objects.isNull(student)) {
            //If not in cache then check db
            log.info("student with given id:{} not found in cache", id);
            Optional<Student> studentOptional = studentRepository.findById(id);
            if (studentOptional.isPresent()) {
                log.info("student with given id:{} found in db", id);
                Student savedStudent = studentOptional.get();
                //Cache the student details
                redisTemplate.opsForValue().set(savedStudent.getId(), savedStudent);
                return savedStudent;
            }
        }
        //If not found in db nor cache then throw 404 -->In ideal scenario we'd not throw exception instead return null
        return student;
    }
}