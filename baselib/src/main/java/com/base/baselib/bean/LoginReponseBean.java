package com.base.baselib.bean;

/**
 * Created by dhy
 * Date: 2019/5/9
 * Time: 11:19
 * describe:
 */
public class LoginReponseBean {

    /**
     * accessToken : eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxODEwMTY3MTk5MCIsImlhdCI6MTUzOTU3NDY1MSwic3ViIjoid3d3LmlkbS5jb20iLCJpc3MiOiJ3d3cuYXBwLmNvbSIsImV4cCI6MTUzOTU3NjQ1MX0.RA2AAJB2tDdpKPaVw38WBUvLaJehiE89Njv60QOw42U
     * refreshToken : eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxODEwMTY3MTk5MCIsImlhdCI6MTUzOTU3NDY1MSwic3ViIjoid3d3LmlkbS5jb20iLCJpc3MiOiJ3d3cuYXBwLmNvbSIsImV4cCI6MTUzOTU3NjQ1MX0.RA2AAJB2tDdpKPaVw38WBUvLaJehiE89Njv60QOw42U
     */

    private String accessToken;
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
