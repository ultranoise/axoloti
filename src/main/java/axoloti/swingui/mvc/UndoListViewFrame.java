package axoloti.swingui.mvc;

import axoloti.abstractui.DocumentWindow;
import axoloti.mvc.AbstractDocumentRoot;
import axoloti.mvc.UndoableEditGroup;
import axoloti.mvc.UndoablePropertyChange;
import axoloti.swingui.menus.StandardMenubar;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import javax.swing.undo.UndoableEdit;

/**
 *
 * @author jtaelman
 */
public class UndoListViewFrame extends AJFrame<AbstractDocumentRoot> {

    private List<UndoableEdit> undoList = new ArrayList<>();

    /**
     * Creates new form UndoListViewFrame
     */
    public UndoListViewFrame(AbstractDocumentRoot documentRoot, DocumentWindow parent) {
        super(documentRoot, parent);
        initComponents();
        initComponents2();
    }

    private void initComponents2() {
        setJMenuBar(new StandardMenubar(model));
        jTable1.setModel(new AbstractTableModel() {
            private final String[] columnNames = {"Name", "Hash", "object", "old value", "new value"};

            @Override
            public String getColumnName(int column) {
                return columnNames[column];
            }

            @Override
            public Class getColumnClass(int column) {
                return String.class;
            }

            @Override
            public int getRowCount() {
                return undoList.size();
            }

            @Override
            public int getColumnCount() {
                return columnNames.length;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                UndoableEdit e = undoList.get(rowIndex);
                if (e == null) {
                    return null;
                }
                switch (columnIndex) {
                    case 0:
                        if (e instanceof UndoableEditGroup) {
                            return e.getPresentationName();
                        } else {
                            return " - " + e.getPresentationName();
                        }
                    case 1:
                        return String.format("%08X", e.hashCode());
                    case 2:
                        if (e instanceof UndoablePropertyChange) {
                            UndoablePropertyChange upc = (UndoablePropertyChange) e;
                            Object val = upc.getModel();
                            return (val != null) ? val.toString() : "null";
                        } else {
                            return "";
                        }
                    case 3:
                        if (e instanceof UndoablePropertyChange) {
                            UndoablePropertyChange upc = (UndoablePropertyChange) e;
                            Object val = upc.getOldValue();
                            return (val != null) ? val.toString() : "null";
                        } else {
                            return "";
                        }
                    case 4:
                        if (e instanceof UndoablePropertyChange) {
                            UndoablePropertyChange upc = (UndoablePropertyChange) e;
                            Object val = upc.getNewValue();
                            return (val != null) ? val.toString() : "null";
                        } else {
                            return "";
                        }
                    default:
                        return "";
                }
            }
        });

        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    int rowIndex = jTable1.getSelectedRow();
                    if (rowIndex == -1) {
                        return;
                    }
                    UndoableEdit e = undoList.get(rowIndex);
                    if (e instanceof UndoableEditGroup) {
                        UndoableEditGroup ueg = (UndoableEditGroup) e;
                        ueg.grabFocus();
                    }
                }
            }
        });
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBoxDetails = new javax.swing.JCheckBox();
        jCheckBoxDirty = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setTitle("Undoable actions");
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.PAGE_AXIS));

        jCheckBoxDetails.setText("Show details");
        jCheckBoxDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxDetailsActionPerformed(evt);
            }
        });
        getContentPane().add(jCheckBoxDetails);

        jCheckBoxDirty.setText("dirty");
        jCheckBoxDirty.setEnabled(false);
        getContentPane().add(jCheckBoxDirty);

        jScrollPane2.setAlignmentX(0.0F);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(jTable1);

        getContentPane().add(jScrollPane2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBoxDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxDetailsActionPerformed
        List<UndoableEdit> list = (List<UndoableEdit>) AbstractDocumentRoot.UNDO_EVENTS.get(model);
        updateList(list);
    }//GEN-LAST:event_jCheckBoxDetailsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBoxDetails;
    private javax.swing.JCheckBox jCheckBoxDirty;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    @Override
    public boolean askClose() {
        return false;
    }

    @Override
    public File getFile() {
        return null;
    }

    @Override
    public List<DocumentWindow> getChildDocuments() {
        return Collections.emptyList();
    }

    void updateList(List<UndoableEdit> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        if (jCheckBoxDetails.isSelected()) {
            // expand list
            undoList = new ArrayList<>();
            for (UndoableEdit e : list) {
                undoList.add(e);
                if (e instanceof UndoableEditGroup) {
                    UndoableEditGroup eg = (UndoableEditGroup) e;
                    for (UndoableEdit e2 : eg.getElements()) {
                        undoList.add(e2);
                    }
                }
            }
        } else {
            undoList = list;
        }
        ((AbstractTableModel) jTable1.getModel()).fireTableDataChanged();
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        if (AbstractDocumentRoot.UNDO_EVENTS.is(evt)) {
            List<UndoableEdit> list = (List<UndoableEdit>) AbstractDocumentRoot.UNDO_EVENTS.getNewValue(evt);
            updateList(list);
        } else if (AbstractDocumentRoot.DIRTY.is(evt)) {
            jCheckBoxDirty.setSelected((Boolean) evt.getNewValue());
        } else if (AbstractDocumentRoot.EDIT_TO_BE_UNDONE.is(evt)) {
            UndoableEdit e = (UndoableEdit) evt.getNewValue();
            int s = undoList.indexOf(e);
            if (s > 0) {
                jTable1.getSelectionModel().setSelectionInterval(s, s);
                Rectangle r = jTable1.getCellRect(s, 0, true);
                r.y += r.height * 3; // scroll down 3 extra rows if possible
                jTable1.scrollRectToVisible(r);
            }
        }
    }
}
