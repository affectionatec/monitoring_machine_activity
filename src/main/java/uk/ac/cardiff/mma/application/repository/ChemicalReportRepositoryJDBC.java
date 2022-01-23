package uk.ac.cardiff.mma.application.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uk.ac.cardiff.mma.application.DTO.*;
import uk.ac.cardiff.mma.application.model.ChemicalDetailMapper;
import uk.ac.cardiff.mma.application.model.ChemicalDetialIDVersionMapper;
import uk.ac.cardiff.mma.application.model.ChemicalMapper;

import java.sql.Types;
import java.util.List;

@Repository
public class ChemicalReportRepositoryJDBC implements ChemicalReportRepository {


    JdbcTemplate jdbcTemplate;

    public ChemicalReportRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ChemicalDTO> queryAllChemical() {
        return jdbcTemplate.query("select * from ChemicalDetail.chemical;", new ChemicalMapper());
    }

    @Override
    public boolean deleteChemical(int id) {
        String sql = "delete from ChemicalDetail.chemical where id = ?";
        Object[] args = new Object[]{id};
        return jdbcTemplate.update(sql, args) == 1;
    }

    @Override
    public ChemicalDTO queryAChemicalByID(int id) {
        int type[] = new int[]{
                Types.INTEGER
        };
        ChemicalDTO chemicalDTO = (ChemicalDTO) jdbcTemplate.queryForObject("select * from ChemicalDetail.chemical where id = ?",
                new Object[]{id},type,new ChemicalMapper());
        return chemicalDTO;
    }


    @Override
    public boolean updateChemicalDetail(String level, int id,String chemicalName,String location,String unit,int bottleNum,double storage) {

        int types[] = new int[]{
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.INTEGER,
                Types.DOUBLE,
                Types.INTEGER,
        };

        int rows = jdbcTemplate.update("update ChemicalDetail.chemical set level = ?, chemical_name = ?, location=?, unit = ?, bottle_num = ?,storage = ? where id = ?",
                new Object[]{level,chemicalName,location,unit,bottleNum,storage,id}, types);
        return rows > 0;
    }

    @Override
    public boolean addChemical(AddingChemicalDTO addingChemicalDTO) {
        int rows = jdbcTemplate.update("INSERT INTO ChemicalDetail.chemical ( `level`, `chemical_name`,`location`,`storage`,`bottle_num`,`unit`)\n" +
                "VALUES (?,?,?,?,?,?)", new Object[]{addingChemicalDTO.getLevel(), addingChemicalDTO.getChemicalName(),addingChemicalDTO.getLocation(),addingChemicalDTO.getStorage(),addingChemicalDTO.getBottleNum(),addingChemicalDTO.getUnit()});
        return rows > 0;
    }

    @Override
    public List<ChemicalDeliveryDTO> queryChemicalDeliveryDetailByID(int id) {
        int type[] = new int[]{
                Types.INTEGER
        };
        return jdbcTemplate.query("select cd.id,c.chemical_name,cd.expiry,cd.delivery_date,cd.weight,c.unit from ChemicalDetail.chemical_delivery cd join ChemicalDetail.chemical c on cd.chemicalID = c.id where c.id = ? ",
                new Object[]{id}, type, new ChemicalDetailMapper());
    }

    @Override
    public boolean deleteChemicalDeliveryByID(int id) {
        String sql = "delete from ChemicalDetail.chemical_delivery where id = ?";
        Object[] args = new Object[]{id};
        return jdbcTemplate.update(sql, args) == 1;
    }

    @Override
    public List<ChemicalDeliveryIDVersionDTO> queryAllChemicalDeliveryDetail() {
        return jdbcTemplate.query("select * from ChemicalDetail.chemical_delivery", new ChemicalDetialIDVersionMapper());
    }

    @Override
    public boolean addChemicalDeliveryRecord(AddingChemicalRecordDTO addingChemicalRecordDTO) {

        int rows = jdbcTemplate.update("insert into ChemicalDetail.chemical_delivery (chemicalID,expiry,delivery_date,weight,unit) values (?,?,?,?,?)",
                new Object[]{getChemicalID(addingChemicalRecordDTO.getChemicalName()),addingChemicalRecordDTO.getExpiry(), addingChemicalRecordDTO.getDeliveryDate(), addingChemicalRecordDTO.getWeight(),addingChemicalRecordDTO.getUnit()});
        return rows > 0;
    }

    @Override
    public int getChemicalID(String chemicalName) {

        String sql = "select id from ChemicalDetail.chemical where chemical_name = ?";
        int chemicalID = jdbcTemplate.queryForObject(sql,Integer.class,chemicalName);
        return chemicalID;
    }

    @Override
    public double getChemicalStorageByID(int id) {
        double chemicalStorage = jdbcTemplate.queryForObject("select storage from ChemicalDetail.chemical where id = ?",Integer.class, id);
        return chemicalStorage;
    }

    @Override
    public boolean updateChemicalStorage(double storage, int id) {
        int types[] = new int[]{
                Types.DOUBLE,
                Types.INTEGER
        };

        int rows = jdbcTemplate.update("update ChemicalDetail.chemical set storage = ? where id = ?",
                new Object[]{storage, id}, types);
        return rows > 0;
    }

    @Override
    public boolean deleteChemicalDeliveryRecordByChemicalID(int chemicalID) {
        String sql = "delete from ChemicalDetail.chemical_delivery where chemicalID = ?";
        Object[] args = new Object[]{chemicalID};
        return jdbcTemplate.update(sql, args) == 1;
    }

}
