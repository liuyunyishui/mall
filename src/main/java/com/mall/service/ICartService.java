package com.mall.service;

import com.mall.common.ServerResponse;
import com.mall.vo.CartVo;

/**
 * Created by ly on 2018/3/24.
 */
public interface ICartService {

    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);

    ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count);

    ServerResponse<CartVo> deleteProduct(Integer userId, String productIds);

    ServerResponse<CartVo> list(Integer userId);

    ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer productId, Integer checked);

    ServerResponse<Integer> getCartProductCount(Integer userId);

}
