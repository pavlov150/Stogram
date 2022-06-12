package run.itlife.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import run.itlife.utils.ZXingQR;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@Controller
public class QRcodeController {

    @GetMapping("qrcode/")
    @PreAuthorize("hasRole('USER')")
    public void qrcode(HttpServletResponse response) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String path = "stogram.moscow/" + "sub-posts" + '/' + username;
        response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(ZXingQR.getQRCodeImage(path, 400, 400));
        outputStream.flush();
        outputStream.close();
    }

}
