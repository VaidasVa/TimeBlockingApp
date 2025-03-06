package digital.vaidas.timeblockingapp.mapper;

import digital.vaidas.timeblockingapp.model.User;
import digital.vaidas.timeblockingapp.repository.DAO.UserDAO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDAO toDAO(User user);

    User toUser(UserDAO userDao);
}
