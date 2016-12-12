package com.practice.util;

import com.practice.entity.Message;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by gaofeng on 2016-12-08.
 */

public class DebugUtil {
    public static List<Message> generateMessageDummyData(int size) {
        LinkedList<Message> messages = new LinkedList<>();
        Random random = new Random();
        for (int i = 0; i < size; ++i) {
            Message message = new Message();
            message.setMessage(random.nextInt(10000) + "");
            message.setReceive(random.nextBoolean());
            messages.add(message);
        }
        return messages;
    }
}
