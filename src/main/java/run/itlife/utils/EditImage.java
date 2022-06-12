package run.itlife.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class EditImage {

    // изменение размера картинки
    public static BufferedImage resizeImage(final BufferedImage image, int width, int height) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        return bufferedImage;
    }

    // обрезка изображения
    public static BufferedImage cropImage(BufferedImage originalImage) throws IOException {
        BufferedImage cropImage = null;
        float imgWidth = originalImage.getWidth();
        float imgHeight = originalImage.getHeight();
        float imgResize = 0;
        float x = 0;
        float y = 0;
        if(imgHeight > imgWidth) {
            imgResize = imgWidth;
            x = 0;
            y = (1 - (imgWidth / imgHeight)) * imgResize / 2;
        }
        else {
            imgResize = imgHeight;
            x = (1 - (imgHeight / imgWidth)) * imgResize / 2;
            y = 0;
        }
        return cropImage = originalImage.getSubimage((int) x, (int) y, (int) imgResize, (int) imgResize);
    }

}
