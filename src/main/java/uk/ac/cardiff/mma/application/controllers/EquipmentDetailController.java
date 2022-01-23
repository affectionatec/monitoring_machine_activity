package uk.ac.cardiff.mma.application.controllers;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import uk.ac.cardiff.mma.application.DTO.EquipmentDetailDTO;
import uk.ac.cardiff.mma.application.PDFGenerator;
import uk.ac.cardiff.mma.application.equipment.entity.Equipment;
import uk.ac.cardiff.mma.application.equipment.repositories.EquipmentRepository;
import uk.ac.cardiff.mma.application.repository.EquipmentDetailRepository;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class EquipmentDetailController {

    @Autowired
    ServletContext servletContext;

    private final TemplateEngine templateEngine;

    public EquipmentDetailRepository equipmentDetailRepo;

    public EquipmentRepository equipmentRepo;

    @Autowired
    public EquipmentDetailController(EquipmentDetailRepository equipmentDetailRepo, EquipmentRepository equipmentRepo, TemplateEngine templateEngine) {
        this.equipmentDetailRepo = equipmentDetailRepo;
        this.equipmentRepo = equipmentRepo;
        this.templateEngine = templateEngine;
    }

//    @Autowired
//    private PdfGenaratorUtil pdfGenaratorUtil;

    // Pull equipment data from the database.
    @RequestMapping(path = "/admin/equipmentDetail", method = RequestMethod.GET)
    public ModelAndView getEquipmentDetail() {
        ModelAndView mav = new ModelAndView();
        System.out.println("Return all equipment");
        mav.addObject("allEquipmentDetails", equipmentDetailRepo.findAllEquipmentDetails());
        mav.setViewName("EquipmentDetail");
//        System.out.println("Rendering the page");
        return mav;
    }

    @RequestMapping(path = "/ScheduleReminder", method = RequestMethod.GET)
    public ModelAndView getEquipmentReminder() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("ScheduleReminder");
        return mav;
    }

    // Adapted from: https://springhow.com/spring-boot-pdf-generation/
    @RequestMapping(path = "/pdf")
    public ResponseEntity<?> getPDF(HttpServletRequest request, HttpServletResponse response) throws IOException {

        /* Create HTML using Thymeleaf template Engine */
        WebContext webContext = new WebContext(request, response, servletContext);
        webContext.setVariable("allEquipmentDetails", equipmentDetailRepo.findAllEquipmentDetails());
        String pdfHtml = templateEngine.process("EquipmentDetailPdfVersion", webContext);

        /* Setup Source and target I/O streams */
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("http://localhost:8080");

        /* Call convert method */
        HtmlConverter.convertToPdf(pdfHtml, outputStream, converterProperties);

        /* extract output as bytes */
        byte[] bytes = outputStream.toByteArray();

        /* Send the response as downloadable PDF */
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=detail.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);

    }

    @GetMapping(value = "/newest/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getPDFReport() throws IOException {
        List<Equipment> equipmentS = (List<Equipment>) equipmentRepo.findAll();

        ByteArrayInputStream bis = PDFGenerator.equipmentDetailPDFReport(equipmentS);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=detail.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

}
