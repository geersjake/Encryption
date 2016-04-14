package EncryptionProject;

/*****************************************************************
 Recreation of Java Linked List class. Can handle any element

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
        char str = s;
        if (top == null) { //case 0: empty list
            System.out.println("You tried removing from an empty message");
            return false;
        }


        if (top.getData().equals(str)) { //case 1: top contains char
            if (top.getNext() == null){ //checking if its the last char in the msg
                System.out.println("You must have at least one char in your message");
                return false; //if so exits and nothing is removed
            } else { //otherwise, if there are other chars after it
                top = top.getNext(); //removing itself from link
                top.setPrevious(null); //changing top to really be the top (no previous link)
                size--;
                return true;
            }
        }



            Node temp = top.getNext();
            while (temp != null) {
                if (temp.getData().equals(str)) { //comparing
                    if ((temp.equals(tail))) {
                        temp.getPrevious().setNext(null);
                        tail = temp.getPrevious(); // TODO: 4/14/2016 test
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

        return false; // TODO: 4/14/2016 fix


    }

    /*****************************************************************
     Removes all occurences of specified string from list.
     @param s string to be removed
     @throws // TODO: 4/11/2016
     *****************************************************************/
    public void removeAll(char s) {
        while (remove(s)) ;
    }





    // TODO: 4/11/2016
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
     /*   if (top == null && pos == 0) { //case 0: empty list
            top = new Node(s, null, null);
            size++;
            return true;
        }
        else if (top == null && pos != 0) { //case 0.1: empty list
            System.out.println("You tried adding to an empty list," +
                    " item will be added as the first item in the list");
            top = new Node(s, null, null);
            size++;
            return true;
        }
        else {
            int counter = 0;
            Node temp = top;
            while (temp != null) {
                counter++;
                if (counter == pos) { //finding the position
                    temp.setNext(new Node(s, temp, temp.getNext()));
                    size++;
                    return true;
                }
                temp = temp.getNext();
            }
            if (temp == null) { //case 2: list of size one
                System.out.println("You tried adding an item after" +
                        " a position which does not exist, pick a new position");

            }
            return false;
        }*/

        boolean isValid = false;
        if (top == null && pos == 0) { //case 0: empty list // TODO: 4/14/2016 test
            top = new Node(s, null, null);
            size++;
            return true;
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
                        tail = temp.getNext(); // TODO: 4/14/2016 test
                    } else {
                        temp.setNext(new Node(s, temp, temp.getNext()));
                        temp.getNext().getNext().setPrevious(temp.getNext());
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
     @throws // TODO: 4/11/2016
     @return size of list
     *****************************************************************/
    public int getSize() {
        return size;
    }


    public void setSize (int newSize){
        size = newSize;
    }




    /*****************************************************************
     Retuns the node at the specified index
     @param nodeAt the location of the node
     @throws // TODO: 4/11/2016
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



/*    public void removeString (String str, int start) {
        Node temp = top;
        int i = 0;
        while (hasNext(temp)) {
            int count = 0;
            while (temp.getData().equals(str.charAt(i))) {
                count++;
                temp = temp.getNext();
                i++;
                if (count == str.length()){
                    for (int j = start+count; j > start ; j--) {
                        String loc = j +"";
                        removeAt(str, loc);
                    }
                }

            }
            temp = temp.getNext();
        }

    }*/

    public boolean hasNext (Node n){
        boolean hasNext = false;
        if (n.getNext() != null){
            hasNext = true;
        }
        return hasNext;
    }

    public static void main(String[] args) {
       /* LinkedList list = new LinkedList();
        list.add("tent");
        list.add("backpack");
        list.display();
        list.remove("backpack");
        list.display();*/


    }
}
