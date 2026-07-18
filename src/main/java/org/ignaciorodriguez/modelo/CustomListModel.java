package org.ignaciorodriguez.modelo;


import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;

public class CustomListModel<E> extends DefaultListModel<E> {

    private final List<E> list;

    public CustomListModel() {
        this.list = new ArrayList();
    }

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public E getElementAt(int i) {
        return list.get(i);
    }

    @Override
    public void removeElementAt(int i) {
        list.remove(i);
        super.removeElement(i);
    }

    @Override
    public void addElement(E element) {
        if (!list.contains(element)) {
            super.addElement(element);
            list.add(element);
        }
    }

    public List<E> getLista() {
        return List.copyOf(list);
    }
}
