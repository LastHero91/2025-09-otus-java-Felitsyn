package ru.otus;

import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class HelloOtus {
    static Logger logger = Logger.getLogger(HelloOtus.class.getName());

    public static void main(String[] args) {
        List<String> wish = new ArrayList<>();
        wish.add("Please say");
        wish.add("that the task");
        wish.add(null);
        wish.add("is done.");

        String result = Joiner.on(" ").skipNulls().join(wish);

        logger.info(result);
    }
}
