package com.Name_Flow.Name_Flow_Server.service.userRelationShip;

import com.Name_Flow.Name_Flow_Server.dto.CreateRelationShipDTO;
import com.Name_Flow.Name_Flow_Server.dto.ResponseDTO;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface UserRelationshipService {

    ResponseDTO createRelationship(CreateRelationShipDTO createRelationShipDTO) throws MessagingException;

    ResponseDTO relationAccessAcceptance(Long accepted_used_id);

}
