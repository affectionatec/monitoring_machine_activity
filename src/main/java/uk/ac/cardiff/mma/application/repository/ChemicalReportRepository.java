package uk.ac.cardiff.mma.application.repository;


import uk.ac.cardiff.mma.application.DTO.AddingChemicalDTO;
import uk.ac.cardiff.mma.application.DTO.AddingChemicalRecordDTO;

public interface ChemicalReportRepository {
    Object queryAllChemical();

    boolean deleteChemical(int id);

    Object queryAChemicalByID(int id);

    boolean updateChemicalDetail(String level, int id,String chemicalName,String location,String unit,int bottleNum,double storage);

    boolean addChemical(AddingChemicalDTO addingChemicalDTO);

    Object queryChemicalDeliveryDetailByID(int id);

    boolean deleteChemicalDeliveryByID(int id);

    Object queryAllChemicalDeliveryDetail();

    boolean addChemicalDeliveryRecord(AddingChemicalRecordDTO addingChemicalRecordDTO);

    int getChemicalID(String chemicalName);

    double getChemicalStorageByID(int id);

    boolean updateChemicalStorage(double storage, int id);

    boolean deleteChemicalDeliveryRecordByChemicalID(int chemicalID);

}
