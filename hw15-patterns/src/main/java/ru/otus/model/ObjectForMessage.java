package ru.otus.model;

import java.util.List;
import java.util.stream.Collectors;

public class ObjectForMessage implements Cloneable{
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public Object clone() {
        ObjectForMessage objClone = new ObjectForMessage();

        if (this.data != null) {
            List<String> dataClone = data.stream()
                    .collect(Collectors.toList());

            objClone.setData(dataClone);
        }

        return objClone;
    }
}
