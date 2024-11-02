package com.example.test.service;

import com.example.test.model.Role;
import com.example.test.model.User;
import com.example.test.repositories.RoleRepository;
import com.example.test.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createUser(String username, String rawPassword) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRoles(new HashSet<>()); // Инициализация ролей
        userRepository.save(user);
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void addUser(User user) {
        saveUser(user);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void assignRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.println("User not found: " + username);
            return; // Если пользователь не найден, выходим из метода
        }

        Role role = roleRepository.findByName("ROLE_" + roleName); // Добавляем префикс ROLE_
        if (role == null) {
            System.out.println("Role not found: " + "ROLE_" + roleName);
            return; // Если роль не найдена, выходим из метода
        }

        // Удаляем все старые роли
        user.getRoles().clear(); // Удаляем все роли
        user.getRoles().add(role); // Добавляем новую роль
        userRepository.save(user); // Сохраняем изменения пользователя
        System.out.println("Role " + roleName + " assigned to user " + username);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

