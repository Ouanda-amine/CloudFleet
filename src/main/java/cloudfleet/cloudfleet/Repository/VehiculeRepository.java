package cloudfleet.cloudfleet.Repository;

import cloudfleet.cloudfleet.Model.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
    boolean existsByMatricule(String matricule);


}
