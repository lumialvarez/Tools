/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumialvarez.tools.system;

/**
 *
 * @author luigu
 */
public class UnsupportedOperativeSystemException extends Exception {
    
    UnsupportedOperativeSystemException() {
        super("This OS is not support (OS: \"" + OperativeSystem.OS + "\")");
    }
    
    UnsupportedOperativeSystemException(String message) {
        super(message + " (OS: \"" + OperativeSystem.OS + "\")");
    }
    
    UnsupportedOperativeSystemException(String message, Throwable throwable) {
        super(message + " (OS: \"" + OperativeSystem.OS + "\")", throwable);
    }

}
