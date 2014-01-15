package com.tentelemed.archipel.core.infrastructure.config;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 15/01/14
 * Time: 15:16
 */
public class VersionInfo {
    private String version;
    private String teamcity;

    public VersionInfo(String version, String teamcity) {
        this.version = version;
        this.teamcity = teamcity;
    }

    public String getVersion() {
        return version;
    }

    public String getTeamcity() {
        return teamcity;
    }
}
