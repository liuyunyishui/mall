package com.mall.controller.backend;

import com.mall.common.ServerResponse;
import com.mall.service.IOrderService;
import com.mall.service.UserUtil;
import com.mall.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by ly on 2018/3/30.
 */
@Controller
@RequestMapping("/manage/order")
public class OrderManageController {

    @Autowired
    private IOrderService iOrderService;

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                               HttpSession session) {
        ServerResponse validResponse = UserUtil.validAdminLogin(session);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        return iOrderService.manageList(pageNum, pageSize);
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<OrderVo> orderDetail(Long orderNo, HttpSession session) {
        ServerResponse validResponse = UserUtil.validAdminLogin(session);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        return iOrderService.manageDetail(orderNo);
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse search(Long orderNo, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                 HttpSession session) {
        ServerResponse validResponse = UserUtil.validAdminLogin(session);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        return iOrderService.manageSearch(orderNo, pageNum, pageSize);
    }

    @RequestMapping("send_goods.do")
    @ResponseBody
    public ServerResponse<String> orderSendGoods(Long orderNo, HttpSession session) {
        ServerResponse validResponse = UserUtil.validAdminLogin(session);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        return iOrderService.manageSendGoods(orderNo);
    }

}
