package uk.ac.cardiff.mma.application.repository;

import java.util.List;

public interface HomepageRepository {

    public Object queryOccupiedEquipment();
    public Object queryBookingRecord(String username);
    public List getAllProjectCodes();
}
