package com.zj.learn.app;

import com.alibaba.fastjson.JSON;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Yangxi on 2017/5/9 0009.
 */
public class Jdms {
    private static WebDriver webDriver;
    static {
        System.setProperty("webdriver.chrome.driver","E:\\myworkspace\\zj-learn\\src\\main\\resource\\chromedriver.exe");
        webDriver = new ChromeDriver();
    }
    public static void main(String[] args) {
        try {
            loginJd("1006652872","Tf]3oei^xTA)u4B]reH8");
            getElememt(By.className("nickname"));
            String url = "https://item.jd.hk/1938009.html";// 尿不湿
//            String url = "https://item.jd.com/3754287.html";//乐高 18:00
            itemOrder(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static WebElement getElememt(final By by) {
        try {
            return new WebDriverWait(webDriver,10).until(
                    new ExpectedCondition<WebElement>() {
                        @Override
                        public WebElement apply(WebDriver input) {
                            return input.findElement(by);
                        }
                    }
            );
        }catch (Exception e){
            System.err.println("获取元素出错："+JSON.toJSONString(by));
        }
        return null;
    }

    private static void itemOrder(String url){
        while (true){
            webDriver.get(url);
            WebElement webElement = getKoWebElememt();
            if(null != webElement){
                webElement.click();
                break;
            }
        }
        getElememt(By.linkText("去购物车结算")).click();
        getElememt(By.className("submit-btn")).click();
        getElememt(By.id("order-submit")).click();
    }

    private static WebElement getKoWebElememt(){
        WebElement webElement = getElememt(By.className("choose-btn-ko"));
        if(webElement != null){
            return webElement;
        }
        System.out.println("没有抢购按钮！");
        webElement = getElememt(By.id("InitCartUrl"));
        if(webElement == null){
            System.out.println("没有加入购物车按钮！");
            return null;
        }
        String clz = webElement.getAttribute("class");
        String price = getElememt(By.className("p-price")).getText().replace("￥","");
        System.out.println(price);
        double priceDouble = Double.parseDouble(price);
        if(clz.contains("disable") || priceDouble > 100){
            System.out.println("价格="+price+"；能否抢购："+clz);
            return null;
        }
        return webElement;
    }

    private static void loginJd(String username,String password){
        webDriver.get("https://www.jd.com");
        String loginLink = "你好，请登录";
        getElememt(By.linkText(loginLink)).click();
        loginLink = "账户登录";
        getElememt(By.linkText(loginLink)).click();

        String usernameTag = "loginname";
        String pwdTag = "nloginpwd";
        String loginBtnTag = "loginsubmit";
        getElememt(By.name(usernameTag)).sendKeys(username);

        getElememt(By.name(pwdTag)).sendKeys(password);
        getElememt(By.id(loginBtnTag)).click();
        System.out.println("login success");
    }
}

