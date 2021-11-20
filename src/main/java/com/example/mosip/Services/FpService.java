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
//                        Files.readAllBytes(Paths.get(String.valueOf(image))),
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
        String ans="";
        Optional<AbisDB> rec = erjpa.findById(id);
        if (rec.isPresent() == false) {
            System.out.println("Finger Print not available in Database");
        } else {
            ArrayList<AbisDB> obj = new ArrayList<>();
            //obj = (ArrayList<AbisDB>) erjpa.findAll();

            byte[] abcd = rec.get().getSertemplate();
            FingerprintTemplate probe = new FingerprintTemplate(abcd);
//t1.stdm.jelgherajgherljh4r.kgnrekjerljh/ljk.tnjk.dgart()
           /* for (int i = 0; i < obj.size(); i++) {
                if (obj.get(i).getRefid() == id) continue;
                byte[] xyz = obj.get(i).getSertemplate();
                FingerprintTemplate fp = new FingerprintTemplate(xyz);
                double score = new FingerprintMatcher(probe)
                        .match(fp);

                System.out.println("*********");
                if (score > 60) {
                    System.out.println(obj.get(i).getRefid()+"  "+score);
                    ans+=obj.get(i).getRefid()+" ";

                }

            }*/

    int i=0;


            while(true)
            {
                obj= (ArrayList<AbisDB>)erjpa.findByOffset(imagelimit,i*imagelimit);
                if(obj.size()==0) break;

                for(int j=0;j<nthread;j++)
                {
                    int next=j+1;
                    int start=(int)Math.ceil(j* obj.size()/nthread);
                    int end=(int)Math.ceil(next* obj.size()/nthread);
                    Thread1 thobj1 = new Thread1(probe,obj,id,result,start,end);
                    Thread t1= new Thread(thobj1);
                    t1.start();
                    t1.join();
                    result.addAll( thobj1.dataHandler());
                }



                obj.clear();
                i++;
            }




          /*  for(int i=0;i<10;i++)
            {
                obj= (ArrayList<AbisDB>)erjpa.findByOffset(100,i*100);
                Thread1 thobj1 = new Thread1(probe,obj,id,result,0);
                Thread t1= new Thread(thobj1);
                t1.start();

                //2nd thread
                Thread1 thobj2 = new Thread1(probe,obj,id,result,obj.size()/2);
                Thread t2= new Thread(thobj2);
                t2.start();
                t1.join();


                t2.join();
                result.addAll( thobj1.dataHandler());
                result.addAll( thobj2.dataHandler());
                obj.clear();
            } */

            //System.out.println(obj.size());
/**
 *              while(true)
 *             {
 *                 obj= (ArrayList<AbisDB>)erjpa.findByOffset(100,i*100);
 *
 *                 for(int i=0;i<2;i++)
 *                 {
 *                     Thread1 thobj1 = new Thread1(probe,obj,id,result,0);
 *                     Thread t1= new Thread(thobj1);
 *                     t1.start();
 *
 *                     //2nd thread
 *                     Thread1 thobj2 = new Thread1(probe,obj,id,result,obj.size()/2);
 *                     Thread t2= new Thread(thobj2);
 *                     t2.start();
 *                     t1.join();
 *                     t2.join();
 *                     result.addAll( thobj1.dataHandler());
 *                     result.addAll( thobj2.dataHandler());
 *                     obj.clear();
 *                 }
 *
 *             }
 * */







        }

        return result;
    }

    public void insertallserv(MultipartFile[] image) throws IOException {
    int p=1050;
        for(int i=0;i< image.length;i++)
        {
            System.out.println(convertToTemplate(image[i], i+p));
        }
    }
}