package com.example.demo.model.dao.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_users")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="email")
    private String email;
    
    @Column(name="password")
    private String password;
}
