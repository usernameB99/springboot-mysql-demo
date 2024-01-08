import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

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

//    @GetMapping("/users")
//    public ResponseEntity<List<String>> getAllUserNames() {
//        List<String> userNames = new ArrayList<>();
//        Iterable<User> users = repository.findAll();
//        for (User user : users) {
//            userNames.add(user.getName());
//        }
//        return ResponseEntity.ok(userNames);
//    }

//    @GetMapping("/users")
//    public ResponseEntity<Object> getAllUserNames() {
//        Map<String, Object> response = new HashMap<>();
//        try {
//            List<String> userNames = new ArrayList<>();
//            Iterable<User> users = repository.findAll();
//            for (User user : users) {
//                userNames.add(user.getName());
//            }
//            response.put("userNames", userNames);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            response.put("error", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//    }

    @GetMapping("/users")
    public ResponseEntity<Object> getUserNamesByEmails(@RequestParam List<String> emails) {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, String> userNames = new HashMap<>();
            for (String email : emails) {
                Optional<User> optionalUser = repository.findByEmail(email);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    userNames.put(email, user.getName());
                }
            }
            response.put("userNames", userNames);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

//    @GetMapping("/users")
//    public ResponseEntity<List<String>> getAllUserNames() {
//        Logger logger = LoggerFactory.getLogger(YourClassName.class);
//        List<String> userNames = new ArrayList<>();
//        try {
//            Iterable<User> users = repository.findAll();
//            for (User user : users) {
//                userNames.add(user.getName());
//            }
//            if (userNames.isEmpty()) {
//                logger.info("Keine Benutzer gefunden in der Datenbank.");
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Keine Benutzer gefunden");
//            } else {
//                logger.info("Benutzer erfolgreich aus der Datenbank abgerufen.");
//                return ResponseEntity.ok(userNames);
//            }
//        } catch (Exception e) {
//            logger.error("Fehler beim Abrufen der Benutzer aus der Datenbank", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein Fehler ist aufgetreten");
//        }

}