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
    public static Color buttonBackground = new Color(120,195,120,255);
    public static Color buttonForeground = new Color(145,50,45,255);
    public static Color barBackground = new Color(/*0xD8D8D8*/50,185,50,80);
    public static Color barForeground = new Color(/*0xFF9933*/50,185,50,255);
    public static Color titleNewsColor = new Color(227,189,38);
    public static Color titleChangeColor = new Color(197,159,8);
    public static Color dateNewsColor = Color.white;
    
    //font
    public static Font titleNewsFont = new Font("Arial", Font.BOLD, 26);
    public static Font titleChangeFont = new Font("Arial", Font.BOLD, 20);
    public static Font dateFont = new Font("Arial", Font.PLAIN, 11);
    public static Font buttonFont = new Font("Arial", Font.BOLD, 12);
    
    //hardCoded parts
    public static String preNewsPosts = "<html><font size=\"4\" style=\"color: #ffffff\">";
    public static String preChangePosts = "<html><font size=\"4\" style=\"color: #ffffff\">";
    public static String preLog = "<html><font size=\"4\" style=\"color: #ffffff\">";
    public static String postNewsPosts = "</font></html>";
    public static String postChangePosts = "</font></html>";
    public static String postLog = "</font></html>";
}
