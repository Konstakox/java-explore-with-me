package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.constant.Status;
import ru.practicum.model.Request;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    Optional<Request> findByEventIdAndRequesterId(Integer eventId, Integer userId);

    List<Request> findAllByRequesterId(Integer id);

    List<Request> findAllByEventId(Integer eventId);

    @Query("SELECT  count(r.id) " +
            "from Request as r " +
            "WHERE r.event.id = :eventId " +
            "and r.status = :status")
    Optional<Integer> findCountRequestByEventIdAndStatus(Integer eventId, Status status);

    List<Request> findAllByIdIn(Set<Integer> requestIds);
}
