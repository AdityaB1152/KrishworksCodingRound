package com.example.krishworkscodinground.Model;

public class DataModel {
    private String studentId , name;

    public DataModel() {

    }

    public DataModel(String studentId, String name, String marks) {
        this.studentId = studentId;
        this.name = name;
        this.marks = marks;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    private String marks;
}
