package cloudfleet.cloudfleet.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private LocalDateTime ActualDateFin;
    private LocalDateTime ActualDateDebut;
    private String DepartAdresse;
    private String destinationAdresse;

    @Enumerated(EnumType.STRING)
    private MissionStatus status;

    @ManyToOne
    private Vehicule vehicule ;

    @ManyToOne
    private Driver driver;


    @ManyToOne
    private Dispatcher dispatcher;




}
