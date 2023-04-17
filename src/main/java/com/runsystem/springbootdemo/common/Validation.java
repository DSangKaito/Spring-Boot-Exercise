package com.runsystem.springbootdemo.common;

import com.runsystem.springbootdemo.handler.CustomException;
import lombok.Data;

@Data
public class Validation {

    /** username */
    private String username;

    /** password */
    private String password;

    /** password check */
    private String rePassword;

    /** array byte from username */
    private byte[] checkByteUsername;

    /** array byte from password */
    private byte[] checkBytePassword;

    /** array byte from password check */
    private byte[] checkByteRePassword;

    /**
     * This is constructor have 3 parameters
     * @param username username of the user
     * @param password password of the user
     * @param rePassword password check of the user
     */
    public Validation(String username, String password, String rePassword) {
        this.username = username;
        this.password = password;
        this.rePassword = rePassword;
        this.checkByteRePassword = rePassword.getBytes();
        this.checkByteUsername = username.getBytes();
        this.checkBytePassword = password.getBytes();
    }

    /**
     * This is constructor have 2 parameters
     * @param username username of the user
     * @param password password of the user
     */
    public Validation(String username, String password) {
        this.username = username;
        this.password = password;
        this.rePassword = null;
        this.checkByteRePassword = null;
        this.checkByteUsername = username.getBytes();
        this.checkBytePassword = password.getBytes();
    }

    /**
     * This is function to check input validity
     * @throws CustomException invalid parameters
     */
    public void checkValid() {
        if (checkByteUsername.length != username.length()) {
            throw new CustomException("401", "Username chỉ được dùng ký tự thường.");
        }
        if (checkBytePassword.length != password.length()) {
            throw new CustomException("401", "Password chỉ được dùng ký tự thường.");
        }
        if (checkBytePassword.length < 6) {
            throw new CustomException("401", "Password phải lớn hơn hoặc bằng 6 ký tự");
        }
        if (checkBytePassword.length > 15) {
            throw new CustomException("401", "Password phải nhỏ hơn hoặc bằng 15 ký tự");
        }
        if (checkByteUsername.length > 20) {
            throw new CustomException("401", "Username tối đa 20 ký tự.");
        }
        if (username.length() == 0) {
            throw new CustomException("401", "Username trống.");
        }
        if (rePassword != null) {
            if (!rePassword.equals(password)) {
                throw new CustomException("401", "Password không trùng nhau.");
            }
        }
    }
}
