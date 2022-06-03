package run.itlife.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import run.itlife.utils.ZXingQR;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.InetAddress;

@Controller
public class QRcodeController {
    private final ServletContext context;

    public QRcodeController(ServletContext context) {
        this.context = context;
    }

    @RequestMapping(value = "qrcode/", method = RequestMethod.GET)
    public void qrcode(HttpServletResponse response) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String path = InetAddress.getLocalHost().getHostName() + '/' + "sub-posts" + '/' + username;
        response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(ZXingQR.getQRCodeImage(path, 400, 400));
        outputStream.flush();
        outputStream.close();

    }
}
