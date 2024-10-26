package com.example.gate.controller;

import com.example.gate.RoomManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class GateController {
    private final RestTemplate restTemplate;
    private final RoomManager roomManager = new RoomManager();

    @Autowired
    public GateController(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Gate";
    }
    @GetMapping("/helloRoom")
    public String sayHelloFromRoom() {
        String roomUrl = "http://room:8080/api/hello";
        ResponseEntity<String> response = restTemplate.getForEntity(roomUrl, String.class);
        String ret = response.getBody();
        return ret;
    }
    @GetMapping("/createNewRoom")
    public String createNewRoom() {
        String roomId = roomManager.createRoom();
        if (roomId != null)
        {
            return "Created room with ID: " + roomId;
        }
        return "Failed to create new room";
    }
    @GetMapping("/getFirstRoomId")
    public String getFirstRoomId() {
        String roomId = roomManager.getFirstRoomName();
        if (roomId != null)
        {
            return roomId;
        }
        return "First room is null";
    }
}