package com.example.rediscachingdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping
    public String getDemo(){
        String demo = "john";
        demo = demo.concat(" doe");
        return demo;
    }

    @PostMapping("/students")
    public ResponseEntity postDetails(@RequestParam String name,@RequestParam  int age){
        redisTemplate.opsForValue().set(name,age);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/age")
    public ResponseEntity<Object> getAge(@RequestParam String name){
        if(Objects.nonNull(redisTemplate.opsForValue().get(name))) {
            int i = (int) redisTemplate.opsForValue().get(name);
            return ResponseEntity.ok(i);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/cache-age")
    @Cacheable(cacheNames="age")
    public int getAgeByDefaultCache(@RequestParam String name){
            return (int) redisTemplate.opsForValue().get(name);
    }


}
