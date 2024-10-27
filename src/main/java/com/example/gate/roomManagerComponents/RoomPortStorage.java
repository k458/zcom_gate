package com.example.gate.roomManagerComponents;

public class RoomPortStorage {
    private final String[] ports;

    public RoomPortStorage(int size)
    {
        ports = new String[size];
    }
    public int getSize(){
        return ports.length;
    }
    public void setPort(int index, String port)
    {
        if (index < 0 || index > ports.length - 1) return;
        ports[index] = port;
    }
    public String getPortByIndex(int index)
    {
        if (index < 0 || index > ports.length - 1) return null;
        return  ports[index];
    }
    public int getEmptyIndex()
    {
        for (int i = 0; i < ports.length; i++) {
            if (ports[i] == null) return i;
        }
        return -1;
    }
}
