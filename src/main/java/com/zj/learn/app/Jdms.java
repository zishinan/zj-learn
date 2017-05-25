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
            itemOrder("https://item.jd.com/4950364.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static WebElement getElememt(final By by) {
        return new WebDriverWait(webDriver,1).until(
                new ExpectedCondition<WebElement>() {
                    @Override
                    public WebElement apply(WebDriver input) {
                        return input.findElement(by);
                    }
                }
        );
    }

    private static void itemOrder(String url){
        webDriver.get(url);
        System.out.println("get :"+url);
        WebElement webElement = getElememt(By.id("InitCartUrl"));
        String clz = webElement.getAttribute("class");
        System.out.println("是否能够抢购："+clz);
        int n = 0;
        while (clz.contains("disable")){
            webDriver.get(webDriver.getCurrentUrl());
            webElement = getElememt(By.id("InitCartUrl"));
            System.out.println("第"+(++n)+"次刷新是否能够抢购"+clz);
            if(!clz.contains("disable")){
                webElement.click();
            }
        }
        getElememt(By.linkText("去购物车结算")).click();
        getElememt(By.className("submit-btn")).click();
        getElememt(By.id("order-submit")).click();
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

