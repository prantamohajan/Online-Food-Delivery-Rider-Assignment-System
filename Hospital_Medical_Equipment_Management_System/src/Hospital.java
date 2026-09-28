import java.util.ArrayList;
import java.util.List;

public class Hospital {
    private String name;
    private List<MedicalEquipment> equipmentList;

    public Hospital(String name) {
        this.name = name;
        this.equipmentList = new ArrayList<>();
    }

    public void addEquipment(MedicalEquipment e) {
        equipmentList.add(e);
    }

    public void removeEquipment(String id) {
        equipmentList.removeIf(e -> e.getEquipmentId().equalsIgnoreCase(id));
    }

    public List<MedicalEquipment> getOperationalEquipment() {
        List<MedicalEquipment> operational = new ArrayList<>();
        for (MedicalEquipment e : equipmentList) {
            if (e.checkStatus() == EquipmentStatus.OPERATIONAL) {
                operational.add(e);
            }
        }
        return operational;
    }

    public void displayAllEquipment() {
        System.out.println("\n===== " + name + " Equipment Inventory =====");
        for (MedicalEquipment e : equipmentList) {
            System.out.println(e.getEquipmentInfo());
        }
    }

    public List<MedicalEquipment> getEquipmentList() {
        return equipmentList;
    }

    public String getName() {
        return name;
    }
}
