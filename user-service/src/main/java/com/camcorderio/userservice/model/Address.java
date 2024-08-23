package com.camcorderio.userservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_address")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private Integer post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
