package com.example.gate.roomManagerComponents;

public class RoomStorage {
    private final StringBuilder sb = new StringBuilder("");
    private final String[] addresses;
    private int nextId = 0;
    private boolean isFull;

    public RoomStorage(int size){
        addresses = new String[size];
    }
    public int getSize()
    {
        return addresses.length;
    }
    public String getRoomIdByIndex(int index)
    {
        if (index < 0 || index > addresses.length - 1) return null;
        return addresses[index];
    }
    public String getNextId()
    {
        if (isFull) return  null;
        sb.setLength(0);
        sb.append('R');
        sb.append(nextId);
        return sb.toString();
    }
    public void AddRoom(String address){
        if (isFull) {
            System.out.println("Failed to add room: storage is full.");
        }
        else {
            addresses[nextId] = address;
            updateNextId();
        }
    }
    public void DeleteRoom(String roomId)
    {
        int id = roomId2Id(roomId);
        if (id < 0 || id > addresses.length - 1) return;
        addresses[id] = null;
        nextId = id;
        isFull = false;
    }
    public String getRoomAddress(String roomId)
    {
        int id = roomId2Id(roomId);
        if (id < 0 || id > addresses.length - 1) return null;
        return addresses[id];
    }
    private void updateNextId()
    {
        for (int i = 0; i < addresses.length; i++) {
            if (addresses[i] == null){
                nextId = i;
                return;
            }
        }
        isFull = true;
    }
    private int roomId2Id(String roomId)
    {
        // assuming it takes "R102" result will be int 102
        sb.setLength(roomId.length() - 1);
        for (int i = 0; i < sb.length(); i++) {
            sb.setCharAt(i, roomId.charAt(i+1));
        }
        return Integer.parseInt(sb.toString());
    }
}
