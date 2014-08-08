/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moduli;

import java.io.Serializable;

/**
 *
 * @author simone
 */
public class Account implements Serializable{
    private OptionManager option;
    private String account;
    private String accessToken;
    private String clientToken;

    public Account(OptionManager option, String account, String accessToken, String clientToken) {
        this.option = option;
        this.account = account;
        this.accessToken = accessToken;
        this.clientToken = clientToken;
    }
    
    public Account(OptionManager option, String account) {
        this.option = option;
        this.account = account;
    }

    public OptionManager getOption() {
        return option;
    }

    public void setOption(OptionManager option) {
        this.option = option;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getClientToken() {
        return clientToken;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }
    
    
    
}
