import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User loginUser) {
        Optional<User> optionalUser = repository.findByEmail(loginUser.getEmail());
        Map<String, String> response = new HashMap<>();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (loginUser.getPassword().equals(user.getPassword())) {
                // Login erfolgreich
                response.put("status", "success");
                response.put("message", "Login erfolgreich");
                return ResponseEntity.ok(response);
            }
        }
        // Login fehlgeschlagen
        response.put("status", "error");
        response.put("message", "Nutzername oder Passwort ist nicht korrekt");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getHashedPassword")
    public ResponseEntity<String> getHashedPassword(@RequestParam String email) {
        Optional<User> optionalUser = repository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return ResponseEntity.ok(user.getPassword());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Benutzer nicht gefunden");
        }
    }
}