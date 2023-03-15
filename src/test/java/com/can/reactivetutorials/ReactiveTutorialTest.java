package com.can.reactivetutorials;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReactiveTutorialTest {

    Person michael = new Person("Michael","Weston");
    Person fiona = new Person("Fiona","Glenance");
    Person sam = new Person("Sam","Axe");
    Person jesse = new Person("Jesse","Porter");

    @Test
    void monoTests() {
        Mono<Person> personMono = Mono.just(michael);
        Person person = personMono.block();

        assertTrue(person.getFirstName().contains("Michael"));
    }

    @Test
    void monoTransform() {
        Mono<Person> personMono = Mono.just(fiona);
        PersonCommand personCommand = personMono
            .map(person -> {
                return new PersonCommand(person);
            }).block();

        assertTrue(personCommand.getFirstName().contains("Fiona"));
    }

    @Test
    void monoFilter() {
        Mono<Person> personMono = Mono.just(sam);
        Person sam = personMono
            .filter(person -> person.getFirstName().equalsIgnoreCase("foo"))
            .block();

        assertThrows(NullPointerException.class, () -> {
            sam.sayName();
        });
    }

    @Test
    void fluxTest() {
        Flux<Person> people = Flux.just(michael,fiona,sam,jesse);
        people.subscribe(person -> System.out.println(person.sayName()));
    }

    @Test
    void fluxFilterTest() {
        Flux<Person> people = Flux.just(michael,fiona,sam,jesse);
        people.filter(person -> person.getFirstName().equals(fiona.getFirstName()))
            .subscribe(person -> System.out.println(person.sayName()));
    }

    @Test
    void fluxDelayNoOutputTest() {
        Flux<Person> people = Flux.just(michael,fiona,sam,jesse);
        people.delayElements(Duration.ofSeconds(1))
            .subscribe(person -> System.out.println(person.sayName()));
    }

    @Test
    void fluxDelayTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Flux<Person> people = Flux.just(michael,fiona,sam,jesse);
        people.delayElements(Duration.ofSeconds(1))
            .doOnComplete(countDownLatch::countDown)
            .subscribe(person -> System.out.println(person.sayName()));
        countDownLatch.await();
    }

    @Test
    void fluxFilterDelayTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Flux<Person> people = Flux.just(michael,fiona,sam,jesse);
        people.delayElements(Duration.ofSeconds(1))
            .filter(person -> person.getFirstName().contains("i"))
            .doOnComplete(countDownLatch::countDown)
            .subscribe(person -> System.out.println(person.sayName()));
        countDownLatch.await();
    }
}
