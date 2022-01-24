package com.shoryukane.redisson.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.shoryukane.redisson.test.dto.Student;

import org.junit.jupiter.api.Test;
import org.redisson.api.RMapReactive;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.TypedJsonJacksonCodec;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec06MapTest extends BaseTest {

    @Test
    public void mapTest1() {
        RMapReactive<String, String> map = this.client.getMap("user:1", StringCodec.INSTANCE);
        Mono<String> name = map.put("name", "sam");
        Mono<String> age = map.put("age", "10");
        Mono<String> city = map.put("city", "atlanta");
        StepVerifier.create(name.concatWith(age).concatWith(city).then())
                .verifyComplete();
    }

    @Test
    public void mapTest2() {
        RMapReactive<String, String> map = this.client.getMap("user:2", StringCodec.INSTANCE);
        Map<String, String> mapOfData = new HashMap<>();
        mapOfData.put("name", "jake");
        mapOfData.put("age", "30");
        mapOfData.put("city", "miami");

        StepVerifier.create(map.putAll(mapOfData).then())
                .verifyComplete();
    }

    @Test
    public void mapTest3() {
        RMapReactive<Integer, Student> map = this.client.getMap("users", new TypedJsonJacksonCodec(Integer.class, Student.class));
        
        Student student1 = new Student("sam", 10, "atlanta", Arrays.asList(1,2,3));
        Student student2 = new Student("jake", 30, "miami", Arrays.asList(10,20,30));
        
        Mono<Student> mono1 = map.put(1, student1);
        Mono<Student> mono2 = map.put(2, student2);

        StepVerifier.create(mono1.concatWith(mono2).then())
                .verifyComplete();
    }
}
