package com.shangpin.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tonny on 2016/9/7.
 */
public class StringValidateUtil {


    public static boolean phoneValidate(String phone) {
        Pattern p = Pattern.compile("^[1][0-9][0-9]{9}$"); // 验证手机号
        Matcher m = p.matcher(phone);
        if(m.matches()){
            return true;
        }
        return false;
    }

    public static boolean emailValidate(String email) {

        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        if(m.matches()){
           return true;
        }
        return false;
    }
}
