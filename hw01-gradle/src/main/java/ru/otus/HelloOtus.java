package ru.otus;

import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("java:S106")
public class HelloOtus {
    public static void main(String[] args) {
        List<String> wish = new ArrayList<>();
        wish.add("Please say");
        wish.add("that the task");
        wish.add(null);
        wish.add("is done.");

        String result = Joiner.on(" ").skipNulls().join(wish);

        System.out.println(result);
    }
}
