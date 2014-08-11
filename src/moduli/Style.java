/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moduli;

import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author simone
 */
public class Style {
    //colors
    public static Color mainBackground = Color.BLACK;
    public static Color mainForeground = Color.white;
    public static Color buttonBackground = Color.BLUE;
    public static Color buttonForeground = Color.orange;
    public static Color barBackground = new Color(/*0xD8D8D8*/10,10,10,100);
    public static Color barForeground = new Color(0xFF9933);
    
    //font
    public static Font titleFont = new Font("Arial", Font.BOLD, 26);
    public static Font dateFont = new Font("Arial", Font.PLAIN, 11);
    public static Font buttonFont = new Font("Arial", Font.PLAIN, 12);
    
    //hardCoded parts
    public static String prePosts = "<html><font size=\"5\" style=\"color: #ffffff\">";
    public static String postPosts = "</font></html>";
}
