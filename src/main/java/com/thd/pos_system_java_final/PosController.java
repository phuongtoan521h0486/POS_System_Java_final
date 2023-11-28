package com.thd.pos_system_java_final;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PosController {
    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "Welcome My POS system";
    }

    @GetMapping("/checkout")
    public String checkOut() {
        return "checkout";
    }
}
