package com.Name_Flow.Name_Flow_Server.service.user.update;

import com.Name_Flow.Name_Flow_Server.dto.ResponseDTO;
import com.Name_Flow.Name_Flow_Server.dto.UpdateUserDetailsDTO;
import com.Name_Flow.Name_Flow_Server.entity.UserData;
import com.Name_Flow.Name_Flow_Server.repository.UserDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateServiceImple implements UpdateService {

    private final UserDataRepository userDataRepository;

    @Override
    public ResponseDTO updateUser(UpdateUserDetailsDTO updateUserDetailsDTO) {
        UserData userData = userDataRepository.findById(updateUserDetailsDTO.getUserId()).orElseThrow();
        userData.setFirstName(updateUserDetailsDTO.getFirstName());
        userData.setLastName(updateUserDetailsDTO.getLastName());
        userData.setGender(updateUserDetailsDTO.getGender());
        userData.setAge(updateUserDetailsDTO.getAge());
        userData.setMobileNumber(updateUserDetailsDTO.getMobileNumber());
        userDataRepository.save(userData);
        return ResponseDTO.builder().status(true)
                .message("Updated Successfully").build();
    }

    @Override
    public ResponseDTO deleteUser(Long userId) {
        userDataRepository.deleteById(userId);
        return  ResponseDTO.builder().status(true)
                .message("Deleted Successfully").build();
    }
}
