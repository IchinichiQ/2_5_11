package ru.vsu.cs.p_p_v;

import java.util.*;

public class TreeAlgorithms {

    @FunctionalInterface
    public interface Visitor<T> {
        /**
         * "Посещение" вершины
         *
         * @param node Вершина, которую "посещаем"
         */
        void visit(Tree.TreeNode<T> node);
    }


    /**
     * Переворот дерева
     * @param treeNode Узел поддерева, с которого нужно начинать переворачивать дерево
     */
    public static <T> void reverseTree(Tree.TreeNode<T> treeNode) {
        preOrderVisit(treeNode, (node) -> {
            List<Tree.TreeNode<T>> childs = node.getChilds();
            Collections.reverse(childs);
            node.setChilds(childs);
        });
    }

    /**
     * Обход поддерева с вершиной в данном узле
     * "посетителем" в прямом/NLR порядке - рекурсивная реализация
     *
     * @param treeNode Узел поддерева, которое требуется "обойти"
     * @param visitor Посетитель
     */
    public static <T> void preOrderVisit(Tree.TreeNode<T> treeNode, Visitor<T> visitor) {
        // данный класс нужен только для того, чтобы "спрятать" его метод (c 3-мя параметрами)
        class Inner {
            void preOrderVisit(Tree.TreeNode<T> node, Visitor<T> visitor, int level) {
                if (node == null) {
                    return;
                }
                visitor.visit(node);
                for (Tree.TreeNode<T> child : node.getChilds()) {
                    preOrderVisit(child, visitor, level + 1);
                }
            }
        }

        // класс приходится создавать, т.к. статические методы в таких класс не поддерживаются
        new Inner().preOrderVisit(treeNode, visitor, 0);
    }

    /**
     * Поиск вершины с заданным значением в дереве
     *
     * @param treeNode Узел поддерева, которое требуется "обойти"
     * @param value Значение нужной вершины
     */
    public static <T> Tree.TreeNode<T> findNodeWithValue(Tree.TreeNode<T> treeNode, T value) {
        // данный класс нужен только для того, чтобы "спрятать" его метод (c 3-мя параметрами)
        class Inner {
            Tree.TreeNode<T> preOrderVisit(Tree.TreeNode<T> node, T value) {
                if (node == null)
                    return null;

                if (node.getValue().equals(value))
                    return node;

                Tree.TreeNode<T> neededNode = null;
                for (Tree.TreeNode<T> child : node.getChilds()) {
                    neededNode = preOrderVisit(child, value);
                    if (neededNode != null)
                        break;
                }

                return neededNode;
            }
        }

        // класс приходится создавать, т.к. статические методы в таких класс не поддерживаются
        return new Inner().preOrderVisit(treeNode, value);
    }

    /**
     * Обход поддерева с вершиной в данном узле
     * в виде итератора в прямом/NLR порядке
     * (предполагается, что в процессе обхода дерево не меняется)
     *
     * @param treeNode Узел поддерева, которое требуется "обойти"
     * @return Итератор
     */
    public static <T> Iterable<T> preOrderValues(Tree.TreeNode<T> treeNode) {
        return () -> {
            Stack<Tree.TreeNode<T>> stack = new Stack<>();
            stack.push(treeNode);

            return new Iterator<T>() {
                @Override
                public boolean hasNext() {
                    return stack.size() > 0;
                }

                @Override
                public T next() {
                    Tree.TreeNode<T> node = stack.pop();
                    for (Tree.TreeNode<T> child : node.getChilds()) {
                        stack.push(child);
                    }
                    return node.getValue();
                }

            };
        };
    }

    /**
     * Обход поддерева с вершиной в данном узле
     * "посетителем" в симметричном/поперечном/центрированном/LNR порядке - рекурсивная реализация
     *
     * @param treeNode Узел поддерева, которое требуется "обойти"
     * @param visitor Посетитель
     */
    public static <T> void inOrderVisit(Tree.TreeNode<T> treeNode, Visitor<T> visitor) {
        // данный класс нужен только для того, чтобы "спрятать" его метод (c 3-мя параметрами)
        class Inner {
            void inOrderVisit(Tree.TreeNode<T> node, Visitor<T> visitor, int level) {
                if (node == null) {
                    return;
                }
                visitor.visit(node);
                for (Tree.TreeNode<T> child : node.getChilds()) {
                    inOrderVisit(child, visitor, level + 1);
                }
            }
        }
        // класс приходится создавать, т.к. статические методы в таких класс не поддерживаются
        new Inner().inOrderVisit(treeNode, visitor, 0);
    }

