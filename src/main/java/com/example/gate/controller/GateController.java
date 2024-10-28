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
    private final StringBuilder sb = new StringBuilder();

    @Autowired
    public GateController(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/h")
    public String sayHello() {
        return "Hello from Gate";
    }
    @GetMapping("/newRoom")
    public String createNewRoom() {
        String roomName = roomManager.createRoom();
        if (roomName != null)
        {
            return "Created room with name: " + roomName;
        }
        return "Failed to create new room";
    }
    @GetMapping("/hfr")
    public String helloFirstRoom() {
        String roomName = "R0";
        String port = roomManager.getPortByName(roomName);
        if (port != null)
        {
            sb.setLength(0);
            sb.append("http://R0:").append("8080").append("/api/hello");
            System.out.println("Attempting: " + sb.toString());
            ResponseEntity<String> response = restTemplate.getForEntity(sb.toString(), String.class);
            return  response.getBody();
        }
        return "R0 is null";
    }
}