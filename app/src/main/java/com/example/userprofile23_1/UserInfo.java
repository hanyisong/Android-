package com.example.userprofile23_1;

public class UserInfo {
    private String nick_name;
    private String sex;
    private String birthday;
    private String city;
    private String school;
    private String sign;

//    UserInfo(String name,String sex,String birthday,String city,String school,String sign){
//        this.name = name;
//        this.sex = sex;
//        this.birthday = birthday;
//        this.city = city;
//        this.school = school;
//        this.sign = sign;
//    }
//
//    UserInfo(){
//    }

    public String getName() {
        return nick_name;
    }

    public void setName(String name) {
        this.nick_name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
