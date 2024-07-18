package com.Name_Flow.Name_Flow_Server.service.user.update;

import com.Name_Flow.Name_Flow_Server.dto.ResponseDTO;
import com.Name_Flow.Name_Flow_Server.dto.UpdateUserDetailsDTO;
import org.springframework.stereotype.Service;

@Service
public interface UpdateService {

    ResponseDTO updateUser(UpdateUserDetailsDTO updateUserDetailsDTO);

    ResponseDTO deleteUser(Long userId);
}
