package uk.ac.cardiff.mma.application.controllers;

import org.json.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cardiff.mma.application.DTO.OccupiedEquipmentDTO;
import uk.ac.cardiff.mma.application.repository.HomepageRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller
public class HomepageController {
    private HomepageRepository homepageRepo;

    @Autowired
    public HomepageController(HomepageRepository homeRepo) {
        this.homepageRepo = homeRepo;
    }


    /**
     * ?
     * this method is doing for querying the occupied machine on the same day.
     *
     * @return
     */
    @RequestMapping(path = "/admin/home", method = RequestMethod.GET)
    public ModelAndView queryOccupiedEquipmentOnManagerHomepage() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String toStringDate = simpleDateFormat.format(date);
        ModelAndView mav = new ModelAndView();
        List list = (List) homepageRepo.queryOccupiedEquipment();

        List<String> projectCodes =  homepageRepo.getAllProjectCodes();
        mav.addObject("projectCodes", projectCodes);

        mav.addObject("OccupiedEquipment", storeTheAfterDate(codeToSlot(list)));
        mav.setViewName("ManagerHomepage");
        return mav;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView queryUsingRecordOnUserHomepage() {
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        System.out.println("auth is"+authorities);

        ModelAndView modelAndView = new ModelAndView();
        String username = getUsername();
        List list = (List) homepageRepo.queryBookingRecord(username);
        modelAndView.addObject("usingRecords", orderLatestRecord(codeToSlot(list)));
        modelAndView.setViewName("UserHomepage");
        return modelAndView;
    }

    private String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        return currentPrincipalName;
    }

    // this method order the latest records to the beginning of list.
    private List orderLatestRecord(List list) {
        ArrayList arrayList = new ArrayList();

        for (int i = list.size() - 1; i >= 0; i--) {
            arrayList.add(list.get(i));
        }
        return arrayList;
    }

    // change time code to time slot
    private List codeToSlot(List list) {

        List showingList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            OccupiedEquipmentDTO occupiedEquipmentDTO = (OccupiedEquipmentDTO) list.get(i);
            switch (occupiedEquipmentDTO.getTime()) {
                case "1":
                    occupiedEquipmentDTO.setTime("8:00-8:30");
                    break;
                case "2":
                    occupiedEquipmentDTO.setTime("8:30-9:00");
                    break;
                case "3":
                    occupiedEquipmentDTO.setTime("9:00-9:30");
                    break;
                case "4":
                    occupiedEquipmentDTO.setTime("9:30-10:00");
                    break;
                case "5":
                    occupiedEquipmentDTO.setTime("10:00-10:30");
                    break;
                case "6":
                    occupiedEquipmentDTO.setTime("10:30-11:00");
                    break;
                case "7":
                    occupiedEquipmentDTO.setTime("11:00-11:30");
                    break;
                case "8":
                    occupiedEquipmentDTO.setTime("11:30-12:00");
                    break;
                case "9":
                    occupiedEquipmentDTO.setTime("12:00-12:30");
                    break;
                case "10":
                    occupiedEquipmentDTO.setTime("12:30-13:00");
                    break;
                case "11":
                    occupiedEquipmentDTO.setTime("13:00-13:30");
                    break;
                case "12":
                    occupiedEquipmentDTO.setTime("13:30-14:00");
                    break;
                case "13":
                    occupiedEquipmentDTO.setTime("14:00-14:30");
                    break;
                case "14":
                    occupiedEquipmentDTO.setTime("14:30-15:00");
                    break;
                case "15":
                    occupiedEquipmentDTO.setTime("15:00-15:30");
                    break;
                case "16":
                    occupiedEquipmentDTO.setTime("15:30-16:00");
                    break;
                case "17":
                    occupiedEquipmentDTO.setTime("16:00-16:30");
                    break;
                case "18":
                    occupiedEquipmentDTO.setTime("16:30-17:00");
                    break;
                case "19":
                    occupiedEquipmentDTO.setTime("17:00-17:30");
                    break;
                case "20":
                    occupiedEquipmentDTO.setTime("17:30-18:00");
                    break;
                default:
                    occupiedEquipmentDTO.setTime("Wrong Code");
                    break;
            }
            showingList.add(occupiedEquipmentDTO);
        }
        return showingList;
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

    /*
     *  the method is return a list that
     * should store the booking record whose date is after the date of today
     * */

    private List storeTheAfterDate(List list) {
        List aDList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            OccupiedEquipmentDTO occupiedEquipmentDTO = (OccupiedEquipmentDTO) list.get(i);
            if (determineDate(occupiedEquipmentDTO.getBookedDate())){
                aDList.add(list.get(i));
            }
        }

        return aDList;

    }

}
