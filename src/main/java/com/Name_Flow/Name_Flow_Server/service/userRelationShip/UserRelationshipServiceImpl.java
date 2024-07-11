package com.Name_Flow.Name_Flow_Server.service.userRelationShip;

import com.Name_Flow.Name_Flow_Server.dto.CreateRelationShipDTO;
import com.Name_Flow.Name_Flow_Server.dto.ResponseDTO;
import com.Name_Flow.Name_Flow_Server.repository.UserAuthenticationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRelationshipServiceImpl implements UserRelationshipService{

    private final UserAuthenticationRepository userAuthenticationRepository;

    @Override
    public ResponseDTO createRelationship(CreateRelationShipDTO createRelationShipDTO) {

        if(!userAuthenticationRepository.existsByEmail(createRelationShipDTO.getEmailToRelation())) {
            return ResponseDTO.builder()
                    .status(false)
                    .message("Accessing User Not Found")
                    .build();
        }
        return ResponseDTO.builder()
                .status(true)
                .message("Access Request Sent Successfully")
                .build();
    }
}
