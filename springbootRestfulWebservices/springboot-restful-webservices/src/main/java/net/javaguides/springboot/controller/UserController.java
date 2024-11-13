package net.javaguides.springboot.controller;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private UserService userService;

    /** build create User REST API */
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user){
        UserDto savedUser = userService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    /** build get user by id REST API
     * localhost:8080/api/users/1 */
    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId){
        UserDto user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /** build Get All Users REST API
     * localhost:8080/api/users */
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /** build Update User REST API
     * localhost:8080/api/users/1 */
    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId,
                                           @RequestBody UserDto userDto){
        userDto.setId(userId);
        UserDto updatedUser = userService.updateUser(userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    /** build Delete User REST API */
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
    }
}
