package cloudfleet.cloudfleet.Services;

import cloudfleet.cloudfleet.DTO.CreatMissionDTO;
import cloudfleet.cloudfleet.DTO.MissionDTO;
import cloudfleet.cloudfleet.DTO.UpdateMissionDTO;
import cloudfleet.cloudfleet.Mapper.MissionMapper;
import cloudfleet.cloudfleet.Model.*;
import cloudfleet.cloudfleet.Repository.DispatcherRepository;
import cloudfleet.cloudfleet.Repository.DriverRepository;
import cloudfleet.cloudfleet.Repository.MissionRepository;
import cloudfleet.cloudfleet.Repository.VehiculeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {

    private static final List<MissionStatus> ACTIVE =
            List.of(MissionStatus.PLANNED, MissionStatus.IN_PROGRESS);
    private static final Long NEW_MISSION = -1L;

    private final MissionRepository missionRepository;
    private final MissionMapper missionMapper;
    private final VehiculeRepository vehiculeRepository;
    private final DriverRepository driverRepository;
    private final DispatcherRepository dispatcherRepository;

    @Transactional
    public MissionDTO createMission(CreatMissionDTO dto , User currentuser) {
       if(!dto.dateFin().isAfter(dto.dateDebut())){
           throw  new IllegalStateException("date fin doit etre apres la date debut");
       }

       Vehicule vehicule = getVehicule(dto.vehiculeId());
       Driver driver = getDriver(dto.driverId());
       checkAvailability(vehicule,driver,dto.dateDebut(),dto.dateFin() , NEW_MISSION);

       Mission mission = missionMapper.toEntity(dto);
       mission.setVehicule(vehicule);
       mission.setDriver(driver);
       mission.setStatus(MissionStatus.PLANNED);
       if(currentuser instanceof Dispatcher){
           mission.setDispatcher(dispatcherRepository.getReferenceById(currentuser.getId()));
       }



       return missionMapper.toDto(missionRepository.save(mission));
    }

    @Transactional(readOnly = true)
    public List<MissionDTO> getAllMissions(User currentuser) {
       List<Mission> missions = (currentuser instanceof Driver)
               ? missionRepository.findByDriverId(currentuser.getId())
               : missionRepository.findAll();
       return missionMapper.toDTOLIST(missions);

    }

    @Transactional(readOnly = true)
    public MissionDTO getMissionById(Long id , User currentuser) {
        Mission mission = getMission(id);
        CheckAcces(mission,currentuser);
        return missionMapper.toDto(mission);

    }

    @Transactional
    public MissionDTO startMission(Long id , User currentuser){
        Mission mission = getMission(id);
        CheckAcces(mission,currentuser);
        requireStatus(mission,MissionStatus.PLANNED,"démarrée");

        if(mission.getVehicule().getStatus() != VehicleStatus.AVAILABLE){
            throw new IllegalStateException("Le véhicule n'est pas disponible pour démarrer la mission");
        }
        if (mission.getDriver().getStatus() != DriverStatus.AVAILABLE){
            throw new IllegalStateException("Le chauffeur n'est pas disponible pour démarrer la mission");
        }
        mission.setStatus(MissionStatus.IN_PROGRESS);
        mission.setActualDateDebut(LocalDateTime.now());
        mission.getVehicule().setStatus(VehicleStatus.UNAVAILABLE);
        mission.getDriver().setStatus(DriverStatus.ON_DELIVERY);
        return missionMapper.toDto(mission);
    }

    @Transactional
    public MissionDTO completeMission (Long id , User currentuser){

        Mission mission = getMission(id);
        CheckAcces(mission,currentuser);
        requireStatus(mission , MissionStatus.IN_PROGRESS , "terminée");

        mission.setActualDateFin(LocalDateTime.now());
        mission.setStatus(MissionStatus.COMPLETED);
        mission.getVehicule().setStatus(VehicleStatus.AVAILABLE);
        mission.getDriver().setStatus(DriverStatus.AVAILABLE);
        return missionMapper.toDto(mission);
    }

    @Transactional
    public MissionDTO cancelMission(Long id  ){
        Mission mission = getMission(id);
        requireStatus(mission , MissionStatus.PLANNED , "annulée");

        mission.setStatus(MissionStatus.CANCELLED);
        return missionMapper.toDto(mission);
    }

    @Transactional
    public MissionDTO updateMission(Long id, UpdateMissionDTO dto) {
        Mission mission = getMission(id);
        requireStatus(mission, MissionStatus.PLANNED, "modifiée");

        if (!dto.dateFin().isAfter(dto.dateDebut())) {
            throw new IllegalStateException("La date de fin doit être après la date de début");
        }

        Vehicule vehicule = getVehicule(dto.vehiculeId());
        Driver driver = getDriver(dto.driverId());
        checkAvailability(vehicule, driver, dto.dateDebut(), dto.dateFin() , mission.getId());

        missionMapper.updateEntity(dto, mission);
        mission.setVehicule(vehicule);
        mission.setDriver(driver);
        return missionMapper.toDto(mission);
    }

    @Transactional
    public void deleteMission(Long id) {
        Mission mission = getMission(id);
        if(mission.getStatus()==MissionStatus.IN_PROGRESS){
            throw new IllegalStateException("Cannot delete a mission that is in progress");
        }
        missionRepository.delete(mission);
    }

    private void checkAvailability(Vehicule v, Driver d, LocalDateTime debut, LocalDateTime fin , Long excludeId) {
        if (v.getStatus() == VehicleStatus.IN_MAINTENANCE) { // adaptez à votre enum
            throw new IllegalStateException("Ce véhicule n'est pas disponible");
        }
        if (!d.isEnabled()) {
            throw new IllegalStateException("Ce chauffeur est désactivé");
        }
        if (missionRepository.existsVehiculeOverlap(v.getId(),excludeId , ACTIVE, debut, fin)) {
            throw new IllegalStateException("Ce véhicule a déjà une mission sur cette période");
        }
        if (missionRepository.existsDriverOverlap(d.getId(), excludeId ,ACTIVE, debut, fin)) {
            throw new IllegalStateException("Ce chauffeur a déjà une mission sur cette période");
        }
    }

    private void CheckAcces(Mission mission , User user){
        if(user instanceof Driver && !mission.getDriver().getId().equals(user.getId())){
            throw new AccessDeniedException("Vous n'avez pas accès à cette mission");
        }
    }
    private void requireStatus(Mission mission, MissionStatus expected, String action) {
        if (mission.getStatus() != expected) {
            throw new IllegalStateException("Cette mission ne peut pas être " + action
                    + " (statut actuel : " + mission.getStatus() + ")");
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
