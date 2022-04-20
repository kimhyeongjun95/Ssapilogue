package com.ssafy.ssapilogue.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "tech_stack")
@NoArgsConstructor
public class TechStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stack_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "techStack", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProjectStack> projectStacks = new ArrayList<>();

    @Builder
    private TechStack(String name) {
        this.name = name;
    }
}
