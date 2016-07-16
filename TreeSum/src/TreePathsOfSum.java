import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by leeioannidis on 7/12/16.
 */

class Node {
    int val;
    Node left;
    Node right;

    public Node(int data) {
        this.val = data;
    }
}

public class TreePathsOfSum {


    private HashMap<Integer, ArrayList<LinkedList<Integer>>> findPathRecursively(Node root, final int sum) {
        if (null == root) {
            return null;
        }
        //Maps for paths for specified nodes and sum of nodes
        HashMap<Integer, ArrayList<LinkedList<Integer>>> leftPathMap = findPathRecursively(root.left, sum);
        HashMap<Integer, ArrayList<LinkedList<Integer>>> rightPathMap = findPathRecursively(root.right, sum);

        //check if the left subtree  has our sum
        if (null != leftPathMap && leftPathMap.containsKey(sum)) {
            printValidPath(leftPathMap.get(sum));
        }
        //check if the right subtree has our sum
        if (null != rightPathMap && rightPathMap.containsKey(sum)) {
            printValidPath(rightPathMap.get(sum));
        }
        // Now that we have the left and right subtrees and check if any combination has our sum
        if (null != leftPathMap) {
            LRAndRootCombo(root,sum,leftPathMap,rightPathMap);
        }

        /**
         * Constructing combined path map from the path map of left subtree and
         * right subtree. This path map is returned back to the calling parent
         * node.
         */
        HashMap<Integer, ArrayList<LinkedList<Integer>>> mapOfPaths = new HashMap<>();
        ArrayList<LinkedList<Integer>> myPathList = new ArrayList<>();
        LinkedList<Integer> myPath = new LinkedList<>();
        myPath.addFirst(root.val);
        myPathList.add(myPath);
        mapOfPaths.put(root.val, myPathList);
        if (null != leftPathMap) {
            LeftRightSubTreeCollection(root,leftPathMap,myPathList,mapOfPaths);
        }
        if (null != rightPathMap) {
            LeftRightSubTreeCollection(root,rightPathMap,myPathList,mapOfPaths);
        }
        return mapOfPaths;
    }

    private void LeftRightSubTreeCollection(Node root, HashMap<Integer, ArrayList<LinkedList<Integer>>> targetPathMap,
                                            ArrayList<LinkedList<Integer>> myPathList,
                                            HashMap<Integer, ArrayList<LinkedList<Integer>>> mapOfPaths){
        for (Map.Entry<Integer, ArrayList<LinkedList<Integer>>> leftEntry : targetPathMap.entrySet()) {
            int newSum = root.val + leftEntry.getKey();

            ArrayList<LinkedList<Integer>> paths = leftEntry.getValue();
            for (LinkedList<Integer> path : paths) {
                path.addFirst(root.val);
            }

            if (mapOfPaths.containsKey(newSum)) {
                myPathList = mapOfPaths.get(newSum);
            } else {
                myPathList = new ArrayList<>();
            }

            myPathList.addAll(paths);
            mapOfPaths.put(newSum, myPathList);
        }

    }
    private void printPathOfGivenSum(Node root, final int sum) {
        HashMap<Integer, ArrayList<LinkedList<Integer>>> map = findPathRecursively(root, sum);
        //Checking the path map returned by root node
        if (null != map && map.containsKey(sum)) {
            printValidPath(map.get(sum));
        }
    }

    private void printValidPath(ArrayList<LinkedList<Integer>> pathToPrint){
        for (LinkedList<Integer> path : pathToPrint) {
            System.out.println("");
            for (int nodeData : path) {
                System.out.print(nodeData + " => ");
            }
        }
    }

    private void  LRAndRootCombo(Node root, int sum, HashMap<Integer, ArrayList<LinkedList<Integer>>> leftPathMap,
                                 HashMap<Integer, ArrayList<LinkedList<Integer>>> rightPathMap ){
        for (Map.Entry<Integer, ArrayList<LinkedList<Integer>>> leftEntry : leftPathMap.entrySet()) {
            int partialSum = root.val + leftEntry.getKey();

            if (null != rightPathMap && rightPathMap.containsKey((sum - partialSum))) {
                ArrayList<LinkedList<Integer>> leftPathList = leftEntry.getValue();
                ArrayList<LinkedList<Integer>> rightPathList = rightPathMap.get((sum - partialSum));

                for (LinkedList<Integer> leftPath : leftPathList) {
                    for (LinkedList<Integer> rightPath : rightPathList) {
                        System.out.println("");

                        for (int index = leftPath.size() - 1; index >= 0; index--) {
                            System.out.print(leftPath.get(index) + " => ");
                        }

                        System.out.print(root.val + " => ");

                        for (int rightNode : rightPath) {
                            System.out.print(rightNode + " => ");
                        }

                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        TreePathsOfSum tree = new TreePathsOfSum();
        Node root = new Node(4);
        root.left = new Node(3);
        root.left.left = new Node(5);

        root.left.left.left = new Node(3);
        root.left.left.right = new Node(-1);

        root.left.left.right.left = new Node(5);
        root.left.left.right.right = new Node(4);
        root.left.left.right.right.left = new Node(2);

        root.right = new Node(-8);
        root.right.left = new Node(3);

        root.right.right = new Node(3);
        root.right.right.left = new Node(7);
        root.right.right.right = new Node(1);
        //TODO use scanner to accept input.
        int  sumTarget = 8  ;
        System.out.printf("Input to sum with value %d \n",sumTarget);
        tree.printPathOfGivenSum(root, sumTarget);
    }
}
