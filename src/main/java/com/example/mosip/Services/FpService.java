package com.example.mosip.Services;

import com.example.mosip.Beans.AbisDB;
import com.example.mosip.Repository.FpRepository;
import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintImageOptions;
import com.machinezoo.sourceafis.FingerprintTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class FpService {


    @Autowired
    FpRepository erjpa;
    @Value("${abis.nthread}")
    int nthread;
    @Value("${abis.imagelimit}")
    int imagelimit;

    public byte[] convertToTemplate(MultipartFile image, int id) throws IOException {
        FingerprintTemplate probe = new FingerprintTemplate(
                new FingerprintImage(
                        image.getBytes(),
                        new FingerprintImageOptions()
                                .dpi(500)));

        byte[] fingerPrintA = probe.toByteArray();

        AbisDB obj = new AbisDB();
        obj.setRefid(id);
        obj.setSertemplate(fingerPrintA);
        erjpa.save(obj);
        System.out.println(fingerPrintA);
        return fingerPrintA;
    }

    @Transactional
    public ArrayList<Integer> identifyall(int id) throws IOException, InterruptedException {

        ArrayList<Integer> result = new ArrayList<>();
        String ans = "";
        Optional<AbisDB> rec = erjpa.findById(id);
        if (rec.isPresent() == false) {
            System.out.println("Finger Print not available in Database");
        } else {
            ArrayList<AbisDB> obj = new ArrayList<>();
            byte[] abcd = rec.get().getSertemplate();
            FingerprintTemplate probe = new FingerprintTemplate(abcd);
            int i = 0;
            while (true) {
                obj = (ArrayList<AbisDB>) erjpa.findByOffset(imagelimit, i * imagelimit);
                if (obj.size() == 0) break;
                Thread1 thobj1[] = new Thread1[nthread];
                Thread t1[] = new Thread[nthread];
                for (int j = 0; j < nthread; j++) {
                    int next = j + 1;
                    int start = (int) Math.ceil(j * obj.size() / nthread);
                    int end = (int) Math.ceil(next * obj.size() / nthread);
                    thobj1[j] = new Thread1(probe, obj, id, result, start, end);
                    t1[j] = new Thread(thobj1[j]);
                    t1[j].start();
                }
                for (int k = 0; k < nthread; k++) {
                    t1[k].join();
                    result.addAll(thobj1[k].dataHandler());
                }
                obj.clear();
                i++;
            }
        }
        return result;
    }

    public void insertallserv(MultipartFile[] image) throws IOException {
        int p = 1050;
        for (int i = 0; i < image.length; i++) {
            System.out.println(convertToTemplate(image[i], i + p));
        }
    }
}