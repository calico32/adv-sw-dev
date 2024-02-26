package lab07_doubly_linked_list;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        var magazineRack = new DoublyLinkedList<Magazine>();
        var time = new Magazine("Time", "Time Inc.", "1234-5678");
        var people = new Magazine("People", "People Inc.", "5678-1234");
        var si = new Magazine("Sports Illustrated", "Sports Illustrated Inc.", "91011-1213");
        var natgeo = new Magazine("National Geographic", "National Geographic Inc.", "1415-1617");

        magazineRack.add(time);
        magazineRack.add(people);
        magazineRack.add(si);
        magazineRack.add(natgeo);

        System.out.println("The magazine rack contains:");
        for (var magazine : magazineRack) {
            System.out.println(magazine);
        }
        System.out.println();


        System.out.println("In reverse order:");
        for (var magazine : magazineRack.reversed()) {
            System.out.println(magazine);
        }
        System.out.println();

        magazineRack.circular();
        System.out.println("Circular (10 items):");
        var iterator  = magazineRack.iterator();
        for (var i = 0; i < 10; i++) {
            System.out.println(iterator.next());
        }


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
    public Node<T> prev;

    public Node(T data) {
        this.data = data;
    }
}

class DoublyLinkedList<T> implements List<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public DoublyLinkedList() {
        head = null;
        tail = null;
    }

    public DoublyLinkedList(Collection<? extends T> collection) {
        addAll(collection);
    }

    public DoublyLinkedList(T[] array) {
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
        return size;
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
        size++;
        if (head == null) {
            head = new Node<>(t);
            tail = head;
        } else {
            var currentTail = tail;
            var newTail = new Node<>(t);
            currentTail.next = newTail;
            newTail.prev = currentTail;
            tail = newTail;
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        size--;
        if (head == null) {
            return false;
        }
        if (head.data.equals(o)) {
            head = head.next;
            return true;
        }
        var current = head;
        while (current != null) {
            if (current.data.equals(o)) {
                current.prev.next = current.next;
                current.next.prev = current.prev;
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
        tail = null;
        size = 0;
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
        size++;
        if (i == 0) {
            var currentHead = head;
            var newHead = new Node<>(t);
            newHead.next = currentHead;
            currentHead.prev = newHead;
        } else {
            var current = head;
            for (var j = 0; j < i - 1; j++) {
                current = current.next;
            }
            var newNode = new Node<>(t);
            newNode.next = current.next;
            newNode.prev = current;
            current.next = newNode;
        }
    }

    @Override
    public T remove(int i) {
        size--;
        if (i == 0) {
            var old = head.data;
            head = head.next;
            head.prev = null;
            return old;
        } else {
            var current = head;
            for (var j = 0; j < i - 1; j++) {
                current = current.next;
            }
            current = current.next;
            var old = current.data;
            current.prev.next = current.next;
            current.next.prev = current.prev;
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
        var current = tail;
        var i = size - 1;
        while (current != null) {
            if (current.data.equals(o)) {
                return i;
            }
            current = current.prev;
            i--;
        }
        return -1;
    }


    @Override
    public Iterator<T> iterator() {
        return new DoublyLinkedListIterator<>(head);
    }

    public Iterator<T> reverseIterator() {
        return new ReverseDoublyLinkedListIterator<>(tail);
    }

    @Override
    public ListIterator<T> listIterator() {
        return new DoublyLinkedListIterator<>(head);
    }

    public ListIterator<T> reverseListIterator() {
        return new ReverseDoublyLinkedListIterator<>(tail);
    }

    @Override
    public ListIterator<T> listIterator(int i) {
        var current = head;
        for (var j = 0; j < i; j++) {
            current = current.next;
        }
        return new DoublyLinkedListIterator<>(current);
    }

    public Iterable<T> reversed() {
        return new ReversedDoublyLinkedList<>(this);
    }

    @Override
    public List<T> subList(int i, int i1) {
        return List.of();
    }

    public DoublyLinkedList<T> circular() {
        tail.next = head;
        head.prev = tail;
        return this;
    }
}

class DoublyLinkedListIterator<T> implements ListIterator<T> {
    private Node<T> current;
    private int index;

    public DoublyLinkedListIterator(Node<T> head) {
        this.current = head;
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
        return current.prev != null;
    }

    @Override
    public T previous() {
        var data = current.data;
        current = current.prev;
        index--;
        return data;
    }

    @Override
    public int nextIndex() {
        return index;
    }

    @Override
    public int previousIndex() {
        return index - 1;
    }

    @Override
    public void remove() {
        var next = current.next;
        var prev = current.prev;
        prev.next = next;
        next.prev = prev;
        current = next;
    }

    @Override
    public void set(T t) {
        current.data = t;
    }

    @Override
    public void add(T t) {
        var newNode = new Node<>(t);
        newNode.next = current.next;
        newNode.prev = current;
        current.next = newNode;
    }
}

class ReverseDoublyLinkedListIterator<T> implements ListIterator<T> {
    private Node<T> current;
    private int index;

    public ReverseDoublyLinkedListIterator(Node<T> tail) {
        this.current = tail;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public T next() {
        var data = current.data;
        current = current.prev;
        index++;
        return data;
    }

    @Override
    public boolean hasPrevious() {
        return current.next != null;
    }

    @Override
    public T previous() {
        var data = current.data;
        current = current.next;
        index--;
        return data;
    }

    @Override
    public int nextIndex() {
        return index;
    }

    @Override
    public int previousIndex() {
        return index - 1;
    }

    @Override
    public void remove() {
        var next = current.next;
        var prev = current.prev;
        prev.next = next;
        next.prev = prev;
        current = next;
    }

    @Override
    public void set(T t) {
        current.data = t;
    }

    @Override
    public void add(T t) {
        var newNode = new Node<>(t);
        newNode.next = current.next;
        newNode.prev = current;
        current.next = newNode;
    }
}

class ReversedDoublyLinkedList<T> implements Iterable<T> {

    private DoublyLinkedList<T> list;

    public ReversedDoublyLinkedList(DoublyLinkedList<T> list) {
        this.list = list;
    }

    @Override
    public Iterator<T> iterator() {
        return list.reverseIterator();

    }
}