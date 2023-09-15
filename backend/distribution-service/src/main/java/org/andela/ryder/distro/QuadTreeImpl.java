package org.andela.ryder.distro;

import org.springframework.stereotype.Component;

@Component
public class QuadTreeImpl<Key extends Comparable<Key>, Value> {
    private Node root;

    // helper node data type
    private class Node {
        Key x, y;
        Node NW, NE, SE, SW;
        Value value;

        Node(Key x, Key y, Value value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }
    }

    public void insert(Key x, Key y, Value value) {
        root = insert(root, x, y, value);
    }

    private Node insert(Node h, Key x, Key y, Value value) {
        if (h == null) return new Node(x, y, value);
        else if (less(x, h.x) && less(y, h.y)) h.SW = insert(h.SW, x, y, value);
        else if (less(x, h.x) && !less(y, h.y)) h.NW = insert(h.NW, x, y, value);
        else if (!less(x, h.x) && less(y, h.y)) h.SE = insert(h.SE, x, y, value);
        else if (!less(x, h.x) && !less(y, h.y)) h.NE = insert(h.NE, x, y, value);
        return h;
    }

    private boolean less(Key k1, Key k2) {
        return k1.compareTo(k2) < 0;
    }

    private boolean eq(Key k1, Key k2) {
        return k1.compareTo(k2) == 0;
    }

    public Value nearestNeighbor(Key x, Key y) {
        if (root == null) return null; // Quadtree is empty
        return nearestNeighbor(root, x, y, null, Double.POSITIVE_INFINITY);
    }

    private Value nearestNeighbor(Node h, Key x, Key y, Value closestValue, double closestDistance) {
        if (h == null) return closestValue;

        double distanceToNode = distanceSquared(h.x, h.y, x, y);

        // If this node is closer, update closestValue and closestDistance
        if (distanceToNode < closestDistance) {
            closestValue = h.value;
            closestDistance = distanceToNode;
        }

        // Determine the quadrant to search next
        int cmpX = x.compareTo(h.x);
        int cmpY = y.compareTo(h.y);

        // Recursively search the closer quadrant first
        if (cmpX < 0 && cmpY < 0) {
            closestValue = nearestNeighbor(h.SW, x, y, closestValue, closestDistance);
            closestValue = nearestNeighbor(h.NW, x, y, closestValue, closestDistance);
            closestValue = nearestNeighbor(h.SE, x, y, closestValue, closestDistance);
            closestValue = nearestNeighbor(h.NE, x, y, closestValue, closestDistance);
        } else if (cmpX < 0 && cmpY >= 0) {
            closestValue = nearestNeighbor(h.NW, x, y, closestValue, closestDistance);
            closestValue = nearestNeighbor(h.SW, x, y, closestValue, closestDistance);
            closestValue = nearestNeighbor(h.NE, x, y, closestValue, closestDistance);
            closestValue = nearestNeighbor(h.SE, x, y, closestValue, closestDistance);
        } else if (cmpX >= 0 && cmpY < 0) {
            closestValue = nearestNeighbor(h.SE, x, y, closestValue, closestDistance);
            closestValue = nearestNeighbor(h.NE, x, y, closestValue, closestDistance);
            closestValue = nearestNeighbor(h.SW, x, y, closestValue, closestDistance);
            closestValue = nearestNeighbor(h.NW, x, y, closestValue, closestDistance);
        } else {
            closestValue = nearestNeighbor(h.NE, x, y, closestValue, closestDistance);
            closestValue = nearestNeighbor(h.SE, x, y, closestValue, closestDistance);
            closestValue = nearestNeighbor(h.NW, x, y, closestValue, closestDistance);
            closestValue = nearestNeighbor(h.SW, x, y, closestValue, closestDistance);
        }

        return closestValue;
    }

    private double distanceSquared(Key x1, Key y1, Key x2, Key y2) {
        double dx = (double) x1 - (double) x2;
        double dy = (double) y1 - (double) y2;
        return dx * dx + dy * dy;
    }

}