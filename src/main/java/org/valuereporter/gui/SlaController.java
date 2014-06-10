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
public class SlaController {
    private static final Logger log = LoggerFactory.getLogger(SlaController.class);

    public static final String PREFIX = "prefix";
    public static final String METHOD_NAME = "methodName";

    @RequestMapping("/sla")
    public ModelAndView showSlaGraph(@RequestParam(value = PREFIX, required = true) String prefix, @RequestParam(value = METHOD_NAME, required = true) String methodName) {
        Map model = new HashMap<String,String>();
        model.put(PREFIX, prefix);
        model.put(METHOD_NAME, methodName);
        log.trace("Input prefix {}, methodName {}", prefix,methodName);
        return new ModelAndView("sla", "model", model);
    }
}
