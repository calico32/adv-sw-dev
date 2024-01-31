package lab03_lockable;

public abstract class Account implements Lockable {
    protected double balance;

    public Account(double balance) {
        this.balance = balance;
    }

    public abstract boolean deposit(double amount);
    public abstract boolean withdraw(double amount);
    public double getBalance() {
        return this.balance;
    }

    public String toString() {
        if (this.locked()) {
            return "Locked account";
        } else {
            return String.format("Account with balance %.2f", this.balance);
        }
    }
}
