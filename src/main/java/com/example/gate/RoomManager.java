package com.example.gate;

import com.example.gate.roomManagerComponents.RoomStorage;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.core.DockerClientBuilder;

public class RoomManager {
    private final RoomStorage roomStorage = new RoomStorage(3);
    private final DockerClient dockerClient;
    private final String roomContainerImageName = "zcom-room";

    public RoomManager() {
        dockerClient = DockerClientBuilder.getInstance().build();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            removeAllRooms();
            System.out.println("All ROOM containers are stopped and removed.");
        }));
    }
    public String getRoomAddress(String roomId)
    {
        return roomStorage.getRoomAddress(roomId);
    }
    public String getFirstRoomName()
    {
        return roomStorage.getRoomIdByIndex(0);
    }
    public String createRoom() {
        System.out.println("CREATING ROOM...");
        String roomId = roomStorage.getNextId();
        if (roomId == null) return null;
        CreateContainerResponse roomContainer = dockerClient.createContainerCmd(roomContainerImageName).withName(roomId).withEnv("ROOM_ID=" + roomId).exec();
        dockerClient.startContainerCmd(roomContainer.getId()).exec();
        roomStorage.AddRoom(roomId);
        return roomContainer.getId();
    }
    public void removeRoom(String containerId) {
        roomStorage.DeleteRoom(containerId);
        dockerClient.stopContainerCmd(containerId).exec();
        dockerClient.removeContainerCmd(containerId).exec();
    }
    public void removeAllRooms()
    {
        for (int i = 0; i < roomStorage.getSize(); i++) {
            String roomId = roomStorage.getRoomIdByIndex(i);
            if (roomId != null) removeRoom(roomId);
        }
    }
}