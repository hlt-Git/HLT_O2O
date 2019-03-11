package com.hlt.util;

import com.google.code.kaptcha.Constants;
import javax.servlet.http.HttpServletRequest;

//验证输入验证码是否正确
public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request){
//        从servlet中取出servlet生成的验证码text值
        String verifyCodeExpected = (String) request.getSession().getAttribute(
                com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
//        获取用户页面输入的验证码
        String verifyCodeActual = HttpServletRequestUtil.getString(request,"verifyCodeActual");
//        校验验证码
        if (verifyCodeActual == null || !verifyCodeActual.equals(verifyCodeExpected)){
            return false;
        }
        return true;
    }
}
