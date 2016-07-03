package com.teddy.jfinal.depend.jetbrick;

import com.jfinal.render.Render;
import com.teddy.jfinal.tools.StringTool;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;


public class Lc4eCaptchaRender extends Render {

    private static Logger log = Logger.getLogger(Lc4eCaptchaRender.class);

    public static final String captcha_code = "lc4e_captcha_code";
    private static boolean casesensitive = false;
    private static List<Font> fontList = new ArrayList<>();
    // 定义图形验证码的大小
    private final int IMG_WIDTH = 120;
    private final int IMG_HEIGTH = 30;

    static {
        String[] fonts = new String[]{"Terminal", "Times New Roman", "Trebuchet MS", "System", "Stencil", "Segoe Print", "Palatino Linotype",
                "Lucida Sans", "Franklin Gothic", "Felix Titling"};

        for (String font : fonts) {
            fontList.add(new Font(font, Font.PLAIN, 18));
        }
    }


    @Override
    public void render() {
        BufferedImage bufferedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGTH, BufferedImage.TYPE_INT_RGB);

        String sRand = graphics(bufferedImage);

        log.debug("Code: [" + sRand + "]");
        //String md5 = encrypt(sRand);

        request.getSession().setAttribute(captcha_code, casesensitive ? sRand : sRand.toLowerCase());

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

    /**
     * 使用md5散列字符串
     *
     * @param srcStr 输入的字符串
     * @return 加密后的字符串
     */
    public static final String encrypt(String srcStr) {
        try {
            StringBuilder result = new StringBuilder();
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(srcStr.getBytes("utf-8"));
            for (byte b : bytes) {
                String hex = Integer.toHexString(b & 0xFF).toUpperCase();
                result.append((hex.length() == 1) ? "0" : "").append(hex);
            }
            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
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

        // 画粗线
        for (int i = 0; i < 2; i++) {
            this.drawThickLine(graphics, 0, StringTool.number(IMG_HEIGTH) + 1, IMG_WIDTH, StringTool.number(IMG_HEIGTH) + 1, 4, getRandColor(100, 200));// 加一道线
        }

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
        shear(graphics, IMG_WIDTH, IMG_HEIGTH, getRandColor(210, 250));
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
            graphics.drawString(String.valueOf(tmp), 20 * i + 20, StringTool.number(10, 28));
        }
        fontIndex = StringTool.number(9);

        TextLayout textLayout = new TextLayout(sRand, fontList.get(fontIndex), new FontRenderContext(null, true, false));// 获得字体一样的字，20是字体的大小
        textLayout.draw(graphics, 30, 60);// 对字体加投影，第二个是左右相距，越大越远，第三个参数是上下两层相距距离，越大越近

        // 添加噪点
        float yawpRate = 0.015f;// 噪声率
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
        ddx += (ddx > 0) ? 0.7 : -0.7;
        ddy += (ddy > 0) ? 0.7 : -0.7;
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

    public static boolean validate(HttpServletRequest requestTmp, String code) {
        Object value = requestTmp.getSession().getAttribute(captcha_code);
        return value != null && value.equals(casesensitive ? code : code.toLowerCase());
    }

    public boolean isCasesensitive() {
        return casesensitive;
    }

    public void setCasesensitive(boolean casesensitive) {
        Lc4eCaptchaRender.casesensitive = casesensitive;
    }
}
