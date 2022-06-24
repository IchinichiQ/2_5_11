package ru.vsu.cs.p_p_v;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.*;
import guru.nidi.graphviz.parse.Parser;

import javax.swing.*;
import java.io.IOException;

public class FormMain extends JFrame {
    private JPanel panelMain;
    private JTextArea textAreaGraph;
    private JButton buttonDrawGraph;
    private JPanel panelGraph;
    private JButton buttonReverseAndDraw;

    SvgPanel panelGraphvizPainter = new SvgPanel();

    public FormMain() {
        this.setTitle("2_5_11");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panelMain);
        this.setSize(500, 500);

        panelGraph.add(panelGraphvizPainter);
        panelGraph.repaint();

        buttonDrawGraph.addActionListener(e -> buttonDrawGraphPressed());
        buttonReverseAndDraw.addActionListener(e -> buttonReverseAndDrawPressed());
    }

    private void drawGraph(String graphvizStr) {
        try {
            MutableGraph g = new Parser().read(graphvizStr);
            panelGraphvizPainter.paint(Graphviz.fromGraph(g).render(Format.SVG).toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

    private void buttonDrawGraphPressed() {
        try {
            SimpleTree<String> tree = new SimpleTree<>();
            tree.fromEdgesList(textAreaGraph.getText());

            drawGraph(tree.toGraphvizStr());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

    private void buttonReverseAndDrawPressed() {
        try {
            SimpleTree<String> tree = new SimpleTree<>();
            tree.fromEdgesList(textAreaGraph.getText());

            TreeAlgorithms.reverseTree(tree.getRoot());

            drawGraph(tree.toGraphvizStr());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }
}
