package com.example.lenovo.versionupdate;

/**
 * Created by lenovo on 2017/9/11.
 */

public class Version {
    private int versioncode=200;
    private String url;

    @Override
    public String toString() {
        return "Version{" +
                "versioncode=" + versioncode +
                ", url='" + url + '\'' +
                '}';
    }

    public int getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(int versioncode) {
        this.versioncode = versioncode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Version() {

    }

    public Version(int versioncode, String url) {

        this.versioncode = versioncode;
        this.url = url;
    }
}
