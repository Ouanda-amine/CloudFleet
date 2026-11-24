package cloudfleet.cloudfleet.Controllers;


import cloudfleet.cloudfleet.DTO.CreatMissionDTO;
import cloudfleet.cloudfleet.DTO.MissionDTO;
import cloudfleet.cloudfleet.DTO.UpdateMissionDTO;
import cloudfleet.cloudfleet.Model.User;
import cloudfleet.cloudfleet.Services.MissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/missions")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN','DISPATCHER')")
    public MissionDTO create(@Valid @RequestBody CreatMissionDTO dto , @AuthenticationPrincipal User currentuser){
        return missionService.createMission(dto,currentuser);
    }

    @GetMapping
    public List<MissionDTO> getAll(@AuthenticationPrincipal User currentuser){
        return missionService.getAllMissions(currentuser);
    }

    @GetMapping("/{id}")
    public MissionDTO getById(@PathVariable Long id , @AuthenticationPrincipal User currentuser){
        return missionService.getMissionById(id,currentuser);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DISPATCHER')")
    public MissionDTO update(@PathVariable Long id , @Valid @RequestBody UpdateMissionDTO dto){
        return missionService.updateMission(id,dto);
    }


    @PatchMapping("/{id}/start")
    public MissionDTO start(@PathVariable Long id , @AuthenticationPrincipal User currentuser){
        return missionService.startMission(id,currentuser);
    }

    @PatchMapping("/{id}/complete")
    public MissionDTO complete(@PathVariable Long id , @AuthenticationPrincipal User currentuser) {
        return missionService.completeMission(id, currentuser);
    }

    @PatchMapping("/{id}/")
    @PreAuthorize("hasAnyRole('ADMIN','DISPATCHER')")
    public MissionDTO cancel(@PathVariable Long id) {

        return missionService.cancelMission(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        missionService.deleteMission(id);
    }





}
