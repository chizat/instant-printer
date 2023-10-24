package com.printer;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;

import javax.imageio.ImageIO;

public class PrinterAndText
{
    public static void main(final String[] args)
    {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        String text = args[1].toString() + "!";
//        String text = "Griffindor"+ "!";
        printJob.setPrintable(new Printable()
        {
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException
            {
                try
                {
                    BufferedImage img = ImageIO.read(new File(args[0]));
                    if(pageIndex != 0)
                    {
                        return NO_SUCH_PAGE;
                    }
                    graphics.drawImage(img, 72, 248, 370, 228, null);
                    Font originalFont = new Font("TimesRoman", Font.BOLD, 70);
//                    Font modifiedFont = originalFont.deriveFont(AffineTransform.getScaleInstance(.90, 1.2));
                    Font scaledFont = scaleFont(text, new Rectangle(85, 230, 310, 110), graphics, originalFont );
                    graphics.setFont(scaledFont); 
                    graphics.drawString(text.toUpperCase(),85,240); 
                    return PAGE_EXISTS;
                }
                catch(Exception e)
                {
                    System.out.println(e.getMessage());
                    return NO_SUCH_PAGE;
                }
            }
        });
        try
        {
            printJob.print();
        }
        catch(Exception e1)
        {
            e1.printStackTrace();
        }
    }
    
    public static Font scaleFont(String text, Rectangle rect, Graphics g, Font font) {
        int width = g.getFontMetrics(font).stringWidth(text);
        int height = g.getFontMetrics(font).getHeight();
        double heightScale = rect.getHeight() / height;
        double widthScale = rect.getWidth() / width;
        return font.deriveFont(AffineTransform.getScaleInstance(widthScale, heightScale));
    }
}
