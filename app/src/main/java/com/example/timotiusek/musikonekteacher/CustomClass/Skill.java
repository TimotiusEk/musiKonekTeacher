package com.example.timotiusek.musikonekteacher.CustomClass;

/**
 * Created by TimotiusEk on 5/6/2017.
 */

public class Skill {
    String instrument;
    String detail;

    public Skill(String instrument, String detail) {
        this.instrument = instrument;
        this.detail = detail;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
