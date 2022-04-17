package edu.templ.usbfamilytree;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class GraphTest {

    Graph g;

    @Before
    public void setup()
    {
        g = new Graph();
    }

    @Test
    public void testingRelationshipBetweenChildAndParentTwo()
    {
        Node dad = g.addNode(new Person("Parent 1"));
        Node mom = g.addNode(new Person("Parent 2"));

        g.addNewEdge(dad.id, mom.id, Edge.Relationship.marital);

        Node childNode = g.addNode(new Person("Child"));

        g.addNewEdge(dad.id, childNode.id, Edge.Relationship.ancestor);
        g.addNewEdge(mom.id, childNode.id, Edge.Relationship.ancestor);

        String relationship = g.findRelationship(childNode.id, mom.id);
        assertEquals("child", relationship);
    }

    @Test
    public void testingRelationshipBetweenChildAndParentOne()
    {
        Node dad = g.addNode(new Person("Parent 1"));
        Node mom = g.addNode(new Person("Parent 2"));

        g.addNewEdge(dad.id, mom.id, Edge.Relationship.marital);

        Node childNode = g.addNode(new Person("Child"));

        g.addNewEdge(dad.id, childNode.id, Edge.Relationship.ancestor);
        g.addNewEdge(mom.id, childNode.id, Edge.Relationship.ancestor);

        String relationship = g.findRelationship(childNode.id, dad.id);
        assertEquals("child", relationship);
    }
}
