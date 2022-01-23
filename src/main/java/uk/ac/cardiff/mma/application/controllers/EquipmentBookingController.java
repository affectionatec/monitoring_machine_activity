package uk.ac.cardiff.mma.application.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cardiff.mma.application.DTO.RequestTrainedDTO;
import uk.ac.cardiff.mma.application.DTO.UserEquipmentTrainedDTO;
import uk.ac.cardiff.mma.application.repository.EquipmentBookingRepositoryJDBC;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

@Controller
public class EquipmentBookingController {
    @Autowired
    EquipmentBookingRepositoryJDBC equipmentBookingRepositoryJDBC;

    @RequestMapping(path="/equipmentBooking")
    private ModelAndView equipmentBooking() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("EquipmentBooking");

        List<String> trainedEquipment = stringifyEquipmentIDs(equipmentBookingRepositoryJDBC.getTrainedEquipment(getUsername()));
        mav.addObject("trainedEquipment", trainedEquipment);

        return mav;
    }

    @RequestMapping(path="/equipmentBooking/datetime", method=RequestMethod.POST, consumes="application/x-www-form-urlencoded")
    private ModelAndView chooseDateTime(@RequestParam String equipmentID) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("DateTimeSelection");

        Integer strippedEquipmentID = stripEquipmentID(equipmentID);
        mav.addObject("equipmentID", strippedEquipmentID);

        List<String> unavailableDates = getUnavailableDates(strippedEquipmentID);
        mav.addObject("unavailableDates", unavailableDates);

        List<String> projectCodes = getUserProjectCodes();
        mav.addObject("projectCodes", projectCodes);

        // Get username of currently logged in user
        String username = getUsername();
        mav.addObject("username", username);

        return mav;
    }

    @RequestMapping(path="/getUnavailableTimes", method=RequestMethod.POST, consumes="application/json")
    @ResponseBody   // the returned value of the method will constitute the body of the HTTP response
    private String getUnavailableTimes(@RequestBody String equipmentIDAndDateJSON) {
        JSONObject obj = new JSONObject(equipmentIDAndDateJSON);

        Integer equipmentID = obj.getInt("equipmentID");
        String date = formatDateForMySQL(obj.getString("date"));    // DD/MM/YYYY to YYYY-MM-DD

        List<Integer> unavailableTimes = equipmentBookingRepositoryJDBC.getUnavailableTimes(equipmentID, date);

        Gson gson = new Gson();
        String response = gson.toJson(unavailableTimes);

        return response;
    }

    @PostMapping
    @RequestMapping(path="/makeBooking", method=RequestMethod.POST, consumes="application/json")
    private ResponseEntity makeBooking(@RequestBody String bookingInfoJSON) {
        JSONObject obj = new JSONObject(bookingInfoJSON);

        Integer equipmentID = obj.getInt("equipmentID");
        String username = obj.getString("username");
        String date = formatDateForMySQL(obj.getString("date"));
        ArrayList<String> times = stripTimes(obj.getJSONArray("times"));
        String projectCode = obj.getString("projectCode");

        return equipmentBookingRepositoryJDBC.makeBooking(equipmentID, username, date, times, projectCode);
    }

    @RequestMapping(path="/updateUsage", method=RequestMethod.POST, consumes="application/json")
    @ResponseBody
    private ResponseEntity updateUsage(@RequestBody String bookingInfoJSON) {
        JSONObject bookingInfo = new JSONObject(bookingInfoJSON);

        String equipmentName = bookingInfo.getString("equipmentName");
        String username = bookingInfo.getString("username");
        String date = bookingInfo.getString("date");
        int timeCode = convertToTimeCode(bookingInfo.getString("timeSlot"));
        int used = bookingInfo.getInt("used");

        return equipmentBookingRepositoryJDBC.updateUsage(equipmentName, username, date, timeCode, used);
    }

    @RequestMapping(path="/updateProjectCode", method=RequestMethod.POST, consumes="application/json")
    @ResponseBody
    private ResponseEntity updateProjectCode(@RequestBody String bookingInfoJSON) {
        JSONObject bookingInfo = new JSONObject(bookingInfoJSON);

        String equipmentName = bookingInfo.getString("equipmentName");
        String username = bookingInfo.getString("username");
        String date = bookingInfo.getString("date");
        int timeCode = convertToTimeCode(bookingInfo.getString("timeSlot"));
        String projectCode = bookingInfo.getString("projectCode");

        return equipmentBookingRepositoryJDBC.updateProjectCode(equipmentName, username, date, timeCode, projectCode);
    }

    private List<String> getUnavailableDates(int equipmentID) {
        List<String> unavailableDates = new ArrayList<String>();

        List<String> fullyBookedDates = equipmentBookingRepositoryJDBC.getFullyBookedDates(equipmentID);
        List<String> scheduledServiceDates = equipmentBookingRepositoryJDBC.getScheduledServiceDates(equipmentID);

        unavailableDates.addAll(fullyBookedDates);
        unavailableDates.addAll(scheduledServiceDates);

        return unavailableDates;
    }

    private String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        return currentPrincipalName;
    }

    private String formatDateForMySQL(String unformattedDate) {
        String year = unformattedDate.substring(6,10);      // (inclusive, exclusive)
        String month = unformattedDate.substring(3,5);
        String day = unformattedDate.substring(0,2);

        String formattedDate = year + "-" + month + "-" + day;

        return formattedDate;
    }

    private ArrayList<String> stripTimes(JSONArray times) {
        ArrayList<String> timesList = new ArrayList<>();
        for (Object time : times) {
            String timeString = time.toString().replace("t","");     // remove t for database entry
            timesList.add(timeString);
        }
        return timesList;
    }

    private Integer stripEquipmentID(String equipmentID) {
        String strippedEquipmentID = equipmentID.replace("e","");     // remove e for database entry
        return Integer.parseInt(strippedEquipmentID);
    }

    private List<String> stringifyEquipmentIDs(List<Integer> equipmentIDs) {
        List<String> stringIDs = new ArrayList<>();
        for (Integer id : equipmentIDs) {
            String stringID = "e" + id.toString();
            stringIDs.add(stringID);
        }
        return stringIDs;
    }

    private int convertToTimeCode(String timeSlot) {
        int timeCode = 0;

        switch(timeSlot) {
            case("8:00-8:30"):
                timeCode = 1;
                break;
            case("8:30-9:00"):
                timeCode = 2;
                break;
            case("9:00-9:30"):
                timeCode = 3;
                break;
            case("9:30-10:00"):
                timeCode = 4;
                break;
            case("10:00-10:30"):
                timeCode = 5;
                break;
            case("10:30-11:00"):
                timeCode = 6;
                break;
            case("11:00-11:30"):
                timeCode = 7;
                break;
            case("11:30-12:00"):
                timeCode = 8;
                break;
            case("12:00-12:30"):
                timeCode = 9;
                break;
            case("12:30-13:00"):
                timeCode = 10;
                break;
            case("13:00-13:30"):
                timeCode = 11;
                break;
            case("13:30-14:00"):
                timeCode = 12;
                break;
            case("14:00-14:30"):
                timeCode = 13;
                break;
            case("14:30-15:00"):
                timeCode = 14;
                break;
            case("15:00-15:30"):
                timeCode = 15;
                break;
            case("15:30-16:00"):
                timeCode = 16;
                break;
            case("16:00-16:30"):
                timeCode = 17;
                break;
            case("16:30-17:00"):
                timeCode = 18;
                break;
            case("17:00-17:30"):
                timeCode = 19;
                break;
            case("17:30-18:00"):
                timeCode = 20;
                break;
        }
        return timeCode;
    }

    private List<String> getUserProjectCodes() {
        String username = getUsername();
        List<String> projectCodes = equipmentBookingRepositoryJDBC.getUserProjectCodes(username);
        return projectCodes;
    }

    /* Pass in the user's name and equipment ID being trained as parameters, and get the time of day to
    ** get the training request. After the acquisition is completed, the data is transferred to the
    ** database, and the user stays on the current page. */
    @RequestMapping(value = "/getTraining")
    private String getTrainingRequestMessage(@RequestParam(value = "userName") String userName,
                                             @RequestParam(value = "equipmentID") int equipmentID){

        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String toStringToday = simpleDateFormat.format(today);

        UserEquipmentTrainedDTO userEquipmentTrainedDTO = new UserEquipmentTrainedDTO(equipmentID,userName,toStringToday);
        equipmentBookingRepositoryJDBC.addTrainingRequest(userEquipmentTrainedDTO);
        return "redirect:/EquipmentBooking";
    }

    // Get all the training request information and render it on the page.
    @RequestMapping(value = "/admin/getRequestInformation", method = RequestMethod.GET)
    public ModelAndView queryAllRequestInformation(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("allRequests", equipmentBookingRepositoryJDBC.queryRequestInformation());
        mav.setViewName("TrainingRequest");
        return mav;
    }

    /* Gets the training status of the equipment, with an approve of 1, marks it as trained and writes it
    ** to the database, and removes it from the database of training requests. A rejection of 0 means that
    ** the training is rejected and therefore also deleted from the database of training requests. */
    @RequestMapping(value = "/admin/approveOrDeny/{id}/{mark}", method = RequestMethod.GET)
    public String approveOrDeny(@PathVariable("id") int id,
                                @PathVariable("mark") int mark){

        if(mark == 1){
            RequestTrainedDTO requestTrainedDTO = (RequestTrainedDTO)equipmentBookingRepositoryJDBC.queryTrainingRequestByID(id);
            equipmentBookingRepositoryJDBC.addUserEquipmentTrained(requestTrainedDTO);
            equipmentBookingRepositoryJDBC.deleteTrainRequest(id);
        }else if (mark == 0){
            equipmentBookingRepositoryJDBC.deleteTrainRequest(id);
        }

        return "redirect:/admin/getRequestInformation";
    }

}
