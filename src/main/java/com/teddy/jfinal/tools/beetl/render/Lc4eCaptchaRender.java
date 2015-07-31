package com.teddy.jfinal.tools.beetl.render;

import com.jfinal.render.Render;
import com.teddy.jfinal.tools.AuthTool;
import com.teddy.jfinal.tools.StringTool;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 验证码
 *
 * @author 董华健
 */
public class Lc4eCaptchaRender extends Render {

    private static Logger log = Logger.getLogger(Lc4eCaptchaRender.class);

    // 定义图形验证码中绘制字符的字体
    // private final Font mFont = new Font("Arial Black", Font.PLAIN, 16);
    private final static List<Font> fontList;

    static {
        fontList = new ArrayList<Font>();

        Font font01 = new Font("Terminal", Font.PLAIN, 18);
        fontList.add(font01);

        Font font02 = new Font("Times New Roman", Font.PLAIN, 18);
        fontList.add(font02);

        Font font03 = new Font("Trebuchet MS", Font.PLAIN, 18);
        fontList.add(font03);

        Font font04 = new Font("System", Font.PLAIN, 18);
        fontList.add(font04);

        Font font05 = new Font("Stencil", Font.PLAIN, 18);
        fontList.add(font05);

        Font font06 = new Font("Segoe Print", Font.PLAIN, 18);
        fontList.add(font06);

        Font font07 = new Font("Palatino Linotype", Font.PLAIN, 18);
        fontList.add(font07);

        Font font08 = new Font("Lucida Sans", Font.PLAIN, 18);
        fontList.add(font08);

        Font font09 = new Font("Franklin Gothic", Font.PLAIN, 18);
        fontList.add(font09);

        Font font10 = new Font("Felix Titling", Font.PLAIN, 18);
        fontList.add(font10);
    }

    // 定义图形验证码的大小
    private final int IMG_WIDTH = 100;
    private final int IMG_HEIGTH = 18;

    @Override
    public void render() {
        BufferedImage bufferedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGTH, BufferedImage.TYPE_INT_RGB);

        String sRand = graphics(bufferedImage).toLowerCase();
        log.debug("验证码：" + sRand);

