package edu.templ.usbfamilytree;

import java.util.ArrayList;

/**
 * Contains parents of a child node.
 * ParentContainer is attached to the line(edge)
 * from parents to a child.
 */
public class ParentContainer {
    /**
     * ArrayList that contains parents of a child
     */
    private ArrayList<Node> parents;
    /**
     * Keeps track of how many children this parentcontainer (couple) has
     */
    private int childIndex;
    /**
     * Empty Constructor that initialize the ArrayList and sets childIndex to 0
     */
    public ParentContainer(){
        parents = new ArrayList<>();
        childIndex = 0;
    }
    /**
     * Getter method for parents ArrayList
     * @return returns parents ArrayList
     */
    public ArrayList<Node> getParents(){
        return parents;
    }
    /**
     * Adds a parent node to ArrayList
     * @param node is the node(parent) to be added to the parents list
     */
    public void addParent(Node node){
        parents.add(node);
    }
    /**
     * Getter method for childIndex field
     * @return current childIndex
     */
    public int getChildIndex() {
        return childIndex;
    }
    /**
     * Setter method for childIndex field
     * @param index the new value for childIndex
     */
    public void setChildIndex(int index) {
        childIndex = index;
    }
}
