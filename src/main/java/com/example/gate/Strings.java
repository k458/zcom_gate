package com.example.gate;

public class Strings {
    public static final StringBuilder sb = new StringBuilder(100);
    public static int roomNameToIndex(String roomName)
    {
        // assuming it takes "R102" result will be int 102
        sb.setLength(roomName.length() - 1);
        for (int i = 0; i < sb.length(); i++) {
            sb.setCharAt(i, roomName.charAt(i+1));
        }
        return Integer.parseInt(sb.toString());
    }
    public static String http = new String("http://");
    public static String api = new String("api/");
}
