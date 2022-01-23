package uk.ac.cardiff.mma.application.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uk.ac.cardiff.mma.application.DTO.InvoiceDTO;
import uk.ac.cardiff.mma.application.model.InvoiceMapper;

import java.sql.Types;
import java.util.List;

@Repository
public class InvoiceRepositoryJDBC implements InvoiceRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public InvoiceRepositoryJDBC(JdbcTemplate injection) {
        this.jdbcTemplate = injection;
    }

    @Override
    public List getUsernames() {
        String query = "SELECT username FROM User WHERE type = 'USER';";
        List<String> usernames = jdbcTemplate.queryForList(query, String.class);

        return usernames;
    }

    @Override
    public List getBookingYears() {
        String query = "SELECT DISTINCT YEAR(date) AS year FROM Booking;";
        List<String> years = jdbcTemplate.queryForList(query, String.class);

        return years;
    }

    @Override
    public List<InvoiceDTO> getInvoice(String username, int month, int year) {
        String query = "SELECT equipmentID, name AS equipmentName, COUNT(1) AS halfHours, charge_rate AS chargeRate, COUNT(1) * charge_rate AS totalCharge" +
                " FROM Booking" +
                " JOIN Equipment" +
                " ON Booking.equipmentID = Equipment.id" +
                " WHERE username = ?" +
                " AND used = 1" +
                " AND MONTH(date) = ?" +
                " AND YEAR(date) = ?" +
                " GROUP BY equipmentID;";

        try {
            return jdbcTemplate.query(query, new InvoiceMapper(), username, month, year);
        } catch (Exception e) {
            return null;
        }
    }
}
