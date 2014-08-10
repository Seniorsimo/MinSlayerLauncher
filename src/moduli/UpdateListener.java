/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moduli;

/**
 *
 * @author Simone
 */
public interface UpdateListener {
    
    public void refreshStatus(String status, String currentIndex, String total, String nameFile, double percent);
    
}
