package lab03_lockable;

public class Main {
    public static void main(String[] args) {
        boolean success;
        CheckingAccount account = new CheckingAccount(1000);
        System.out.println(account);
        success = account.setKey(1234);
        System.out.println("Set key " + (success ? "succeeded" : "failed"));
        account.lock();
        System.out.println("Locked: " + account.locked());
        success = account.deposit(100);
        System.out.println("Deposit " + (success ? "succeeded" : "failed"));
        success = account.unlock(1234);
        System.out.println("Unlock " + (success ? "succeeded" : "failed"));
        success = account.deposit(100);
        System.out.println("Deposit " + (success ? "succeeded" : "failed"));
        System.out.println(account);
    }
}
