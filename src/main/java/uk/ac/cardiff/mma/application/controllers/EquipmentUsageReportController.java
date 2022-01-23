package uk.ac.cardiff.mma.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cardiff.mma.application.DTO.OccupiedEquipmentDTO;
import uk.ac.cardiff.mma.application.repository.EquipmentDetailRepository;
import uk.ac.cardiff.mma.application.repository.EquipmentUsageRepository;

import java.util.List;


@Controller
public class EquipmentUsageReportController {


    public EquipmentDetailRepository equipmentDetailRepo;
    private final EquipmentUsageRepository equipmentUsageRepository;

    @Autowired
    public EquipmentUsageReportController(EquipmentDetailRepository equipmentDetailRepo, EquipmentUsageRepository equipmentUsageRepository) {
        this.equipmentDetailRepo = equipmentDetailRepo;
        this.equipmentUsageRepository = equipmentUsageRepository;
    }


    // Query equipment detail from the database.
    @RequestMapping(path = "/admin/equipmentReport", method = RequestMethod.GET)
    public ModelAndView getEquipmentReport() {
        ModelAndView mav = new ModelAndView();
        System.out.println("Return all equipment");
        mav.addObject("equipmentList", equipmentDetailRepo.findAllEquipmentDetails());

        List<OccupiedEquipmentDTO> equipmentBooked= equipmentUsageRepository.queryBookEquipment();
        //Format time slot
        ChangeSlotToTime(equipmentBooked);

        //query BookedInfo from databases to show equipmentBooked table
        mav.addObject("equipmentsBookedInfo",equipmentBooked);


        List<OccupiedEquipmentDTO> equipmentUsed= equipmentUsageRepository.queryUsedEquipment();
        //Format time slot
        ChangeSlotToTime(equipmentUsed);

        //query UsedInfo from databases to show equipmentUsed table
        mav.addObject("equipmentsUsedInfo",equipmentUsed);

        mav.setViewName("EquipmentUsageReportPage");
        return mav;
    }

    static void ChangeSlotToTime(List<OccupiedEquipmentDTO> equipmentBooked) {
        for (OccupiedEquipmentDTO occupiedEquipmentDTO: equipmentBooked){
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
        }
    }


    @RequestMapping(path = "/admin/equipmentReport/{name}", method = RequestMethod.GET)
    public ModelAndView eachEquipmentReport(@PathVariable String name) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("equipmentList", equipmentDetailRepo.findAllEquipmentDetails_byName(name));
        List<OccupiedEquipmentDTO> equipmentBooked= equipmentUsageRepository.queryBookedEquipment_byName(name);
        //Format time slot
        ChangeSlotToTime(equipmentBooked);

        //use data from databases to show equipmentUsageList table
        mav.addObject("equipmentsBookedInfo",equipmentBooked);


        List<OccupiedEquipmentDTO> equipmentUsed= equipmentUsageRepository.queryUsedEquipment_byName(name);
        //Format time slot
        ChangeSlotToTime(equipmentUsed);

        //use data from databases to show equipmentUsageList table
        mav.addObject("equipmentsUsedInfo",equipmentUsed);
        mav.addObject("equipmentName",name);


        mav.setViewName("EachEquipmentUsageReport");
        return mav;
    }
}
