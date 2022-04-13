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
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.addNewEdge(0, 2, false, true);
        g.addNewEdge(2, 1, true, false);
        g.addNewEdge(2, 5, false, true);
        g.addNewEdge(2, 6, false, true);
        g.addNewEdge(0, 3, false, true);
        g.addNewEdge(3, 4, true, false);
        g.addNewEdge(3, 7, false, true);
    }
    @Test
    public void testInsertingOneNode()
    {
        Graph graph = new Graph();
        graph.addNode(0);
        assertEquals("Supposed to be 1", 1, graph.nodes.size());
    }
    @Test
    public void testInsertingTwoNodes()
    {
        Graph graph = new Graph();
        graph.addNode(0);
        graph.addNode(1);
        assertEquals("Supposed to be 2", 2, graph.nodes.size());
    }
    @Test
    public void testAddingOneEdge()
    {
        Graph graph = new Graph();
        graph.addNode(0);
        graph.addNode(1);
        graph.addNewEdge(0, 1, true, false);
        assertEquals("Supposed to be 1", 2, graph.Adj.size());
    }
    @Test
    public void testPathFromZeroToSix()
    {
        int[] expected = new int[] { 0, 2, 6};
        ArrayList<Integer> given = g.Bidirectional(0, 6);
        int[] real = given.stream().mapToInt(i -> i).toArray();
        assertArrayEquals("Should be [0, 2, 6]", expected, real);
    }
    @Test
    public void testPathFromSixToZero()
    {
        int[] expected = new int[] { 6, 2, 0};
        ArrayList<Integer> given = g.Bidirectional(6, 0);
        int[] real = given.stream().mapToInt(i -> i).toArray();
        assertArrayEquals("Should be [6, 2, 0]", expected, real );
    }

}
