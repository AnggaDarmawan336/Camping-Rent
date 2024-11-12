package com.code.camping.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Primary;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "admins")
@Builder
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;

    @Column(unique = true)
    private String email;
    private String password;

}
