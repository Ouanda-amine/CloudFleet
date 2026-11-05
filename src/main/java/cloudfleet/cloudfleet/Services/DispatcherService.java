package cloudfleet.cloudfleet.Services;

import cloudfleet.cloudfleet.DTO.CreateDispatcherDTO;
import cloudfleet.cloudfleet.DTO.DispatcherDTO;
import cloudfleet.cloudfleet.Mapper.DispatcherMapper;
import cloudfleet.cloudfleet.Model.Dispatcher;
import cloudfleet.cloudfleet.Repository.DispatcherRepository;
import cloudfleet.cloudfleet.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new RuntimeException("Email already exists");
        }

        Dispatcher dispatcher = dispatcherMapper.ToEntity(dto);
        dispatcher.setPassword(passwordEncoder.encode(dto.password()));
        dispatcher.setEnabled(true);
        return dispatcherMapper.ToDTO(dispatcherRepository.save(dispatcher));
    }


}
