package lab03_lockable;

public class CheckingAccount extends Account implements Lockable {
    private int key;
    private boolean locked;
    private double balance;

    public CheckingAccount(double balance) {
        super(balance);
        this.locked = false;
        this.balance = balance;
    }

    public boolean setKey(int key) {
        if (this.locked) {
            return false;
        }
        this.key = key;
        return true;
    }

    public void lock() {
        this.locked = true;
    }

    public boolean locked() {
        return this.locked;
    }

    public boolean unlock(int key) {
        if (this.key == key) {
            this.locked = false;
            return true;
        }
        return false;
    }

    public boolean deposit(double amount) {
        if (this.locked) {
            return false;
        }

        this.balance += amount;
        return true;
    }

    public boolean withdraw(double amount) {
        if (this.locked) {
            return false;
        }

        if (this.balance < amount) {
            return false;
        }

        this.balance -= amount;
        return true;
    }

    public String toString() {
        if (this.locked) {
            return "Locked checking account";
        } else {
            return String.format("Checking account with balance %.2f", this.balance);
        }
    }
}
