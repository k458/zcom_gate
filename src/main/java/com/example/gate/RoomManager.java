package com.example.gate;

import com.example.gate.roomManagerComponents.RoomPortStorage;
import com.example.gate.roomManagerComponents.RoomStorage;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.DockerClientBuilder;

import static com.example.gate.Strings.sb;

public class RoomManager {
    private final RoomPortStorage roomPortStorage = new RoomPortStorage(3);
    private final DockerClient dockerClient;
    private final String roomContainerImageName = "zcom-room";

    public RoomManager() {
        dockerClient = DockerClientBuilder.getInstance().build();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            removeAllRooms();
            System.out.println("All ROOM containers are stopped and removed.");
        }));
    }
    public String createRoom() {
        System.out.println("CREATING ROOM...");
        int emptyIndex = roomPortStorage.getEmptyIndex();
        if (emptyIndex >= 0) {
            sb.setLength(0);
            sb.append('R').append(emptyIndex);
            String roomName = sb.toString();
            System.out.println("\twith name: " + roomName);
            int exposedPort = 8080;
            int localPort = 8082+emptyIndex;
            sb.setLength(0);
            sb.append(localPort).append(':').append(exposedPort);
            String portBinding = sb.toString();
            CreateContainerResponse roomContainer = dockerClient.createContainerCmd(roomContainerImageName)
                    .withName(roomName)
                    .withNetworkMode("zcom-back-network")
                    .withExposedPorts(ExposedPort.tcp(exposedPort))
                    .withPortBindings(new Ports(PortBinding.parse(portBinding)))
                    .exec();
            dockerClient.startContainerCmd(roomName).exec();
            sb.setLength(0);
            roomPortStorage.setPort(emptyIndex, sb.append(localPort).toString());
            return roomName;
        }
        else {
            System.out.println("\tfailed: full");
            return null;
        }
    }
    public String getPortByName(String roomName)
    {
        return roomPortStorage.getPortByIndex(Strings.roomNameToIndex(roomName));
    }
    private void removeAllRooms()
    {
        for (int i = 0; i < roomPortStorage.getSize(); i++) {
            if (roomPortStorage.getPortByIndex(i) == null) continue;
            sb.setLength(0);
            sb.append('R').append(i);
            String roomName = sb.toString();
            roomPortStorage.setPort(i, null);
            dockerClient.stopContainerCmd(roomName).exec();
            dockerClient.removeContainerCmd(roomName).exec();
        }
    }
}