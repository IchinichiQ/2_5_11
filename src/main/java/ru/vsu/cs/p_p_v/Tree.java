package ru.vsu.cs.p_p_v;

import java.awt.Color;
import java.util.*;

/**
 * Интерфейс для дерева с реализацияей по умолчанию различных обходов
 * дерева
 *
 * @param <T>
 */
public interface Tree<T> {

    /**
     * Интерфейс для описания узла двоичного дерева
     *
     * @param <T>
     */
    interface TreeNode<T> {

        /**
         * @return Значение в узле дерева
         */
        T getValue();

        /**
         * @return Поддеревья
         */
        default List<TreeNode<T>> getChilds() {
            return null;
        }

        /**
         * @return Добавить поддерево
         */
        default void addChild(TreeNode<T> child) {
        }

        /**
         * @return Установить поддеревья
         */
        default void setChilds(List<TreeNode<T>> childs) {
        }

        /**
         * @return Цвет узла (для рисования и не только)
         */
        default Color getColor() {
            return Color.BLACK;
        }

        /**
         * Представление поддерева в виде строки в скобочной нотации
         *
         * @return дерево в виде строки
         */
        default String toGraphvizStr() {
            return TreeAlgorithms.toGraphvizStr(this);
        }
    }

    /**
     * @return Корень (вершина) дерева
     */
    TreeNode<T> getRoot();

    /**
     * Представление дерева в виде строки в скобочной нотации
     *
     * @return дерево в виде строки
     */
    default String toGraphvizStr() {
        return this.getRoot().toGraphvizStr();
    }
}

