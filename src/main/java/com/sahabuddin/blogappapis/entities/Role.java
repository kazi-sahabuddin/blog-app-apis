package com.sahabuddin.blogappapis.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ROLES")
@Getter
@Setter
@ToString
public class Role {

    @Id
    private Long id;

    private String name;
}
