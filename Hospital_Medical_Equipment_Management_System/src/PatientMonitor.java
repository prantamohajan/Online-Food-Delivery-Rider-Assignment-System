public class PatientMonitor extends MedicalEquipment {
    private int heartRate;
    private String bloodPressure;

    public PatientMonitor(String equipmentId, String name, String manufacturer, String model) {
        super(equipmentId, name, manufacturer, model);
        this.heartRate = 75;
        this.bloodPressure = "120/80";
    }

    @Override
    public void startOperation() {
        this.status = EquipmentStatus.OPERATIONAL;
        System.out.println("[PatientMonitor " + equipmentId + "] Real-time vital sign tracking started.");
    }

    @Override
    public void stopOperation() {
        System.out.println("[PatientMonitor " + equipmentId + "] Vital sign tracking stopped.");
    }

    public void displayVitals() {
        System.out.println("[PatientMonitor " + equipmentId + "] Heart Rate: " + heartRate + " bpm | BP: " + bloodPressure);
    }

    public void updateVitals(int hr, String bp) {
        this.heartRate = hr;
        this.bloodPressure = bp;
    }

    public int getHeartRate() { return heartRate; }
    public String getBloodPressure() { return bloodPressure; }
}
