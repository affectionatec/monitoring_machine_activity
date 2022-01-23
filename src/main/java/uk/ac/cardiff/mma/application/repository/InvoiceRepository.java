package uk.ac.cardiff.mma.application.repository;
import uk.ac.cardiff.mma.application.DTO.InvoiceDTO;

import java.util.List;

public interface InvoiceRepository {
    public List getUsernames();

    public List getBookingYears();

    public List<InvoiceDTO> getInvoice(String username, int month, int year);
}
