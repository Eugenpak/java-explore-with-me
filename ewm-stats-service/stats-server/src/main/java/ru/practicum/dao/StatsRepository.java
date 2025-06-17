package ru.practicum.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.ViewStats;
import ru.practicum.entity.Stat;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<Stat, Long> {
    @Query("""
           SELECT new ru.practicum.ViewStats(
             st.app,
             st.uri,
             COUNT(st.id)
           )
           FROM Stat st
           WHERE st.timestamp BETWEEN :start AND :end
             AND (:urisEmpty = TRUE OR st.uri IN :uris)
           GROUP BY st.app, st.uri
           ORDER BY COUNT(st.id) DESC
           """)
    List<ViewStats> getAllStats(@Param("start") LocalDateTime start,
                                @Param("end") LocalDateTime end,
                                @Param("uris") List<String> uris,
                                @Param("urisEmpty") boolean urisEmpty);

    // Подсчёт УНИКАЛЬНЫХ IP (distinct).
    @Query("""
           SELECT new ru.practicum.ViewStats(
             st.app,
             st.uri,
             COUNT(DISTINCT st.ip)
           )
           FROM Stat st
           WHERE st.timestamp BETWEEN :start AND :end
             AND (:urisEmpty = TRUE OR st.uri IN :uris)
           GROUP BY st.app, st.uri
           ORDER BY COUNT(DISTINCT st.ip) DESC
           """)
    List<ViewStats> getUniqueStats(@Param("start") LocalDateTime start,
                                  @Param("end") LocalDateTime end,
                                  @Param("uris") List<String> uris,
                                  @Param("urisEmpty") boolean urisEmpty);
}
