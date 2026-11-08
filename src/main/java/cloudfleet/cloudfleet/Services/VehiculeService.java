package cloudfleet.cloudfleet.Services;

import cloudfleet.cloudfleet.DTO.CreateVehiculeDTO;
import cloudfleet.cloudfleet.DTO.UpdateVehiculePositionDTO;
import cloudfleet.cloudfleet.DTO.VehiculeDTO;
import cloudfleet.cloudfleet.Mapper.VehiculeMapper;
import cloudfleet.cloudfleet.Model.VehicleStatus;
import cloudfleet.cloudfleet.Model.Vehicule;
import cloudfleet.cloudfleet.Repository.VehiculeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeService {

    private final VehiculeMapper vehiculeMapper;
    private final VehiculeRepository vehiculeRepository;


    @Transactional
    public VehiculeDTO createVehicule(CreateVehiculeDTO dto){
        if(vehiculeRepository.existsByMatricule(dto.matricule())){
            throw new IllegalStateException("Vehicule with this matricule already exists");
        }
        Vehicule vehicule = vehiculeMapper.toEntity(dto);
        vehicule.setStatus(VehicleStatus.AVAILABLE);
        return vehiculeMapper.toDto(vehiculeRepository.save(vehicule));

    }

    @Transactional(readOnly = true)
    public List<VehiculeDTO> getAllVehicules(){
        return vehiculeMapper.toDtoList(vehiculeRepository.findAll());

    }

    @Transactional(readOnly = true)
    public VehiculeDTO getVehiculeById(Long id){
        return vehiculeMapper.toDto(getVehiculeid(id));
    }

    @Transactional
    public VehiculeDTO updateVehiculePosition(Long id , UpdateVehiculePositionDTO dto){
        Vehicule vehicule = getVehiculeid(id);
        vehiculeMapper.updateLocation(dto , vehicule);
        return vehiculeMapper.toDto(vehiculeRepository.save(vehicule));
    }
    @Transactional
    public VehiculeDTO updateStatus(Long id, VehicleStatus status) {
        Vehicule vehicule = getVehiculeid(id);
        vehicule.setStatus(status);
        return vehiculeMapper.toDto(vehiculeRepository.save(vehicule));
    }

    @Transactional
    public void deleteVehicule(Long id) {
        Vehicule vehicule = getVehiculeid(id);
        if (vehicule.getMissions() != null && !vehicule.getMissions().isEmpty()) {
            throw new IllegalStateException(
                    "Ce véhicule a des missions : changez son statut au lieu de le supprimer");
        }
        vehiculeRepository.delete(vehicule);
    }

    private Vehicule getVehiculeid(Long id){
        return vehiculeRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Vehicule not found with id: " + id));
    }
}
