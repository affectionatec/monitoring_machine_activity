package uk.ac.cardiff.mma.application.repository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import uk.ac.cardiff.mma.application.DTO.RequestTrainedDTO;
import uk.ac.cardiff.mma.application.DTO.UserEquipmentTrainedDTO;

import java.util.List;

public interface EquipmentBookingRepository {
    public List getTrainedEquipment(String username);

    public List getFullyBookedDates(int equipmentID);

    public List getScheduledServiceDates(int equipmentID);

    public List getUnavailableTimes(Integer equipmentID, String date);

    public ResponseEntity makeBooking(Integer equipmentID, String username, String date, List<String> times, String projectCode);

    public ResponseEntity updateUsage(String equipmentName, String username, String date, int timeCode, int used);

    public List getUserProjectCodes(String username);

    public ResponseEntity updateProjectCode(String equipmentName, String username, String date, int timeCode, String projectCode);

    public boolean addTrainingRequest(UserEquipmentTrainedDTO userEquipmentTrainedDTO);

    Object queryRequestInformation();

    public boolean addUserEquipmentTrained(RequestTrainedDTO requestTrainedDTO);
    Object queryTrainingRequestByID(int id);

    boolean deleteTrainRequest(int id);
}
