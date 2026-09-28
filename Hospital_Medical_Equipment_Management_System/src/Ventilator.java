public class Ventilator extends MedicalEquipment {
    private double oxygenLevel;
    private int breathingRate;

    public Ventilator(String equipmentId, String name, String manufacturer, String model) {
        super(equipmentId, name, manufacturer, model);
        this.oxygenLevel = 98.0;
        this.breathingRate = 16;
    }

    @Override
    public void startOperation() {
        this.status = EquipmentStatus.OPERATIONAL;
        System.out.println("[Ventilator " + equipmentId + "] Mechanical ventilation delivery active.");
    }

    @Override
    public void stopOperation() {
        System.out.println("[Ventilator " + equipmentId + "] Ventilation safely paused.");
    }

    public void adjustOxygenLevel(double level) {
        this.oxygenLevel = level;
        System.out.println("[Ventilator " + equipmentId + "] Oxygen concentration adjusted to " + level + "%.");
    }

    public double getOxygenLevel() { return oxygenLevel; }
    public int getBreathingRate() { return breathingRate; }
}
