package com.ums.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class VerifyCodeUtil {
	private static int width = 100;//¶¨ÒåÍ¼Æ¬µÄwidth
    private static int height = 32;//¶¨ÒåÍ¼Æ¬µÄheight
    private static int codeCount = 4;//¶¨ÒåÍ¼Æ¬ÉÏÏÔÊ¾ÑéÖ¤ÂëµÄ¸öÊý
    private static int xx = 22;
    private static int fontHeight = 25;
    private static int codeY = 26;
    static char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', 'a','b','c','d','e','f','g','h','i','j','k','l','m','n',
            'o','p','q','r','s','t','u','v','w','x','y','z','0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9' };
 // ¶¨ÒåÍ¼Ïñbuffer
    static BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    static Graphics gd = buffImg.getGraphics();
    // ´´½¨Ò»¸öËæ»úÊýÉú³ÉÆ÷Àà
    static Random random = new Random();
    
    public static String getVerCode() {
    	 // ½«Í¼ÏñÌî³äÎª°×É«
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, width, height);
        // ´´½¨×ÖÌå£¬×ÖÌåµÄ´óÐ¡Ó¦¸Ã¸ù¾ÝÍ¼Æ¬µÄ¸ß¶ÈÀ´¶¨¡£
        Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
        // ÉèÖÃ×ÖÌå¡£
        gd.setFont(font);

        // »­±ß¿ò¡£
        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, width - 1, height - 1);

        // Ëæ»ú²úÉú40Ìõ¸ÉÈÅÏß£¬Ê¹Í¼ÏóÖÐµÄÈÏÖ¤Âë²»Ò×±»ÆäËü³ÌÐòÌ½²âµ½¡£
        int red = 0, green = 0, blue = 0;
//        gd.setColor(Color.GREEN);
        for (int j = 0; j < 40; j++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);

            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);

            // ÓÃËæ»ú²úÉúµÄÑÕÉ«½«ÑéÖ¤Âë»æÖÆµ½Í¼ÏñÖÐ¡£
            gd.setColor(new Color(red, green, blue));
            gd.drawLine(x, y, x + xl, y + yl);
        }

        // randomCodeÓÃÓÚ±£´æËæ»ú²úÉúµÄÑéÖ¤Âë£¬ÒÔ±ãÓÃ»§µÇÂ¼ºó½øÐÐÑéÖ¤¡£
        StringBuffer randomCode = new StringBuffer();

        // Ëæ»ú²úÉúcodeCountÊý×ÖµÄÑéÖ¤Âë¡£
        for (int i = 0; i < codeCount; i++) {
            // µÃµ½Ëæ»ú²úÉúµÄÑéÖ¤ÂëÊý×Ö¡£
            String code = String.valueOf(codeSequence[random.nextInt(62)]);
            // ²úÉúËæ»úµÄÑÕÉ«·ÖÁ¿À´¹¹ÔìÑÕÉ«Öµ£¬ÕâÑùÊä³öµÄÃ¿Î»Êý×ÖµÄÑÕÉ«Öµ¶¼½«²»Í¬¡£
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);

            // ÓÃËæ»ú²úÉúµÄÑÕÉ«½«ÑéÖ¤Âë»æÖÆµ½Í¼ÏñÖÐ¡£
            gd.setColor(new Color(red, green, blue));
            gd.drawString(code, 8 + i * xx, codeY);

            // ½«²úÉúµÄËÄ¸öËæ»úÊý×éºÏÔÚÒ»Æð¡£
            randomCode.append(code);
        }
        
        return randomCode.toString();
    }
}
