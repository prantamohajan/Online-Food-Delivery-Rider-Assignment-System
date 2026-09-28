import java.text.SimpleDateFormat;
import java.util.Date;

public class MaintenanceRecord {
    private String recordId;
    private Date date;
    private String technician;
    private String description;

    public MaintenanceRecord(String recordId, Date date, String technician, String description) {
        this.recordId = recordId;
        this.date = date;
        this.technician = technician;
        this.description = description;
    }

    public String getRecordId() {
        return recordId;
    }

    public Date getDate() {
        return date;
    }

    public String getTechnician() {
        return technician;
    }

    public String getDescription() {
        return description;
    }

    public String getSummary() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return String.format("[%s] Record ID: %s | Tech: %s | Details: %s",
                sdf.format(date), recordId, technician, description);
    }
}
