package com.example.timotiusek.musikonekteacher.CustomClass;

/**
 * Created by TimotiusEk on 7/7/2017.
 */

public class Order {

    int courseId;
    int studentImage;
    String courseName;
    String coursePackage;
    String studentName;
    String status;
    String desc;
    String email;
    String address;
    String phone;

    public Order(int courseId, int studentImage, String courseName, String coursePackage, String studentName, String status) {
        this.courseId = courseId;
        this.studentImage = studentImage;
        this.courseName = courseName;
        this.coursePackage = coursePackage;
        this.studentName = studentName;
        this.status = status;
    }

    public Order(int courseId, int studentImage, String courseName, String coursePackage, String studentName, String status, String desc, String email, String address, String phone) {
        this.courseId = courseId;
        this.studentImage = studentImage;
        this.courseName = courseName;
        this.coursePackage = coursePackage;
        this.studentName = studentName;
        this.status = status;
        this.desc = desc;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getStudentImage() {
        return studentImage;
    }

    public void setStudentImage(int studentImage) {
        this.studentImage = studentImage;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCoursePackage() {
        return coursePackage;
    }

    public void setCoursePackage(String coursePackage) {
        this.coursePackage = coursePackage;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
