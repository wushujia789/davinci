package edp.davinci.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weshare.base.common.Utility;

import edp.core.annotation.AuthIgnore;
import edp.davinci.common.controller.BaseController;
import edp.davinci.core.common.Constants;
import edp.davinci.core.common.ResultMap;
import edp.davinci.dto.userDto.UserRegist;
import edp.davinci.model.User;
import edp.davinci.service.CustomUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "/customUser", tags = "customUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@ApiResponses(@ApiResponse(code = 404, message = "sources not found"))
@Slf4j
@RestController
@RequestMapping(value = Constants.BASE_API_PATH + "/customUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CustomUserController extends BaseController{
	@Autowired
    private CustomUserService customUserService;

	/**
     * 用户注册 用户打通(内网调用)
     *
     * @param userRegist
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "insert user")
    @AuthIgnore
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity regist(@Valid @RequestBody UserRegist userRegist, @ApiIgnore BindingResult bindingResult) {
    	// 判断是否内网
    	Utility.checkInternalIp();
        if (bindingResult.hasErrors()) {
            ResultMap resultMap = new ResultMap().fail().message(bindingResult.getFieldErrors().get(0).getDefaultMessage());
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }
        User user = customUserService.regist(userRegist);
        return ResponseEntity.ok(new ResultMap().success().payload(user));
    }


}
