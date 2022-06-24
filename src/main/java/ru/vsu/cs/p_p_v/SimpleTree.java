package ru.vsu.cs.p_p_v;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Реализация простейшего дерева
 */
public class SimpleTree<T> implements Tree<T> {

    protected class SimpleTreeNode implements Tree.TreeNode<T> {
        public T value;
        public List<SimpleTreeNode> childs;

        public SimpleTreeNode(T value, List<SimpleTreeNode> childs) {
            this.value = value;
            this.childs = childs;
        }

        public SimpleTreeNode(T value) {
            this(value, new ArrayList<>());
        }

        @Override
        public T getValue() {
            return value;
        }

        @Override
        public List<TreeNode<T>> getChilds() {
            List<TreeNode<T>> newChilds = new ArrayList<>();
            for (SimpleTreeNode child : childs) {
                newChilds.add((TreeNode<T>) child);
            }

            return newChilds;
        }

        @Override
        public void addChild(TreeNode<T> child) {
            childs.add((SimpleTreeNode) child);
        }

        @Override
        public void setChilds(List<TreeNode<T>> childs) {
            List<SimpleTreeNode> newChilds = new ArrayList<>();
            for (TreeNode<T> child : childs) {
                newChilds.add((SimpleTreeNode) child);
            }

            this.childs = newChilds;
        }
    }

    protected SimpleTreeNode root = null;

    protected Function<String, T> fromStrFunc;
    protected Function<T, String> toStrFunc;

    public SimpleTree(Function<String, T> fromStrFunc, Function<T, String> toStrFunc) {
        this.fromStrFunc = fromStrFunc;
        this.toStrFunc = toStrFunc;
    }

    public SimpleTree(Function<String, T> fromStrFunc) {
        this(fromStrFunc, Object::toString);
    }

    public SimpleTree() {
        this(null);
    }

    @Override
    public TreeNode<T> getRoot() {
        return root;
    }

    public void clear() {
        root = null;
    }

    public void fromEdgesList(String graph) throws IOException {
        BufferedReader bufReader = new BufferedReader(new StringReader(graph));

        String line=null;
        while( (line=bufReader.readLine()) != null )
        {
            String[] lineItems = line.trim().split(" ");

            TreeNode<String> from = TreeAlgorithms.findNodeWithValue((TreeNode<String>) getRoot(), lineItems[0]);
            if (from == null) {
                from = (TreeNode<String>) new SimpleTreeNode((T) lineItems[0]);
            }
            if (root == null)
                root = (SimpleTreeNode) from;

            TreeNode<String> to = TreeAlgorithms.findNodeWithValue((TreeNode<String>) getRoot(), lineItems[1]);
            if (to == null) {
                to = (TreeNode<String>) new SimpleTreeNode((T) lineItems[1]);
            }

            from.addChild(to);
        }
    }
}

