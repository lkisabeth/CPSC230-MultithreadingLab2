import javax.print.Doc;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HospitalController {
    private List<Doctor> doctors = new ArrayList<>();
    private List<Equipment> tmpEqList = new ArrayList<>(); // used to build proper EquipmentStorage for each run
    private List<Patient> tmpPatList = new ArrayList<>(); // used to build proper WaitingRoom for each run
    private int[] doctorsOnDuty = {1, 2, 4, 8};

    void go() {
        // Use JFileChooser to select a Patients file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        File file = fileChooser.getSelectedFile();
        Scanner input = null;
        try {
            input = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        }

        // Populate the hospital with the file
        populate(input);

        // Perform a run for each value of doctorsOnDuty
        for (int value : doctorsOnDuty) {
            // Instantiate fresh WaitingRoom and EquipmentStorage for each run
            WaitingRoom patients = new WaitingRoom(tmpPatList);
            EquipmentStorage equipmentStorage = new EquipmentStorage(tmpEqList);

            // Instantiate fresh, well-rested doctors
            for (int i = 0; i < value; i++) {
                Doctor doctor = new Doctor(patients, equipmentStorage);
                doctors.add(doctor);
            }

            // start the timer and all of the Doctor threads
            System.out.println("Doors are open!");
            long startTime = System.currentTimeMillis();
            for (Doctor doctor : doctors) {
                doctor.start();
            }

            // DISCLAIMER
            // I know this timing isn't right, but I haven't yet figured out a practical way to detect task completion

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            System.out.println("All patients treated!");
            System.out.println("That run took: " + duration + " milliseconds.");
        }
    }

    private void populate(Scanner input) {
        int numEquip = Integer.parseInt(input.nextLine());
        for (int i = 0; i < numEquip; i++) {
            String line = input.nextLine();
            String[] split = line.split(" ");
            Equipment equipment = new Equipment(split[0], Integer.parseInt(split[1]));
            tmpEqList.add(equipment);
        }
        int numPatients = Integer.parseInt(input.nextLine());
        for (int i = 0; i < numPatients; i++) {
            long timeToTreat = Long.parseLong(input.nextLine());
            String line = input.nextLine();
            String[] equipmentNeeded = line.split(" ");
            Patient patient = new Patient(timeToTreat, equipmentNeeded);
            tmpPatList.add(patient);
        }
    }
}
