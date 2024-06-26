package seals.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @RequestMapping("/resource")
	public Map<String,Object> home() {
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("content", "I'm feeling really Sealy right now");
		return model;
	}
}
