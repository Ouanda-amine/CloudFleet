package cloudfleet.cloudfleet.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor @AllArgsConstructor @Setter @Getter
public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String matricule;
    private VehicleType type;
    private VehicleStatus status;
    private Double currentLatitude;
    private Double currentLongitude;
}
