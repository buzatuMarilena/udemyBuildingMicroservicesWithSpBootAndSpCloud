package net.javaguides.springboot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "CRUD REST APIs for User Resource",
        description = "create user, update user, get user, get all users, delete user"
)
@RestController
@AllArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private UserService userService;

    /**
     * build create User REST API
     */
    @Operation(
            summary = "Create User Rest Api",
            description = "save user in db"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user) {
        UserDto savedUser = userService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    /**
     * build get user by id REST API
     * localhost:8080/api/users/1
     */
    @Operation(
            summary = "Get User by ID Rest Api",
            description = "Get user by Id from db"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId) {
        UserDto user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * build Get All Users REST API
     * localhost:8080/api/users
     */
    @Operation(
            summary = "Get all users Rest Api",
            description = "Get all users from db"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * build Update User REST API
     * localhost:8080/api/users/1
     */
    @Operation(
            summary = "Update user Rest Api",
            description = "Update a particular user in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId,
                                              @RequestBody @Valid UserDto userDto) {
        userDto.setId(userId);
        UserDto updatedUser = userService.updateUser(userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    /**
     * build Delete User REST API
     */
    @Operation(
            summary = "Delete user Rest Api",
            description = "Delete a particular user from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
    }

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
//                                                                        WebRequest webRequest){
//        ErrorDetails errorDetails = new ErrorDetails(
//                LocalDateTime.now(),
//                exception.getMessage(),
//                webRequest.getDescription(false),
//                "USER_NOT_FOUND"
//        );
//        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
//    }
}
