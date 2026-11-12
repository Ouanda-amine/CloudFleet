package cloudfleet.cloudfleet.Repository;

import cloudfleet.cloudfleet.Model.Mission;
import cloudfleet.cloudfleet.Model.MissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    List<Mission> findByDriverId(Long driverId);

    @Query("""
        select count(m) > 0 from Mission m
        where m.vehicule.id = :vehiculeId
          and m.status in :statuses
          and m.dateDebut < :fin
          and m.dateFin > :debut
        """)
    boolean existsVehiculeOverlap(@Param("vehiculeId") Long vehiculeId,
                                  @Param("statuses") Collection<MissionStatus> statuses,
                                  @Param("debut") LocalDateTime debut,
                                  @Param("fin") LocalDateTime fin);

    @Query("""
        select count(m) > 0 from Mission m
        where m.driver.id = :driverId
          and m.status in :statuses
          and m.dateDebut < :fin
          and m.dateFin > :debut
        """)
    boolean existsDriverOverlap(@Param("driverId") Long driverId,
                                @Param("statuses") Collection<MissionStatus> statuses,
                                @Param("debut") LocalDateTime debut,
                                @Param("fin") LocalDateTime fin);
}
