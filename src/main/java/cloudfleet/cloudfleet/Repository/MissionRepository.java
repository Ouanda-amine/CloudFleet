package cloudfleet.cloudfleet.Repository;

import cloudfleet.cloudfleet.Model.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {
}
