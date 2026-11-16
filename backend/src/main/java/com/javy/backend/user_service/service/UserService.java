package com.javy.backend.user_service.service;

import com.javy.backend.shared.exceptions.NotFoundException;
import com.javy.backend.user_service.dto.request.UpdateProfileRequest;
import com.javy.backend.user_service.dto.response.UserResponse;
import com.javy.backend.user_service.model.User;
import com.javy.backend.user_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves the profile information of the specified user.
     *
     * @param email the email of the authenticated user
     * @return the user's profile
     * @throws NotFoundException if the user does not exist
     */
    public UserResponse getProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found."));

        return UserResponse.from(user);
    }

    /**
     * Updates the non-null profile fields of the specified user.
     *
     * @param email the email of the authenticated user
     * @param request the profile update request
     * @return the updated user's profile
     * @throws NotFoundException if the user does not exist
     */
    @Transactional
    public UserResponse updateProfile(String email, UpdateProfileRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found."));

        if (request.firstName() != null) {
            user.setFirstName(request.firstName().trim());
        }

        if (request.lastName() != null) {
            user.setLastName(request.lastName().trim());
        }

        userRepository.save(user);

        return UserResponse.from(user);
    }
}
