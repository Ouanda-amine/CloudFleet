package cloudfleet.cloudfleet.Services;

import cloudfleet.cloudfleet.DTO.CreateDriverDTO;
import cloudfleet.cloudfleet.DTO.DriverDTO;
import cloudfleet.cloudfleet.DTO.UpdateDriverDTO;
import cloudfleet.cloudfleet.Mapper.DriverMapper;
import cloudfleet.cloudfleet.Model.Driver;
import cloudfleet.cloudfleet.Model.DriverStatus;
import cloudfleet.cloudfleet.Repository.DriverRepository;
import cloudfleet.cloudfleet.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public DriverDTO CreateDriver(CreateDriverDTO DTO){

        if(userRepository.existsByEmail(DTO.email())){
            throw new IllegalStateException("Email already exists");
        }

        Driver driver = driverMapper.ToEntity(DTO);
        driver.setPassword(passwordEncoder.encode(DTO.password()));
        driver.setEnabled(true);
        driver.setStatus(DriverStatus.AVAILABLE);

        return driverMapper.ToDTO(driverRepository.save(driver));
    }

    @Transactional(readOnly = true)
    public List<DriverDTO> FindAllDrivers(){
        return driverMapper.ToDTOList(driverRepository.findAll());
    }

    @Transactional(readOnly = true)
    public DriverDTO FindDriverById(Long id){
        return driverMapper.ToDTO(getDriver(id));

    }

    @Transactional
    public DriverDTO update(Long id, UpdateDriverDTO dto) {
        Driver driver = getDriver(id);
        driverMapper.updateEntity(dto, driver);
        return driverMapper.ToDTO(driverRepository.save(driver));
    }

    @Transactional
    public DriverDTO setEnabled(Long id, boolean enabled) {
        Driver driver = getDriver(id);
        driver.setEnabled(enabled);
        return driverMapper.ToDTO(driverRepository.save(driver));
    }

    @Transactional
    public void delete(Long id) {
        Driver driver = getDriver(id);
        if (driver.getMissions() != null && !driver.getMissions().isEmpty()) {
            throw new IllegalStateException(
                    "Ce chauffeur a des missions : désactivez-le au lieu de le supprimer");
        }
        driverRepository.delete(driver);
    }

    private Driver getDriver(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chauffeur introuvable : " + id));
    }

}
