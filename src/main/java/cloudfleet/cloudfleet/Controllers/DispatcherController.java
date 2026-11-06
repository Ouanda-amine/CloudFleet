package cloudfleet.cloudfleet.Controllers;

import cloudfleet.cloudfleet.DTO.CreateDispatcherDTO;
import cloudfleet.cloudfleet.DTO.DispatcherDTO;
import cloudfleet.cloudfleet.DTO.UpdateDispatcherDTO;
import cloudfleet.cloudfleet.Services.DispatcherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dispatchers")
@RequiredArgsConstructor

public class DispatcherController {

    private final DispatcherService dispatcherService;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public DispatcherDTO create(@Valid @RequestBody CreateDispatcherDTO dto){
        return dispatcherService.CreateDispatcher(dto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<DispatcherDTO> getAll(){
        return dispatcherService.FindAllDispatchers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('DISPATCHER') and #id == principal.id)")
    public DispatcherDTO getById(@PathVariable Long id){
        return dispatcherService.FindDispatcherById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public DispatcherDTO update(@PathVariable Long id, @Valid @RequestBody UpdateDispatcherDTO dto) {
        return dispatcherService.updateDispatcher(id, dto);
    }

    @PatchMapping("/{id}/enabled")
    @PreAuthorize("hasRole('ADMIN')")
    public DispatcherDTO setEnabled(@PathVariable Long id, @RequestParam boolean enabled) {
        return dispatcherService.setEnabled(id, enabled);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        dispatcherService.deleteDispatcher(id);
    }
}
