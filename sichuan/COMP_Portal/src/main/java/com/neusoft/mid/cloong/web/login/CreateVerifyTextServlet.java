package com.neusoft.mid.cloong.web.login;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 生成验证码文本
 * 
 * @author <a href="mailto:zhang.ge@neusoft.com">ZHANG.GE</a>
 * @version $Revision 1.1 $ 2017-11-08 上午11:34:35
 */
public class CreateVerifyTextServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected final Log log = LogFactory.getLog(getClass());

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 设置页面不缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		String type = request.getParameter("type");
		String codes[];
		String letters[] = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
				"k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
				"w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H",
				"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
				"U", "V", "W", "X", "Y", "Z" };
		String nums[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

		if ("blend".equals(type)) {
			codes = ArrayUtils.addAll(nums, letters);
		} else if ("number".equals(type)) {
			codes = nums;
		} else {
			codes = letters;
		}

		// 创建随机类
		Random r = new Random();

		// 随机产生4位数字的验证码
		StringBuffer rs = new StringBuffer();
		int index;
		for (int i = 0; i < 4; i++) {
			index = r.nextInt(codes.length);
			rs.append(codes[index]);
		}

		HttpSession session = request.getSession();
		session.setAttribute("random", rs.toString());

		PrintWriter out = response.getWriter();
		out.write(rs.toString());
	}

}
