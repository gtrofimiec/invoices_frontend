package com.myprojects.invoices_frontend.layout.dialogboxes;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

public class FolderChooser {

    public String getPath() {
        String path = " ";
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setDialogTitle("Wybierz folder do zapisu faktur .pdf");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnValue = chooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            if (chooser.getSelectedFile().isDirectory()) {
                path = chooser.getSelectedFile().getAbsolutePath();
            }
        }
        return path;
    }
}