package uk.ac.cardiff.mma.application.controllers;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.xdevapi.JsonString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cardiff.mma.application.DTO.CheckItemsDTO;
import uk.ac.cardiff.mma.application.DTO.EquipmentOperationDTO;
import uk.ac.cardiff.mma.application.DTO.UserDTO;
import uk.ac.cardiff.mma.application.repository.CheckItemsRepository;
import uk.ac.cardiff.mma.application.repository.EquipmentOperationRepository;
import uk.ac.cardiff.mma.application.repository.EquipmentUsageRepository;
import uk.ac.cardiff.mma.application.repository.UserRepository;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class EquipmentOperationController {
    private final EquipmentOperationRepository operateRepository;
    private final CheckItemsRepository checkItemsRepository;



    @Autowired
    public EquipmentOperationController(EquipmentOperationRepository operateRepo, CheckItemsRepository checkRepo) {
        this.operateRepository=operateRepo;
        this.checkItemsRepository=checkRepo;
    }

    @RequestMapping(path="/admin/equipmentOperationReport", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView equipmentOperationReport() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("EquipmentOperationReport");
        return mav;
    }

    @RequestMapping(path="/admin/equipmentOperation", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView equipmentOperation() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("EquipmentOperationPage");
        return mav;
    }


    //Data loaded when the operation page is initialized
    @RequestMapping(path="/postOperateData", method = RequestMethod.POST)
    @ResponseBody
    public String postOperateData(@RequestBody JSONObject info) {
        Map<String, Object> data =new HashMap<>();
        System.out.println(info);
        String equipName=info.getString("name");
        String date=info.getString("date");
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = dateFormat.format(now);
        Map<Integer, Object> operationMap=new  HashMap<>();
        List operationDTOs = new ArrayList<>();
        if ((operateRepository.findAllEquipments_byName(equipName).size() !=0) & (date.compareTo(nowDate) <= 0)) {
            int eid=operateRepository.findAllEquipments_byName(equipName).get(0).getId();
            if(operateRepository.findEquipmentOperationData_byNameDate(eid,date).size() !=0) {
                int id=(operateRepository.findEquipmentOperationData_byNameDate(eid,date).get(0).getId());
                operateRepository.deleteOperateData(id);
                for(String key : info.keySet()){
                    if(key.equals("operateData")){
                        for (int i=0;i<info.getJSONArray(key).size();i++){
                            System.out.println(info.getJSONArray(key).getJSONObject(i));
                            for(String key1:info.getJSONArray(key).getJSONObject(i).keySet()){
                                operateRepository.insertOperateData(id,checkItemsRepository.findAllItems_byName(key1).get(0).getId(),  info.getJSONArray(key).getJSONObject(i).getString(key1));}
                        }
                    }
                }
            }
            else {
                operateRepository.insertEquipmentOperateDates(eid, date);
                int id=(operateRepository.findEquipmentOperationData_byNameDate(eid,date).get(0).getId());
                for(String key : info.keySet()){
                    if(key.equals("operateData")){

                        for (int i=0;i<info.getJSONArray(key).size();i++){
                            System.out.println(info.getJSONArray(key).getJSONObject(i));
                            for(String key1:info.getJSONArray(key).getJSONObject(i).keySet()){
                                operateRepository.insertOperateData(id,checkItemsRepository.findAllItems_byName(key1).get(0).getId(),  info.getJSONArray(key).getJSONObject(i).getString(key1));}

                        }
                    }
                }
            }
            List<String> dates=operateRepository.findAllEquipmentOperateDates();
            List<EquipmentOperationDTO> operationDTO= operateRepository.findAllEquipmentOperationData();

            Collections.sort(dates);
            generateOperationDataMap(data, operationDTOs, operationMap, dates, operationDTO);
            data.put("status","ok");

        } else {
            data.put("status","fail");
        }

        return JSONArray.toJSONString(data);

    }

    //If the user selects a time range to select information
    @RequestMapping(path="/postOperateDataRange", method = RequestMethod.POST)
    @ResponseBody
    public String postOperateDataRange(@RequestBody JSONObject dateRange) {
        Map<String, Object> data =new HashMap<>();
        JSONArray  dateRangeList;
        List operationDTOs = new ArrayList<>();
        dateRangeList  =dateRange.getJSONArray("data");
        Map<Integer, Object> operationMap=new  HashMap<>();
        //Judge date range
        if (dateRangeList.get(0).toString().equals("")){

            List<String> dates=operateRepository.findAllEquipmentOperateDates();
            List<EquipmentOperationDTO> operationDTO= operateRepository.findAllEquipmentOperationData();
            generateOperationDataMap(data, operationDTOs, operationMap, dates, operationDTO);

        }else {
            List<String> dates=operateRepository.findAllEquipmentOperateRangeDates(dateRangeList.get(0).toString(),dateRangeList.get(1).toString());
            List<EquipmentOperationDTO> operationDTO= operateRepository.findEquipmentOperationDataRangeDate(dateRangeList.get(0).toString(),dateRangeList.get(1).toString());
            generateOperationDataMap(data, operationDTOs, operationMap, dates, operationDTO);
        }
        data.put("status","ok");
        return JSONArray.toJSONString(data);
    }

    //generate OperationDataMap
    private void generateOperationDataMap(Map<String, Object> data, List operationDTOs, Map<Integer, Object> operationMap, List<String> dates, List<EquipmentOperationDTO> operationDTO) {
        for(int i=0;i<operationDTO.size();i++) {
            if (operationMap.containsKey(operationDTO.get(i).getId())) {
                Map<String,String> item= (Map<String, String>) operationMap.get(operationDTO.get(i).getId());
                item.put(operationDTO.get(i).getItemName(),operationDTO.get(i).getValue());

            }else {
            Map<String,String> item =new HashMap<>();
            item.put("date",operationDTO.get(i).getDate());
            item.put("equipmentName",operationDTO.get(i).getEquipmentName());
            item.put(operationDTO.get(i).getItemName(),operationDTO.get(i).getValue());
            operationMap.put(operationDTO.get(i).getId(),item);}

        }
        for(int key : operationMap.keySet()){
            operationDTOs.add(operationMap.get(key));

        }

        Collections.sort(dates);


        List<CheckItemsDTO> checkItemsDTOs=checkItemsRepository.findAllItems();

        data.put("dateList",dates);
        data.put("dataList",operationDTOs);
        data.put("standardList",checkItemsDTOs);
    }



    @RequestMapping(path="/checkItemAdd", method = RequestMethod.POST)
    @ResponseBody
    public String addCheckItems(@RequestBody JSONObject addItem) {
        System.out.println(addItem);
        String name=addItem.getString("name");
        String mode=addItem.getString("mode");
        String value=addItem.getString("value");

        if (checkItemsRepository.findAllItems_byName(name).size() ==0){
        boolean status=checkItemsRepository.insertCheckItem(name,mode,value);
        if (status){
            List<CheckItemsDTO> checkItemsDTOs=checkItemsRepository.findAllItems();
            return JSONArray.toJSONString(checkItemsDTOs);
        }else {
            return "Failed to add this item";
        }} else {
            return "This item already exists";

        }

    }

    @RequestMapping(path="/checkItemEdit", method = RequestMethod.POST)
    @ResponseBody
    public String editCheckItems(@RequestBody JSONObject editItem) {
        int id=editItem.getInteger("id");
        String name=editItem.getString("name");
        String mode=editItem.getString("mode");
        String value=editItem.getString("value");

        boolean status=checkItemsRepository.updateCheckItem(id,name,mode,value);
        if (status){
            List<CheckItemsDTO> checkItemsDTOs=checkItemsRepository.findAllItems();
            return JSONArray.toJSONString(checkItemsDTOs);
        }else {
            return "Failed to edit this item";
        }

    }


    @RequestMapping(path="/checkItemDelete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteCheckItems(@RequestBody JSONObject deleteItem) {
        int id=deleteItem.getInteger("id");
        String name=deleteItem.getString("name");

        boolean status=checkItemsRepository.deleteCheckItem(id,name);
        if (status){
            List<CheckItemsDTO> checkItemsDTOs=checkItemsRepository.findAllItems();
            return JSONArray.toJSONString(checkItemsDTOs);
        }else {
            return "Failed to delete this item";
        }

    }

    @RequestMapping(path="/checkItemAll", method = RequestMethod.GET)
    @ResponseBody
    public String getCheckItems() {

        List<CheckItemsDTO> checkItemsDTOs=checkItemsRepository.findAllItems();

        return JSONArray.toJSONString(checkItemsDTOs);
    }
}
