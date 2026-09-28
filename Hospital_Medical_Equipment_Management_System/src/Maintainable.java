import java.util.Date;

public interface Maintainable {
    void scheduleMaintenance(Date date);
    void performMaintenance();
    Date getNextMaintenanceDate();
}
