package com.example.StartWithTextract.Controller;
import com.example.StartWithTextract.BusinessLayer.Service;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api")
public class Controller {

    private Service service;

     @GetMapping("/gettext")
     public String getdata(@RequestParam String imgPath){
         // send the image data if someone wants to retrieve particular image data
         // http://localhost:8080/api/gettext?imgPath=C:/Users/HP/Desktop/ZoomCar/ConcatenatedImages/image2.jpeg
          service=new Service();
          System.out.println(imgPath);
           return service.getdata(imgPath);
     }

     @GetMapping("/documenttype")
     @ResponseBody
     public String DocumentType(@PathVariable String imgPath){
         // return the finalised ans(documentType) for a particular image
         return "";
     }
}
