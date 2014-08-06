/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moduli;

/**
 *
 * @author simone
 */
public class Controller {
    private state status = state.loading;
    
    
    
    private static enum state{
        loading, idle, logging, waiting, option, running;
    }
}
