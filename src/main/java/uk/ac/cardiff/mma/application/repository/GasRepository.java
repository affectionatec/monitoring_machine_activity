package uk.ac.cardiff.mma.application.repository;

import uk.ac.cardiff.mma.application.DTO.AddGasDeliveryForm;
import uk.ac.cardiff.mma.application.DTO.AddGasForm;
import uk.ac.cardiff.mma.application.DTO.GasDTO;

import java.util.List;

public interface GasRepository {

    public Object findAllGasDetails();

    public Object findGasByLocation(String location);

    public Object findGasByName(String name);

    boolean addGas(AddGasForm addGasForm);

    public boolean deleteGas(int id);

    public Object queryGasByID(int id);

    public boolean updateGasDetail(GasDTO gasDTO);

    public List<GasDTO> findNoneRepeatGasLevel();

    public Object queryGasDeliveryDetailByID(int id);

    boolean addGasDelivery(AddGasDeliveryForm addGasDeliveryForm);

    public boolean deleteGasDelivery(int id);

    boolean deleteGasDeliveryRecordByGasID(int gasID);

    public int getStorageByGasID(int id);

    public boolean updateGasStorage(int storage, int id);

}