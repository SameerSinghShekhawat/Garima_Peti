package com.example.garimapeti.entity;

import java.io.Serializable;
import java.util.Date;

public class ReportEntity implements Serializable {

    String name;
    String clss;
    String school;
    String phone;
    String reportDesc;
    String reportDate;
    public ReportEntity() {
    }

    public ReportEntity(String name, String clss, String school, String phone, String reportDesc, String reportDate) {
        this.name = name;
        this.clss = clss;
        this.school = school;
        this.phone = phone;
        this.reportDesc = reportDesc;
        this.reportDate = reportDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClss() {
        return clss;
    }

    public void setClss(String clss) {
        this.clss = clss;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReportDesc() {
        return reportDesc;
    }

    public void setReportDesc(String reportDesc) {
        this.reportDesc = reportDesc;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }
}
