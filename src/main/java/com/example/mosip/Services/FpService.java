package com.example.mosip.Services;

import com.example.mosip.Beans.AbisDB;
import com.example.mosip.Repository.FpRepository;
import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintImageOptions;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FpService {


    @Autowired
    FpRepository erjpa;


    public void createAbisDB(AbisDB e) {
        erjpa.save(e);
    }

    public List<AbisDB> getAllAbisDBs() {
        return erjpa.findAll();
    }

    public byte[] serial(FingerprintTemplate arr) {

//        String temp="";
//        boolean check=true;
//        while(check)
//        {
//            try
//            {
//                ByteArrayOutputStream bo=new ByteArrayOutputStream();
//                ObjectOutputStream so=new ObjectOutputStream(bo);
//                so.writeObject(arr);
//                so.flush();
//                temp=bo.toString();
//                return temp;
//            }
//            catch (Exception e)
//            {
//                check=false;
//                System.out.println(e+" Serialization exception***");
//            }
//        }
//        return temp;
        return arr.toByteArray();
    }

    public byte[] convertToTemplate(MultipartFile image, int id) throws IOException {
        FingerprintTemplate probe = new FingerprintTemplate(
                new FingerprintImage(
//                        Files.readAllBytes(Paths.get(String.valueOf(image))),
                        image.getBytes(),
                        new FingerprintImageOptions()
                                .dpi(500)));
        /**
         * public void write(MultipartFile file, Path dir) {
         *     Path filepath = Paths.get(dir.toString(), file.getOriginalFilename());
         *     try (OutputStream os = Files.newOutputStream(filepath)) {
         *         os.write(file.getBytes());
         *     }Subah
         * }
         * */
//        Path dire = null;
//        Path filepaths = Paths.get(dire.toString(), image.getOriginalFilename());
        //temp
//        FingerprintTemplate candidate = new FingerprintTemplate(
//                new FingerprintImage(
////                        Files.readAllBytes(Paths.get(String.valueOf(image))),
//                        image.getBytes(), //multipart file to byte array
//                        new FingerprintImageOptions()
//                                .dpi(500)));
//        System.out.println(candidate);
        //Serialization
        byte[] fingerPrintA = serial(probe);
        //  String fingerPrintB=serial(candidate);

        //System.out.println(fingerPrintB);
        // double score = new FingerprintMatcher(probe)
        //  .match(candidate);

        AbisDB obj = new AbisDB();
        obj.setRefid(id);
        obj.setSertemplate(fingerPrintA);
        erjpa.save(obj);
        System.out.println(fingerPrintA);
        return fingerPrintA;

    }

    //    public ArrayList<AbisDB> rtrfps() {
//        ArrayList<AbisDB> obj=new ArrayList<>();
//         obj= (ArrayList<AbisDB>) erjpa.findAll();
//         return obj;
//    }
    public String identifyall(int id) throws IOException {

//        FingerprintTemplate probe = new FingerprintTemplate(
//                new FingerprintImage(
////                        Files.readAllBytes(Paths.get(String.valueOf(image))),
//                        image.getBytes(),
//                        new FingerprintImageOptions()
//                                .dpi(500)));

        // id-check in database if present or not

        // Optional<AbisDB> image=erjpa.findById(id);
        String ans="";
        Optional<AbisDB> rec = erjpa.findById(id);
        if (rec.isPresent() == false) {
            System.out.println("Finger Print not available in Database");
        } else {
            ArrayList<AbisDB> obj = new ArrayList<>();
            obj = (ArrayList<AbisDB>) erjpa.findAll();

            byte[] abcd = rec.get().getSertemplate();
            FingerprintTemplate probe = new FingerprintTemplate(abcd);
            //creating fingerprint

            for (int i = 0; i < obj.size(); i++) {
                if (obj.get(i).getRefid() == id) continue;
                byte[] xyz = obj.get(i).getSertemplate();
                FingerprintTemplate fp = new FingerprintTemplate(xyz);
//            fp.deserialize(xyz);//
//            fp.toByteArray();
                double score = new FingerprintMatcher(probe)
                        .match(fp);

                System.out.println("*********");
                if (score > 60) {
                    System.out.println(obj.get(i).getRefid()+"  "+score);
                    ans+=obj.get(i).getRefid()+" ";

                }
                else
                    System.out.println(obj.get(i).getRefid()+" didnt match"+"  "+score);
            }

        }
        //
//        ArrayList<AbisDB> obj=new ArrayList<>();
//        obj= (ArrayList<AbisDB>) erjpa.findAll();
//
//        for(int i=0;i<obj.size();i++)
//        {
////            boolean check=true;
////            while(check)
////            {
////                try
////                {
////                    String xyz=obj.get(i).getSertemplate();
////                    byte[] b=xyz.getBytes();
////                    ByteArrayInputStream bi=new ByteArrayInputStream(b);
////                    ObjectInputStream si=new ObjectInputStream(bi);
////
////                    FingerprintTemplate fp=(FingerprintTemplate) si.readObject() ;
////                    double score = new FingerprintMatcher(probe)
////                            .match(fp);
////                    System.out.println(score);
////                    if(score>60)
////                        return "Fingerprint Matched";
////                }
////                catch(Exception e)
////                {
////                    check=false;
////                    System.out.println(e+" Deserial");
////                }
////
////            }
//            // FingerprintTemplate fp = null;
//         //   fp=fp.deserialize(xyz);
//            // string to byte array
//            byte[] xyz=obj.get(i).getSertemplate();
//            FingerprintTemplate fp=new FingerprintTemplate(xyz) ;
////            fp.deserialize(xyz);//
////            fp.toByteArray();
//            double score = new FingerprintMatcher(probe)
//                            .match(fp);
//            System.out.println(score);
//            System.out.println("*********");
//            if(score>60)
//                return "Fingerprint Matched";
//        }
//        return "Fingerprint didn't Match.";
//    }
        return ans;
    }

}