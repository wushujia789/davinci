package edp.core.utils;

import edp.davinci.dto.shareDto.ShareInfo;
import edp.davinci.model.User;

/**
 * 
 * @author wushujia
 *
 */
public class SSOUtils {
	private static String defaultUserCode = "UnintegratedSSO" ;
	/**
	 * 获取鉴权中心userCode
	 * @return
	 */
	public static String getUserCode() {
		return defaultUserCode ;  // TODO 集成SSO只需要修改此方法即可
	}
	/**
	 * 
	 * @param shareInfo
	 * @return
	 */
	public static boolean checkUser(ShareInfo shareInfo) {
		User shareUser = shareInfo.getShareUser();
		String userCode = shareUser.getUserCode() ;
		if(userCode!=null&&!"".equals(userCode)&&!"null".equalsIgnoreCase(userCode)) {
			String sessionUserCode = getUserCode() ;
			if(defaultUserCode.equals(sessionUserCode)) {
				return true ;
			}else {
				return userCode.equals(getUserCode());
			}
		}else {
			return true ;
		}
	}
}
