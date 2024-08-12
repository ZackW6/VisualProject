package Canvas.Util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import Canvas.Pathing.RRT.Node;

public class KDTree {
    private static class KDNode {
        Node point;
        KDNode left, right;
        boolean vertical;

        KDNode(Node point, boolean vertical) {
            this.point = point;
            this.vertical = vertical;
        }
    }

    private KDNode root;

    public KDNode getRoot(){
        return root;
    }
    
    public void addAll(List<Node> list){
        for (Node vec : list){
            add(vec);
        }
    }

    public void add(Node point) {
        root = add(root, point, true);
    }

    private KDNode add(KDNode node, Node point, boolean vertical) {
        if (node == null) {
            return new KDNode(point, vertical);
        }

        if (vertical) {
            if (point.x < node.point.x) {
                node.left = add(node.left, point, !vertical);
            } else {
                node.right = add(node.right, point, !vertical);
            }
        } else {
            if (point.y < node.point.y) {
                node.left = add(node.left, point, !vertical);
            } else {
                node.right = add(node.right, point, !vertical);
            }
        }

        return node;
    }

    public void removeAll(List<Node> list){
        for (Node vec : list){
            remove(vec);
        }
    }

    public void remove(Node point) {
        root = remove(root, point, true);
    }

    private KDNode remove(KDNode node, Node point, boolean vertical) {
        if (node == null) {
            return null;
        }

        if (node.point.equals(point)) {
            // Node found, remove it
            if (node.right != null) {
                // Find the minimum node in the right subtree
                KDNode minNode = findMin(node.right, vertical);
                node.point = minNode.point;
                node.right = remove(node.right, minNode.point, !vertical);
            } else if (node.left != null) {
                // Find the minimum node in the left subtree
                KDNode minNode = findMin(node.left, vertical);
                node.point = minNode.point;
                node.right = remove(node.left, minNode.point, !vertical);
                node.left = null; // Ensure left subtree is removed
            } else {
                // Node is a leaf, remove it
                return null;
            }
        } else {
            if ((vertical && point.x < node.point.x) || (!vertical && point.y < node.point.y)) {
                node.left = remove(node.left, point, !vertical);
            } else {
                node.right = remove(node.right, point, !vertical);
            }
        }

        return node;
    }

    private KDNode findMin(KDNode node, boolean vertical) {
        if (node == null) {
            return null;
        }

        KDNode minNode = node;
        if (vertical) {
            if (node.left != null) {
                KDNode leftMin = findMin(node.left, vertical);
                if (leftMin.point.x < minNode.point.x) {
                    minNode = leftMin;
                }
            }
        } else {
            if (node.left != null) {
                KDNode leftMin = findMin(node.left, vertical);
                if (leftMin.point.y < minNode.point.y) {
                    minNode = leftMin;
                }
            }
        }

        return minNode;
    }

    public Node search(Node target) {
        return search(root, target, true);
    }

    private Node search(KDNode node, Node target, boolean vertical) {
        if (node == null) {
            return null; // Target not found
        }

        if (node.point.equals(target)) {
            return node.point; // Target found
        }

        if (vertical) {
            if (target.x < node.point.x) {
                return search(node.left, target, !vertical);
            } else {
                return search(node.right, target, !vertical);
            }
        } else {
            if (target.y < node.point.y) {
                return search(node.left, target, !vertical);
            } else {
                return search(node.right, target, !vertical);
            }
        }
    }

    // Find the k nearest KDNodes to the target point
    public List<Node> findKNearest(Node target, int k) {
        PriorityQueue<KDNode> pq = new PriorityQueue<>(k, Comparator.comparingDouble(n -> -target.distanceTo(n.point)));
        findKNearest(root, target, pq, k, true);

        List<Node> nearestPoints = new ArrayList<>();
        while (!pq.isEmpty()) {
            nearestPoints.add(pq.poll().point);
        }

        return nearestPoints;
    }

    private void findKNearest(KDNode node, Node target, PriorityQueue<KDNode> pq, int k, boolean vertical) {
        if (node == null) {
            return;
        }

        double dist = target.distanceTo(node.point);
        if (pq.size() < k) {
            pq.offer(node);
        } else if (dist < target.distanceTo(pq.peek().point)) {
            pq.poll();
            pq.offer(node);
        }

        KDNode goodSide, badSide;
        if (vertical) {
            if (target.x < node.point.x) {
                goodSide = node.left;
                badSide = node.right;
            } else {
                goodSide = node.right;
                badSide = node.left;
            }
        } else {
            if (target.y < node.point.y) {
                goodSide = node.left;
                badSide = node.right;
            } else {
                goodSide = node.right;
                badSide = node.left;
            }
        }

        findKNearest(goodSide, target, pq, k, !vertical);

        if (vertical && Math.abs(target.x - node.point.x) < target.distanceTo(pq.peek().point) ||
            !vertical && Math.abs(target.y - node.point.y) < target.distanceTo(pq.peek().point)) {
            findKNearest(badSide, target, pq, k, !vertical);
        }
    }

    public List<Node> findInRange(Node lowerBound, Node upperBound) {
        List<Node> result = new ArrayList<>();
        findInRange(root, lowerBound, upperBound, result);
        return result;
    }
    
    private void findInRange(KDNode node, Node lowerBound, Node upperBound, List<Node> result) {
        if (node == null) {
            return;
        }
    
        // Check if current node is within the bounding box
        if (node.point.x >= lowerBound.x && node.point.x <= upperBound.x &&
            node.point.y >= lowerBound.y && node.point.y <= upperBound.y) {
            result.add(node.point);
        }
    
        // Traverse left and right subtrees based on the dimension and bounds
        if (node.vertical) {
            if (lowerBound.x <= node.point.x) {
                findInRange(node.left, lowerBound, upperBound, result);
            }
            if (upperBound.x >= node.point.x) {
                findInRange(node.right, lowerBound, upperBound, result);
            }
        } else {
            if (lowerBound.y <= node.point.y) {
                findInRange(node.left, lowerBound, upperBound, result);
            }
            if (upperBound.y >= node.point.y) {
                findInRange(node.right, lowerBound, upperBound, result);
            }
        }
    }
}
