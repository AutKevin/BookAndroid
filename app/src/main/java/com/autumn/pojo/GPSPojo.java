package com.autumn.pojo;


/**
 * Created by Autumn on 2018/7/16.
 */
public class GPSPojo {
    private String userId;   //用户id
    private String userName;   //用户昵称
    private String time;  //时间
    private String locType;
    private String latitude;   //纬度
    private String lontitude;   //经度
    private String radius;
    private String CountryCode;
    private String Country;
    private String citycode;
    private String city;
    private String District;
    private String Street;
    private String addr;   //地址
    private String locationdescribe;//地址描述
    private String UserIndoorState;
    private String Direction;
    private String Poi;
    private String operationers;
    private String describe;

    @Override
    public String toString() {
        return "GPSPojo{" +
                "time='" + time + '\'' +
                ", locType='" + locType + '\'' +
                ", latitude='" + latitude + '\'' +
                ", lontitude='" + lontitude + '\'' +
                ", radius='" + radius + '\'' +
                ", CountryCode='" + CountryCode + '\'' +
                ", Country='" + Country + '\'' +
                ", citycode='" + citycode + '\'' +
                ", city='" + city + '\'' +
                ", District='" + District + '\'' +
                ", Street='" + Street + '\'' +
                ", addr='" + addr + '\'' +
                ", UserIndoorState='" + UserIndoorState + '\'' +
                ", Direction='" + Direction + '\'' +
                ", locationdescribe='" + locationdescribe + '\'' +
                ", Poi='" + Poi + '\'' +
                ", operationers='" + operationers + '\'' +
                ", describe='" + describe + '\'' +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocType() {
        return locType;
    }

    public void setLocType(String locType) {
        this.locType = locType;
    }


    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLontitude() {
        return lontitude;
    }

    public void setLontitude(String lontitude) {
        this.lontitude = lontitude;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getUserIndoorState() {
        return UserIndoorState;
    }

    public void setUserIndoorState(String userIndoorState) {
        UserIndoorState = userIndoorState;
    }

    public String getDirection() {
        return Direction;
    }

    public void setDirection(String direction) {
        Direction = direction;
    }

    public String getLocationdescribe() {
        return locationdescribe;
    }

    public void setLocationdescribe(String locationdescribe) {
        this.locationdescribe = locationdescribe;
    }

    public String getPoi() {
        return Poi;
    }

    public void setPoi(String poi) {
        Poi = poi;
    }

    public String getOperationers() {
        return operationers;
    }

    public void setOperationers(String operationers) {
        this.operationers = operationers;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
