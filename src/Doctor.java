public class Doctor extends Thread {
    private EquipmentStorage equipmentStorage;
    private WaitingRoom patients;

    public Doctor (WaitingRoom patients, EquipmentStorage equipmentStorage) {
        this.equipmentStorage = equipmentStorage;
        this.patients = patients;
    }

    public void run() {
        while (patients.size() != 0) {
            // Get the next patient
            Patient patient = patients.get();
            // Reserve all the equipment needed for that patient
            for (String equipment : patient.equipmentNeeded) {
                equipmentStorage.get(equipment);
            }
            // Wait for the treatment time to expire
            try {
                sleep(patient.timeToTreat);
            } catch (InterruptedException e) {}
            // Release all the equipment used to treat that patient
            for (String equipment : patient.equipmentNeeded) {
                equipmentStorage.add(equipment);
            }
        }

        // I can output something here to the console when the patient size gets to 0..
        // and that would tell me that this run is done..
        // but I don't know how to access this programmatically since run() must return void..?
    }
}
