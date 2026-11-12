package cloudfleet.cloudfleet.Services;

import cloudfleet.cloudfleet.DTO.CreatMissionDTO;
import cloudfleet.cloudfleet.DTO.MissionDTO;
import cloudfleet.cloudfleet.Mapper.MissionMapper;
import cloudfleet.cloudfleet.Model.*;
import cloudfleet.cloudfleet.Repository.DriverRepository;
import cloudfleet.cloudfleet.Repository.MissionRepository;
import cloudfleet.cloudfleet.Repository.VehiculeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {

    private static final List<MissionStatus> ACTIVE =
            List.of(MissionStatus.PLANNED, MissionStatus.IN_PROGRESS);

    private final MissionRepository missionRepository;
    private final MissionMapper missionMapper;
    private final VehiculeRepository vehiculeRepository;
    private final DriverRepository driverRepository;

    @Transactional
    public MissionDTO createMission(CreatMissionDTO dto) {
       if(!dto.dateFin().isAfter(dto.dateDebut())){
           throw  new IllegalStateException("date fin doit etre apres la date debut");
       }

       Vehicule vehicule = getVehicule(dto.vehiculeId());
       Driver driver = getDriver(dto.driverId());

       Mission mission = missionMapper.toEntity(dto);
       mission.setVehicule(vehicule);
       mission.setDriver(driver);
       mission.setStatus(MissionStatus.PLANNED);

       return missionMapper.toDto(missionRepository.save(mission));
    }

    @Transactional(readOnly = true)
    public List<MissionDTO> getAllMissions() {
        return missionRepository.findAll().stream()
                .map(missionMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public MissionDTO getMissionById(Long id) {
        return missionMapper.toDto(getMission(id));
    }

    @Transactional
    public MissionDTO updateMission(Long id, CreatMissionDTO dto) {
        Mission mission = getMission(id);
        mission.setDateDebut(dto.dateDebut());
        mission.setDateFin(dto.dateFin());
        mission.setDepartAdresse(dto.departAdresse());
        mission.setDestinationAdresse(dto.destinationAdresse());
        mission.setVehicule(getVehicule(dto.vehiculeId()));
        mission.setDriver(getDriver(dto.driverId()));

        return missionMapper.toDto(missionRepository.save(mission));
    }

    @Transactional
    public void deleteMission(Long id) {
        missionRepository.delete(getMission(id));
    }

    private void checkAvailability(Vehicule v, Driver d, LocalDateTime debut, LocalDateTime fin) {
        if (v.getStatus() == VehicleStatus.IN_MAINTENANCE) { // adaptez à votre enum
            throw new IllegalStateException("Ce véhicule n'est pas disponible");
        }
        if (!d.isEnabled()) {
            throw new IllegalStateException("Ce chauffeur est désactivé");
        }
        if (missionRepository.existsVehiculeOverlap(v.getId(), ACTIVE, debut, fin)) {
            throw new IllegalStateException("Ce véhicule a déjà une mission sur cette période");
        }
        if (missionRepository.existsDriverOverlap(d.getId(), ACTIVE, debut, fin)) {
            throw new IllegalStateException("Ce chauffeur a déjà une mission sur cette période");
        }
    }

    private Mission getMission(Long id) {
        return missionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mission not found with id: " + id));
    }

    private Vehicule getVehicule(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicule not found with id: " + id));
    }

    private Driver getDriver(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found with id: " + id));
    }
}
