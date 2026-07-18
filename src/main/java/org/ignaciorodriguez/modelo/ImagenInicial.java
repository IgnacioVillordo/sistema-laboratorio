package org.ignaciorodriguez.modelo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class ImagenInicial {

    public BufferedImage imagen(String inicial){
    try{
    String yourText = inicial.toUpperCase();
    BufferedImage imagen = new BufferedImage(60, 60,
        BufferedImage.TYPE_INT_RGB);
    
    Graphics graphics = imagen.getGraphics();
    Color verde = new Color(212,232,96);
    graphics.setColor(verde);
    graphics.fillRect(0, 0, 70, 70);
    graphics.setColor(Color.WHITE);
    graphics.setFont(new Font("Arial Black", Font.BOLD, 51));
    graphics.drawString(yourText, 8, 49);
    return imagen;
    } catch(Exception e){
            System.err.println("error "+e);
            return null;
    }
    
  }
    
    public  ImageIcon imagenCircular(BufferedImage imagen) {
        try {
            BufferedImage image = imagen;
            Area clip = new Area(new Rectangle(0, 0, image.getWidth(), image.getHeight()));
            Area oval = new Area(new Ellipse2D.Double(0, 0, image.getWidth() - 1, image.getHeight() - 1));
            clip.subtract(oval);
            Graphics g2d = image.createGraphics();
            g2d.setClip(clip);
            Color beige = new Color(240,240,240);
            g2d.setColor(beige);
            g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
            ImageIcon icono = new ImageIcon(image);
            return icono;
        } catch (Exception ex) {
            System.err.println("Error "+ex);
        }
        return null;
    }
}
