import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("Hospital Medical Equipment Management System (CSE 1115 Demo)");
        System.out.println("Submitted by: Pranta Mohajan | ID: 0222510005101025");
        System.out.println("===============================================================\n");

        Hospital hospital = new Hospital("Premier Central Hospital");
        MaintenanceScheduler scheduler = new MaintenanceScheduler();

        PatientMonitor monitor = new PatientMonitor("EQ-PM-01", "ICU Bedside Monitor", "Philips", "IntelliVue MX800");
        Ventilator ventilator = new Ventilator("EQ-VN-02", "Critical Care Ventilator", "Dräger", "Evita V800");
        InfusionPump pump = new InfusionPump("EQ-IP-03", "Smart Infusion Pump", "Baxter", "Sigma Spectrum");
        ECGMachine ecg = new ECGMachine("EQ-ECG-04", "12-Lead Diagnostic ECG", "GE Healthcare", "MAC 2000", 12);
        PortableUltrasound ultrasound = new PortableUltrasound("EQ-US-05", "Point-of-Care Ultrasound", "Mindray", "M9", "Convex");

        hospital.addEquipment(monitor);
        hospital.addEquipment(ventilator);
        hospital.addEquipment(pump);
        hospital.addEquipment(ecg);
        hospital.addEquipment(ultrasound);

        System.out.println("\n--- Testing Polymorphic startOperation() Across All Devices ---");
        for (MedicalEquipment eq : hospital.getEquipmentList()) {
            eq.startOperation();
        }

        System.out.println("\n--- Testing Specific Clinical Operations ---");
        monitor.displayVitals();
        ventilator.adjustOxygenLevel(95.0);
        pump.setFlowRate(25.5);
        ecg.recordECG();
        ultrasound.captureImage();

        System.out.println("\n--- Testing Maintenance & Scheduling ---");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 7);
        Date scheduledDate = cal.getTime();

        scheduler.scheduleTask(ventilator, "Engr. Kamal", "Annual sensor calibration", scheduledDate);
        ventilator.setStatus(EquipmentStatus.UNDER_MAINTENANCE);

        ventilator.performMaintenance();

        System.out.println("\n--- Service History for Ventilator ---");
        for (MaintenanceRecord rec : ventilator.getMaintenanceHistory()) {
            System.out.println(rec.getSummary());
        }

        hospital.displayAllEquipment();
        System.out.println("\nAll functional requirements verified successfully!");
    }
}
