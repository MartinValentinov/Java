package staff;
import core.Person;

public class Trainer extends Person {
    private String specialty = "General Fitness";

    public Trainer(String name) {
        super(name);
    }

    public Trainer(String name, int age, String specialty) {
        super(name, age);
        validateNotBlank(specialty, this.specialty);
        this.specialty = specialty;
    }

    @Override
    public String getRole() {
        return "Trainer";
    }
}
