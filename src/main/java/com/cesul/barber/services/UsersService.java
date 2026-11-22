package com.cesul.barber.services;

import com.cesul.barber.dtos.UserCreationDTO;
import com.cesul.barber.entity.Users;
import com.cesul.barber.repository.ClientsRepository;
import com.cesul.barber.utils.Mapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsersService {

    private final ClientsRepository repository;

    public UsersService(ClientsRepository repository) {
        this.repository = repository;
    }

    public UserCreationDTO create(UserCreationDTO user) {

        Users newUser = new Users(user.getName(), user.getEmail(), user.getAvatarUrl());

        Users userSaved = repository.save(newUser);

        return Mapper.usersToUserCreationDTO(userSaved);
    }

    public List<UserCreationDTO> findAll() {

        List<Users> users = repository.findAll();

        List<UserCreationDTO> listUser = new ArrayList<>();

        for (Users user: users){
            listUser.add(Mapper.usersToUserCreationDTO(user));
        }

        return listUser;
    }

    public Optional<UserCreationDTO> findById(UUID id) {
        Optional<Users> user = repository.findById(id);

        if (user.isPresent()){
            UserCreationDTO dto = Mapper.usersToUserCreationDTO(user.get());
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    public Optional<UserCreationDTO> update(UUID id, UserCreationDTO updatedUser) {

        Optional<Users> users = repository.findById(id);

        if(users.isEmpty()){
            return Optional.empty();
        }

        users.get().setName(updatedUser.getName());
        users.get().setAvatarUrl(updatedUser.getAvatarUrl());
        users.get().setEmail(updatedUser.getEmail());

        repository.save(users.get());

        return Optional.empty();
    }

    public Optional<UserCreationDTO> updateAvatar(UUID id, Map<String, Object> request) {
        Optional<Users> userOptional = repository.findById(id);

        if (userOptional.isPresent()) {

            Users existingUser = userOptional.get();

            if (request.containsKey("avatarUrl")) {
                existingUser.setAvatarUrl((String) request.get("avatarUrl"));
            }

            repository.save(existingUser);

            return Optional.of(new UserCreationDTO(id, existingUser.getName(), existingUser.getEmail(), existingUser.getAvatarUrl()));
        }

        return Optional.empty();
    }

    public boolean delete(UUID id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public void clear() {
        repository.deleteAll();
    }
}