    /**
     * Обход поддерева с вершиной в данном узле
     * "посетителем" в обратном/LRN порядке - рекурсивная реализация
     *
     * @param treeNode Узел поддерева, которое требуется "обойти"
     * @param visitor Посетитель
     */
    public static <T> void postOrderVisit(Tree.TreeNode<T> treeNode, Visitor<T> visitor) {
        // данный класс нужен только для того, чтобы "спрятать" его метод (c 3-мя параметрами)
        class Inner {
            void postOrderVisit(Tree.TreeNode<T> node, Visitor<T> visitor, int level) {
                if (node == null) {
                    return;
                }
                for (Tree.TreeNode<T> child : node.getChilds()) {
                    postOrderVisit(child, visitor, level + 1);
                }
                visitor.visit(node);
            }
        }
        // класс приходится создавать, т.к. статические методы в таких класс не поддерживаются
        new Inner().postOrderVisit(treeNode, visitor, 0);
    }

    /**
     *  Класс для хранения узла дерева вместе с его уровнем, нужен для методов
     *  {@link #byLevelVisit(Tree.TreeNode, Visitor)} и {@link #byLevelValues(Tree.TreeNode)}
     *
     * @param <T>
     */
    private static class QueueItem<T> {
        public Tree.TreeNode<T> node;
        public int level;

        public QueueItem(Tree.TreeNode<T> node, int level) {
            this.node = node;
            this.level = level;
        }
    }

    /**
     * Обход поддерева с вершиной в данном узле "посетителем" по уровням (обход в ширину)
     *
     * @param treeNode Узел поддерева, которое требуется "обойти"
     * @param visitor Посетитель
     */
    public static <T> void byLevelVisit(Tree.TreeNode<T> treeNode, Visitor<T> visitor) {
        Queue<QueueItem<T>> queue = new LinkedList<>();
        queue.add(new QueueItem<>(treeNode, 0));
        while (!queue.isEmpty()) {
            QueueItem<T> item = queue.poll();
            for (Tree.TreeNode<T> child : item.node.getChilds()) {
                queue.add(new QueueItem<>(child, item.level + 1));
            }
            visitor.visit(item.node);
        }
    }

    /**
     * Обход поддерева с вершиной в данном узле в виде итератора по уровням (обход в ширину)
     * (предполагается, что в процессе обхода дерево не меняется)
     *
     * @param treeNode Узел поддерева, которое требуется "обойти"
     * @return Итератор
     */
    public static <T> Iterable<T> byLevelValues(Tree.TreeNode<T> treeNode) {
        return () -> {
            Queue<QueueItem<T>> queue = new LinkedList<>();
            queue.add(new QueueItem<>(treeNode, 0));

            return new Iterator<T>() {
                @Override
                public boolean hasNext() {
                    return queue.size() > 0;
                }

                @Override
                public T next() {
                    QueueItem<T> item = queue.poll();
                    if (item == null) {
                        // такого быть не должно, но на вский случай
                        return null;
                    }

                    for (Tree.TreeNode<T> child : item.node.getChilds()) {
                        queue.add(new QueueItem<>(child, item.level + 1));
                    }
                    return item.node.getValue();
                }
            };
        };
    }


    /**
     * Представление дерева в формате dot
     *
     * @param treeNode Узел поддерева, которое требуется представить в виде dot файла
     * @return дерево в виде строки
     */
    public static <T> String toGraphvizStr(Tree.TreeNode<T> treeNode) {
        StringBuilder sb = new StringBuilder();

        sb.append("graph {\r\n");
        preOrderVisit(treeNode, (node) -> {
            for (Tree.TreeNode<T> child : node.getChilds()) {
                sb.append(node.getValue());
                sb.append(" -- ");
                sb.append(child.getValue());
                sb.append("\r\n");
            }
        });
        sb.append("}");

        return sb.toString();
    }
}
