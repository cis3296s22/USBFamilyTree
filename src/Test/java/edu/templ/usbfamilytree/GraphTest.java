/**
 *  Testcases for the Graph Class
 */

package edu.templ.usbfamilytree;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GraphTest {

    Graph g;

    @Before
    public void setup()
    {
        g = new Graph();
    }

    /**
     *  Creating two nodes, and adding them to the graph. Connecting them with a marital edge, then adding a child edge to them and testing
     *  relationship from the child to parent 1
     */
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

    /**
     *  Creating two nodes, and adding them to the graph. Connecting them with a marital edge, then adding a child edge to them and testing
     *  relationship from the child to parent 2
     */
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
}
