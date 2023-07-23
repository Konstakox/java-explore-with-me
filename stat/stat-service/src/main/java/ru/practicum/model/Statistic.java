package ru.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "statistic")
public class Statistic {
    //statistics event requests
    @Id
    @Column(name = "request_id", nullable = false, length = Integer.MAX_VALUE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String app;
    @Column(nullable = false)
    private String uri;
    @Column(nullable = false)
    private String ip;
//    @DateTimeFormat
    @Column(nullable = false)
    private LocalDateTime timestamp;

}
