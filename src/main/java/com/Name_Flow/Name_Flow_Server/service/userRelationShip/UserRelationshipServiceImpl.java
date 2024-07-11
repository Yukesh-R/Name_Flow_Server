package com.Name_Flow.Name_Flow_Server.service.userRelationShip;

import com.Name_Flow.Name_Flow_Server.dto.CreateRelationShipDTO;
import com.Name_Flow.Name_Flow_Server.dto.ResponseDTO;
import com.Name_Flow.Name_Flow_Server.emailsender.EmailService;
import com.Name_Flow.Name_Flow_Server.emailsender.EmailTemplateName;
import com.Name_Flow.Name_Flow_Server.entity.*;
import com.Name_Flow.Name_Flow_Server.repository.*;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRelationshipServiceImpl implements UserRelationshipService{

    private final UserAuthenticationRepository userAuthenticationRepository;
    private final WaitingUserAccessRelationDataRepository waitingUserAccessRelationDataRepository;
    private final WaitingUserSharedRelationDataRepository waitingUserSharedRelationDataRepository;
    private final UserSharedRelationDataRepository userSharedRelationDataRepository;
    private final UserAccessRelationDataRepository userAccessRelationDataRepository;
    private final EmailService emailService;
    private final UserDataRepository userDataRepository;

    @Override
    public ResponseDTO createRelationship(CreateRelationShipDTO createRelationShipDTO) throws MessagingException {

        if(!userAuthenticationRepository.existsByEmail(createRelationShipDTO.getEmailToRelation())) {
            return ResponseDTO.builder()
                    .status(false)
                    .message("Accessing User Not Found")
                    .build();
        }

        UserData providedUser = userDataRepository.findById(createRelationShipDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Main User Not Found"));

        UserAuthentication sharedUserAuth = userAuthenticationRepository.findByEmail(
                createRelationShipDTO.getEmailToRelation()
        );

        UserData sharedUserData = userDataRepository.findById(sharedUserAuth.getUserId())
                .orElseThrow(() -> new RuntimeException("Accessing User Not Found"));

        WaitingUserSharedRelationData waitingUserSharedRelationData=WaitingUserSharedRelationData.builder()
                .userId(createRelationShipDTO.getUserId())
                .sharedUserId(sharedUserAuth.getUserId())
                .sharedProjectId(createRelationShipDTO.getProjectId())
                .build();

        waitingUserSharedRelationDataRepository.save(waitingUserSharedRelationData);

        WaitingUserAccessRelationData waitingUserAccessRelationDataMain=WaitingUserAccessRelationData.builder()
                .userId(sharedUserAuth.getUserId())
                .accessUserId(createRelationShipDTO.getUserId())
                .accessProjectId(createRelationShipDTO.getProjectId())
                .build();

        waitingUserAccessRelationDataRepository.save(waitingUserAccessRelationDataMain);

        emailService.sendEmail(
                createRelationShipDTO.getEmailToRelation(),
                sharedUserData.getFirstName()+sharedUserData.getLastName(),
                providedUser.getFirstName()+providedUser.getLastName(),
                EmailTemplateName.ACCESS_NOTIFY,
                "",
                "Project Access Notification"
        );

        return ResponseDTO.builder()
                .status(true)
                .message("Access Request Sent Successfully")
                .build();
    }

    @Override
    public ResponseDTO relationAccessAcceptance(Long accepted_user_id) {
        List<WaitingUserSharedRelationData> sharedData = waitingUserSharedRelationDataRepository.findAllBySharedUserId(
                accepted_user_id
        );

        List<WaitingUserAccessRelationData> provideData = waitingUserAccessRelationDataRepository.findAllByUserId(
                accepted_user_id
        );
        for(WaitingUserSharedRelationData eachSharedData : sharedData){
            UserSharedRelationData sharedUser = UserSharedRelationData.builder()
                    .userId(eachSharedData.getUserId())
                    .sharedUserId(eachSharedData.getSharedUserId())
                    .sharedProjectId(eachSharedData.getSharedProjectId())
                    .build();
            userSharedRelationDataRepository.save(sharedUser);
            waitingUserSharedRelationDataRepository.deleteById(sharedUser.getId());
        }
        for(WaitingUserAccessRelationData eachProvideData : provideData){
            UserAccessRelationData provideUser = UserAccessRelationData.builder()
                    .userId(eachProvideData.getUserId())
                    .accessUserId(eachProvideData.getAccessUserId())
                    .accessProjectId(eachProvideData.getAccessProjectId())
                    .build();
            userAccessRelationDataRepository.save(provideUser);
            waitingUserAccessRelationDataRepository.deleteById(provideUser.getId());
        }

        return ResponseDTO.builder()
                .status(true)
                .message("Access Accepted Successfully")
                .build();
    }
}
