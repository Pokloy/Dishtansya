package com.example.demo.model.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_users")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="email", unique = true, nullable = false)
    private String email;
    
    @Column(name="password", nullable = false)
    private String password;
    
    private int failedAttempts = 0; 
    private LocalDateTime lockoutTime; 

    public boolean isAccountLocked() {
        return lockoutTime != null && lockoutTime.isAfter(LocalDateTime.now());
    }
}
