package com.example.juasa.apporganizador;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class Encriptacion {

        public static String encriptarPass (String password){
            String passEncriptada = "";
            try {
                byte[] passByte = password.getBytes();
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(passByte);
                byte[] resumenHash = md.digest();
                passEncriptada = new String(resumenHash);
            }catch (NoSuchAlgorithmException e) {
            }
            return passEncriptada;
        }
    }
