package cloudfleet.cloudfleet.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor @Setter @Getter
public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String matricule;

    @Enumerated(EnumType.STRING)
    private VehicleType type;

    @Enumerated(EnumType.STRING)
    private VehicleStatus status;
    private Double currentLatitude;
    private Double currentLongitude;




    @OneToMany
    private List<Mission> missions;
}
