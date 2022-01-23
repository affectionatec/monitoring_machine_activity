package uk.ac.cardiff.mma.application.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uk.ac.cardiff.mma.application.DTO.AddGasDeliveryForm;
import uk.ac.cardiff.mma.application.DTO.AddGasForm;
import uk.ac.cardiff.mma.application.DTO.GasDTO;
import uk.ac.cardiff.mma.application.DTO.GasDeliveryDTO;
import uk.ac.cardiff.mma.application.model.GasDeliveryMapper;
import uk.ac.cardiff.mma.application.model.GasMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class GasRepositoryJDBC implements GasRepository{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public GasRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<GasDTO> findAllGasDetails() {
//        System.out.println("Sending SQL request to the database");
        return jdbcTemplate.query("SELECT * FROM GasUse.gas;", new GasMapper());
    }

    @Override
    public List<GasDTO> findGasByLocation(String location) {
        List<GasDTO> gasDTOList = jdbcTemplate.query("SELECT * FROM GasUse.gas WHERE location = ?;",
                new Object[]{location}, new GasMapper());
        return gasDTOList;
    }

    @Override
    public List<GasDTO> findGasByName(String name) {
        List<GasDTO> gasDTOList = jdbcTemplate.query("SELECT * FROM GasUse.gas WHERE name = ?;",
                new Object[]{name}, new GasMapper());
        return gasDTOList;
    }

    @Override
    public boolean addGas(AddGasForm addGasForm) {
        int rows = jdbcTemplate.update(
                "INSERT INTO GasUse.gas (name,storage,unit,bottleNum,coshh,location,hazardLevel,comments) VALUES (?,?,?,?,?,?,?,?);" ,
                new Object[]{addGasForm.getName(), addGasForm.getStorage(), addGasForm.getUnit(), addGasForm.getBottleNum(),
                        addGasForm.getCoshh(), addGasForm.getLocation(), addGasForm.getHazardLevel(), addGasForm.getComments()});
        return rows>0;
    }

    @Override
    public boolean deleteGas(int id) {
        String sql = "DELETE FROM GasUse.gas WHERE id = ?;";
        Object[] argsForGasDelete = new Object[]{id};
        boolean gasDeleted = jdbcTemplate.update(sql,argsForGasDelete) == 1;
        return gasDeleted;
    }

    @Override
    public GasDTO queryGasByID(int id) {
        int type[] = new int[] {
                Types.INTEGER
        };
        GasDTO gasDTO = (GasDTO) jdbcTemplate.queryForObject(
                "SELECT * FROM GasUse.gas WHERE id = ?;",
                new Object[]{id}, type, new GasMapper());
        return gasDTO;
    }

    @Override
    public boolean updateGasDetail(GasDTO gasDTO) {
        int types[] = new int[] {
                Types.INTEGER,
                Types.VARCHAR,
                Types.INTEGER,
                Types.VARCHAR,
                Types.INTEGER,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.INTEGER
        };
        int rows = jdbcTemplate.update(
                "UPDATE GasUse.gas SET id=?, name=?, storage=?, unit=?, bottleNum=?, coshh=?, location=?, hazardLevel=?, comments=? WHERE id = ?;",
                new Object[]{gasDTO.getId(), gasDTO.getName(), gasDTO.getStorage(), gasDTO.getUnit(), gasDTO.getBottleNum(),
                        gasDTO.getCoshh(), gasDTO.getLocation(), gasDTO.getHazardLevel(), gasDTO.getComments(), gasDTO.getId()}, types);
        return rows>0;
    }

    /* Need another result mapper than, which will deal only with level name column.
    ** Used to filter out duplicate level names, which use DISTINCT keyword. Also should create anonymous class. */
    @Override
    public List<GasDTO> findNoneRepeatGasLevel() {
        return jdbcTemplate.query("SELECT DISTINCT hazardLevel FROM GasUse.gas;", new GasMapper() {
            public GasDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                GasDTO gasDTO = new GasDTO();
                gasDTO.setHazardLevel(rs.getString("hazardLevel"));
                return gasDTO;
            }
        });
    }

    @Override
    public List<GasDeliveryDTO> queryGasDeliveryDetailByID(int id) {
        int type[] = new int[] {
                Types.INTEGER
        };
        return jdbcTemplate.query(
                "SELECT gd.deliveryID,gd.gasID,gd.deliveryDate,gd.expiryDate,gd.distributionWeight,gd.deliveryStaff \n" +
                        "FROM GasUse.gasDelivery gd \n" +
                        "JOIN GasUse.gas g \n" +
                        "ON gd.gasID = g.id \n" +
                        "WHERE g.id = ?;",
                new Object[]{id}, type, new GasDeliveryMapper());
    }

    @Override
    public boolean addGasDelivery(AddGasDeliveryForm addGasDeliveryForm) {
        int rows = jdbcTemplate.update(
                "INSERT INTO GasUse.gasDelivery (gasID,deliveryDate,expiryDate,distributionWeight,deliveryStaff) VALUES (?,?,?,?,?);",
                new Object[]{addGasDeliveryForm.getGasID(), addGasDeliveryForm.getDeliveryDate(), addGasDeliveryForm.getExpiryDate(),
                        addGasDeliveryForm.getDistributionWeight(), addGasDeliveryForm.getDeliveryStaff()});
        return rows>0;
    }

    @Override
    public boolean deleteGasDelivery(int id) {
        String sql = "DELETE FROM GasUse.gasDelivery WHERE deliveryID = ?;";
        Object[] argsForGasDeliveryDelete = new Object[]{id};
        boolean gasDeliveryDeleted = jdbcTemplate.update(sql,argsForGasDeliveryDelete) == 1;
        return gasDeliveryDeleted;
    }

    @Override
    public boolean deleteGasDeliveryRecordByGasID(int gasID) {
        String sql = "DELETE FROM GasUse.gasDelivery WHERE gasID = ?;";
        Object[] argsForGasDeliveryRecordByGasID = new Object[]{gasID};
        boolean gasDeliveryRecordByGasIDDeleted = jdbcTemplate.update(sql, argsForGasDeliveryRecordByGasID) == 1;
        return gasDeliveryRecordByGasIDDeleted;
    }

    @Override
    public int getStorageByGasID(int id) {
        int gasTotalDeliveryStorage = jdbcTemplate.queryForObject("SELECT storage FROM GasUse.gas WHERE id = ?;", Integer.class, id);
        return gasTotalDeliveryStorage;
    }

    @Override
    public boolean updateGasStorage(int storage, int id) {
        int types[] = new int[] {
                Types.INTEGER,
                Types.INTEGER,
        };
        int rows = jdbcTemplate.update("UPDATE GasUse.gas SET storage = ? WHERE id = ?;", new Object[]{storage, id}, types);
        return rows > 0;
    }

}
