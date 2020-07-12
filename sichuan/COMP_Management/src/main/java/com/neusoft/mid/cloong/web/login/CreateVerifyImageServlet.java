package com.neusoft.mid.cloong.web.login;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**生成验证码
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-3-7 上午11:07:35
 */
public class CreateVerifyImageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected final Log log = LogFactory.getLog(getClass());

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        this.doPost(req, resp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

//        String uri = request.getRequestURI();
//
//        String randomStr = uri.substring(uri.lastIndexOf("/") + 1);

        //创建随机类
        Random r = new Random();

        //在内存中创建图像，宽度为width,高度为height
        int width = 63, height = 22;
        BufferedImage pic = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //获取图形上下文环境
        Graphics gc = pic.getGraphics();

        //设定背景图形上下文填充
        gc.setColor(getRandColor(200, 250));
        gc.fillRect(0, 0, width, height);

        //设定图形上下文环境字体
        gc.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        //随机产生200条干扰直线，使图像中的认证码不易被其他分析程序探测
        gc.setColor(getRandColor(160, 200));
        for (int i = 0; i < 200; i++) {
            int x1 = r.nextInt(width);
            int y1 = r.nextInt(height);
            int x2 = r.nextInt(15);
            int y2 = r.nextInt(15);
            gc.drawLine(x1, y1, x1 + x2, y1 + y2);
        }

        //随机产生100个干扰点，使图像中的验证码不易被其他分析程序探测到
        gc.setColor(getRandColor(120, 240));
        for (int i = 0; i < 100; i++) {
            int x = r.nextInt(width);
            int y = r.nextInt(height);
            gc.drawOval(x, y, 0, 0);
        }

        //随机产生4位数字的验证码
        StringBuffer rs = new StringBuffer();
        String rn = "";
        for (int i = 0; i < 4; i++) {
            //产生10以内的随机数字rn
            rn = String.valueOf(r.nextInt(10));
            rs.append(rn);
            //将认证码用drawString函数显示到图像里
            gc.setColor(new Color(20 + r.nextInt(110), 20 + r.nextInt(110), 20 + r
                    .nextInt(110)));
            gc.drawString(rn, 13 * i + 6, 16);
        }

        //释放图形上下文环境
        gc.dispose();

        HttpSession session = request.getSession();
        session.setAttribute("random", rs.toString());

        //输出生成后的验证码图像到页面
        ImageIO.write(pic, "PNG", response.getOutputStream());
        gc.dispose();

        response.getOutputStream().close();
    }

    private Color getRandColor(int fc, int bc) {
        Random r = new Random();
        if (fc > 255) {fc = 255;}
        if (bc > 255) {bc = 255;}
        int red = fc + r.nextInt(bc - fc); //red
        int green = fc + r.nextInt(bc - fc); //green
        int blue = fc + r.nextInt(bc - fc); //blue
        return new Color(red, green, blue);
    }
}
