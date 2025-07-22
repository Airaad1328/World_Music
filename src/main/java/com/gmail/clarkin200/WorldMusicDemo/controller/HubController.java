package com.gmail.clarkin200.WorldMusicDemo.controller;

import com.gmail.clarkin200.WorldMusicDemo.model.HubSession;
import com.gmail.clarkin200.WorldMusicDemo.service.HubService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/hub")
public class HubController {

    private HubService hubService;

    public HubController (@Qualifier("hubService") HubService hubService) {
        this.hubService = hubService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<HubSession>> fetchAll (){
        return ResponseEntity.status(HttpStatus.OK).body(hubService.fetchAll().get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HubSession> getById (@PathVariable Long id) {
        Optional<HubSession> hubSession = hubService.findById(id);
        if(hubSession.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(hubSession.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<HubSession> joinHubSession(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(hubService.joinHubSession(id));
    }

    @PostMapping("/create")
    public ResponseEntity<HubSession> createHubSession() {
        return ResponseEntity.status(HttpStatus.OK).body(hubService.create(new HubSession()).get());
    }

    @PostMapping("/{id}/leave")
    public ResponseEntity<Map<String,String>> leaveHubSession(@PathVariable Long id) {
        Map<String,String> response = new HashMap<>();
        if(hubService.leaveHubSession(id)) {
            response.put("message","Leave Success");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        response.put("message","Leave Denied");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
