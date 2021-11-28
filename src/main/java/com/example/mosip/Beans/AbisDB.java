package com.example.mosip.Beans;

import javax.persistence.*;

@Entity
@Table(name = "AbisDB")
public class AbisDB {

    @Id
    public int refid;

    @Lob
    @Column(columnDefinition="TEXT")//
    public byte[] sertemplate;

    public AbisDB()
    {

    }
    public AbisDB(int refid, byte[] sertemplate) {
        this.refid = refid;
        this.sertemplate = sertemplate;
    }

    public int getRefid() {
        return refid;
    }

    public void setRefid(int refid) {
        this.refid = refid;
    }

    public byte[] getSertemplate() {
        return sertemplate;
    }

    public void setSertemplate(byte[] sertemplate) {
        this.sertemplate = sertemplate;
    }
}
