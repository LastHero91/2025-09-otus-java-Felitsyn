package ru.otus.listener.homework;

import java.util.*;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

public class HistoryListener implements Listener, HistoryReader {
    private final Map<Long, Deque<Optional<Message>>> historyMsgByID = new HashMap<>();
    @Override
    public void onUpdated(Message msg) {
        if (msg == null)
            return;

        long id = msg.getId();
        Deque<Optional<Message>> historyMsg;

        if (historyMsgByID.containsKey(id)) {
            historyMsg = historyMsgByID.get(id);
        } else {
            historyMsg = new ArrayDeque<>();
            historyMsgByID.put(id, historyMsg);
        }

        historyMsg.addLast(Optional.of((Message) msg.clone()));
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return historyMsgByID.get(id).pollFirst();
    }
}
