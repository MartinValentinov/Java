import billing.BillingException;
import billing.GymException;
import core.Person;
import members.Member;
import staff.Trainer;
import util.IdGenerator;
import util.Settings;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void processBilling() throws BillingException {
        throw new BillingException("Testing");
    }

    public static void test() {
        System.out.println("=== Gym Billing Exception Demo ===");

        try {
            Member member = new Member("Gold");
            member.addCharge(500);
            member.addCharge("Equipment fee", 12_000);
        } catch (GymException e) {
            System.err.println("Business rule violation: " + e.getMessage());
        }

        try (FileReader reader = new FileReader("charges.txt")) {
            System.out.println("File opened successfully.");
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error closing file: " + e.getMessage());
        } finally {
            System.out.println("Closing file reader (simulation).");
        }

        try {
            processBilling();
        } catch (BillingException e) {
            System.err.println("Recoverable billing issue: " + e.getMessage());
        } catch (GymException e) {
            System.err.println("Business rule issue: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }

        System.out.println("=== End of demo ===");
    }

    static void main(String[] args) {
        Member member1 = new Member("Member1");
        Member member2 = new Member("Member2", 11, 2);

        System.out.printf("M1: %s M2: %s \n", member1.getBadge(), member2.getBadge());

        member1.addCharge(15);
        System.out.println(IdGenerator.nextId());
        member2.addCharge("He deserves it", 1000);
        System.out.println(IdGenerator.nextId("MEMBER2"));

        Trainer trainer1 = new Trainer("Trainer1");
        Trainer trainer2 = new Trainer("Trainer2", 19, "Fitness");

        System.out.printf("T1: %s T2: %s \n", trainer1.getBadge(), trainer2.getBadge());

        System.out.printf("MS: %d, MC: %d, ME: %d \n", Settings.MAX_CHARGES, Settings.MAX_CHARGE, Settings.MIN_CHARGE);

        Person[] people = {
                new Member("smn"),
                new Trainer("tms")
        };

        for (Person person: people) {
            System.out.println(person.getBadge());
        }
    }
}
