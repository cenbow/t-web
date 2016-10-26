package com.shangpin.web.controller.temp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/alibba/promotion")
public class AlibabaPromotion {
    /*
     * 计算MD5+BASE64
     */
    private static String MD5Base64(String s) {
        if (s == null)
            return null;
        String encodeStr = "";
        byte[] utfBytes = s.getBytes();
        MessageDigest mdTemp;
        try {
            mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(utfBytes);
            byte[] md5Bytes = mdTemp.digest();
            encodeStr = Base64.encodeBase64String(md5Bytes);
        } catch (Exception e) {
            throw new Error("Failed to generate MD5 : " + e.getMessage());
        }
        return encodeStr;
    }

    /*
     * 计算 HMAC-SHA1
     */
    private static String HMACSha1(String data, String key) {
        String result;
        try {

            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            result = Base64.encodeBase64String(rawHmac);
        } catch (Exception e) {
            throw new Error("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }

    /*
     * 等同于javaScript中的 new Date().toUTCString();
     */
    private static String toGMTString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.UK);
        df.setTimeZone(new java.util.SimpleTimeZone(0, "GMT"));
        return df.format(date);
    }

    @RequestMapping
    @ResponseBody
    public String promotion(@RequestParam("uid")String uid){
    	 // 发送GET请求
		 String ak_id1 = "0BC8b8bWubSiL1Yi"; //用户ak
		 String ak_secret1 = "mbc7PR6DTNHaQ1UhOnjVRJjwz23Fcq"; // 用户ak_secret
		 String url1 = "https://shujuapi.aliyun.com/dataplus_16197/re/doRec?biz_code=shangpinrec1&scn_code=main_rec&user_id="+uid+"&item_id=";
		 String rs=AlibabaPromotion.sendGet(url1, ak_id1, ak_secret1);
    	return rs;
    }
    /*
     * 发送POST请求
     */
    private static String sendPost(String url, String body, String ak_id, String ak_secret) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);

            /*
             * http header 参数
             */
            String method = "POST";
            String accept = "json";
            String content_type = "application/json";
            String path = realUrl.getFile();
            String date = toGMTString(new Date());

            // 1.对body做MD5+BASE64加密
            String bodyMd5 = MD5Base64(body);
            String stringToSign = method + "\n" + accept + "\n" + bodyMd5 + "\n" + content_type + "\n" + date + "\n"
                    + path;
            // 2.计算 HMAC-SHA1
            String signature = HMACSha1(stringToSign, ak_secret);
            // 3.得到 authorization header
            String authHeader = "Dataplus " + ak_id + ":" + signature;

            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", accept);
            conn.setRequestProperty("content-type", content_type);
            conn.setRequestProperty("date", date);
            conn.setRequestProperty("Authorization", authHeader);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(body);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /*
     * GET请求
     */

    public static String sendGet(String url, String ak_id, String ak_secret) {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            /*
             * http header 参数
             */
            String method = "GET";
            String accept = "json";
            String content_type = "application/json";
            String path = realUrl.getFile();
            String date = toGMTString(new Date());
            // 1.对body做MD5+BASE64加密
            // String bodyMd5 = MD5Base64(body);
            String stringToSign = method + "\n" + accept + "\n" + "" + "\n" + content_type + "\n" + date + "\n" + path;
            // 2.计算 HMAC-SHA1
            String signature = HMACSha1(stringToSign, ak_secret);
            // 3.得到 authorization header
            String authHeader = "Dataplus " + ak_id + ":" + signature;
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", accept);
            connection.setRequestProperty("content-type", content_type);
            connection.setRequestProperty("date", date);
            connection.setRequestProperty("Authorization", authHeader);
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /*public static void main(String[] args) throws Exception {
    	
        // 发送POST请求示例
        String ak_id1 = "NMV.............5jv"; //用户ak
        String ak_secret1 = "Fgs...............3zu"; // 用户ak_secret
        String url = "https://shujuapi.aliyun.com/org_code/service_code/api_name";
        String body = "{\"param1\": \"xxx\", \"param2\":\"xxx\"}";
        System.out.println("response body:" + sendPost(url, body, ak_id, ak_secret));

        
        // 发送GET请求
        String ak_id1 = "0BC8b8bWubSiL1Yi"; //用户ak
        String ak_secret1 = "mbc7PR6DTNHaQ1UhOnjVRJjwz23Fcq"; // 用户ak_secret
        String url1 = "https://shujuapi.aliyun.com/dataplus_16197/re/doRec?biz_code=shangpinrec1&scn_code=main_rec&user_id=6DB1A4D2DDFBE6FCF1413D349D3B4D27&item_id=";
        System.out.println("response body:" + sendGet(url1, ak_id1, ak_secret1));

    }*/
}
