package com.example.timotiusek.musikonekteacher.CustomClass;

/**
 * Created by TimotiusEk on 7/7/2017.
 */

public class Earning {
    private int studentImage;
    private String courseName;
    private String coursePackage;
    private String studentName;

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    private String status;
    private String courseID;

    public Earning(int studentImage, String courseName, String coursePackage, String studentName, String status, String courseID) {
        this.studentImage = studentImage;
        this.courseName = courseName;
        this.coursePackage = coursePackage;
        this.studentName = studentName;
        this.status = status;
        this.courseID = courseID;
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
}
