package com.robo.robo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "ignorar")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IgnorarEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String telefone;
}
