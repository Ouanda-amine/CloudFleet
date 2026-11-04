package cloudfleet.cloudfleet.Controllers;

import cloudfleet.cloudfleet.DTO.CreateDriverDTO;
import cloudfleet.cloudfleet.DTO.DriverDTO;
import cloudfleet.cloudfleet.DTO.UpdateDriverDTO;
import cloudfleet.cloudfleet.Services.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public DriverDTO create(@Valid @RequestBody CreateDriverDTO dto) {
        return driverService.CreateDriver(dto);
    }
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN' , 'DISPATCHER')")
    public List<DriverDTO> findAll(){
        return driverService.FindAllDrivers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER') or #id == principal.id")
    public DriverDTO findById(@PathVariable Long id) {
        return driverService.FindDriverById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public DriverDTO update(@PathVariable Long id, @Valid @RequestBody UpdateDriverDTO dto) {
        return driverService.update(id, dto);
    }

    @PatchMapping("/{id}/enabled")
    @PreAuthorize("hasRole('ADMIN')")
    public DriverDTO setEnabled(@PathVariable Long id, @RequestParam boolean enabled) {
        return driverService.setEnabled(id, enabled);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        driverService.delete(id);
    }
}
