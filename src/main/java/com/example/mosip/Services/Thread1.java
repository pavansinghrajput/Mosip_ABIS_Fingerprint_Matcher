package com.example.mosip.Services;

import com.example.mosip.Beans.AbisDB;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;

import java.util.ArrayList;

public class Thread1 implements Runnable {
    FingerprintTemplate probe;
    ArrayList<AbisDB> obj;
    int id;
    int start;
    int end;
    ArrayList<Integer> result = new ArrayList<>();

    public Thread1(FingerprintTemplate probe, ArrayList<AbisDB> obj, int id, ArrayList<Integer> result, int start, int end) {
        this.probe = probe;
        this.obj = obj;
        this.id = id;
        this.start = start;
        this.end = end;
        // this.result=result;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            if (obj.get(i).getRefid() == id) continue;
            byte[] xyz = obj.get(i).getSertemplate();
            FingerprintTemplate fp = new FingerprintTemplate(xyz);
            double score = new FingerprintMatcher(probe)
                    .match(fp);

            if (score > 60) {
                System.out.println("*********");
                System.out.println(obj.get(i).getRefid() + "  " + score);
                result.add(obj.get(i).getRefid());
            }
        }
    }

    public ArrayList<Integer> dataHandler() {
        return result;
    }
}
