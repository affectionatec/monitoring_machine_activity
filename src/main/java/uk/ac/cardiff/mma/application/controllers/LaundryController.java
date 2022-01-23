package uk.ac.cardiff.mma.application.controllers;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cardiff.mma.application.DTO.EquipmentDetailDTO;
import uk.ac.cardiff.mma.application.DTO.EquipmentUsageDTO;
import uk.ac.cardiff.mma.application.DTO.LaundryDetailDTO;
import uk.ac.cardiff.mma.application.DTO.OccupiedEquipmentDTO;
import uk.ac.cardiff.mma.application.repository.LaundryRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LaundryController {

    private final LaundryRepository laundryRepository;

    @Autowired
    public LaundryController(LaundryRepository laundryRepos) {
        laundryRepository=laundryRepos;
    }

    @RequestMapping(path="/admin/laundryInfo",method = RequestMethod.GET)
    public ModelAndView getLaundryInfo() {
        ModelAndView mav = new ModelAndView();
        List<LaundryDetailDTO> laundries= laundryRepository.findAllLaundryInfo();

        //use data from databases to show equipmentUsageList table
        mav.addObject("laundries",laundries);
        mav.setViewName("LaundryPage");
        return mav;

    }

    @RequestMapping(path="/getLaundryInfoByCode", method = RequestMethod.POST)
    @ResponseBody
    public String postLaundryInfo(@RequestBody JSONObject info){
        String barcode =info.getString("barcode");
        List<LaundryDetailDTO> boots= laundryRepository.findAllBootsInfo_byBarcode(barcode);
        List<LaundryDetailDTO> laundry= new ArrayList<>();

        for (LaundryDetailDTO boot : boots) {
            boot.setItem("boots");
            laundry.add(boot);
        }
        List<LaundryDetailDTO> suits= laundryRepository.findAllSuitInfo_byBarcode(barcode);

        for (LaundryDetailDTO suit : suits) {
            suit.setItem("suit");
            laundry.add(suit);
        }

        List<LaundryDetailDTO> hoods= laundryRepository.findAllHoodInfo_byBarcode(barcode);

        for (LaundryDetailDTO hood : hoods) {
            hood.setItem("hood");
            laundry.add(hood);
        }

        //use data from databases to show laundry info table
        return JSONObject.toJSONString(laundry);

    }

    @RequestMapping(path = "/update/laundryItems", method = RequestMethod.POST)
    @ResponseBody
    public String postLaundryItem(@RequestBody JSONObject info){
        int id =Integer.parseInt(info.getString("id"));
        String barcode=info.getString("barcode");
        String item =info.getString("item");
        String size =info.getString("size");
        String status =info.getString("status");
        boolean stat = false;
        if (item.equals("suit")){
            stat=laundryRepository.updateSuit(id,status,size);
        }
        else if (item.equals("boots")){
            stat=laundryRepository.updateBoots(id,status,size);
        }
        else if (item.equals("hood")) {
            stat=laundryRepository.updateHood(id,status,size);
        }else {return "fail";}
        return getRepoResultStatus(barcode, stat);
    }

    @RequestMapping(path = "/add/laundryItems", method = RequestMethod.POST)
    @ResponseBody
    public String addLaundryItem(@RequestBody JSONObject info){

        String barcode=info.getString("barcode");
        String item =info.getString("item");
        String size =info.getString("size");
        String status =info.getString("status");
        LaundryDetailDTO laundryDetailDTO =laundryRepository.findAllLaundryInfo_byBarcode(barcode).get(0);
        int laundryId=Integer.parseInt(laundryDetailDTO.getLaundry_id());
        boolean stat = false;
        if (item.equals("suit")){
            stat=laundryRepository.insertSuit(status,size,laundryId);
        }
        else if (item.equals("boots")){
            stat=laundryRepository.insertBoots(status,size,laundryId);
        }
        else if (item.equals("hood")) {
            stat=laundryRepository.insertHood(status,size,laundryId);
        }else {return "fail";}
        return getRepoResultStatus(barcode, stat);
    }


    @RequestMapping(path = "/laundry/{barcode}", method = RequestMethod.GET)
    public ModelAndView LaundryPage(@PathVariable String barcode) {
        ModelAndView mav = new ModelAndView();
        List<LaundryDetailDTO> laundries= laundryRepository.findAllLaundryInfo_byBarcode(barcode);

        //query data from databases to show laundry list table
        mav.addObject("laundries",laundries);

        mav.setViewName("LaundryInfoPage");
        return mav;
    }

    //according to the barcode to update laundryInfo
    @RequestMapping(path = "/update/laundryInfo", method = RequestMethod.POST)
    @ResponseBody
    public String updateLaundryInfo(@RequestBody JSONObject info){
        String barcode=info.getString("barcode");
        String comment =info.getString("comment");
        String status =info.getString("status");
        System.out.println(barcode);
        boolean stat=laundryRepository.updateLaundry(barcode,status,comment);
        if(stat){

        List<LaundryDetailDTO> laundries= laundryRepository.findAllLaundryInfo_byBarcode(barcode);


        return JSONObject.toJSONString(laundries.get(0));}
        else {return "fail";}
    }

    @RequestMapping(path = "/delete/laundryItem", method = RequestMethod.POST)
    @ResponseBody
    public String deleteLaundryItem(@RequestBody JSONObject info){
        int id =Integer.parseInt(info.getString("id"));
        String barcode=info.getString("barcode");
        String item =info.getString("item");
        boolean stat = false;
        if (item.equals("suit")){
            stat=laundryRepository.deleteSuit(id);
        }
        else if (item.equals("boots")){
            stat=laundryRepository.deleteBoots(id);
        }
        else if (item.equals("hood")) {
            stat=laundryRepository.deleteHood(id);
        }else {return "fail";}
        return getRepoResultStatus(barcode, stat);
    }

    private String getRepoResultStatus(String barcode, boolean stat) {
        if(stat){
        List<LaundryDetailDTO> boots= laundryRepository.findAllBootsInfo_byBarcode(barcode);
        List<LaundryDetailDTO> laundry= new ArrayList<>();

        for (LaundryDetailDTO boot : boots) {
            boot.setItem("boots");
            laundry.add(boot);
        }
        List<LaundryDetailDTO> suits= laundryRepository.findAllSuitInfo_byBarcode(barcode);

        for (LaundryDetailDTO suit : suits) {
            suit.setItem("suit");
            laundry.add(suit);
        }

        List<LaundryDetailDTO> hoods= laundryRepository.findAllHoodInfo_byBarcode(barcode);

        for (LaundryDetailDTO hood : hoods) {
            hood.setItem("hood");
            laundry.add(hood);
        }


        //query data from databases to show laundry info table
        return JSONObject.toJSONString(laundry);}
        else {return "fail";}
    }


}
