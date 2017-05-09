package com.zj.learn.app;

import com.alibaba.fastjson.JSON;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Jsoup实践
 * Created by Yangxi on 2017/5/9 0009.
 */
public class NetGetSSQRecord {
    private static final String baseUrlPrefix = "http://kaijiang.zhcw.com/zhcw/html/ssq/list_";
    private static final String baseUrlSuffix = ".html";
    public static void main(String[] args) {
        try {
            Document document = Jsoup.connect(baseUrlPrefix+1+baseUrlSuffix).get();
            Elements elements = document.select(".pg");
            int page = Integer.parseInt(elements.get(0).child(0).ownText());
            System.out.println("总页数：" + page);
            List<Ssq> list = new ArrayList<>();
            for (int i = 1; i <= page; i++) {
                Document doc = Jsoup.connect(baseUrlPrefix+i+baseUrlSuffix).get();
                addOnePageData2List(doc, list);
            }
            System.out.println("完成！总共条数："+list.size());
            String pathname = "E:\\myworkspace\\zj-learn\\src\\ssq.txt";
            File file = new File(pathname);
            OutputStream os = new FileOutputStream(file);
            os.write(JSON.toJSONString(list).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取一页数据放到list
     * @param document
     * @param list
     * @throws ParseException
     */
    private static void addOnePageData2List(Document document, List<Ssq> list) throws ParseException {
        Elements trs = document.getElementsByTag("tr");
        for (int i = 0; i <trs.size(); i++) {
            Element element = trs.get(i);
            int childSize = element.childNodeSize();
            if(childSize != 15){
                continue;
            }
            Ssq ssq = getSsq(element);
            list.add(ssq);
        }
    }

    private static Ssq getSsq(Element element) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date openTime = format.parse(element.child(0).ownText());
        int openNum = Integer.parseInt(element.child(1).ownText());
        Element balls = element.child(2);
        String op = ",";
        String reds = balls.child(0).ownText()+op+
                balls.child(1).ownText()+op+
                balls.child(2).ownText()+op+
                balls.child(3).ownText()+op+
                balls.child(4).ownText()+op+
                balls.child(5).ownText();
        int blue = Integer.parseInt(balls.child(6).ownText());
        long sales = Long.parseLong(element.child(3).child(0).ownText().replace(op,""));
        int firstPrizeNum = Integer.parseInt(element.child(4).child(0).ownText());
        int secondPrizeNum = Integer.parseInt(element.child(5).child(0).ownText());
        Ssq ssq = new Ssq(openTime,openNum,reds,blue,sales,firstPrizeNum,secondPrizeNum);
        System.out.println(JSON.toJSONString(ssq));
        return ssq;
    }
}

class Ssq{
    private Date openTime;
    private Integer openNum;
    private String reds;
    private Integer blue;
    private Long sales;
    private Integer firstPrizeNum;
    private Integer secondPrizeNum;

    public Ssq(Date openTime, Integer openNum, String reds, Integer blue, Long sales, Integer firstPrizeNum, Integer secondPrizeNum) {
        this.openTime = openTime;
        this.openNum = openNum;
        this.reds = reds;
        this.blue = blue;
        this.sales = sales;
        this.firstPrizeNum = firstPrizeNum;
        this.secondPrizeNum = secondPrizeNum;
    }

    public Date getOpenTime() {

        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Integer getOpenNum() {
        return openNum;
    }

    public void setOpenNum(Integer openNum) {
        this.openNum = openNum;
    }

    public String getReds() {
        return reds;
    }

    public void setReds(String reds) {
        this.reds = reds;
    }

    public Integer getBlue() {
        return blue;
    }

    public void setBlue(Integer blue) {
        this.blue = blue;
    }

    public Long getSales() {
        return sales;
    }

    public void setSales(Long sales) {
        this.sales = sales;
    }

    public Integer getFirstPrizeNum() {
        return firstPrizeNum;
    }

    public void setFirstPrizeNum(Integer firstPrizeNum) {
        this.firstPrizeNum = firstPrizeNum;
    }

    public Integer getSecondPrizeNum() {
        return secondPrizeNum;
    }

    public void setSecondPrizeNum(Integer secondPrizeNum) {
        this.secondPrizeNum = secondPrizeNum;
    }
}
