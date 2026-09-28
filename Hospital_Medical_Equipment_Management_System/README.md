# Hospital Medical Equipment Management System

An Object-Oriented Software Design & Management Dashboard for Heterogeneous Clinical Medical Equipment.

## Project Details
* **Course:** Object Oriented Programming (CSE 1115)
* **Semester:** Spring 2026, 3rd Semester
* **Institution:** Premier University, Chittagong
* **Submitted By:** Pranta Mohajan (ID: 0222510005101025)

---

## Key OOP Implementations
1. **Abstraction:** `MedicalEquipment` abstract class enforcing `startOperation()` and `stopOperation()`.
2. **Encapsulation:** Device parameters protected/private and controlled via validated methods.
3. **Inheritance:** 5 specialized device subclasses:
   - `PatientMonitor`
   - `Ventilator`
   - `InfusionPump`
   - `ECGMachine`
   - `PortableUltrasound`
4. **Polymorphism:** Method overriding across device types and uniform operations via `List<MedicalEquipment>`.
5. **Interface (Realization):** `Maintainable` contract implemented by `MedicalEquipment`.
6. **Composition:** `MedicalEquipment` owns its `MaintenanceRecord` list.
7. **Aggregation:** `Hospital` manages collection of independent `MedicalEquipment` instances.
8. **Association:** `MaintenanceScheduler` schedules tasks for `MedicalEquipment`.

---

## How to Run

### Option 1: Run Interactive GUI Dashboard (Recommended)
```bash
javac src/*.java
java -cp src HospitalGUI
```

### Option 2: Run Terminal / Console Demo
```bash
javac src/*.java
java -cp src Main
```
