package seals.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * This controller is used for the angular frontend to foward the routes on the frontend app. This is needed because without this, if you go to a link using the angular router, it will first try to send a get request to the spring api first. Since no endpoints exist, it will send a 404.
 */
@Controller
public class RouteController {
    @GetMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/";
    }
}