package ru.kata.spring.boot_security.demo.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.kata.spring.boot_security.demo.exceptionHandlers.NoSuchUserException;
import ru.kata.spring.boot_security.demo.exceptionHandlers.UserNotCreatedException;
import ru.kata.spring.boot_security.demo.exceptionHandlers.UserNotUpdateException;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import javax.ws.rs.client.Entity;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

//    private final ModelMapper modelMapper;

    @Autowired
    public AdminRestController(ModelMapper modelMapper, RoleService roleService, UserService userService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
//        this.modelMapper = modelMapper;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> showAllUsers(Principal principal) {
        List<User> usersList = userService.findAll();
        return ResponseEntity.ok(usersList);
    }

    @GetMapping("/user-profile/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping("/showAccount")
    public ResponseEntity<User> showInfoUserByUsername(Principal principal) {
        return ResponseEntity.ok(userService.findByUsername(principal.getName()));
    }

    @GetMapping("/roles")
    public ResponseEntity<Collection<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<Collection<Role>> getRole(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(userService.findUserById(userId).getRoles());
    }

    @PostMapping("/users")
    public ResponseEntity<User> saveNewUser(@RequestBody @Valid User newUserFromClient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder inputUserErrors = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                inputUserErrors.append(error.getDefaultMessage()).append("; \n");
                throw new UserNotCreatedException("Пользователь не создан: " + inputUserErrors);
            }
        }
        userService.saveUser(newUserFromClient);
        return ResponseEntity.ok(newUserFromClient);
    }

    /**
     * Возмоожно, тут не надо проверкку на валидность
     */
    @PatchMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody @Valid User updateUserFromClient,
                                           @PathVariable("id") Long id,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder inputUserErrors = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                inputUserErrors.append(error.getDefaultMessage()).append("; \n");
                throw new UserNotUpdateException("Пользователь не обновлен: " + inputUserErrors);
            }
        }
        userService.updateUser(updateUserFromClient, id);
        return ResponseEntity.ok(updateUserFromClient);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
        User userForDelete = userService.findUserById(id);
        if (userForDelete == null) {
            throw new NoSuchUserException("Пользователя с таким id нет в бд");
        }
        userService.deleteUserById(id);
        return ResponseEntity.ok(userService.findUserById(id));
    }

}
