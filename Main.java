import java.util.Scanner;


public class Main {

    public static class Node {
        long data;
        Node left;
        Node right;
        Node parent;

        public Node(long data) {
            this.data = data;
        }
    }

    public static class SplayTree {
        static Node root;

        public static void rightRotation(Node node){
            Node grandPr = node.parent.parent;
            Node pr = node.parent;

            Node rightChild = node.right;
            pr.left = rightChild;
            if(rightChild != null)
                rightChild.parent = pr;
            node.right = pr;
            pr.parent = node;

            if(grandPr != null){
                if(grandPr.right == pr)
                    grandPr.right = node;
                else
                    grandPr.left = node;
            }
            else
                root = node;

            node.parent = grandPr;
        }

        public static void leftRotation(Node node){
            Node grandPr = node.parent.parent;
            Node pr = node.parent;

            Node leftChild = node.left;
            pr.right = leftChild;
            if(leftChild != null)
                leftChild.parent = pr;
            node.left = pr;
            pr.parent = node;

            if(grandPr != null){
                if(grandPr.right == pr)
                    grandPr.right = node;
                else
                    grandPr.left = node;
            }
            else
                root = node;

            node.parent = grandPr;

        }

        public static void splay(Node node){
            if(node == null) return;
            while (node.parent != null){
                if(node.parent.equals(root) || node.parent.parent == null){
                    if (node.data > node.parent.data)
                        leftRotation(node);
                    else
                        rightRotation(node);
                }
                else if(node.parent.right == node && node.parent.parent.right == node.parent ||
                        node.parent.left == node && node.parent.parent.left == node.parent){
                    if(node.parent.right == node){
                        leftRotation(node.parent);
                        leftRotation(node);
                    }else {
                        rightRotation(node.parent);
                        rightRotation(node);
                    }
                }
                else{ // zig zag
                    if(node.parent.right != null && node.parent.right.equals(node)){
                        leftRotation(node);
                        rightRotation(node);
                    }
                    else{
                        rightRotation(node);
                        leftRotation(node);
                    }
                }
            }
            root = node;
        }

        public static boolean find(long data){
            if(root == null)
                return false;
            Node node = root;
            while(node.data != data){
                if(data > node.data){
                    if(node.right == null)
                        return false;
                    node = node.right;
                }
                if(data < node.data){
                    if(node.left == null)
                        return false;
                    node = node.left;
                }
            }
            splay(node);
            return root.data == data;
        }

    public static void add(long data){
        if (root == null){
            root = new Node(data);
            return;
        }

        if (root.data == data)
            return;

        Node node = new Node(data);

        Node prev = null;
        Node temp = root;
        while (temp != null) {
            if (temp.data > data) {
                prev = temp;
                temp = temp.left;
            }
            else if (temp.data < data) {
                prev = temp;
                temp = temp.right;
            }
            else{
                splay(temp);
                return;
            }
        }
        if (prev.data > data)
            prev.left = node;
        else
            prev.right = node;
        node.parent = prev;

        splay(node);

    }


        public static long sum(Node node, long l, long r){
            if(node == null || l > r)
                return 0;
            if(node.data > r)
                return sum(node.left, l, r);
            if(node.data < l)
                return sum(node.right, l, r);
            return node.data + sum(node.left, l, r) + sum(node.right, l, r);
        }

        public static void del(long data){
            if(root == null)
                return;
            Node node = root;
            while(node.data != data){
                if(node.data > data)
                    if(node.left != null)
                        node = node.left;
                    else
                        return;
                if(node.data < data)
                    if(node.right != null)
                        node = node.right;
                    else
                        return;
            }
            Node pr = node.parent;
            Node ch;
            if(node.right != null && node.left != null){
                Node leaf = node.left;
                while(leaf.right != null){
                    leaf = leaf.right;
                }
                leaf.right = node.right;
                node.right.parent = leaf;
                node.left.parent = node.parent;
                if(node.parent != null){
                    if(node.parent.right != null && node.parent.right.equals(node))
                        node.parent.right = node.left;
                    else
                        node.parent.left = node.left;
                }
                else
                    root = node.left;
                splay(pr);
            }
            else if (node.left != null) {
                ch = node.left;
                ch.parent = node.parent;
                if(node.parent != null) {
                    if(node.parent.left == node)
                        node.parent.left = node.left;
                    else
                        node.parent.right = node.left;
                }
                else
                    root = node.left;
                splay(pr);
            } else if(node.right != null) {
                ch = node.right;
                ch.parent = node.parent;
                if(node.parent != null) {
                    if(node.parent.left == node)
                        node.parent.left = node.right;
                    else
                        node.parent.right = node.right;
                }
                else
                    root = node.right;
                splay(pr);
            }
            else {
                if(pr != null) {
                    if (pr.left == node)
                        pr.left = null;
                    else
                        pr.right = null;
                }
                else
                    root = null;
                splay(pr);
            }
        }

        public static void printTree(Node root){
            if(root == null) return;
            System.out.print(root.data + " ");
            printTree(root.left);
            printTree(root.right);
        }


    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < n; i++) {
            String[] inst = sc.nextLine().split(" ");
            switch (inst[0]) {
                case "add" : SplayTree.add(Long.parseLong((inst[1])));break;
                case "find" : System.out.println(SplayTree.find(Integer.parseInt(inst[1])));break;
                case "sum" : System.out.println(SplayTree.sum(SplayTree.root, Integer.parseInt(inst[1]), Integer.parseInt(inst[2])));break;
                case "del" : SplayTree.del(Integer.parseInt(inst[1]));break;
            }
        }
    }

}
