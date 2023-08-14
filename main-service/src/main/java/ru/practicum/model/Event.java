package ru.practicum.model;

import lombok.*;
import ru.practicum.constant.State;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", nullable = false)
    private Integer id;
    @Column(nullable = false, length = 1024)
    private String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @Column(name = "confirmed_requests", nullable = false)
    private Integer confirmedRequests;
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;
    @Column(nullable = false, length = 1024)
    private String description;
    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "initiator_id", nullable = false)
    private User initiator;
    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;
    @Column(nullable = false)
    private Boolean paid;
    @Column(name = "participant_limit", nullable = false)
    private Integer participantLimit;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Column(name = "request_moderation", nullable = false)
    private Boolean requestModeration;
    @Enumerated(EnumType.STRING)
    private State state;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private Integer views;
}
