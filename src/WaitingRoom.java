import java.util.List;

public class WaitingRoom {
    private List<Patient> patients;

    public WaitingRoom(List<Patient> patients) {
        this.patients = patients;
    }

    public void add(Patient patient) {
        patients.add(patient);
    }

    public Patient get() {
        synchronized (patients) {
            Patient patient = patients.get(0);
            patients.remove(0);

            return patient;
        }
    }

    public int size() {
        return patients.size();
    }
}
