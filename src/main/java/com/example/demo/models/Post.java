package com.example.demo.models;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy =GenerationType.SEQUENCE)
    private Long id;

    private String title;

    @Column(columnDefinition ="TEXT")
    private String body;

    @CreationTimestamp
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;



    @ManyToOne
    @JoinColumn(name="account_id",referencedColumnName = "id",nullable=true)
    private Account account;

}
