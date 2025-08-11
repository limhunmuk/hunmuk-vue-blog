package home.hmvueblog.web.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hunmuk
 */
@RestController
public class MainController {

    @GetMapping("/api/main")
    public ResponseEntity<String> getMain() {
        return ResponseEntity.ok("Hello, Main!");
    }
}
