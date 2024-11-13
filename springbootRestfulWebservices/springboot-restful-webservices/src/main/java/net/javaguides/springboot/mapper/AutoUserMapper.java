package net.javaguides.springboot.mapper;

import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoUserMapper {

    AutoUserMapper MAPPER = Mappers.getMapper(AutoUserMapper.class);
    /**
     * @Mapping(source = "email", target= "emailAddress")
     * ex:
     * daca au nume diferite in user - email
     * si userDto - userAddress adaug adnotarea de mai sus
     */
    UserDto mapToUserDto(User user);

    User mapToUser(UserDto userDto);
}
