package EncryptionProject;

import java.util.Collection;

/*****************************************************************
 Recreation of Java Linked List class. Can handle any element.
 JavaDoc is not thorough as it is not required

 @author Jake Geers
 @version 4/11/2016
 *****************************************************************/
public class LinkedList<E> {

    /** Node which points to top of list */
    private Node top;

    /** Node which points to end of list */
    private Node tail;

    /** variable which tracks size of list */
    transient private int size = 0; //idk what this is but it looked cool


    /*****************************************************************
     Constructor which initalizes top and tail nodes to null
     *****************************************************************/
    public LinkedList() {
        top = null;
        tail = null;
    }

    public LinkedList(Collection<? extends E> c) {
        this();
        Object[] a = c.toArray();
        for (Object o:
             a) {
            E e = (E) o;
            add(e);
        }

    }

    /*****************************************************************
     Adds the specified element to the top of the list
     @param s the element to add
     @throws // TODO: 4/11/2016
     *****************************************************************/
    public void addAtTop(E s) {
        size++;
        // case 0, no list
        if (top == null)
            tail = top = new Node(s, null, top);
        else
            // case 1: list exist....
            top = new Node(s, null, top);
            top.getNext().setPrevious(top);
    }

    /*****************************************************************
     Creates a string which seperates each node's data by three spaces
     @throws // TODO: 4/11/2016
     @return formatted string msg
     *****************************************************************/
    public String display() {
        String msg = "";
        Node temp = top; //begining at the top

        while (temp != null) {
            msg = msg + temp.getData(); // separating data
            temp = temp.getNext();
        }
        return msg;
    }

    /*****************************************************************
     Adds elements to end of list. Primary method used as it adds
     elements in the order which they were created.
     @param s element to be added
     @throws // TODO: 4/11/2016
     *****************************************************************/
    public void add(E s) {
        // case 0: no list,
        if (top == null)
            tail = top = new Node(s, null, null);
        else { //case 1: list exists
            tail.setNext(new Node(s, tail, null));
            tail = tail.getNext();
        }
        size++;
    }

    /*****************************************************************
     Removes first occurnce of specified. See removeAll()
     @param s string containing char to remove
     @throws // TODO: 4/11/2016
     @return true if list contains string and thus removed it
     *****************************************************************/
    public boolean remove(char s) {
        boolean isValid = false;
        char str = s;
        if (top == null) { //case 0: empty list
            System.out.println("You tried removing from an empty msg");
            return isValid;
        }

        if (top.getData().equals(str)) { //case 1: top contains char
            //checking if its the last char in the msg
            if (top.getNext() == null){
                System.out.println("You must have at least one" +
                        " char in your message");
                return isValid; //if so exits and nothing is removed
            } else { //otherwise, if there are other chars after it
                top = top.getNext(); //removing itself from link
                top.setPrevious(null);
                size--;
                return true;
            }
        }

            Node temp = top.getNext();
            while (temp != null) {
                if (temp.getData().equals(str)) { //comparing
                    if ((temp.equals(tail))) {
                        temp.getPrevious().setNext(null);
                        tail = temp.getPrevious();
                    } else {
                        temp.getPrevious().setNext(temp.getNext());
                    }
                    if (temp.getNext() != null) //attaching tail
                        temp.getNext().setPrevious(temp.getPrevious());
                    size--;
                    return true;
                }
                temp = temp.getNext();
            }
        return isValid;
    }

    /*****************************************************************
     Removes all occurences of specified string from list.
     @param s string to be removed
     @throws
     *****************************************************************/
    public void removeAll(char s) {
        while (remove(s)) ;
    }

    /*****************************************************************
     Clear entire linked list. I stole this method from the api as it
     is currently 2am..
     @param
     @throws
     @return
     *****************************************************************/
    public void clear() {
        for (Node<E> x = top; x != null; ) {
            Node<E> next = x.getNext();
            x.setData(null);
            x.setNext(null);
            x.setPrevious(null);
            x = next;
        }
        top = tail = null;
        size = 0;
    }

    /*****************************************************************
     Removes char at specified position String param s is never used
     @param
     @throws IndexOutOfBoundsException
     @return
     *****************************************************************/
    public boolean removeAt(String s, int position) {
        int pos = position;
        boolean ableToRemove = false;
        Node temp = top;

        if (pos < -1 || pos >= size) {
            throw new IndexOutOfBoundsException();
            //null pointer
        }
        if (pos == -1){
            top = temp.getNext();
            top.setPrevious(null);
        }

        for (int i = 0; i < pos; i++) { // changed this line
            temp = temp.getNext();
        }

        if (temp.getNext().equals(tail)) {
            temp.setNext(null);
            tail = temp;
            size--;
            ableToRemove = true;
        }
        if (temp.getNext()!= null){
            temp.setNext(temp.getNext().getNext());
            size--;
            ableToRemove = true;
        }
        return ableToRemove;
    }

    /*****************************************************************
     Inserts new element into list after specified position
     @param pos the position after which the new element goes
     @param s new element to be added
     @throws // TODO: 4/11/2016
     @return true if able to insert
     *****************************************************************/
    public boolean insertAfter(int pos, char s) {
        boolean isValid = false;
        if (top == null && pos == 0) { //case 0: empty list
            top = new Node(s, null, null);
            size++;
            return true;
        }
        if (pos >= size || pos < -1){
            System.out.println(pos +" is not a valid position");
        }
        if (top == null && pos != 0) { //case 0.1: empty list
            System.out.println("You tried adding to an empty list," +
                    " item will be added as the first item in the list");
            top = new Node(s, null, null);
            size++;
            return true;
        }

        if (top != null) {
            Node temp = top;
            int i = 0;
            while (temp != null) {
                if (i == pos) {
                    if (temp == tail){
                        temp.setNext(new Node(s, temp, null));
                        tail = temp.getNext();
                    } else {
                        temp.setNext(new Node(s, temp, temp.getNext()));
                        temp.getNext().getNext().setPrevious
                                (temp.getNext());
                    }
                    size++;
                    return true;
                }
                i++;
                temp = temp.getNext();
            }
        }
        return isValid;
    }

    /*****************************************************************
     Returns the current value of size which tracks list size
     @throws
     @return size of list
     *****************************************************************/
    public int getSize() {
        return size;
    }

    /*****************************************************************
     Sets size of list
     @param
     @throws
     @return
     *****************************************************************/
    public void setSize (int newSize){
        size = newSize;
    }

    /*****************************************************************
     Retuns the node at the specified index
     @param nodeAt the location of the node
     @throws
     @return temp, the node at the parameter location
     *****************************************************************/
    public Node get(int nodeAt) {
        int counter = 0;
        Node temp = top;
        while (temp != null) {
            if (counter == nodeAt) { //ending search
                break;
            }
            temp = temp.getNext();
            counter++;
        }
        return temp;
    }

    /*****************************************************************
     Determining if a particular node has a next link
     @param
     @throws
     @return
     *****************************************************************/
    public boolean hasNext (Node n){
        boolean hasNext = false;
        if (n.getNext() != null){
            hasNext = true;
        }
        return hasNext;
    }

    public static void main(String[] args) {

    }
}
