package spring.springbootweb.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @GetMapping("/")
    public ResponseEntity<String> isAvailable() {
        return new ResponseEntity<>("hi", HttpStatus.OK);
    }
}
