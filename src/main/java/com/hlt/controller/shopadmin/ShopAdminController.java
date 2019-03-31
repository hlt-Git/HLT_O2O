package com.hlt.controller.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//控制浏览器访问界面路径
@Controller
@RequestMapping(value = "/shopadmin",method = {RequestMethod.GET})
public class ShopAdminController {
    @RequestMapping(value = "/shopoperation")
    public String shopOperation(){
        return "shop/shopoperation";
    }

    @RequestMapping(value = "/shoplist")
    public String shopList(){
        return "shop/shoplist";
    }

    @RequestMapping(value = "/shopmanagement")
    public String shopManagement(){
        return "shop/shopmanagement";
    }

    @RequestMapping(value = "/productcategorymanagement",method = RequestMethod.GET)
    private String productCategoryManage(){
        return "shop/productcategorymanagement";
    }
}