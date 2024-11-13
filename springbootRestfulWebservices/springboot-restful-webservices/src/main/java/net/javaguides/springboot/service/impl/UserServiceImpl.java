package net.javaguides.springboot.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.mapper.UserMapper;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        // Convert UserDto info user jpa entity
        //User user = UserMapper.mapToUser(userDto); -> varianta fara modelMapper
        User user = modelMapper.map(userDto, User.class);
        User savedUser = userRepository.save(user);

        // Convert user jpa entity into UserDto
        //return UserMapper.mapToUserDto(savedUser); -> varianta fara modelMapper
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.get();
        //return UserMapper.mapToUserDto(user); -> varianta fara modelMapper
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users =  userRepository.findAll();
        //return users.stream().map(UserMapper::mapToUserDto).toList();-> varianta fara modelMapper
        return users.stream().map(user -> modelMapper.map(user, UserDto.class)).toList();
    }

    @Override
    public UserDto updateUser(UserDto user) {
        User existingUser = userRepository.findById(user.getId()).get();
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        User updatedUser = userRepository.save(existingUser);
        //return UserMapper.mapToUserDto(updatedUser); -> varianta fara modelMapper
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
