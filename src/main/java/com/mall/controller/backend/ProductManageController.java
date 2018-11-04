package com.mall.controller.backend;

import com.google.common.collect.Maps;
import com.mall.common.ServerResponse;
import com.mall.pojo.Product;
import com.mall.service.IFileService;
import com.mall.service.IProductService;
import com.mall.util.PropertiesUtil;
import com.mall.service.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by ly on 2018/3/23.
 */
@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService iFileService;

    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(Product product, HttpSession session) {
        ServerResponse validResponse = UserUtil.validAdminLogin(session);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        return iProductService.saveOrUpdateProduct(product);
    }

    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(Integer productId, Integer status, HttpSession session) {
        ServerResponse validResponse = UserUtil.validAdminLogin(session);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        return iProductService.setSaleStatus(productId, status);
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(Integer productId, HttpSession session) {
        ServerResponse validResponse = UserUtil.validAdminLogin(session);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        return iProductService.manageProductDetail(productId);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                  HttpSession session) {
        ServerResponse validResponse = UserUtil.validAdminLogin(session);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        return iProductService.getProductList(pageNum, pageSize);
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse productSearch(String productName, Integer productId,
                                        @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                        HttpSession session) {
        ServerResponse validResponse = UserUtil.validAdminLogin(session);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        return iProductService.searchProduct(productName, productId, pageNum, pageSize);
    }

    @RequestMapping("upload")
    @ResponseBody
    public ServerResponse upload(@RequestParam(value = "upload_file", required = false) MultipartFile file,
                                 HttpServletRequest request, HttpSession session) {
        ServerResponse validResponse = UserUtil.validAdminLogin(session);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }

        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file, path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;

        Map<String, Object> fileMap = Maps.newHashMap();
        fileMap.put("uri", targetFileName);
        fileMap.put("url", url);
        return ServerResponse.createBySuccess(fileMap);
    }

    @RequestMapping("richtext_img_upload.do")
    @ResponseBody
    public Map richtextImgUpload(@RequestParam(value = "upload_file", required = false) MultipartFile file,
                                 HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Map<String, Object> resultMap = Maps.newHashMap();
        ServerResponse validResponse = UserUtil.validAdminLogin(session);
        if (!validResponse.isSuccess()) {
            resultMap.put("success", false);
            resultMap.put("msg", "无权限操作，需要管理员权限");
            return resultMap;
        }
        /*
        富文本中对于返回值有自己的要求,我们使用是simditor所以按照simditor的要求进行返回
        {
            "success": true/false,
            "msg": "error message", # optional
            "file_path": "[real file path]"
        }
        */
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file, path);
        if (StringUtils.isBlank(targetFileName)) {
            resultMap.put("success", false);
            resultMap.put("msg", "上传失败");
            return resultMap;
        }
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
        resultMap.put("success", true);
        resultMap.put("msg", "上传成功");
        resultMap.put("file_path", url);
        response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
        return resultMap;
    }

}
