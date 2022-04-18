package com.ssafy.ssapilogue.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
}
