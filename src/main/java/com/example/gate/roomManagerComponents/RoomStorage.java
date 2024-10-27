package com.example.gate.roomManagerComponents;

import static com.example.gate.Strings.sb;

public class RoomStorage {

    private final String[] addresses;
    private int nextIndex = 0;
    private boolean isFull;

    public RoomStorage(int size){
        addresses = new String[size];
    }
    public int getSize()
    {
        return addresses.length;
    }
    public String getRoomNameByIndex(int index)
    {
        if (index < 0 || index > addresses.length - 1) return null;
        return addresses[index];
    }
    public String getNextRoomName()
    {
        if (isFull) return  null;
        sb.setLength(0);
        sb.append('R');
        sb.append(nextIndex);
        return sb.toString();
    }
    public void AddRoom(String roomName){
        if (isFull) {
            System.out.println("Failed to add room: storage is full.");
        }
        else {
            addresses[nextIndex] = roomName;
            updateNextId();
        }
    }
    public void DeleteRoom(String roomName)
    {
        int id = roomId2Id(roomName);
        if (id < 0 || id > addresses.length - 1) return;
        addresses[id] = null;
        nextIndex = id;
        isFull = false;
    }
    private void updateNextId()
    {
        for (int i = 0; i < addresses.length; i++) {
            if (addresses[i] == null){
                nextIndex = i;
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
