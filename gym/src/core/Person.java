package core;

abstract public class Person {
    private String name;
    private int age;

    protected Person(String name) {
        this.name = name;
        this.age = 0;
    }

    protected Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = Math.max(age, 0);
    }

    public abstract String getRole();

    public String getBadge() {
        return String.format("%s (%s), age %d", name, getRole(), age);
    }

    protected void validateNotBlank(String value, String fieldName) {
        if (value == null || value.isBlank() || fieldName == null || fieldName.isBlank()) {
            throw new IllegalArgumentException("Values can not be null or empty");
        }
    }
}
