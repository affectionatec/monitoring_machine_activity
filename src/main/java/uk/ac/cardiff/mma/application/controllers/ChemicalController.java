package uk.ac.cardiff.mma.application.controllers;

import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cardiff.mma.application.DTO.*;
import uk.ac.cardiff.mma.application.repository.ChemicalReportRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ChemicalController {


    private ChemicalReportRepository chemicalReportRepository;


    @Autowired
    public ChemicalController(ChemicalReportRepository chemicalReportRepository) {
        this.chemicalReportRepository = chemicalReportRepository;
    }


    // query all chemical details
    @RequestMapping(value = "/admin/chemicalReport", method = RequestMethod.GET)
    public ModelAndView queryAllChemical() {
        ModelAndView mav = new ModelAndView();
        //delete the expire chemical delivery records.
        List allDetailList = (List) chemicalReportRepository.queryAllChemicalDeliveryDetail();
        for (int i = 0; i < allDetailList.size(); i++) {
            ChemicalDeliveryIDVersionDTO chemicalDeliveryIDVersionDTO = (ChemicalDeliveryIDVersionDTO) allDetailList.get(i);
            if (!determineDate(chemicalDeliveryIDVersionDTO.getExpiry())) {
                chemicalReportRepository.deleteChemicalDeliveryByID(chemicalDeliveryIDVersionDTO.getId());
            }
        }

        mav.addObject("allChemical", codeToImage((List) chemicalReportRepository.queryAllChemical()));
        mav.setViewName("ChemicalReport");
        return mav;
    }

    // delete a chemical record
    @GetMapping("/admin/deleteChemical/{id}")
    public String deleteChemical(@PathVariable("id") Integer id) {
        chemicalReportRepository.deleteChemicalDeliveryRecordByChemicalID(id);
        chemicalReportRepository.deleteChemical(id);
        return "redirect:/admin/chemicalReport";
    }


    //go to update chemical page.
    @RequestMapping(value = "/admin/goIntoUpdatePage/{id}")
    public ModelAndView goIntoUpdatePage(@PathVariable("id") int id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("particularChemical",chemicalReportRepository.queryAChemicalByID(id));
        modelAndView.setViewName("UpdateChemicalDetail");
        return modelAndView;
    }

    // update a chemical detail
    @RequestMapping(value = "/admin/updateChemicalDetail")
    public String updateChemical(@RequestParam(value = "id") int id,
                                 @RequestParam(value = "chemicalName") String chemicalName,
                                 @RequestParam(value = "location") String location,
                                 @RequestParam(value = "unit") String unit,
                                 @RequestParam(value = "storage") double storage,
                                 @RequestParam(value = "bottleNum") int bottleNum,
                                 @RequestParam(value = "level") String level) throws Exception {
        chemicalReportRepository.updateChemicalDetail(level, id,chemicalName,location,unit,bottleNum,storage);
        return "redirect:/admin/chemicalReport";
    }



    //add a chemical
    @RequestMapping(value = "/admin/addChemical", method = RequestMethod.POST)
    public String addChemical(AddingChemicalDTO addingChemicalDTO) throws Exception {
        chemicalReportRepository.addChemical(addingChemicalDTO);
        return "redirect:/admin/chemicalReport";
    }


    //showing the delivery details of a chemical.
    @GetMapping(value = "/admin/viewDelivery/{id}")
    public ModelAndView queryChemicalDeliveryDetailByID(@PathVariable("id") int id) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        ChemicalDTO chemicalDTO = (ChemicalDTO)chemicalReportRepository.queryAChemicalByID(id) ;
        modelAndView.addObject("chemicalName", chemicalDTO.getChemicalName());
        modelAndView.addObject("ChemicalDeliveryDetails", chemicalReportRepository.queryChemicalDeliveryDetailByID(id));
        modelAndView.setViewName("ChemicalDeliveryDetail");
        return modelAndView;
    }


    //add a new chemical delivery record.
    @RequestMapping(value = "/admin/addChemicalRecord", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView addDeliveryRecord(AddingChemicalRecordDTO addingChemicalRecordDTO) throws Exception {
        //add the weight to the total storage.
        double updatedStorage = addingChemicalRecordDTO.getWeight()+chemicalReportRepository.getChemicalStorageByID(chemicalReportRepository.getChemicalID(addingChemicalRecordDTO.getChemicalName()));
        chemicalReportRepository.updateChemicalStorage(updatedStorage,chemicalReportRepository.getChemicalID(addingChemicalRecordDTO.getChemicalName()));
        chemicalReportRepository.addChemicalDeliveryRecord(addingChemicalRecordDTO);
        ModelAndView mav = new ModelAndView("redirect:/admin/viewDelivery/{id}");
        mav.addObject("id", chemicalReportRepository.getChemicalID(addingChemicalRecordDTO.getChemicalName()));
        return mav;
    }

    @RequestMapping(value = "/admin/deleteDeliveryRecord/{id}/{chemicalName}")
    public ModelAndView deleteDeliveryRecord(@PathVariable("id") int id,
                                             @PathVariable("chemicalName") String ChemicalName) {
        chemicalReportRepository.deleteChemicalDeliveryByID(id);
        ModelAndView mav = new ModelAndView("redirect:/admin/viewDelivery/{id}");
        mav.addObject("id", chemicalReportRepository.getChemicalID(ChemicalName));
        return mav;
    }

    @RequestMapping(value = "/admin/updateStorage", method = RequestMethod.GET)
    public ModelAndView updateStorage(@RequestParam(value = "id") int id,
                                      @RequestParam(value = "storage") double storage) throws Exception{
        chemicalReportRepository.updateChemicalStorage(storage,id);
        ModelAndView mav = new ModelAndView("redirect:/admin/chemicalReport");
        return mav;
    }

    // get the year of date
    private int getYear(String date) {
        String[] split = date.split("-");
        int year = Integer.parseInt(split[0]);
        return year;
    }

    //get the monte of date
    private int getMonth(String date) {
        String[] split = date.split("-");
        int month = Integer.parseInt(split[1]);
        return month;
    }

    //get the day of date
    private int getDay(String date) {
        String[] split = date.split("-");
        int day = Integer.parseInt(split[2]);
        return day;
    }

    /*
     * This method determines whether a date is before or after today
     * if the date before the date of today then return false else return true.
     *
     * */

    private boolean determineDate(String date) {
        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String toStringToday = simpleDateFormat.format(today);

        int todayYear = getYear(toStringToday);
        int todayMonth = getMonth(toStringToday);
        int todayDay = getDay(toStringToday);

        int dateYear = getYear(date);
        int dateMonth = getMonth(date);
        int dateDay = getDay(date);
        if (todayYear > dateYear) {
            return false;
        } else if (todayYear == dateYear && todayMonth > dateMonth) {
            return false;
        } else if (todayYear == dateYear && todayMonth == dateMonth && todayDay > dateDay) {
            return false;
        } else {
            return true;
        }
    }

    // Changing the level to picture
    private List codeToImage(List list) {
        List imageList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            ChemicalDTO chemicalDTO = (ChemicalDTO) list.get(i);
            switch (chemicalDTO.getLevel()) {
                case "Explosives":
                    chemicalDTO.setLevelPicture("https://www2.illinois.gov/dnr/mines/EAD/PublishingImages/Explosives%20Sign.jpg");
                    break;
                case "Flammable":
                    chemicalDTO.setLevelPicture("https://i0.wp.com/www.chemstore.ie/wp-content/uploads/2014/10/Flammable-Liquid.jpg?ssl=1");
                    break;
                case "Oxidizing substances and organic peroxides":
                    chemicalDTO.setLevelPicture("https://s3.amazonaws.com/static.wd7.us/b/b7/HAZMAT_Class_5-2_Organic_Peroxide_Oxidizing_Agent.png");
                    break;
                case "Toxic":
                    chemicalDTO.setLevelPicture("https://www.emergingrnleader.com/wp-content/uploads/2018/05/Toxic.jpg");
                    break;
                case "Radioactive":
                    chemicalDTO.setLevelPicture("https://s3.amazonaws.com/healthtap-public/ht-staging/user_answer/reference_image/7544/topic_large/Radioactive.jpeg");
                    break;
                case "corrosion":
                    chemicalDTO.setLevelPicture("https://accuform-img3.akamaized.net/files/damObject/Image/huge/MISO3311.jpg");
                    break;
                default:
                    chemicalDTO.setLevelPicture("Wrong code");
                    break;
            }
            imageList.add(chemicalDTO);
        }
        return imageList;
    }


}
