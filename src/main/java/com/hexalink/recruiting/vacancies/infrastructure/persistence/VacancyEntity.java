package com.hexalink.recruiting.vacancies.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SoftDelete;

@Entity
@Table(name = "vacancies")
@SoftDelete
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VacancyEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
}
