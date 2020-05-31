package edp.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weshare.base.common.ApplicationContextManage;
import com.weshare.base.common.util.BaseSessionHandle;
import com.weshare.base.common.util.ReadConf;
import com.weshare.base.component.sessionmanager.bean.BaseUserInfo;
import com.weshare.base.sso.LocalLogin;

import edp.davinci.model.User;
import edp.davinci.service.UserService;

/**
 * 用户第一次登陆
 * */
public class AuthCenterLoginImpl implements LocalLogin {
    Logger logger = LoggerFactory.getLogger(AuthCenterLoginImpl.class.getName());
    
    @Override
    public BaseUserInfo login(BaseUserInfo userInfo, int isWeb, String SSOtokenId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    	/*
    	UserService userService = ApplicationContextManage.getBean("userService", UserService.class);
    	String username = userInfo.gettSysUser().getUserCode();
    	User user = userService.getByUsername(username);
    	*/
        return userInfo;
    }

    @Override
    public void logout(int isWeb, String SSOtokenId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    	BaseSessionHandle.setCookieValue(httpServletRequest, httpServletResponse, ReadConf.getProperty("sso.properties","tokenId"), "", "UTF-8", -1, true, "", true, "");
    }

    @Override
    public void setLastOperateTime(String SSOtokenId, BaseUserInfo userInfo, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        
    }
}
