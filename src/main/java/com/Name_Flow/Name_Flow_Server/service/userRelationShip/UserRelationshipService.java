package com.Name_Flow.Name_Flow_Server.service.userRelationShip;

import com.Name_Flow.Name_Flow_Server.dto.*;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserRelationshipService {

    ResponseDTO createRelationship(CreateRelationShipDTO createRelationShipDTO) throws MessagingException;

    ResponseDTO relationAccessAcceptance(AccessAcceptDTO accessAcceptDTO);

    ResponseDTO removeAccess(RemoveProjectAccessDTO removeProjectAccessDTO) throws MessagingException;

    List<AccessProviderDTO> getAccessProviders(Long user_id);

}
