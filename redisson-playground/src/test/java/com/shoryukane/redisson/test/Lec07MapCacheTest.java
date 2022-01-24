package com.shoryukane.redisson.test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.shoryukane.redisson.test.dto.Student;

import org.junit.jupiter.api.Test;
import org.redisson.api.RMapCacheReactive;
import org.redisson.codec.TypedJsonJacksonCodec;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec07MapCacheTest extends BaseTest {

    @Test
    public void mapCacheTest() {

        RMapCacheReactive<Integer, Student> mapCache = this.client.getMapCache("users:cache",
                new TypedJsonJacksonCodec(Integer.class, Student.class));

        Student student1 = new Student("sam", 10, "atlanta", Arrays.asList(1, 2, 3));
        Student student2 = new Student("jake", 30, "miami", Arrays.asList(10, 20, 30));

        Mono<Student> st1 = mapCache.put(1, student1, 5, TimeUnit.SECONDS);
        Mono<Student> st2 = mapCache.put(2, student2, 10, TimeUnit.SECONDS);

        StepVerifier.create(st1.concatWith(st2).then())
                .verifyComplete();

        sleep(3000);

        mapCache.get(1).doOnNext(System.out::println).subscribe();
        mapCache.get(2).doOnNext(System.out::println).subscribe();

        sleep(3000);

        mapCache.get(1).doOnNext(System.out::println).subscribe();
        mapCache.get(2).doOnNext(System.out::println).subscribe();

    }

}
