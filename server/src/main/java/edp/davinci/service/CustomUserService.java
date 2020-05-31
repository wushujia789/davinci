package edp.davinci.service;

import edp.core.exception.ServerException;
import edp.davinci.dto.userDto.UserRegist;
import edp.davinci.model.User;

public interface CustomUserService {
	public User regist(UserRegist userRegist) throws ServerException ;

}
