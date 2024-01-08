package com.example.springbootmysqldemo;
//import org.springframework.data.repository.CrudRepository;
//import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

//public interface UserRepository extends CrudRepository<User, Long> {
//    Optional<User> findByEmail(String email);
//}

