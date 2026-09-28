import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class MedicalEquipment implements Maintainable {
    protected String equipmentId;
    protected String name;
    protected String manufacturer;
    protected String model;
    protected EquipmentStatus status;
    protected Date lastMaintenanceDate;
    protected Date nextMaintenanceDate;
    
    // Composition: MaintenanceRecord এর তালিকা
    protected List<MaintenanceRecord> maintenanceHistory;

    public MedicalEquipment(String equipmentId, String name, String manufacturer, String model) {
        this.equipmentId = equipmentId;
        this.name = name;
        this.manufacturer = manufacturer;
        this.model = model;
        this.status = EquipmentStatus.OPERATIONAL;
        this.maintenanceHistory = new ArrayList<>();
    }

    public abstract void startOperation();
    public abstract void stopOperation();

    public EquipmentStatus checkStatus() {
        return this.status;
    }

    public void setStatus(EquipmentStatus status) {
        this.status = status;
    }

    public String getEquipmentInfo() {
        return String.format("ID: %s | Name: %s | Model: %s (%s) | Status: %s",
                equipmentId, name, model, manufacturer, status);
    }

    @Override
    public void scheduleMaintenance(Date date) {
        this.nextMaintenanceDate = date;
    }

    @Override
    public void performMaintenance() {
        this.lastMaintenanceDate = new Date();
        this.status = EquipmentStatus.OPERATIONAL;
        String recordId = "REC-" + (maintenanceHistory.size() + 1);
        MaintenanceRecord record = new MaintenanceRecord(recordId, this.lastMaintenanceDate, "Certified Technician", "Routine calibration & inspection completed.");
        maintenanceHistory.add(record);
    }

    public void addMaintenanceRecord(MaintenanceRecord record) {
        this.maintenanceHistory.add(record);
        this.lastMaintenanceDate = record.getDate();
    }

    @Override
    public Date getNextMaintenanceDate() {
        return this.nextMaintenanceDate;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public Date getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }

    public List<MaintenanceRecord> getMaintenanceHistory() {
        return maintenanceHistory;
    }
}