        // 设置验证码值到cookie
        AuthTool.setAuthCode(response, sRand);

        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        ServletOutputStream sos = null;
        try {
            sos = response.getOutputStream();
            ImageIO.write(bufferedImage, "jpeg", sos);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (sos != null) {
                try {
                    sos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String graphics(BufferedImage bufferedImage) {
        // Graphics graphics = bufferedImage.getGraphics();
        Graphics2D graphics = bufferedImage.createGraphics();

        // 设置背景色
        graphics.setColor(getRandColor(200, 250));

        // 填充背景色
        graphics.fillRect(1, 1, IMG_WIDTH - 1, IMG_HEIGTH - 1);

        // 为图形验证码绘制边框
        graphics.setColor(new Color(102, 102, 102));
        graphics.drawRect(0, 0, IMG_WIDTH - 1, IMG_HEIGTH - 1);

        // 画一道粗线
        this.drawThickLine(graphics, 0, StringTool.number(IMG_HEIGTH) + 1, IMG_WIDTH, StringTool.number(IMG_HEIGTH) + 1, 4, getRandColor(100, 200));// 加一道线

        // 从左上到右下加上多道干扰线
        graphics.setColor(getRandColor(160, 200));
        for (int i = 0; i < 80; i++) {
            int x = StringTool.number(IMG_WIDTH - 1);
            int y = StringTool.number(IMG_HEIGTH - 1);
            int xl = StringTool.number(6) + 1;
            int yl = StringTool.number(12) + 1;
            graphics.drawLine(x, y, x + xl, y + yl);
        }

        // 从右上到左下加多道干扰线
        graphics.setColor(getRandColor(160, 200));
        for (int i = 0; i < 80; i++) {
            int x = StringTool.number(IMG_WIDTH - 1);
            int y = StringTool.number(IMG_HEIGTH - 1);
            int xl = StringTool.number(12) + 1;
            int yl = StringTool.number(6) + 1;
            graphics.drawLine(x, y, x - xl, y - yl);
        }

        // 使图片扭曲
        shear(graphics, IMG_WIDTH, IMG_HEIGTH, getRandColor(200, 250));

        // 设置绘制字符的字体
        int fontIndex = StringTool.number(9);
        graphics.setFont(fontList.get(fontIndex));// mFont

        // 生成随机字符串
        String sRand = "";
        for (int i = 0; i < 4; i++) {
            char tmp = StringTool.getAuthCodeChar();
            sRand += tmp;
            // 获取随机颜色
            graphics.setColor(new Color(20 + StringTool.number(110), 20 + StringTool.number(110), 20 + StringTool.number(110)));
            // 在图片上绘制系统生成的随机字符
            graphics.drawString(String.valueOf(tmp), 20 * i + 20, 15);
        }

        TextLayout textLayout = new TextLayout(sRand, new Font("Fixedsys", Font.PLAIN, 20), new FontRenderContext(null, true, false));// 获得字体一样的字，20是字体的大小
        textLayout.draw(graphics, 30, 60);// 对字体加投影，第二个是左右相距，越大越远，第三个参数是上下两层相距距离，越大越近

        // 添加噪点
        float yawpRate = 0.012f;// 噪声率
        int area = (int) (yawpRate * IMG_WIDTH * IMG_HEIGTH);
        for (int i = 0; i < area; i++) {
            int x = StringTool.number(IMG_WIDTH);
            int y = StringTool.number(IMG_HEIGTH);
            int rgb = getRandomIntColor();
            bufferedImage.setRGB(x, y, rgb);
        }

        // 图象生效
        graphics.dispose();

        return sRand;
    }

    // 定义一个获取随机颜色的方法
    private Color getRandColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + StringTool.number(bc - fc);
        int g = fc + StringTool.number(bc - fc);
        int b = fc + StringTool.number(bc - fc);
        // 得到随机颜色
        return new Color(r, g, b);
    }

    // 画一道粗线的方法
    private void drawThickLine(Graphics graphics, int x1, int y1, int x2, int y2, int thickness, Color c) {
        // The thick line is in fact a filled polygon
        graphics.setColor(c);
        int dX = x2 - x1;
        int dY = y2 - y1;
        // line length
        double lineLength = Math.sqrt(dX * dX + dY * dY);

        double scale = (double) (thickness) / (2 * lineLength);

        // The x and y increments from an endpoint needed to create a
        // rectangle...
        double ddx = -scale * (double) dY;
        double ddy = scale * (double) dX;
        ddx += (ddx > 0) ? 0.5 : -0.5;
        ddy += (ddy > 0) ? 0.5 : -0.5;
        int dx = (int) ddx;
        int dy = (int) ddy;

        // Now we can compute the corner points...
        int xPoints[] = new int[4];
        int yPoints[] = new int[4];

        xPoints[0] = x1 + dx;
        yPoints[0] = y1 + dy;
        xPoints[1] = x1 - dx;
        yPoints[1] = y1 - dy;
        xPoints[2] = x2 - dx;
        yPoints[2] = y2 - dy;
        xPoints[3] = x2 + dx;
        yPoints[3] = y2 + dy;

        graphics.fillPolygon(xPoints, yPoints, 4);
    }

    // 扭曲方法
    private void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    private void shearX(Graphics g, int w1, int h1, Color color) {
        int period = StringTool.number(2);
        boolean borderGap = true;
        int frames = 1;
        int phase = StringTool.number(2);
        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1) * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }
    }

    private void shearY(Graphics g, int w1, int h1, Color color) {
        int period = StringTool.number(40) + 10; // 50;
        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1) * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }
        }
    }

    // 添加噪点的方法
    private int getRandomIntColor() {
        int[] rgb = StringTool.getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

}
