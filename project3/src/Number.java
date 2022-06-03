package project3;

/**
* This class is the Number class with a LinkedList implementation.
* It represents a number, where each digit is contained in a node.
*
* @author Tanuj Sistla
* @version 11/02/2021
*/

public class Number implements Comparable<Number>{
    
    String number;
    Node head;
    Node tail;

    /**
    * Constructor that instantiates Number object
    *
    * @param number String form of a number to be converted to a Number object .
    *
    * @throws NullPointerException if other is null
    */

    public Number(String number) throws IllegalArgumentException, NullPointerException{
        this.number = number;
        Node newnext = null;
        for (int i = 0; i < number.length(); i++){
            Node q = new Node(number.substring(i,i+1), newnext);
            if (newnext == null){
                tail = q;
            }
            newnext = q;
        }
        head = newnext;
    }
    
    /**
    * Constructor that instantiates Number object if parameters are empty
    */
    public Number() {
        this.head = new Node();
    }

    /**
    * Adds 2 number objects .
    *
    * @param other Number object being added to this Number object .
    *
    * @return A number object representing the sum of the number objects.
    *
    * @throws NullPointerException if other is null
    */    
    public Number add(Number other) throws NullPointerException{
        
        if (other == null){
            throw new NullPointerException("Null!");
        }

        Node otherDig = other.head; 
        Node thisDig = this.head;
        Number newNum = new Number();
        Node iter = newNum.head;
        int carry = 0;
        int firstloop = 0;
        
        while(otherDig != null || thisDig != null || carry != 0) {
            int one = otherDig == null ? 0 : otherDig.data;
            int two = thisDig == null ? 0 : thisDig.data;
            int totalSum = one + two + carry;
            carry = totalSum / 10;
        
            if (firstloop == 0){
                newNum.head.data = totalSum % 10; 
            }
            else{
                iter.next = new Node(String.valueOf(totalSum % 10), null);
                iter = iter.next;
            }
            
            if (otherDig != null){
                otherDig = otherDig.next;
            }
            
            if (thisDig != null){
                thisDig = thisDig.next;
            }
            
            firstloop++;
        
        }
        
        return newNum;
    }

        /**
    * Compares this Number object with another Number object .
    *
    * @param other Number object that is being compared with this Number object .
    *
    * @return 1 if this object is greater than the other, 
    * -1 if this object is less than the other, and 0 if they are equal
    *
    * @throws NullPointerException if other Number object is
    * null
    */
    public int compareTo(Number other) throws NullPointerException{
        if (other == null){
            throw new NullPointerException("Object is null.");
        }
        
        Node thisDig = this.head;  
        Node otherDig = other.head; 
        Long greater = Long.valueOf(0);
        Long tens = Long.valueOf(1);
        
        while (otherDig != null || thisDig != null){
            int otherData = otherDig == null ? 0 : otherDig.data;
            int thisData = thisDig == null ? 0 : thisDig.data;
            if (thisData < otherData){
                greater -= tens;
            }
            else if (thisData > otherData){
                greater += tens;
            }
            
            if (thisDig != null){
                thisDig = thisDig.next;
            }
            
            if (otherDig != null){
                otherDig = otherDig.next;
            }
            
            tens *= 10;
        
        }
        
        if (greater < 0){
            return -1;
        }
        else if (greater > 0){
            return 1;
        }
        
        return 0;
    }

    /**
    * Checks if two objects are equal
    *
    * @param obj object being compared with this object .
    *
    * @return true if objects are equal
    * false , otherwise .
    *
    * Overrides equals method in implemented class
    */

    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        
        if (obj == null){
            return false;
        }
        
        if (getClass() != obj.getClass()){
            return false;
        }
        
        Number other = (Number) obj;    
        
        if (this.number == null) {
        
            if (other.number != null){
                return false;
            }
        
        } 
        
        else if (!this.number.equals(other.number)){
            return false;
        }
        
        return true;
    }

    /**
    * Finds the length of this object .
    *
    * @return length of our number
    *
    * @throws PotionFullException if this Potion object is
    * full ( reached its maximum capacity )
    */
    public int length(){
        return this.number.length();
    }

    /**
    * Computes the product of this Number object with another Number object
    *
    * @param other object to multiply with this Number object .
    *
    * @return a Number object representing the product of the two Number objects
    *
    * @throws NullPointerException if Number object other is
    * null
    */
    public Number multiply(Number other) throws NullPointerException{
        if (other == null){
            throw new NullPointerException("Null!");
        }
    
        Number smaller = other;
        Number bigger = this;
    
        if (other.length() > this.length()){
            smaller = this;
            bigger = other;
        }
    
        Node smallerDig = smaller.head; 
        Number newNum = new Number();
        int tens = 0;
    
        while (smallerDig != null){
    
            Number singDigProd = bigger.multiplyByDigit(smallerDig.data);
            if (tens > 0){
                for (int i = 0; i < tens; i++){
                    Node zeroDig = new Node("0", singDigProd.head);
                    singDigProd.head = zeroDig;
                }
            }

            newNum = singDigProd.add(newNum);

            smallerDig = smallerDig.next;
            tens++;
        }
        return newNum;
    }

    /**
    * Computes the product of this Number object a single digit
    *
    * @param digit integer to multiply with this Number object .
    *
    * @return a Number object representing the product of this number object and a digit
    *
    * @throws IllegalArgumentException if integer digit is greater than nine or less than 0
    */

    public Number multiplyByDigit(int digit) throws IllegalArgumentException{
        try{
            digit *=1;
        }
        catch(Exception e){
            throw new IllegalArgumentException("Must enter a digit between 0 and 9.");
        }
        if (digit > 9 || digit < 0){
            throw new IllegalArgumentException("Digit must be greater than 0 and less than 9.");
        }
        Node thisDig = this.head;
        Number newNum = new Number();
        Node iter = newNum.head;
        int carry = 0;
        int firstloop = 0;
        while(thisDig != null || carry != 0) {
            int one = thisDig == null ? 0 : thisDig.data;
            int totalProd = (one * digit) + carry;
            carry = totalProd / 10;
            if (firstloop == 0){
                iter.data = totalProd % 10; 
            }
            else{
                iter.next = new Node(String.valueOf(totalProd % 10), null);
                iter = iter.next;
            }
            if (thisDig != null){
                thisDig = thisDig.next;
            }            
            firstloop++;
        }
        return newNum;
    }

        /**
    * String representation of our number object (gives us the number itself, not in LinkedList form)
    *
    *
    * @return a String representing this Number object
    *
    */
    @Override
    public String toString(){ 
        Node thisDig = this.head;
        String num = "";
        while(thisDig != null) {
            int digit = thisDig.data;
            num = String.valueOf(digit) + num;
            thisDig = thisDig.next;
        }
        return num;
    }

/**
* This class is the Node class.
* It represents the data and properties of each node in a LinkedList.
*
* @author Tanuj Sistla
* @version 11/02/2021
*/

private class Node{
    int data;
    Node next;

    
    public Node(String data, Node next) throws NullPointerException{
        this.data = Integer.parseInt(data);
        this.next = next;
    }

    public Node() throws NullPointerException{
        this.data = 0;
        this.next = null;
    }
}
}