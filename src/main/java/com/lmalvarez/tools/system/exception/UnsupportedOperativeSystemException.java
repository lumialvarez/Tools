/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lmalvarez.tools.system.exception;

import com.lmalvarez.tools.system.OperativeSystem;

/**
 *
 * @author lmalvarez
 */
public class UnsupportedOperativeSystemException extends Exception {
    
    public UnsupportedOperativeSystemException() {
        super("This OS is not support (OS: \"" + OperativeSystem.OS + "\")");
    }
    
    public UnsupportedOperativeSystemException(String message) {
        super(message + " (OS: \"" + OperativeSystem.OS + "\")");
    }
    
    public UnsupportedOperativeSystemException(String message, Throwable throwable) {
        super(message + " (OS: \"" + OperativeSystem.OS + "\")", throwable);
    }

}
