package com.dheeraj.passwordmanager;

public class Model {
    public static final int TEXT_TYPE=0;
    public static final int IMAGE_TYPE=1;
    public static final int AUDIO_TYPE=2;

    public int type;
    public int data;
    public String application;
    public String userName;
    public String domain;
    public String password;
    public String url;

    public Model(String application,String userName,String domain,String password,String url)
    {
        this.userName=userName;
        this.password=password;
        this.application=application;
        this.domain=domain;
        this.url=url;
    }
}
