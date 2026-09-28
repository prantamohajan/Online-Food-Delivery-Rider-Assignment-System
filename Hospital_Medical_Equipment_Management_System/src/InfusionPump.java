public class InfusionPump extends MedicalEquipment {
    private double flowRate;
    private String medicationName;

    public InfusionPump(String equipmentId, String name, String manufacturer, String model) {
        super(equipmentId, name, manufacturer, model);
        this.flowRate = 20.0;
        this.medicationName = "Saline Solution";
    }

    @Override
    public void startOperation() {
        this.status = EquipmentStatus.OPERATIONAL;
        System.out.println("[InfusionPump " + equipmentId + "] Administering " + medicationName + " at rate: " + flowRate + " mL/h.");
    }

    @Override
    public void stopOperation() {
        System.out.println("[InfusionPump " + equipmentId + "] Medication infusion halted.");
    }

    public void setFlowRate(double rate) {
        this.flowRate = rate;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public double getFlowRate() { return flowRate; }
    public String getMedicationName() { return medicationName; }
}
