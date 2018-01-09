package org.jasig.cas.authentication.handler.randImage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class RandImageCreateController extends AbstractController {

    private final static Logger logger = LoggerFactory.getLogger(RandImageCreateController.class);

    // 随机数字符串
    private final static String RAND_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int width = 65;
        int height = 30;
        String format = "png";
        // 设置response头信息，禁止缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Random random = new Random();
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        StringBuilder sRand = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(RAND_STRING.charAt(random.nextInt(RAND_STRING.length())));
            sRand.append(rand);
            g.setColor(new Color(height + random.nextInt(110), height + random.nextInt(110), height + random.nextInt(110)));
            //居中显示
            int x = height / 2 * i + 5;
            int y = height / 2 + g.getFontMetrics().getHeight() / 4;
            g.drawString(rand, x, y);
        }
        HttpSession session = request.getSession();
        // 将认证码存入SESSION
        session.setAttribute("sRand", sRand.toString());
        g.dispose();
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            ImageIO.write(image, format, out);
        } catch (IOException e) {
            logger.error("图片验证码生成异常 - {}", e);
        } finally {
            if (null != out) {
                out.flush();
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error("图片验证码生成异常 - {}", e);
                }
            }

        }
        return null;
    }

    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
