package com.example.timotiusek.musikonekteacher.CustomClass;

/**
 * Created by TimotiusEk on 7/7/2017.
 */

public class Attendance {
    private int studentImage;
    private String courseName;
    private String whichAttendance; //Contoh : Pertemuan 1, Pertemuan 2, dst.
    private String studentName;
    private String appointmentID;
    private boolean teacherAttendance;

    public boolean isTeacherAttendance() {
        return teacherAttendance;
    }

    public void setTeacherAttendance(boolean teacherAttendance) {
        this.teacherAttendance = teacherAttendance;
    }

    public Attendance(int studentImage, String courseName, String whichAttendance, String studentName, String appointmentID, boolean teacherAttendance) {
        this.studentImage = studentImage;
        this.courseName = courseName;
        this.whichAttendance = whichAttendance;
        this.studentName = studentName;
        this.appointmentID = appointmentID;
        this.teacherAttendance = teacherAttendance;
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
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

    public String getWhichAttendance() {
        return whichAttendance;
    }

    public void setWhichAttendance(String whichAttendance) {
        this.whichAttendance = whichAttendance;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
