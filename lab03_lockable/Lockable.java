package lab03_lockable;

public interface Lockable {
    public boolean setKey(int key);
    public void lock();
    public boolean locked();
    public boolean unlock(int key);
}
