package com.akka.example.util;

public class AkkaActorUtil {
    public static String entityId(int nodePort, int id) {
        return String.format("%d-%d", nodePort, id);
    }
}
