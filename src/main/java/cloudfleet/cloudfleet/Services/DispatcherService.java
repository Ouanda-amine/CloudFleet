package cloudfleet.cloudfleet.Services;

import cloudfleet.cloudfleet.DTO.CreateDispatcherDTO;
import cloudfleet.cloudfleet.DTO.DispatcherDTO;
import cloudfleet.cloudfleet.DTO.UpdateDispatcherDTO;
import cloudfleet.cloudfleet.Mapper.DispatcherMapper;
import cloudfleet.cloudfleet.Model.Dispatcher;
import cloudfleet.cloudfleet.Repository.DispatcherRepository;
import cloudfleet.cloudfleet.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DispatcherService {

    private final DispatcherRepository dispatcherRepository;
    private final DispatcherMapper dispatcherMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public DispatcherDTO CreateDispatcher(CreateDispatcherDTO dto){
        if(userRepository.existsByEmail(dto.email())){
            throw new IllegalStateException("Email already exists");
        }

        Dispatcher dispatcher = dispatcherMapper.ToEntity(dto);
        dispatcher.setPassword(passwordEncoder.encode(dto.password()));
        dispatcher.setEnabled(true);
        return dispatcherMapper.ToDTO(dispatcherRepository.save(dispatcher));
    }

    @Transactional(readOnly = true)
    public List<DispatcherDTO> FindAllDispatchers(){
        return dispatcherMapper.ToDTOList(dispatcherRepository.findAll());
    }

    @Transactional(readOnly = true)
    public DispatcherDTO FindDispatcherById(Long id) {
        return dispatcherMapper.ToDTO(getDispatcher(id));

    }


    @Transactional
    public DispatcherDTO updateDispatcher(Long id , UpdateDispatcherDTO dto){

        Dispatcher dispatcher = getDispatcher(id);
        dispatcherMapper.updateEntity(dto,dispatcher);
        return dispatcherMapper.ToDTO(dispatcherRepository.save(dispatcher));
    }

    @Transactional
    public DispatcherDTO setEnabled(Long id, boolean enabled) {
        Dispatcher dispatcher = getDispatcher(id);
        dispatcher.setEnabled(enabled);
        return dispatcherMapper.ToDTO(dispatcherRepository.save(dispatcher));
    }

    @Transactional
    public void deleteDispatcher(Long id){
        Dispatcher dispatcher = getDispatcher(id);
        if(dispatcher.getMissions() != null && !dispatcher.getMissions().isEmpty() ){
            throw new IllegalStateException("Cannot delete dispatcher with assigned missions");
        }
        dispatcherRepository.delete(dispatcher);
    }


    private Dispatcher getDispatcher(Long id){
        return dispatcherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dispatcher not found"));
    }

}
