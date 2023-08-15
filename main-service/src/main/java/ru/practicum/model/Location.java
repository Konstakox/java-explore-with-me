package ru.practicum.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "locations")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id", nullable = false)
    private Integer id;
    @Column(nullable = false)
    private float lat;
    @Column(nullable = false)
    private float lon;
}
