package com.szinton.companymanager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "workers")
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32, nullable = false)
    private String firstName;

    @Column(length = 64, nullable = false)
    private String lastName;

    @Column(length = 128, nullable = false)
    private String job;

    @Column(nullable = false)
    private Double salary;

    @Column
    private String email;

    @Column
    private String phoneNumber;

    @OneToOne(mappedBy = "owner")
    private IdentityCard identityCard;

    @OneToMany(mappedBy = "worker")
    private Set<ProjectMembership> projects;
}
