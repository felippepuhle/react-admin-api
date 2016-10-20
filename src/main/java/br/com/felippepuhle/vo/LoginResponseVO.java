package br.com.felippepuhle.vo;

public class LoginResponseVO {

    private final String token;

    public LoginResponseVO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
