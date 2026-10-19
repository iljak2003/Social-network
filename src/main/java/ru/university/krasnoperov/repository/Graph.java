package ru.university.krasnoperov.repository;

// Взято с сайта https://www.geeksforgeeks.org/java/java-program-to-implement-adjacency-list/

import java.util.*;

class Graph {
    private final Map<UUID, List<UUID>> adjacencyList;

    // Constructor to initialize the adjacency list
    public Graph() {
        adjacencyList = new HashMap<>();
    }

    public Map<UUID, List<UUID>> getGraph() {
        return adjacencyList;
    }

    // Method to add a new vertex to the graph
    public void addVertex(UUID vertex) {
        adjacencyList.put(vertex, new ArrayList<>());
    }

    // Method to add an edge between two vertices
    public void addEdge(UUID source, UUID destination) {
        adjacencyList.get(source).add(destination);
    }

    // Method to remove a vertex from the graph
    public void removeVertex(UUID vertex) {
        adjacencyList.remove(vertex);
        // Remove the vertex from the neighbors of other
        // vertices
        for (List<UUID> neighbors : adjacencyList.values()) {
            neighbors.remove(vertex);
        }
    }

    // Method to remove an edge between two vertices
    public void removeEdge(UUID source, UUID destination) {
        adjacencyList.get(source).remove(destination);

        // For undirected graph, uncomment below line
        // adjacencyList.get(destination).remove(Integer.valueOf(source));
    }

    // Method to get the neighbors of a vertex
    public List<UUID> getNeighbors(UUID vertex) {
        return adjacencyList.get(vertex);
    }

    // Method to print the graph
    public void printGraph() {
        for (Map.Entry<UUID, List<UUID>> entry :
                adjacencyList.entrySet()) {
            System.out.print(entry.getKey() + " -> ");
            for (UUID neighbor : entry.getValue()) {
                System.out.print(neighbor + " ");
            }
            System.out.println();
        }
    }
}
