package com.mall.controller.backend;

import com.mall.common.ServerResponse;
import com.mall.service.ICategoryService;
import com.mall.service.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by ly on 2018/3/22.
 */
@Controller
@RequestMapping("/manage/category/")
public class CategoryManageController {

    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping(value = "add_category.do")
    @ResponseBody
    public ServerResponse addCategory(
            String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId, HttpSession session) {
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
//        }
//        if(!iUserService.checkAdminRole(user).isSuccess()) {
//            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
//        }
        ServerResponse validResponse = UserUtil.validAdminLogin(session);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }

        return iCategoryService.addCategory(categoryName, parentId);
    }

    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(Integer categoryId, String categoryName, HttpSession session) {
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
//        }
//        if(!iUserService.checkAdminRole(user).isSuccess()) {
//            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
//        }
        ServerResponse validResponse = UserUtil.validAdminLogin(session);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }

        return iCategoryService.updateCategoryName(categoryId, categoryName);
    }

    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(
            @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId, HttpSession session) {
        ServerResponse validResponse = UserUtil.validAdminLogin(session);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        //查询子节点的category信息，不递归
        return iCategoryService.getChildrenParallelCategory(categoryId);
    }

    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(
            @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId, HttpSession session) {
        ServerResponse validResponse = UserUtil.validAdminLogin(session);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        return iCategoryService.selectCategoryAndChildrenById(categoryId);
    }

}
