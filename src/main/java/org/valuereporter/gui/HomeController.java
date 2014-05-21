package org.valuereporter.gui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public ModelAndView test() {
        String message = "Welcome to Spring 4.0 !";
        return new ModelAndView("hello", "message", message);
    }
}
