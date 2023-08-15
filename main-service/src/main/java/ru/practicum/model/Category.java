package ru.practicum.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Integer id;
    @Column(name = "category_name", nullable = false, unique = true)
    private String name;
}
