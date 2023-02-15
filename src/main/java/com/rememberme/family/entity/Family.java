package com.rememberme.family.entity;

import com.rememberme.memoryquiz.entity.MemoryQuiz;
import com.rememberme.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "family")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Entity
public class Family {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "family_id")
    private Long id;

    @Column(name = "patient_id")
    private Long patientId;

    @Builder.Default
    @OneToMany(mappedBy = "family")
    private List<User> user = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "family")
    private List<MemoryQuiz> memoryQuiz = new ArrayList<>();
}