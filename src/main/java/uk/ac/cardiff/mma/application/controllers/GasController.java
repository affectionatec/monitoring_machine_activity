package uk.ac.cardiff.mma.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cardiff.mma.application.DTO.AddGasDeliveryForm;
import uk.ac.cardiff.mma.application.DTO.AddGasForm;
import uk.ac.cardiff.mma.application.DTO.GasDTO;
import uk.ac.cardiff.mma.application.repository.GasRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GasController {

    public GasRepository gasRepository;

    @Autowired
    public GasController(GasRepository gasRepository) {
        this.gasRepository = gasRepository;
    }

    // Pull gas all data from the database.
    @RequestMapping(path = "/admin/gasDetail", method = RequestMethod.GET)
    public ModelAndView getGasDetail() {
        ModelAndView mav = new ModelAndView();
//        System.out.println("Return all gas");
        List list = (List) gasRepository.findAllGasDetails();
        mav.addObject("allGasDetails", codeToImage(list));
        mav.setViewName("AllGasDetails");
//        System.out.println("Rendering the page");
        return mav;
    }

    // Search gas by location.
    @RequestMapping(path = "/admin/searchGasLocation", method = RequestMethod.GET)
    public ModelAndView searchGasLocation(@RequestParam(value="location", defaultValue="null") String locationString) {
        ModelAndView mav = new ModelAndView();
        List list = (List) gasRepository.findGasByLocation(locationString);
        mav.addObject("allGasDetails", codeToImage(list));
        mav.setViewName("SearchGasDetails");
        return mav;
    }

    // Search gas by name.
    @RequestMapping(path = "/admin/searchGasName", method = RequestMethod.GET)
    public ModelAndView searchGasName(@RequestParam(value="name", defaultValue="null") String nameString) {
        ModelAndView mav = new ModelAndView();
        List list = (List) gasRepository.findGasByName(nameString);
        mav.addObject("allGasDetails", codeToImage(list));
        mav.setViewName("SearchGasDetails");
        return mav;
    }

//    // Render to add gas page.
//    @RequestMapping(path = "/Site/AddGas", method = RequestMethod.GET)
//    public ModelAndView addGas(){
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("level",gasRepository.findNoneRepeatGasLevel());
//        mav.setViewName("AddGasForm");
//        return mav;
//    }

    // Render to add gas page.
    @RequestMapping(path = "/admin/Site/AddGas", method = RequestMethod.GET)
    public String addGas(Model model, @RequestParam(value="name", required=false, defaultValue="null") String name) {
        model.addAttribute("gasName", name);
        model.addAttribute("level", gasRepository.findNoneRepeatGasLevel());
        return "AddGasForm.html";
    }

    // Add new gas.
    @RequestMapping(path = "/admin/addGas", method = RequestMethod.POST)
    public ModelAndView addNewGas(AddGasForm addGasForm, BindingResult br) {
        ModelAndView mav = new ModelAndView();
        if(br.hasErrors()) {
            mav.setViewName("ManagerHomepage");
        } else {
            if(gasRepository.addGas(addGasForm)) {
//                System.out.println("added gas");
                List list = (List) gasRepository.findAllGasDetails();
                mav.addObject("allGasDetails", codeToImage(list));
                mav.setViewName("AllGasDetails");
            } else {
                mav.setViewName("ManagerHomepage");
            }
        }
        return mav;
    }

    // Delete the corresponding gas information.
    @GetMapping("/admin/deleteGas/{id}")
    public String deleteGas(@PathVariable("id") Integer id) {
        gasRepository.deleteGasDeliveryRecordByGasID(id);
        gasRepository.deleteGas(id);
        return "redirect:/admin/gasDetail";
    }

    // Edit the corresponding gas information.
    @RequestMapping(value = "/admin/toEditGas", method = RequestMethod.GET)
    public ModelAndView queryGasByID(@RequestParam(value="id") int id) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("gas", gasRepository.queryGasByID(id));
        mav.addObject("level", gasRepository.findNoneRepeatGasLevel());
        mav.setViewName("GasEdit");
        return mav;
    }

    // Edit successfully and return to the main page.
    @RequestMapping(value = "/admin/updateGas", method = RequestMethod.POST)
    public String updateGasDetail(GasDTO gasDTO) throws Exception {
        gasRepository.updateGasDetail(gasDTO);
        return "redirect:/admin/gasDetail";
    }

    // Change level code to image.
    private List codeToImage(List list) {
        List imageList = new ArrayList();
        for(int i = 0; i < list.size(); i++) {
            GasDTO gasDTO = (GasDTO) list.get(i);
            switch(gasDTO.getHazardLevel()) {
                case "Flammable":
                    gasDTO.setHazardLevel("https://dbdzm869oupei.cloudfront.net/img/sticker/preview/8625.png");
                    break;
                case "Oxidizing":
                    gasDTO.setHazardLevel("https://cdn.shopify.com/s/files/1/2090/4401/products/Oxidizing-Gas_grande.png?v=1557799513");
                    break;
                case "Non-flammable":
                    gasDTO.setHazardLevel("https://selectequip.co.uk/wp-content/uploads/2016/08/DMEU_C41A.jpg");
                    break;
                case "Toxic":
                    gasDTO.setHazardLevel("http://cdn.shopify.com/s/files/1/0194/3447/products/Toxic_Gas_2_label_grande.png?v=1585928323");
                    break;
                case "Corrosive":
                    gasDTO.setHazardLevel("https://upload.wikimedia.org/wikipedia/commons/5/5b/Corrosive_gas_2_placard.png");
                    break;
                case "Compressed":
                    gasDTO.setHazardLevel("https://m.media-amazon.com/images/I/71H0PYikiRL._AC_SL1500_.jpg");
                    break;
                default:
                    gasDTO.setHazardLevel("Wrong code");
                    break;
            }
            imageList.add(gasDTO);
        }
        return imageList;
    }

    // View the delivery information corresponding to the gas.
    @RequestMapping(value = "/admin/view", method = RequestMethod.GET)
    public ModelAndView queryGasDetailByID(@RequestParam(value="id", defaultValue="null") int id) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("gasName", gasRepository.queryGasByID(id));
        modelAndView.addObject("allGasDeliveryDetails", gasRepository.queryGasDeliveryDetailByID(id));
        modelAndView.setViewName("AllGasDeliveryDetails");
        return modelAndView;
    }

    // Render to add gas page.
    @RequestMapping(path = "/admin/Site/AddGasDelivery", method = RequestMethod.GET)
    public String addGasDelivery(Model model, @RequestParam(value="deliveryDate", required=false, defaultValue="null") String deliveryDate,
                                 @RequestParam(value="gasID", required=false, defaultValue="null") int gasID) {
        model.addAttribute("deliveryDate", deliveryDate);
        model.addAttribute("gasID", gasID);
        return "AddGasDeliveryForm.html";
    }

    // Add new gas delivery.
    @RequestMapping(path = "/admin/addGasDelivery", method = RequestMethod.POST)
    public ModelAndView addNewGasDelivery(AddGasDeliveryForm addGasDeliveryForm, BindingResult br) {
        ModelAndView mav = new ModelAndView();
        if(br.hasErrors()) {
            mav.setViewName("ManagerHomepage");
        } else {
            if(gasRepository.addGasDelivery(addGasDeliveryForm)) {
//                System.out.println(2);
                int originalStorage = gasRepository.getStorageByGasID(addGasDeliveryForm.getGasID());
                int totalStorage = addGasDeliveryForm.getDistributionWeight() + originalStorage;
                gasRepository.updateGasStorage(totalStorage, addGasDeliveryForm.getGasID());
                mav.addObject("gasName", gasRepository.queryGasByID(addGasDeliveryForm.getGasID()));
                mav.addObject("allGasDeliveryDetails", gasRepository.queryGasDeliveryDetailByID(addGasDeliveryForm.getGasID()));
                mav.setViewName("AllGasDeliveryDetails");
            } else {
                mav.setViewName("ManagerHomepage");
            }
        }
        return mav;
    }

    // Delete the corresponding gas delivery information.
    @GetMapping("/admin/deleteGasDelivery/{id}")
    public String deleteGasDelivery(@PathVariable("id") Integer id) {
        gasRepository.deleteGasDelivery(id);
        return "redirect:/admin/gasDetail";
    }

}
