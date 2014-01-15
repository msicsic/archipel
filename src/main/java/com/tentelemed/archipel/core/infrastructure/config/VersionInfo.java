package com.tentelemed.archipel.core.infrastructure.config;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 15/01/14
 * Time: 15:16
 */
public class VersionInfo {
    String version;

    public VersionInfo(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
