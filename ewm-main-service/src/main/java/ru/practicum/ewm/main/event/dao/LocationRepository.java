package ru.practicum.ewm.main.event.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.main.event.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
