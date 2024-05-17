package ru.nsu.votintsev.client.view;

import java.awt.*;

public class ReverseBoxLayout implements LayoutManager2 {

    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        int width = 0;
        int height = 0;

        for (Component comp : parent.getComponents()) {
            Dimension prefSize = comp.getPreferredSize();
            height += prefSize.height;
            width = Math.max(width, prefSize.width);
        }

        return new Dimension(width, height);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {
        int y = parent.getHeight();

        for (Component comp : parent.getComponents()) {
            Dimension prefSize = comp.getPreferredSize();
            y -= prefSize.height;
            comp.setBounds(0, y, parent.getWidth(), prefSize.height);
        }
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        // При добавлении нового компонента, сдвигаем все старые компоненты вверх
        for (Component c : comp.getParent().getComponents()) {
            c.setLocation(c.getX(), c.getY() - comp.getHeight());
        }
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return null;
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    @Override
    public void invalidateLayout(Container target) {
    }
}