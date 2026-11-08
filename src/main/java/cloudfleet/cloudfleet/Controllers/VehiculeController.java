package cloudfleet.cloudfleet.Controllers;

import cloudfleet.cloudfleet.DTO.CreateVehiculeDTO;
import cloudfleet.cloudfleet.DTO.UpdateVehiculePositionDTO;
import cloudfleet.cloudfleet.DTO.VehiculeDTO;
import cloudfleet.cloudfleet.Model.VehicleStatus;
import cloudfleet.cloudfleet.Services.VehiculeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicules")
public class VehiculeController {

    private final VehiculeService vehiculeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public VehiculeDTO create(@Valid @RequestBody CreateVehiculeDTO dto){
        return vehiculeService.createVehicule(dto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    public List<VehiculeDTO> getAllVehicules(){
        return vehiculeService.getAllVehicules();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    public VehiculeDTO getVehiculeById(@PathVariable Long id){
        return vehiculeService.getVehiculeById(id);
    }

    @PatchMapping("/{id}/position")
    @PreAuthorize("hasAnyRole('ADMIN', 'DRIVER')")
    public VehiculeDTO updatePosition(@PathVariable Long id,
                                      @Valid @RequestBody UpdateVehiculePositionDTO dto) {
        return vehiculeService.updateVehiculePosition(id, dto);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public VehiculeDTO updateStatus(@PathVariable Long id, @RequestParam VehicleStatus status) {
        return vehiculeService.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        vehiculeService.deleteVehicule(id);
    }


}
