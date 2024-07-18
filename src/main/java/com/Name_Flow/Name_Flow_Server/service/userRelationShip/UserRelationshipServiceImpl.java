package com.Name_Flow.Name_Flow_Server.service.userRelationShip;

import com.Name_Flow.Name_Flow_Server.dto.*;
import com.Name_Flow.Name_Flow_Server.emailsender.EmailService;
import com.Name_Flow.Name_Flow_Server.emailsender.EmailTemplateName;
import com.Name_Flow.Name_Flow_Server.entity.*;
import com.Name_Flow.Name_Flow_Server.repository.*;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    private final ProjectDataRepository projectDataRepository;

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
                "",
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
    public ResponseDTO relationAccessAcceptance(AccessAcceptDTO accessAcceptDTO) {
        WaitingUserAccessRelationData waitingAccess = waitingUserAccessRelationDataRepository
                .findByUserIdAndAccessUserIdAndAccessProjectId(
                        accessAcceptDTO.getUsedId(),
                        accessAcceptDTO.getAccessProviderId(),
                        accessAcceptDTO.getAccessProjectId()
                );

        UserAccessRelationData userAccess = UserAccessRelationData.builder()
                .accessProjectId(waitingAccess.getAccessProjectId())
                .accessUserId(waitingAccess.getAccessUserId())
                .userId(waitingAccess.getUserId())
                .build();

        userAccessRelationDataRepository.save(userAccess);

        waitingUserAccessRelationDataRepository.delete(waitingAccess);

        WaitingUserSharedRelationData waitingShared = waitingUserSharedRelationDataRepository
                .findBySharedProjectIdAndSharedUserIdAndUserId(
                        accessAcceptDTO.getAccessProjectId(),
                        accessAcceptDTO.getUsedId(),
                        accessAcceptDTO.getAccessProviderId()
                );

        UserSharedRelationData userShared = UserSharedRelationData.builder()
                .sharedProjectId(waitingShared.getSharedProjectId())
                .sharedUserId(waitingShared.getSharedUserId())
                .userId(waitingShared.getUserId())
                .build();

        userSharedRelationDataRepository.save(userShared);

        waitingUserSharedRelationDataRepository.delete(waitingShared);

        return ResponseDTO.builder()
                .status(true)
                .message("Access Accepted Successfully")
                .build();
    }

    @Override
    @Transactional
    public ResponseDTO removeAccess(RemoveProjectAccessDTO removeProjectAccessDTO) throws MessagingException {

        userAccessRelationDataRepository.deleteByUserIdAndAccessProjectId(
                removeProjectAccessDTO.getAccess_remove_user_id(),
                removeProjectAccessDTO.getAccess_remove_project_id()
        );

        String toEmail=userAuthenticationRepository.findByUserId(
                removeProjectAccessDTO.getAccess_remove_user_id()
                ).getEmail();

        UserData accessGetter=userDataRepository.findById(
                removeProjectAccessDTO.getAccess_remove_user_id())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        String accessGetterName= accessGetter.getFirstName()+accessGetter.getLastName();

        UserData accessProvider=userDataRepository.findById(
                removeProjectAccessDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        String providerName= accessProvider.getFirstName()+accessProvider.getLastName();

        ProjectData accessDeniedProjectData=projectDataRepository.findById(
                removeProjectAccessDTO.getAccess_remove_project_id())
                .orElseThrow(() -> new RuntimeException("Project Not Found"));

        String accessDeniedProjectName=accessDeniedProjectData.getProjectName();

        emailService.sendEmail(
                toEmail,
                accessGetterName,
                providerName,
                accessDeniedProjectName,
                EmailTemplateName.ACCESS_REMOVE,
                "",
                "project Access Denied/Removed"
        );

        return ResponseDTO.builder()
                .status(true)
                .message("Access Removed Successfully")
                .build();
    }

    @Override
    public List<AccessProviderDTO> getAccessProviders(Long user_id) {
        List<WaitingUserAccessRelationData> accessUsers = waitingUserAccessRelationDataRepository.findAll();

        return accessUsers.stream()
                .filter(access -> Objects.equals(access.getUserId(), user_id))
                .map((WaitingUserAccessRelationData access) -> {
                    UserData userProvide = userDataRepository.findById(access.getAccessUserId())
                            .orElseThrow(() -> new RuntimeException("User Not Found"));
                    ProjectData accessProject = projectDataRepository.findById(access.getAccessProjectId())
                            .orElseThrow(() -> new RuntimeException("Project Not Found"));
                    return AccessProviderDTO.builder()
                            .accessProviderId(userProvide.getId())
                            .accessProjectId(accessProject.getId())
                            .accessProviderName(userProvide.getFirstName()+userProvide.getLastName())
                            .accessProjectName(accessProject.getProjectName())
                            .build();
                })
                .toList();
    }
}
