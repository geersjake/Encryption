package EncryptionProject;

/*****************************************************************
 Each individual element of Linkedlist is represented by a node
 which points to the element before and after it

 @author Jake Geers
 @version 4/11/2016
 *****************************************************************/
public class Node<E> {

    /** The contents/guts of the element */
    private E data;

    /** Pointer to next sequential element */
    private Node<E> next;

    /** Pointer to prevous sequential element */
    private Node<E> previous;


    /*****************************************************************
     Constructor which creates a node and sets Pointers and data
     @param data the contens of the node
     @param previous the node before
     @param next the node after
     @throws // TODO: 4/11/2016
     *****************************************************************/
    public Node(E data, Node previous, Node next) {
        super();
        this.data = data;
        this.next = next;
        this.previous = previous;
    }

    /*****************************************************************
     Empty constructor for creating an un-initialized node
     @throws // TODO: 4/11/2016
     *****************************************************************/
    public Node() {
    }

    /*****************************************************************
     Getter for data
     @throws // TODO: 4/11/2016
     @return data of node
     *****************************************************************/
    public E getData() {
        return data;
    }

    /*****************************************************************
     Setter of data
     @param data element to set as data
     @throws // TODO: 4/11/2016
     *****************************************************************/
    public void setData(E data) {
        this.data = data;
    }

    /*****************************************************************
     Getter of next pointer
     @throws // TODO: 4/11/2016
     @return the next sequential node
     *****************************************************************/
    public Node getNext() {
        return next;
    }

    /*****************************************************************
     Setter of next pointer
     @param next node
     @throws // TODO: 4/11/2016
     *****************************************************************/
    public void setNext(Node next) {
        this.next = next;
    }

    /*****************************************************************
     Getter of pevious pointer.
     @throws // TODO: 4/11/2016
     @return node prior to the current node
     *****************************************************************/
    public Node getPrevious() {
        return previous;
    }

    /*****************************************************************
     Setter of previous pointer.
     @param previous node
     @throws // TODO: 4/11/2016
     *****************************************************************/
    public void setPrevious(Node previous) {
        this.previous = previous;
    }
}

