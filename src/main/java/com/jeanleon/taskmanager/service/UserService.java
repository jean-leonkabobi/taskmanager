// UserService.java
package com.jeanleon.taskmanager.service;

import com.jeanleon.taskmanager.dto.response.UserProfileResponse;
import com.jeanleon.taskmanager.exception.ResourceNotFoundException;
import com.jeanleon.taskmanager.model.User;
import com.jeanleon.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Récupère le profil d'un utilisateur par son ID
     */
    public UserProfileResponse getUserProfile(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + userId));

        return mapToUserProfileResponse(user);
    }

    /**
     * Met à jour les informations du profil utilisateur
     */
    @Transactional
    public UserProfileResponse updateProfile(String userId, String fullName, String email) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + userId));

        // Vérifier si l'email est déjà utilisé par un autre utilisateur
        if (email != null && !email.equals(user.getEmail())) {
            if (userRepository.existsByEmail(email)) {
                throw new RuntimeException("Cet email est déjà utilisé par un autre compte");
            }
            user.setEmail(email);
        }

        if (fullName != null && !fullName.isEmpty()) {
            user.setFullName(fullName);
        }

        User updatedUser = userRepository.save(user);
        return mapToUserProfileResponse(updatedUser);
    }

    /**
     * Supprime un compte utilisateur et toutes ses tâches associées
     */
    @Transactional
    public void deleteAccount(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + userId));

        userRepository.delete(user);
    }

    /**
     * Vérifie si un utilisateur existe
     */
    public boolean userExists(String userId) {
        return userRepository.existsById(userId);
    }

    /**
     * Récupère l'entité User complète (pour usage interne)
     */
    public User getUserEntity(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + userId));
    }

    /**
     * Mappe l'entité User vers le DTO UserProfileResponse
     */
    private UserProfileResponse mapToUserProfileResponse(User user) {
        return UserProfileResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .totalTasks((long) user.getTasks().size())
                .build();
    }
}