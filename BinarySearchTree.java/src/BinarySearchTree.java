import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class BinarySearchTree <T extends Comparable<T>> implements Iterable<T>{

    String name;
    Node<T> root;
    List<T> nodeList = new ArrayList<>();
    String treeString = "";
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            List<T> traversalList = inorderTraversal(root);
            @Override
            public boolean hasNext() {
                return !traversalList.isEmpty();
            }

            @Override
            public T next() {
                return traversalList.remove(0);
            }
        };
    }

    public List<T> inorderTraversal (Node<T> root){
        if (root != null) inorderTraversalHelper(root);
        return nodeList;
    }

    public void inorderTraversalHelper(Node<T> node){
        if(node.left != null) inorderTraversalHelper(node.left);
        nodeList.add(node.root);
        if(node.right != null) inorderTraversalHelper(node.right);
    }
    static class Node <T extends Comparable<T>> {
        T root;
        Node<T> left, right;
        public Node(T rt){
            root = rt;
            left = right = null;
        }

        public Node<T> getLeft() { return left; }
        public void setLeft(T left) { this.left = new Node<T>(left); }
        public Node<T> getRight() { return right; }
        public void setRight(T right) { this.right = new Node<T>(right); }
        public T getRoot() { return root; }
        public void setRoot(T root) { this.root = root; }

        @Override
        public String toString() {
            return root.toString();
        }
    }

    /** Constructor for BinarySearchTree **/
    public BinarySearchTree(String nm) {
        name = nm;
        root = null;
    }

    public void add (T val){
        if (root == null) root = new Node<T>(val);
        else insert(root, val);
    }

    public void insert(Node<T> node, T val){
        if (node.getRoot().compareTo(val) > 0){
            if (node.getLeft() == null) node.setLeft(val);
            else insert(node.getLeft(),val);
        }
        else{
            if(node.getRight() == null) node.setRight(val);
            else insert(node.getRight(), val);
        }
    }

    public void addAll(List<T> list){
        for(T item: list){
            add(item);
        }
    }

    public String printTree(Node<T> root){
        String newString = "";
        if (root != null) {
            newString += " " + root + " ";
            String leftString = printTree(root.left);
            if (!leftString.isEmpty())  newString += " L:(" + leftString +")";
            String rightString = printTree(root.right);
            if (!rightString.isEmpty()) newString += " R:(" + rightString +")";
        }

        return newString;
    }
    @Override
    public String toString() {
        return "[" + name + "]" + printTree(root);
    }

    public void mergesort(List<T> list2){
        int index1 = 0;
        int index2 = 0;

        while (index1 < nodeList.size() && index2 < list2.size()){
            if (nodeList.get(index1).compareTo(list2.get(index2)) < 0){
                list2.add(index2, nodeList.get(index1));
                index1++;
                index2++;
            }
            else{
                index2++;
            }
        }

        while (index1 < nodeList.size()){
            list2.add(index2, nodeList.get(index1));
            index1++;
            index2++;
        }
    }

    public static <T extends Comparable<T>> List<T> merge(List<BinarySearchTree<T>> bstlist){
        // create a big list for the already merged elements
        ArrayList<T> list = new ArrayList<>();
        // create a pointer i to loop through the list of trees
        for (int i = 0;i < bstlist.size(); i++){
            //make the i final to prevent changes
            int finalI = i;
            //create thread
            Runnable block = () -> {
                synchronized (list){
                    // get the ith tree from the list and perform inorder traversal
                    bstlist.get(finalI).inorderTraversal(bstlist.get(finalI).root);
                    // perform merge operation on it
                    bstlist.get(finalI).mergesort(list);
                }
            };
            //kick start the thread
            new Thread(block).start();
        }
        //return list
        return list;
    }


    public static void main(String... args) {
        BinarySearchTree<String> ta = new BinarySearchTree<String>("a");
        ta.addAll(Arrays.asList("bat", "ball", "blot", "bleach"));
        BinarySearchTree<String> tb = new BinarySearchTree<String>("b");
        tb.addAll(Arrays.asList("cat", "call", "clot", "kitten"));
        BinarySearchTree<String> tc = new BinarySearchTree<String>("c");
        tc.addAll(Arrays.asList("at", "all", "spiderman", "bandana"));
        BinarySearchTree<String> td = new BinarySearchTree<String>("d");
        td.addAll(Arrays.asList("dodo", "sand", "spiderman"));
        System.out.println(merge(Arrays.asList(ta,tb,tc,td)));
        
//// each tree has a name, provided to its constructor
//        BinarySearchTree<Integer> t1 = new BinarySearchTree<>("Oak");
//// adds the elements to t1 in the order 5, 3, 0, and then 9
//        t1.addAll(Arrays.asList(1, 2, 3, 4));
//        BinarySearchTree<Integer> t2 = new BinarySearchTree<>("Maple");
//// adds the elements to t2 in the order 9, 5, and then 10
//        t2.addAll(Arrays.asList(1, 2, 3, 4));
//        BinarySearchTree<Integer> t5 = new BinarySearchTree<>("Pine");
//// adds the elements to t2 in the order 9, 5, and then 10
//        t5.addAll(Arrays.asList(0));
//        BinarySearchTree<Integer> t4 = new BinarySearchTree<>("cherry");
//// adds the elements to t2 in the order 9, 5, and then 10
//        t4.addAll(Arrays.asList(4,3,2,1));
//
//        BinarySearchTree<String> t3 = new BinarySearchTree<>("Cornucopia");
//        t3.addAll(Arrays.asList("orange"));
//        BinarySearchTree<String> t6 = new BinarySearchTree<>("A");
//        t6.addAll(Arrays.asList("apple"));
//        //t3.forEach(System.out::println); // iteration in increasing order
//
//        BinarySearchTree<String> t7 = new BinarySearchTree<>("AA");
//        BinarySearchTree<String> t8 = new BinarySearchTree<>("B");
//        BinarySearchTree<String> t9 = new BinarySearchTree<>("C");
//        BinarySearchTree<String> t10 = new BinarySearchTree<>("D");
//
//        t7.addAll(Arrays.asList("bat", "ball", "blot", "bleach"));
//        t8.addAll(Arrays.asList("cat", "call", "clot", "kitten"));
//        t9.addAll(Arrays.asList("at", "all", "spiderman", "bandana"));
//        t10.addAll(Arrays.asList("dodo", "sand", "spiderman"));
//
//        List<BinarySearchTree<Integer>> bstlist = new ArrayList<>();
//        bstlist.add(t1);
//        bstlist.add(t2);
//        bstlist.add(t4);
//        bstlist.add(t5);
//
//        List<BinarySearchTree<String>> newTree = new ArrayList<>();
//        newTree.add(t3);
//        newTree.add(t6);
//
//        List<BinarySearchTree<String>> bstlist2 = new ArrayList<>();
//        bstlist2.add(t7);
//        bstlist2.add(t8);
//        bstlist2.add(t9);
//        bstlist2.add(t10);
//
//
//
//        BinarySearchTree<Integer> t11 = new BinarySearchTree<>("Oak");
//        t11.addAll(Arrays.asList( 5, 3, 0, 9 ));
//
//        List<BinarySearchTree<Integer>> bstlist11 = new ArrayList<>();
//        bstlist11.add(t11);
//
//        System.out.println("Test case 1");
//        List<Integer> newlist11 = merge(bstlist11);
//        newlist11.forEach(System.out::println);
//
//        System.out.println("Test case 4");
//        List<Integer> newlist = merge(bstlist);
//        newlist.forEach(System.out::println);
//
//        System.out.println("Test case 3");
//        List<String> newlist3 = merge(bstlist2);
//        newlist3.forEach(System.out::println);
//
//        System.out.println("Test case 6");
//        List<String> newlist2 = merge(newTree);
//        newlist2.forEach(System.out::println);
    }
}