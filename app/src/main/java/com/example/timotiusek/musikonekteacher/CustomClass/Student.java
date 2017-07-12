package com.example.timotiusek.musikonekteacher.CustomClass;

/**
 * Created by TimotiusEk on 7/8/2017.
 */

public class Student {
    private int studentImage;
    private String courseName;
    private String coursePackage;
    private String studentName;
    private String status;

    public Student(int studentImage, String courseName, String coursePackage, String studentName, String status) {
        this.studentImage = studentImage;
        this.courseName = courseName;
        this.coursePackage = coursePackage;
        this.studentName = studentName;
        this.status = status;
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
