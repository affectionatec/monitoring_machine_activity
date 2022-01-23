package uk.ac.cardiff.mma.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ConsumableController {

    @RequestMapping(path="/admin/showconsumables")
    private ModelAndView showConsumables() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("consumables");
        return mav;
    }


    @RequestMapping(path="/admin/consumable_operation")
    private ModelAndView consumableOperation() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("ConsumableDetails");
        return mav;
    }

}
