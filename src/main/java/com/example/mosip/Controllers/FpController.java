package com.example.mosip.Controllers;

import com.example.mosip.Beans.AbisDB;
import com.example.mosip.Services.FpService;
import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintImageOptions;
import com.machinezoo.sourceafis.FingerprintTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FpController {
    // @RequestMapping("/hello")
    @Autowired
    FpService es;



    @RequestMapping(value = "/insert", method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public byte[] insert(@RequestParam MultipartFile image, @RequestParam int id) throws IOException {
        //add your logics here
        //File newFile = new File("blablabla.xxx");
        //file.transferTo(newFile);

//        Path filepath = Paths.get(dir.toString(), image.getOriginalFilename());
        return es.convertToTemplate(image,id);
    }

//    @GetMapping("/identify")
//    public ArrayList<AbisDB> identify() throws IOException {
//        //add your logics here
//        //File newFile = new File("blablabla.xxx");
//        //file.transferTo(newFile);
//        return es.rtrfps();
//
////        Path filepath = Paths.get(dir.toString(), image.getOriginalFilename());
//     //   return es.convertToTemplate(image,id);
//    }

    @RequestMapping(value = "/identify", method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String identify(@RequestParam int id) throws IOException, ClassNotFoundException {
        //add your logics here
        //File newFile = new File("blablabla.xxx");
        //file.transferTo(newFile);

//        Path filepath = Paths.get(dir.toString(), image.getOriginalFilename());
        return es.identifyall(id);
    }





}