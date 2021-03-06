package de.rieckpil.learning.streamsandlambdas;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class LambdaStart {

    public static void main(String[] args) {

        System.out.println(compute(121, 51));


    }

    public static int compute(int k, int n) {

        return Stream.iterate(k, e -> e +1)
                .filter(e -> e % 2 == 0)
                .filter(e -> Math.sqrt(e) > 20)
                .mapToInt(e -> e * 2)
                .limit(n)
                .sum();

    }

    public void collectors() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        List<Integer> newList =
                numbers.stream()
                        .filter(e -> e % 2 == 0)
                        .map(e -> e * 2)
                        .collect(toList());

        System.out.println(newList);

        List<Person> persons = createPersons();

        System.out.println(
                persons.stream()
                        .collect(toMap(
                                person -> person.getName() + "-" + person.getAge(),
                                person -> person)
                        ));

        System.out.println(
                persons.stream()
                        .collect(groupingBy(Person::getName)
                        ));

        System.out.println(
                persons.stream()
                        .collect(groupingBy(Person::getName, mapping(Person::getAge,
                                toList())
                        )));
    }

    public static List<Person> createPersons() {

        List<Person> result = Arrays.asList(
                new Person("Tom", Gender.MALE, 20),
                new Person("Tim", Gender.MALE, 21),
                new Person("Jenny", Gender.FEMALE, 27),
                new Person("Anna", Gender.FEMALE, 41),
                new Person("Hans", Gender.MALE, 2),
                new Person("Tom", Gender.MALE, 81),
                new Person("Sarah", Gender.FEMALE, 35),
                new Person("Elisabeth", Gender.FEMALE, 66)
        );

        return result;

    }

    public static void parallelStreams() {

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        TimeIt.code(() -> System.out.println(
                numbers.stream()
                        .filter(integer -> integer % 2 == 0)
                        .mapToInt(LambdaStart::compute)
                        .sum()
        ));

        TimeIt.code(() -> System.out.println(
                numbers.parallelStream()
                        .filter(integer -> integer % 2 == 0)
                        .mapToInt(LambdaStart::compute)
                        .sum()
        ));
    }

    public static int compute(Integer number) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {

        }

        return number * 2;
    }


    public void lambdaForInnerAnonymousClasses() {
        Thread th = new Thread(() -> System.out.println("in thread"));
        th.start();
        System.out.println("In main");
    }

    public void forEach() {

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        numbers.forEach(integer -> System.out.println(integer));
        numbers.forEach(System.out::println);

        numbers.stream().map(String::valueOf).forEach(System.out::println);
    }
}