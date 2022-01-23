package uk.ac.cardiff.mma.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cardiff.mma.application.DTO.InvoiceDTO;
import uk.ac.cardiff.mma.application.repository.InvoiceRepositoryJDBC;

import java.util.List;

@Controller
public class InvoiceController {
    @Autowired
    InvoiceRepositoryJDBC invoiceRepositoryJDBC;

    @RequestMapping(path="/admin/invoiceGenerator")
    private ModelAndView openInvoiceGenerator() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("InvoiceGenerator");

        List<String> usernames = invoiceRepositoryJDBC.getUsernames();
        mav.addObject("usernames", usernames);

        List<String> years = invoiceRepositoryJDBC.getBookingYears();
        mav.addObject("years", years);

        return mav;
    }

    @RequestMapping(path="/admin/generateInvoice", method=RequestMethod.POST, consumes="application/x-www-form-urlencoded")
    @ResponseBody
    private ModelAndView generateInvoice(@RequestParam String username,
                                         @RequestParam int month,
                                         @RequestParam int year) {

        List<InvoiceDTO> invoices = invoiceRepositoryJDBC.getInvoice(username, month, year);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("Invoice");

        mav.addObject("username", username);
        mav.addObject("month", month);
        mav.addObject("year", year);
        mav.addObject("invoices", invoices);

        return mav;
    }
}
