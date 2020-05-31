package edp.davinci.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edp.core.exception.ServerException;
import edp.davinci.core.enums.UserOrgRoleEnum;
import edp.davinci.dao.OrganizationMapper;
import edp.davinci.dao.RelUserOrganizationMapper;
import edp.davinci.dao.UserMapper;
import edp.davinci.dto.userDto.UserRegist;
import edp.davinci.model.Organization;
import edp.davinci.model.RelUserOrganization;
import edp.davinci.model.User;
import edp.davinci.service.CustomUserService;
import edp.davinci.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("customUserServiceImpl")
public class CustomUserServiceImpl extends BaseEntityService implements CustomUserService{
	@Autowired
	private UserService userService ;
	@Autowired
    private UserMapper userMapper;
    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
    private RelUserOrganizationMapper relUserOrganizationMapper;
    
	@Override
	public User regist(UserRegist userRegist) throws ServerException {
		User user = userService.regist(userRegist);
		activateUser(user.getUsername());
    	return user;
    }
	
	@Transactional
    public void activateUser(String username) {
		User user = userService.getByUsername(username);
		// 已经激活，不需要再次激活
		if (!user.getActive()) {
			// 验证激活token
			user.setActive(true);
			user.setUpdateTime(new Date());
			userMapper.activeUser(user);

			String orgName = user.getUsername() + "'s Organization";
			// 激活成功，创建默认Orgnization
			Organization organization = new Organization(orgName, null, user.getId());
			organizationMapper.insert(organization);

			// 关联用户和组织，创建人是组织的owner
			RelUserOrganization relUserOrganization = new RelUserOrganization(organization.getId(), user.getId(),
					UserOrgRoleEnum.OWNER.getRole());
			relUserOrganizationMapper.insert(relUserOrganization);
		}
    }

}
