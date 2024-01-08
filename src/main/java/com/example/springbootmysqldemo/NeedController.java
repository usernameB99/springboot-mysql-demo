package com.example.springbootmysqldemo;
import com.example.springbootmysqldemo.Need;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NeedController {
    private final NeedRepository repository;
    private final UserRepository userRepository;

    NeedController(NeedRepository needRepository, UserRepository userRepository) {
        this.repository = needRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/needs")
    public List<Need> getAllNeeds(@RequestParam Long userId) {
        User owner = userRepository.findById(userId).orElse(null);
        if (owner != null) {
            return repository.findByOwner(owner);
        } else {
            return new ArrayList<>();
        }
    }
}