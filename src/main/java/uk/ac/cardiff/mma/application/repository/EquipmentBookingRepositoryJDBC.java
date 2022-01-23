package uk.ac.cardiff.mma.application.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.support.NullValue;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;
import uk.ac.cardiff.mma.application.DTO.ChemicalDTO;
import uk.ac.cardiff.mma.application.DTO.RequestTrainedDTO;
import uk.ac.cardiff.mma.application.DTO.UserEquipmentTrainedDTO;
import uk.ac.cardiff.mma.application.model.ChemicalMapper;
import uk.ac.cardiff.mma.application.model.UserEquipmentTrainedMapper;

import java.sql.Types;
import java.util.List;

@Repository
public class EquipmentBookingRepositoryJDBC implements EquipmentBookingRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public EquipmentBookingRepositoryJDBC(JdbcTemplate injection) {
        this.jdbcTemplate = injection;
    }

    @Override
    public List getTrainedEquipment(String username) {
        String query = "SELECT equipmentID FROM UserEquipmentTrained WHERE username=?;";
        List<Integer> trainedEquipment = jdbcTemplate.queryForList(query, Integer.class, username);
        return trainedEquipment;
    }

    @Override
    public List getFullyBookedDates(int equipmentID) {
        // 20 time slots
        String query = "SELECT date FROM (SELECT date, COUNT(time) AS booking_count FROM Booking WHERE equipmentID = ? GROUP BY date) AS booking_counts WHERE booking_count = 20;";
        List<String> unavailableDates = jdbcTemplate.queryForList(query, String.class, equipmentID);
        return unavailableDates;
    }

    @Override
    public List getScheduledServiceDates(int equipmentID) {
        String query = "SELECT date_next_service FROM ServicingSchedule WHERE equipmentID = ?;";     // YYY-MM-DD format
        List<String> scheduledServiceDates = jdbcTemplate.queryForList(query, String.class, equipmentID);
        return scheduledServiceDates;
    }

    @Override
    public List getUnavailableTimes(Integer equipmentID, String date) {
        String query = "SELECT time FROM Booking WHERE equipmentID = ? AND date = ?;";
        List<Integer> unavailableTimes = jdbcTemplate.queryForList(query, Integer.class, equipmentID, date);
        return unavailableTimes;
    }

    @Override
    public ResponseEntity makeBooking(Integer equipmentID, String username, String date, List<String> times, String projectCode) {
        String query = "INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (?, ?, ?, ?, 0, ?);";

        try {
            if (projectCode.equals("")) {
                query = "INSERT INTO Booking (equipmentID, username, date, time, used, project_code) VALUES (?, ?, ?, ?, 0, NULL);";
                for (String time : times) {
                    jdbcTemplate.update(query, equipmentID, username, date, time);
                }
            } else {
                for (String time : times) {
                    jdbcTemplate.update(query, equipmentID, username, date, time, projectCode);
                }
            }
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity updateUsage(String equipmentName, String username, String date, int timeCode, int used) {
        String updateUsageQuery = "UPDATE Booking SET used = ? WHERE equipmentID = ? AND username = ? AND date = ? AND time = ?;";

        try {
            int equipmentID = getEquipmentID(equipmentName);
            jdbcTemplate.update(updateUsageQuery, used, equipmentID, username, date, timeCode);

            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    };

    @Override
    public List<String> getUserProjectCodes(String username) {
        String query = "SELECT project_code FROM ProjectCodes WHERE username = ?;";
        List<String> projectCodes = jdbcTemplate.queryForList(query, String.class, username);

        return projectCodes;
    }

    @Override
    public ResponseEntity updateProjectCode(String equipmentName, String username, String date, int timeCode, String projectCode) {
        String query = "";

        try {
            int equipmentID = getEquipmentID(equipmentName);

            if (projectCode == "") {
                query = "UPDATE Booking SET project_code=NULL WHERE equipmentID=? AND username=? AND date=? AND time=?;";
                jdbcTemplate.update(query, equipmentID, username, date, timeCode);

                return new ResponseEntity(HttpStatus.CREATED);
            } else {
                query = "UPDATE Booking SET project_code=? WHERE equipmentID=? AND username=? AND date=? AND time=?;";
                jdbcTemplate.update(query, projectCode, equipmentID, username, date, timeCode);

                return new ResponseEntity(HttpStatus.CREATED);
            }
        } catch(Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    private int getEquipmentID(String equipmentName) {
        String equipmentIDQuery = "SELECT id FROM Equipment WHERE name = ?";
        int equipmentID = jdbcTemplate.queryForObject(equipmentIDQuery, Integer.class, equipmentName);

        return equipmentID;
    }





    @Override
    public boolean addTrainingRequest(UserEquipmentTrainedDTO userEquipmentTrainedDTO) {
        int types[] = new int[] {
                Types.INTEGER,
                Types.VARCHAR,
                Types.VARCHAR,
        };
        int rows = jdbcTemplate.update("INSERT INTO EquipmentBooking.TrainedRequestInformation(equipmentID,username,date_trained) VALUES (?,?,?)",
                new Object[]{userEquipmentTrainedDTO.getEquipmentID(), userEquipmentTrainedDTO.getUsername(), userEquipmentTrainedDTO.getDateTrained()}, types);
        return rows>0;
    }

    @Override
    public List<RequestTrainedDTO> queryRequestInformation() {
        return jdbcTemplate.query("SELECT t.id,e.name,t.username,t.date_trained FROM TrainedRequestInformation t JOIN Equipment e ON t.equipmentID = e.id;", new UserEquipmentTrainedMapper());
    }

    @Override
    public boolean addUserEquipmentTrained(RequestTrainedDTO requestTrainedDTO) {

        int equipmentID = getEquipmentID(requestTrainedDTO.getEquipmentName());

        int types[] = new int[] {
                Types.INTEGER,
                Types.VARCHAR,
                Types.VARCHAR,
        };
        int rows = jdbcTemplate.update("INSERT INTO EquipmentBooking.UserEquipmentTrained(equipmentID,username,date_trained) VALUES (?,?,?)",
                new Object[]{equipmentID,requestTrainedDTO.getUsername(), requestTrainedDTO.getDateTrained()}, types);
        return rows>0;
    }

    @Override
    public RequestTrainedDTO queryTrainingRequestByID(int id) {
        int type[] = new int[]{
                Types.INTEGER
        };
        RequestTrainedDTO requestTrainedDTO = (RequestTrainedDTO) jdbcTemplate.queryForObject("SELECT T.id,E.name,T.username,T.date_trained FROM TrainedRequestInformation T JOIN Equipment E ON T.equipmentID = E.id WHERE T.id = ?",
                new Object[]{id},type,new UserEquipmentTrainedMapper());
        return requestTrainedDTO;
    }

    @Override
    public boolean deleteTrainRequest(int id) {
        String sql = "delete from TrainedRequestInformation where id = ?";
        Object[] args = new Object[]{id};
        return jdbcTemplate.update(sql, args) == 1;
    }


}
