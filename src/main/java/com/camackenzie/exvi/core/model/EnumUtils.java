/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model;

/**
 *
 * @author callum
 */
public class EnumUtils {

    public static <T extends Enum<T>> T enumFromString(Class<T> en, String st) {
        for (var enm : en.getEnumConstants()) {
            if (enm.toString().equalsIgnoreCase(st)) {
                return enm;
            }
        }
        throw new RuntimeException("Enum type could not be found froms string: " + st);
    }

    public static String formatName(String superStr) {
        return superStr.toLowerCase().replaceAll("_", " ");
    }

}
