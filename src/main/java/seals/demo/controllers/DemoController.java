package seals.demo.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("/resource")
	public Map<String,Object> home() {
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("content", "I'm feeling really Sealy right now");
		return model;
	}
}