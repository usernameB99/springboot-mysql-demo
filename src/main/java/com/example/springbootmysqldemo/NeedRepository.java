package com.example.springbootmysqldemo;
import com.example.springbootmysqldemo.Need;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NeedRepository extends JpaRepository<Need, Long> {
    List<Need> findByOwner(User owner);
}