package com.zj.learn.itext;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @author Mr. xi.yang<br/>
 * @version V1.0 <br/>
 * @description: iText插件使用，pdf处理 <br/>
 * @date 2017-05-15 下午 2:12 <br/>
 */
public class ITextDemo {
    // 黑体
    private static final String FONT = "simhei.ttf";
    private static final String DIST = "target/test.pdf";
    public static void main(String[] args) {
        Document document = new Document();
        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(DIST));
            document.open();
            // 中文字体支持
            Font font = FontFactory.getFont(FONT, BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
            document.add(new Paragraph("test insert 中文"));
            document.close();
            pdfWriter.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
