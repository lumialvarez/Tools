/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lmalvarez.tools.system;

import com.lmalvarez.tools.system.exception.UnsupportedOperativeSystemException;

/**
 *
 * @author lmalvarez
 */
public class OperativeSystem {

    public static final String OS = System.getProperty("os.name");
    public static final String WINDOWS = "Windows";
    public static final String MAC = "Mac OS X";
    public static final String LINUX = "Linux";
    public static final String SOLARIS = "Solaris";

    public static boolean isWindows() {
        return OS.toLowerCase().contains("win");
    }

    public static boolean isMac() {
        return OS.toLowerCase().contains("mac");
    }

    public static boolean isUnix() {
        String OsNormal = OS.toLowerCase();
        return (OsNormal.contains("nix") || OsNormal.contains("nux") || OsNormal.contains("aix"));
    }

    public static boolean isSolaris() {
        return OS.toLowerCase().contains("sunos");
    }

    public static String getCurrentOS() throws Exception {
        if (isWindows()) {
            return WINDOWS;
        } else if (isMac()) {
            return MAC;
        } else if (isUnix()) {
            return LINUX;
        } else if (isSolaris()) {
            return SOLARIS;
        } else {
            throw new UnsupportedOperativeSystemException();
        }
    }
}
