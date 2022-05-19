package run.itlife.utils;

import java.util.Random;

public class OtherUtils {

    public static String generateFileName() {
        char c = 0;
        StringBuilder sb = new StringBuilder();
        String fileName;
        for (int i = 0; i < 8; i++) {
            Random r = new Random();
            c = (char) (r.nextInt(26) + 'a');
            sb.append(c);
        }
        fileName = sb.toString();
        return fileName;
    }

}
