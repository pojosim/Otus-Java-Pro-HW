package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.util.*;

public class HistoryListener implements Listener, HistoryReader {

    Map<Long, Message> history = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        history.putIfAbsent(msg.getId(), msg.toBuilder().field13((ObjectForMessage) msg.getField13().clone()).build());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(history.get(id));
    }
}
