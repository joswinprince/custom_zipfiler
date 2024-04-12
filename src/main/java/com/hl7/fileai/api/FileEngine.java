package com.hl7.fileai.api;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("fileai")
public class FileEngine {
@GetMapping("/file")
public String HelloWorld(){
return "get file";
}

@GetMapping("/hl7")
public String hl7Page() {
    return "index"; // This corresponds to the filename without the extension
}
}