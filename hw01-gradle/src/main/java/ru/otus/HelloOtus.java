package ru.otus;

import com.google.common.collect.ImmutableList;
import java.util.stream.Collectors;

@SuppressWarnings("java:S106")
public class HelloOtus {
    public static void main(String[] args) {
        ImmutableList<String> wish = ImmutableList.of("Please", "say that the task", "is done", ":)");
        String result = wish.stream().collect(Collectors.joining(" "));

        System.out.println(result);
    }
}
