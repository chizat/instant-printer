package com.printer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;

import javax.imageio.ImageIO;

public class Printer
{
    public static void main(final String[] args)
    {
        PrinterJob printJob = PrinterJob.getPrinterJob();
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
                    graphics.drawImage(img, 92, 248, 342, 213, null);
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
}
