package org.valuereporter.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
@Controller
public class ValueController {
    private static final Logger log = LoggerFactory.getLogger(ValueController.class);

    public static final String PREFIX = "prefix";
    public static final String METHOD_NAME = "methodName";

    @RequestMapping("/inuse")
    public ModelAndView showSlaGraph(@RequestParam(value = PREFIX, required = true) String prefix) {
        Map model = new HashMap<String,String>();
        model.put(PREFIX, prefix);
        log.trace("Input prefix {}", prefix);
        return new ModelAndView("inuse", "model", model);
    }

}
