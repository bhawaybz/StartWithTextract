package com.example.StartWithTextract.controller;

import com.example.StartWithTextract.businesslayer.Service;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.Map;
import org.slf4j.Logger;

@RestController
@RequestMapping("/apiClassifier")
public class Controller {
    private Service service;
    Logger logger = LoggerFactory.getLogger(Controller.class);
    @GetMapping("/getdocument")
    public String getdata(@RequestParam Map<String, String> map) {
        service = new Service();

        ArrayList<String> imgPaths = new ArrayList<>();
        String url1=map.get("img1");
        String url2=map.get("img2");
        imgPaths.add(url1);
        imgPaths.add(url2);
        logger.trace(url1);
        logger.trace(url2);

        return service.getDocumentType(imgPaths);
    }
}
//zoomcar-user-profiling.s3.ap-south-1.amazonaws.com/production/3235836/2e372387aac3.png
//https://zoomcar-user-profiling.s3.ap-south-1.amazonaws.com/production/3235836/2e372387aac3.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=ASIAW3CG5LCAE7APUNNM%2F20210512%2Fap-south-1%2Fs3%2Faws4_request&X-Amz-Date=20210512T102015Z&X-Amz-Expires=1800&X-Amz-Security-Token=IQoJb3JpZ2luX2VjEDIaCmFwLXNvdXRoLTEiRzBFAiEAkUN0rYlyHu3vp2m8tQD73KSbBnrVQv6iI1heMlM%2BhuECICb9BLcbnQtXtScSd6nWF%2B%2BdE82E%2FC6cGE7Hqw7DcQiSKr8DCLv%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEQABoMNDcwNDQ3NjQyNzUyIgzdKUfh%2B7h0u7zRa0YqkwNHEWURjVZEeMcfUQV2S9PHwBSsimMPRuvw8GLNmhsTFy%2FngylOlDruQaKLgoOjgjsmPAkf0o8aWTHAe%2BEFDoyT%2F9pXehNizKVtJKjXjm%2FOI%2BNGX3h4roy%2BlelelartLKgxlgPFHGTvtsUnQMdc7JIEaCpMj2Xei%2Bq5NL0WA6rPPdeSxu5A%2BHexdmv5MJIfudIOF6ZdvbtrUlEc5qdL%2BMEuWbtxB%2Fyj2K2j8L8NLNWGVsY4WxqskF4BmttYUWrko%2BN1LV1ZfP06k%2FPSW6RUeBmLuL60uplB3NfZI3g%2B4GW18wzFpJH%2FT9u%2FLxTzl3rUtE8zYQaho1Ku3fFZ1kWQkObS2nc6R5W45qlgrmQgEthN2dCsWRCjlm6Qov1F1N8VvMbpuXAlWOPuAsbP2O%2FZ%2FyU51hL%2BsmPmP1ol2dOwoLrBZoV%2BwOhOsohartuyDtH8%2F8BCjEEaYfzG0dA6cvZk3IDnpBQFwm8U1DGlijhHCh9NxGeB9MfiIQ9%2Fga%2FQCDYOIXGisnUaKuXkUlL02efUIf6jt5kAMJrI7oQGOusBciw%2Bpx6l0KVNWQtbWWZ2CriBLwHxhcZ7n14EVlREX6639sRGxRvjnUv6XbT5JYcNF7ZDB3lATKaz79oYlEw3QWe4qeDY%2F%2FpnH%2FfwYS8mVaDvrFM6H7FOZVmt7NLyNe4tEglnPhMkCKQg8Jk9DOCnUoqj5fs%2BEARpdADEHVjTsZSAdKuNvbzTsieZbMyt%2FBN%2FhbdSBsQuSMPcRlTYR2C9sPQenusGAfyvSm5ssFyNAEhwyxzKaAHQdOVS4wR8%2FTDYwLDa%2FGbwbzzMvPdrmfNEbebaTIq0Uifu8VSAJDzv1ZYcJRGaoh%2BGBxdLBQ%3D%3D&X-Amz-SignedHeaders=host&X-Amz-Signature=d9ac8550c78fd2d6b716af9ef24d4442ecd1617d929da1dd8b0750015fc23a1b
