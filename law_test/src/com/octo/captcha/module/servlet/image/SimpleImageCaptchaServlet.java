//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.octo.captcha.module.servlet.image;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleImageCaptchaServlet extends HttpServlet implements Servlet {
    public static ImageCaptchaService service = new DefaultManageableImageCaptchaService();

    public SimpleImageCaptchaServlet() {
    }

    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletResponse.setDateHeader("Expires", 0L);
        httpServletResponse.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        httpServletResponse.addHeader("Cache-Control", "post-check=0, pre-check=0");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setContentType("image/jpeg");
        BufferedImage bi = service.getImageChallengeForID(httpServletRequest.getSession(true).getId());
        System.out.println("向验证码系统加入session"+httpServletRequest.getSession(true).getId());
        ServletOutputStream out = httpServletResponse.getOutputStream();
        ImageIO.write(bi, "jpg", out);

        try {
            out.flush();
        } finally {
            out.close();
        }

    }

    public static boolean validateResponse(HttpServletRequest request, String userCaptchaResponse) {
        if(request.getSession(false) == null) {
            return false;
        } else {
            boolean validated = false;

            try {
                validated = service.validateResponseForID(request.getSession().getId(), userCaptchaResponse).booleanValue();
                System.out.println("向验证码系统删除session"+request.getSession(true).getId());
                System.out.println("Response是"+userCaptchaResponse);
            } catch (CaptchaServiceException var4) {
                var4.printStackTrace();
            }

            return validated;
        }
    }
}
