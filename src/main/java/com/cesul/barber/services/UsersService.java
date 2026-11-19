package com.cesul.barber.services;

import com.cesul.barber.dtos.UserCreationDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsersService {

    private final List<UserCreationDTO> users = new ArrayList<>();

    public UserCreationDTO create(UserCreationDTO user) {
        user.setId(UUID.randomUUID());
        this.users.add(user);
        return user;
    }

    public List<UserCreationDTO> findAll() {
        return this.users;
    }

    public Optional<UserCreationDTO> findById(UUID id) {
        return this.users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    public Optional<UserCreationDTO> update(UUID id, UserCreationDTO updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                updatedUser.setId(id);
                users.set(i, updatedUser);
                return Optional.of(updatedUser);
            }
        }
        return Optional.empty();
    }

    public Optional<UserCreationDTO> updateAvatar(UUID id, Map<String, Object> request) {
        Optional<UserCreationDTO> userOptional = findById(id);

        if (userOptional.isPresent()) {
            UserCreationDTO existingUser = userOptional.get();
            if (request.containsKey("avatarUrl")) {
                existingUser.setAvatarUrl((String) request.get("avatarUrl"));
            }
            return Optional.of(existingUser);
        }

        return Optional.empty();
    }

    public boolean delete(UUID id) {
        return this.users.removeIf(u -> u.getId().equals(id));
    }

    public void clear() {
        this.users.clear();
    }
}