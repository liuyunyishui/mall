package com.mall.service;

import com.mall.common.Const;
import com.mall.common.ResponseCode;
import com.mall.common.ServerResponse;
import com.mall.pojo.User;

import javax.servlet.http.HttpSession;

/**
 * Created by ly on 2018/3/23.
 */
public class UserUtil {

    private UserUtil() {
    }

    public static ServerResponse checkLogin(User user) {
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        return ServerResponse.createBySuccess();
    }

    public static ServerResponse checkAdminRole(User user) {
        if (user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    public static ServerResponse validAdminLogin(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        ServerResponse response = checkLogin(user);
        if (!response.isSuccess()) {
            return response;
        }
        if (!checkAdminRole(user).isSuccess()) {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
        return ServerResponse.createBySuccess();
    }
}
