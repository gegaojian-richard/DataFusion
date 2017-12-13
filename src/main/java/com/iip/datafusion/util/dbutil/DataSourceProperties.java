package com.iip.datafusion.util.dbutil;

public class DataSourceProperties {
    private String id;
    private String displayName;
    private String driverClassName;
    private String url;
    private String username;
    private String password;

    public DataSourceProperties() {
        super();
    }

    public DataSourceProperties(String id, String displayName, String driverClassName, String url, String username, String password) {
        this.id = id;
        this.displayName = displayName;
        this.driverClassName = driverClassName;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
