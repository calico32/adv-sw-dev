package lab06_linked_list;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        var magazineRack = new LinkedList<Magazine>();
        var time = new Magazine("Time", "Time Inc.", "1234-5678");
        var people = new Magazine("People", "People Inc.", "5678-1234");
        var si = new Magazine("Sports Illustrated", "Sports Illustrated Inc.", "91011-1213");
        var natgeo = new Magazine("National Geographic", "National Geographic Inc.", "1415-1617");

        magazineRack.add(time);
        magazineRack.add(people);
        magazineRack.add(si);

        System.out.println("The magazine rack contains:");
        System.out.println(magazineRack);

        magazineRack.remove(people);
        magazineRack.add(1, natgeo);

        System.out.println("The magazine rack now contains:");
        System.out.println(magazineRack);
    }

}

record Magazine(String title, String publisher, String isbn) {
    public String toString() {
        return String.format("%s by %s; ISBN: %s", title, publisher, isbn);
    }
}

class Node<T> {
    public T data;
    public Node<T> next;
    public Node(T data) {
        this.data = data;
    }
}

class LinkedList<T> implements List<T> {
    private Node<T> head;

    public LinkedList() {
        head = null;
    }

    public LinkedList(Collection<? extends T> collection) {
        addAll(collection);
    }

    public LinkedList(T[] array) {
        addAll(List.of(array));
    }

    public String toString() {
        var s = new StringBuilder();
        s.append("[");
        var current = head;
        while (current != null) {
            s.append(current.data);
            if (current.next != null) {
                s.append(", ");
            }
            current = current.next;
        }
        s.append("]");
        return s.toString();
    }

    @Override
    public int size() {
        var current = head;
        var count = 0;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public boolean contains(Object o) {
        var current = head;
        while (current != null) {
            if (current.data.equals(o)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                var data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    @Override
    public Object[] toArray() {
        var array = new Object[size()];
        var current = head;
        var i = 0;
        while (current != null) {
            array[i++] = current.data;
            current = current.next;
        }
        return array;
    }

    @Override
    public <T1> T1[] toArray(T1[] t1s) {
        var current = head;
        var i = 0;
        while (current != null) {
            t1s[i++] = (T1) current.data;
            current = current.next;
        }
        return t1s;
    }

    @Override
    public boolean add(T t) {
        if (head == null) {
            head = new Node<>(t);
        } else {
            var current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = new Node<>(t);
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (head == null) {
            return false;
        }
        if (head.data.equals(o)) {
            head = head.next;
            return true;
        }
        var current = head;
        while (current.next != null) {
            if (current.next.data.equals(o)) {
                current.next = current.next.next;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (var o : collection) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        for (var o : collection) {
            add(o);
        }
        return true;
    }

    @Override
    public boolean addAll(int i, Collection<? extends T> collection) {
        for (var o : collection) {
            add(i++, o);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        var changed = false;
        for (var o : collection) {
            changed |= remove(o);
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    @Override
    public void clear() {
        head = null;
    }

    @Override
    public T get(int i) {
        var current = head;
        for (var j = 0; j < i; j++) {
            current = current.next;
        }
        return current.data;
    }

    @Override
    public T set(int i, T t) {
        var current = head;
        for (var j = 0; j < i; j++) {
            current = current.next;
        }
        var old = current.data;
        current.data = t;
        return old;
    }

    @Override
    public void add(int i, T t) {
        if (i == 0) {
            var newHead = new Node<>(t);
            newHead.next = head;
            head = newHead;
        } else {
            var current = head;
            for (var j = 0; j < i - 1; j++) {
                current = current.next;
            }
            var newNode = new Node<>(t);
            newNode.next = current.next;
            current.next = newNode;
        }
    }

    @Override
    public T remove(int i) {
        if (i == 0) {
            var old = head.data;
            head = head.next;
            return old;
        } else {
            var current = head;
            for (var j = 0; j < i - 1; j++) {
                current = current.next;
            }
            var old = current.next.data;
            current.next = current.next.next;
            return old;
        }
    }

    @Override
    public int indexOf(Object o) {
        var current = head;
        var i = 0;
        while (current != null) {
            if (current.data.equals(o)) {
                return i;
            }
            current = current.next;
            i++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        var current = head;
        var i = 0;
        var lastIndex = -1;
        while (current != null) {
            if (current.data.equals(o)) {
                lastIndex = i;
            }
            current = current.next;
            i++;
        }
        return lastIndex;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new LinkedListIterator<>(head);
    }

    @Override
    public ListIterator<T> listIterator(int i) {
        var current = head;
        for (var j = 0; j < i; j++) {
            current = current.next;
        }
        return new LinkedListIterator<>(current);
    }

    @Override
    public List<T> subList(int i, int i1) {
        return List.of();
    }
}

class LinkedListIterator<T> implements ListIterator<T> {
    private Node<T> current;
    private int index;

    public LinkedListIterator(Node<T> current) {
        this.current = current;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public T next() {
        var data = current.data;
        current = current.next;
        index++;
        return data;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public T previous() {
        return null;
    }

    @Override
    public int nextIndex() {
        return index;
    }

    @Override
    public int previousIndex() {
        return -1;
    }

    @Override
    public void remove() {
        current = current.next;
    }

    @Override
    public void set(T t) {
        current.data = t;
    }

    @Override
    public void add(T t) {
        var newNode = new Node<>(t);
        newNode.next = current.next;
        current.next = newNode;
    }
}