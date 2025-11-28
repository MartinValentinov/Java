package members;
import billing.GymException;
import core.Person;

import static util.Settings.*;

public class Member extends Person{
    private int memberShipLevel;
    private final int[] charges = new int[MAX_CHARGES];
    private final String[] descriptions = new String[MAX_CHARGES];
    private int chargeCount;

    public Member(String name) {
        super(name);
        this.memberShipLevel = 0;
        this.chargeCount = 0;
    }

    public Member(String name, int age, int memberShipLevel) {
        super(name, age);
        this.memberShipLevel = memberShipLevel;
        this.chargeCount = 0;
    }

    @Override
    public String getRole() {
        return "Member";
    }

    public void addCharge(int amount) {
        if (amount < 0) {
            throw new GymException("Amount can not be negative");
        }
        if (chargeCount >= MAX_CHARGES) {
            throw new GymException("Charges are full!");
        }

        charges[chargeCount] = amount;
        chargeCount ++;
    }

    public void addCharge(String description, int amount) {
        if (amount < MIN_CHARGE || amount > MAX_CHARGE) {
            throw new GymException("Amount is out of range");
        }
        if (chargeCount < 0 || chargeCount >= MAX_CHARGES) {
            throw new GymException("Charges are full!");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description can not be empty or null");
        }

        descriptions[chargeCount] = description;
        charges[chargeCount] = amount;

        chargeCount ++;
    }

    public int getTotalCharges() {
        int result = 0;
        for (int i = 0; i < chargeCount; i ++) {
            result += charges[i];
        }
        return result;
    }

    public int getAverageCharge() {
        if (chargeCount == 0) return 0;

        return getTotalCharges() / chargeCount;
    }

    public int getMemberShipLevel() {
        return memberShipLevel;
    }

    public void setMemberShipLevel(int memberShipLevel) {
        if (memberShipLevel < 1 || memberShipLevel > 3) {
            throw new GymException("Membership level is invalid!");
        }
        else this.memberShipLevel = memberShipLevel;
    }

    public int getCharge() {
        return charges[chargeCount];
    }

    public int getChargeCount() {
        return chargeCount;
    }

    public void setChargeCount(int count) {
        chargeCount = count;
    }
}
