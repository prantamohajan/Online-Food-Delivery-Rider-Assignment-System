import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MaintenanceScheduler {
    private List<MaintenanceRecord> scheduledTasks;

    public MaintenanceScheduler() {
        this.scheduledTasks = new ArrayList<>();
    }

    public void scheduleTask(MedicalEquipment equipment, String technician, String description, Date date) {
        equipment.scheduleMaintenance(date);
        String recordId = "SCHED-" + (scheduledTasks.size() + 101);
        MaintenanceRecord record = new MaintenanceRecord(recordId, date, technician, description + " [" + equipment.getName() + "]");
        scheduledTasks.add(record);
    }

    public List<MaintenanceRecord> getUpcomingTasks() {
        return scheduledTasks;
    }
}
