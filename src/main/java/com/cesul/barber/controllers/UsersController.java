package com.cesul.barber.controllers;

import com.cesul.barber.dtos.UserCreationDTO;
import com.cesul.barber.services.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuários", description = "Gerenciamento de usuários da barbearia")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    @Operation(summary = "Listar todos", description = "Retorna a lista de todos os usuários cadastrados.")
    public ResponseEntity<List<UserCreationDTO>> getAll() {
        List<UserCreationDTO> users = usersService.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    @Operation(summary = "Cadastrar usuário", description = "Cria e adiciona um novo usuário com um ID único.")
    public ResponseEntity<UserCreationDTO> create(@RequestBody UserCreationDTO user) {
        UserCreationDTO newUser = this.usersService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Substitui e atualiza os dados de um usuário identificando-o pelo ID.")
    public ResponseEntity<UserCreationDTO> update(@PathVariable UUID id, @RequestBody UserCreationDTO updatedUser) {
        return this.usersService.update(id, updatedUser).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/avatar")
    @Operation(summary = "Atualizar Avatar", description = "Atualiza o avatar de um usuário específico.")
    public ResponseEntity<UserCreationDTO> patchUser(@PathVariable UUID id, @RequestBody Map<String, Object> avatar) {
        return this.usersService.updateAvatar(id, avatar).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir usuário", description = "Remove um usuário da lista com base no seu ID.")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boolean removed = this.usersService.delete(id);
        if (removed) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/clear")
    @Operation(summary = "Limpar todos os usuários", description = "Remove todos os usuários cadastrados da lista.")
    public ResponseEntity<Void> clearUsers() {
        this.usersService.clear();
        return ResponseEntity.noContent().build();
    }
}