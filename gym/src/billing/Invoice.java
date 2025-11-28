package billing;

import members.Member;

public final class Invoice {
    public static void applyMonthlyFee(Member member, int fee) {
        member.addCharge(fee);
    }

    public static void applyDiscount(Member member, double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new GymException("Percentage is out of bounds");
        }

        member.setChargeCount(member.getChargeCount() - 1);
        int fee = member.getCharge();

        fee *= (int)(1 - percentage);

        member.addCharge(fee);
    }
}
