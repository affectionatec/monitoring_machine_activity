package uk.ac.cardiff.mma.application.controllers;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cardiff.mma.application.DTO.EquipmentDetailDTO;
import uk.ac.cardiff.mma.application.DTO.EquipmentUsageDTO;
import uk.ac.cardiff.mma.application.DTO.OccupiedEquipmentDTO;
import uk.ac.cardiff.mma.application.repository.EquipmentUsageRepository;
import uk.ac.cardiff.mma.application.repository.UserRepository;

import java.util.*;

import com.alibaba.fastjson.JSONArray;

@Controller
public class EquipmentUsageController {
    private final UserRepository userRepository;
    private final EquipmentUsageRepository equipmentUsageRepository;

    @Autowired
    public EquipmentUsageController(UserRepository userRepo, EquipmentUsageRepository equipmentOperationRepo) {
        userRepository=userRepo;
        equipmentUsageRepository =equipmentOperationRepo;
    }


    //get all equipments
    @RequestMapping(path="/getEquipmentList", method = RequestMethod.GET)
    @ResponseBody
    public String GetEquipmentList() {
        ArrayList<String> equipments = new ArrayList<>();

        List<EquipmentDetailDTO> equipmentList = equipmentUsageRepository.findAllEquipments();
        for (EquipmentDetailDTO equipment : equipmentList) {
            equipments.add(equipment.getName());
        }
        return JSONArray.toJSONString(equipments);
    }


    // get graph data and transfer data to front-end
    @RequestMapping(path="/postGraphData", method = RequestMethod.POST)
    @ResponseBody
    public String getData(@RequestBody JSONObject info){
        System.out.println(info);
        String name =info.getString("name");

        List<EquipmentUsageDTO> equipmentByDate= equipmentUsageRepository.queryDateEquipment_byName(name);
        Map<String, Integer> graphData =new HashMap<>();;
        for (EquipmentUsageDTO equipment : equipmentByDate) {
            graphData.put(equipment.getBookedDate(),equipment.getTimes()*30);
        }
        Map<String, Integer> resultMap = sortMapByKey(graphData);
        System.out.println(JSONObject.toJSONString(resultMap));
        return JSONObject.toJSONString(resultMap);
    }

    //according map's key to sort
    public static Map<String, Integer> sortMapByKey(Map<String, Integer> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, Integer> sortMap = new TreeMap<String, Integer>(
                new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    public static class MapKeyComparator implements Comparator<String>{
        @Override
        public int compare(String str1, String str2) {
            return str1.compareTo(str2);
        }
    }



    @RequestMapping(path="/admin/equipmentUsageList",method = RequestMethod.GET)
    public ModelAndView equipmentUsage() {
        ModelAndView mav = new ModelAndView();
        List<OccupiedEquipmentDTO> equipments= equipmentUsageRepository.queryUsedEquipment();
        //Format time slot
        EquipmentUsageReportController.ChangeSlotToTime(equipments);

        //use data from databases to show equipmentUsageList table
        mav.addObject("equipments",equipments);
        mav.setViewName("EquipmentUsagePage");
        return mav;

    }

}
