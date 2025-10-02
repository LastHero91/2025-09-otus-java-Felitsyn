package ru.otus;

import com.google.common.collect.ImmutableList;

public class HelloOtus {
    public static void main(String[] args) {
        ImmutableList<String> wish = ImmutableList.of("Please", "say that the task", "is done", ":)");
        String result = String.join(" ", wish);

        System.out.println(result);
    }
}
