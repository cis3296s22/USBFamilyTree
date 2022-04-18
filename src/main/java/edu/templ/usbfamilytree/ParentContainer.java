package edu.templ.usbfamilytree;

import java.util.ArrayList;

public class ParentContainer {
    private ArrayList<Node> parents;
    private int childIndex;

    public ParentContainer(){
        parents = new ArrayList<>();
        childIndex = 0;
    }

    public ArrayList<Node> getParents(){
        return parents;
    }
    public void addParent(Node node){
        parents.add(node);
    }

    public int getChildIndex() {
        return childIndex;
    }

    public void setChildIndex(int index) {
        childIndex = index;
    }
}
