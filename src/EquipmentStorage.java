import java.util.List;

public class EquipmentStorage {
    private List<Equipment> equipmentList;

    public EquipmentStorage(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public synchronized void add(String equipmentName) {
        Equipment equipment = equipmentList.get(lookupEquipment(equipmentName));
        equipment.quantity++;
        notify();
    }

    public synchronized Equipment get(String equipmentName) {
        Equipment equipment = equipmentList.get(lookupEquipment(equipmentName));

        while (equipment.quantity == 0) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }

        equipment.quantity--;
        return equipment;
    }

    private int lookupEquipment(String name) {
        for (final Equipment equipment : equipmentList) {
            if (equipment.name.equals(name)) {
                return equipmentList.indexOf(equipment);
            }
        }
        return -1;
    }
}
