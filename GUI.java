import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.Toolkit;
import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import javax.swing.JOptionPane;
import javax.swing.JDialog;

import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.concurrent.TimeUnit; //used for testing

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.event.*;
import javax.swing.text.*;
import java.awt.event.*;

public class GUI{
    Color customGrey1 = new Color(103,103,103); //lightest custom grey
    Color customGrey2 = new Color(53,53,53);
    Color customGrey3 = new Color(43,43,43);
    Color customGrey4 = new Color(33,33,33); //darkest custom grey
    Color customWhite = new Color(253,253,253);
    Color customOrange = new Color(210,103,6); //custom orange colour for ready and possibly cancel buttons

    private static String inputFolder, outputFolder;
    private JTextField outFolder, inFolder;
    private Driver pro;
    // private JLabel numberOfFramesDetected, numberOfObjectsDetected, numberOfDisksDetected, numberDisksInRed, numberDisksInGreen, numberDisksInBlue;
    private static JPanel panel, panelOutputLabels, panelFrames, outputPanel;
    private static GUI gui;
    private JLabel processingGif;
    private String[][] col;
    private DefaultTableModel tableModel;
    private int index;
    private Category[] catArray;
    private String categoryCheck;
    private ArrayList<String> categoryOrder;

    public GUI(){
        JFrame frame = new JFrame();
        frame.setResizable(false);
        panel = new JPanel();

        // panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        // panel.setLayout(new GridLayout(1, 2));
        // panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        // panel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.setBackground(customGrey2);
        frame.setBackground(customGrey2);

        JPanel menu = new JPanel();
        createMenu(menu);

        outputPanel = new JPanel();
        createOutputPanel(outputPanel);

        panel.add(menu);
        panel.add(outputPanel);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("VTRACK");
        frame.pack();
        frame.setSize(1300,700);
        frame.setVisible(true);
    }

    public void createMenu(JPanel menu){
        menu.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        // menu.setBorder(BorderFactory.createLineBorder(Color.blue)); //outlines menu panel
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setPreferredSize(new Dimension(350,700));
        menu.setMaximumSize(new Dimension(350,700));
        menu.setBackground(customGrey3);

        JPanel panelFiles = createPanelFolders();
        menu.add(panelFiles);
        JPanel panelCategories = createPanelCategories();
        menu.add(panelCategories);
        JPanel panelButtons = createPanelButtons();
        menu.add(panelButtons);
    }

    public JPanel createPanelFolders(){ //change "files" to folders or directories
        JPanel panelFiles = new JPanel();
        panelFiles.setBorder(BorderFactory.createTitledBorder("Folders"));
        // panelFiles.setLayout(new BoxLayout(panelFiles, BoxLayout.Y_AXIS));
        // panelFiles.setLayout(new GridLayout(2,2));

        ((javax.swing.border.TitledBorder)panelFiles.getBorder()).setTitleColor(Color.yellow);

        panelFiles.setLayout(new FlowLayout(FlowLayout.LEFT,0,6));
        panelFiles.setPreferredSize(new Dimension(335,180));
        panelFiles.setMaximumSize(new Dimension(335,180));
        panelFiles.setBackground(customGrey3);

        JPanel inFolPanel = new JPanel();

        inFolder = new JTextField("none",22);
        inFolder.setEditable(false);

        JButton searchFolder = new JButton("Search");
        searchFolder.setSize(200 , 150);
        searchFolder.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JFileChooser chooser = new JFileChooser();
                // int i = chooser.showOpenDialog(panelFiles);
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);
                int i = chooser.showOpenDialog(panelFiles);
                if (i == JFileChooser.APPROVE_OPTION){
                    inputFolder = chooser.getSelectedFile().getPath();
                    inFolder.setText(inputFolder);
                    inFolPanel.repaint();
                }
            }
        });

        inFolPanel.add(inFolder);
        inFolPanel.add(searchFolder);
        // inFolPanel.setBorder(new EmptyBorder(0, 20, 0, 0));
        inFolPanel.setBackground(customGrey3);

        JPanel outFolPanel = new JPanel();

        outFolder = new JTextField("none",22);
        outFolder.setEditable(false);
        // inFolder.setMinimumSize(new Dimension(300,200));
        // inFolder.setBounds(0,0, 200,30);

        JButton searchOutFolder = new JButton("Search");
        searchOutFolder.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JFileChooser chooser2 = new JFileChooser();
                chooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser2.setAcceptAllFileFilterUsed(false);
                int i = chooser2.showOpenDialog(panelFiles);
                if (i == JFileChooser.APPROVE_OPTION){
                    outputFolder = chooser2.getSelectedFile().getPath();
                    outFolder.setText(outputFolder);
                    outFolPanel.repaint();
                }
            }
        });

        outFolPanel.add(outFolder);
        outFolPanel.add(searchOutFolder);
        outFolPanel.setBackground(customGrey3);

        JLabel inFol = new JLabel("Input Folder:");
        inFol.setForeground(Color.white);
        JLabel outFol = new JLabel("Output Folder:");
        outFol.setForeground(Color.white);

        panelFiles.add(inFol);
        panelFiles.add(inFolPanel);
        panelFiles.add(outFol);
        panelFiles.add(outFolPanel);
        return panelFiles;
    }

    public JPanel createPanelCategories(){
        JPanel panelCategories = new JPanel();
        panelCategories.setBorder(BorderFactory.createTitledBorder("Categories"));
        ((javax.swing.border.TitledBorder)panelCategories.getBorder()).setTitleColor(Color.white);
        panelCategories.setLayout(new GridLayout(3,1));
        panelCategories.setMaximumSize(new Dimension(335,470));
        panelCategories.setBackground(customGrey3);
    	
    	JPanel redCategory = new JPanel();

        JRadioButton radioRed = new JRadioButton("Red");
        JRadioButton radioBlue = new JRadioButton("Blue");
        radioBlue.setEnabled(false);
        JRadioButton radioGreen = new JRadioButton("Green");
        radioGreen.setEnabled(false);
        
        JTextField minRed = new JTextField("Minimum");
        minRed.setEnabled(false);
        JTextField maxRed = new JTextField("Maximum");
        maxRed.setEnabled(false);
        JTextField minBlue = new JTextField("Minimum");
        minBlue.setEnabled(false);
        JTextField maxBlue = new JTextField("Maximum");
        maxBlue.setEnabled(false);
        JTextField minGreen = new JTextField("Minimum");
        minGreen.setEnabled(false);
        JTextField maxGreen = new JTextField("Maximum");
        maxGreen.setEnabled(false);
        
    	panelCategories.add(redCategory);
    	redCategory.setLayout(new GridLayout(3,1));
        redCategory.setBackground(customGrey3); 
        redCategory.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    	radioRed.setBackground(Color.red);
    	redCategory.add(radioRed);
    	redCategory.add(minRed);
    	redCategory.add(maxRed);
        
    	radioRed.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                int clicked = event.getStateChange();
                if (clicked == ItemEvent.SELECTED) {
                    minRed.setEnabled(true);
                }	
                //add else to remove 
            }
    	});
        
        minRed.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
    
            }
            public void removeUpdate(DocumentEvent e) {
 
            }
            public void insertUpdate(DocumentEvent e) {
                  minRed.setEnabled(false);
            maxRed.setEnabled(true);
            }
            
        });
        
        maxRed.getDocument().addDocumentListener(new DocumentListener() {
          public void changedUpdate(DocumentEvent e) {
    
            }
            public void removeUpdate(DocumentEvent e) {
 
            }
          
          public void insertUpdate(DocumentEvent e) {
            maxRed.setEnabled(false);
    			radioBlue.setEnabled(true);
    			if (Integer.parseInt(minRed.getText()) <= Integer.parseInt(maxRed.getText())) {
    				categoryCheck = "red";
    				//categoryOrder.add(categoryCheck);
    			}
                else{
                    JOptionPane.showMessageDialog(panel, "Min and max not okay", "fahoihfnaolif", JOptionPane.ERROR_MESSAGE);
                }
          }
        });
            
    	JPanel blueCategory = new JPanel();
    	panelCategories.add(blueCategory);
    	blueCategory.setLayout(new GridLayout(3,1));
        blueCategory.setBackground(customGrey3); 
        blueCategory.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    	radioBlue.setBackground(Color.blue);
    	blueCategory.add(radioBlue);
    	blueCategory.add(minBlue);
    	blueCategory.add(maxBlue);
    	
    	radioBlue.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                int clicked = event.getStateChange();
                if (clicked == ItemEvent.SELECTED) {
                    minBlue.setEnabled(true);
                }	
                //add else to remove 
            }
    	});
        
        minBlue.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
    
            }
            public void removeUpdate(DocumentEvent e) {
 
            }
            public void insertUpdate(DocumentEvent e) {
                  minBlue.setEnabled(false);
            maxBlue.setEnabled(true);
            }
            
        });
        
        maxBlue.getDocument().addDocumentListener(new DocumentListener() {
          public void changedUpdate(DocumentEvent e) {
    
            }
            public void removeUpdate(DocumentEvent e) {
 
            }
          
          public void insertUpdate(DocumentEvent e) {
            maxBlue.setEnabled(false);
    			radioGreen.setEnabled(true);
    			if (Integer.parseInt(maxBlue.getText()) >= Integer.parseInt(minBlue.getText())) {
    				if ((Integer.parseInt(minBlue.getText()) > Integer.parseInt(minRed.getText())) && 
    					(Integer.parseInt(minBlue.getText()) > Integer.parseInt(maxRed.getText())) ||
    					(Integer.parseInt(minBlue.getText())) < (Integer.parseInt(minRed.getText())))
    								categoryCheck = "blue";
    								//categoryOrder.add(categoryCheck);
    			}
                else{
                    JOptionPane.showMessageDialog(panel, "Min and max not okay", "fahoihfnaolif", JOptionPane.ERROR_MESSAGE);
                }
          }
        });
    	
    	JPanel greenCategory = new JPanel();
    	panelCategories.add(greenCategory);
    	greenCategory.setLayout(new GridLayout(3,1));
        greenCategory.setBackground(customGrey3); 
        greenCategory.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    	radioGreen.setBackground(Color.green);
    	greenCategory.add(radioGreen);
    	greenCategory.add(minGreen);
    	greenCategory.add(maxGreen);
    	
    	radioGreen.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                int clicked = event.getStateChange();
                if (clicked == ItemEvent.SELECTED) {
                    minGreen.setEnabled(true);
                }	
                //add else to remove 
            }
    	});
        
        minGreen.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
    
            }
            public void removeUpdate(DocumentEvent e) {
 
            }
            public void insertUpdate(DocumentEvent e) {
                  minGreen.setEnabled(false);
            maxGreen.setEnabled(true);
            }
            
        });
        
        maxGreen.getDocument().addDocumentListener(new DocumentListener() {
          public void changedUpdate(DocumentEvent e) {
    
            }
            public void removeUpdate(DocumentEvent e) {
 
            }
          
          public void insertUpdate(DocumentEvent e) {
            maxGreen.setEnabled(false);
    			//radioGreen.setEnabled(true);
				if (Integer.parseInt(maxGreen.getText()) >= Integer.parseInt(minGreen.getText())){
					if (((Integer.parseInt(minGreen.getText()) > Integer.parseInt(minBlue.getText())) && 
							(Integer.parseInt(minGreen.getText()) > Integer.parseInt(minRed.getText())) &&
							(Integer.parseInt(minGreen.getText()) > Integer.parseInt(maxBlue.getText())) &&
							(Integer.parseInt(minGreen.getText()) > Integer.parseInt(maxRed.getText()))) ||
							((Integer.parseInt(maxGreen.getText()) < Integer.parseInt(minBlue.getText())) &&
							(Integer.parseInt(minGreen.getText()) > Integer.parseInt(maxRed.getText()))) ||
							((Integer.parseInt(maxGreen.getText()) < Integer.parseInt(minBlue.getText())) &&
							(Integer.parseInt(maxGreen.getText()) < Integer.parseInt(maxGreen.getText())))){
								categoryCheck = "green";
						//categoryOrder.add(categoryCheck);
					}
				
				}	
    			
                else{
                    JOptionPane.showMessageDialog(panel, "Min and max not okay", "fahoihfnaolif", JOptionPane.ERROR_MESSAGE);
                }
          }
        }); 
        return panelCategories;
    }
    
    public void changeIcon(ImageIcon img){
        processingGif.setIcon(img);
    }

    public JPanel createPanelButtons(){ //uses flowlayout
        JPanel panelButtons = new JPanel();
        panelButtons.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        //panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.X_AXIS));
        panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER,45,0));
        panelButtons.setMaximumSize(new Dimension(335,50));
        panelButtons.setBackground(customGrey3);

        JButton readyButton = new JButton("Ready!"); //will start processing of frames in input folder and adding disks to selected categories as well as adding processed frames to output folder.
        readyButton.setBackground(customOrange);
        //panelButtons.add(readyButton, BorderLayout.CENTER);
        panelButtons.add(readyButton);

        readyButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if((inFolder.getText()).equals("none") || (outFolder.getText()).equals("none")){
                    // System.out.println("Input folder has not been selected. Please select one by clicking the search button and choosing folder in dialog.");
                    JOptionPane.showMessageDialog(panel, "Valid input and/or output folders have not been selected.", "Invalid Input and Output Folders", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    readyButton.setEnabled(false);
                    
                    //code that adds to catArray.
                    
                    pro = new Driver(inputFolder, outputFolder,catArray, gui);
                    pro.start();
                    try{
                        pro.join();
                    }
                    catch(InterruptedException v){
                        v.printStackTrace();
                    }
                    readyButton.setEnabled(true);
                    createProcessedFrames();
                }
            }
        });

//         JButton cancelButton = new JButton("Cancel");
//         cancelButton.setBackground(customOrange);
//         panelButtons.add(cancelButton);
//         cancelButton.addActionListener(new ActionListener(){
//             public void actionPerformed(ActionEvent e){
//                 // Image img = Toolkit.getDefaultToolkit().createImage("giphyNew.gif");
//                 // ImageIcon imageI = new ImageIcon(img);
//                 processingGif.setIcon(null); //temp test if gif disappears correctly
//             }
//         });

        return panelButtons;
    }

    public void createOutputPanel(JPanel outputPanel){
        // outputPanel.setBorder(BorderFactory.createLineBorder(Color.red)); //outlines output panel
        outputPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));
        outputPanel.setPreferredSize(new Dimension(950,700));
        outputPanel.setMaximumSize(new Dimension(950,700));
        outputPanel.setBackground(customGrey2);
        outputPanel.add(createPanelFrames());
        outputPanel.add(createPanelOutputLabels());
    }

    public JPanel createPanelFrames(){
        panelFrames = new JPanel();
        panelFrames.setBorder(BorderFactory.createLineBorder(customGrey3));
        // panelFrames.setLayout(new BoxLayout(panelFrames, BoxLayout.X_AXIS));
        panelFrames.setLayout(new BorderLayout());
        panelFrames.setPreferredSize(new Dimension(920,500));
        panelFrames.setMaximumSize(new Dimension(920,500));
        panelFrames.setBackground(customGrey3);

        processingGif = new JLabel();
        panelFrames.add(processingGif, BorderLayout.CENTER);
        processingGif.setHorizontalAlignment(JLabel.CENTER);
        processingGif.setVerticalAlignment(JLabel.CENTER);

        return panelFrames;
    }
    
    // public Image findImageInOutputFolder(File[] arrayOfFrames, int number){
    //     for (File f: arrayOfFrames){
    //         String name = arrayOfFrames[number].getName();
    //         if (name.substring(name.length()-14).equals("_processed.gif"));
    //             Image img = Toolkit.getDefaultToolkit().createImage(outputFolder + "\\" + arrayOfFrames[number].getName());
    //             return img;
    //         }
    //     }
    //     return null;
    // }

    public ArrayList<File> createArrayofProcessedFrames(File[] arrayOfFrames){
        ArrayList<File> foundProcessedFrames = new ArrayList<File>();
        for (File f: arrayOfFrames){
            String name = f.getName();
            if (name.substring(name.length()-14).equals("_processed.gif")){
                foundProcessedFrames.add(f);
            }
        }
        return foundProcessedFrames;
    }
    
    public void createProcessedFrames(){
        File folder = new File(outputFolder);
        System.out.println("apfijpfj");
        if (folder.isDirectory()){
            File[] a = folder.listFiles();
            ArrayList<File> listOfProcessedFrames = new ArrayList<File>(Arrays.asList(a));
            // File[] intialListOfProcessedFrames = folder.listFiles();
            // ArrayList<File> listOfProcessedFrames = createArrayofProcessedFrames(intialListOfProcessedFrames);
            // System.out.println(listOfProcessedFrames.length);
            panelFrames.remove(processingGif);

            JPanel theFrames = new JPanel();
            theFrames.setLayout(new BorderLayout());
            theFrames.setPreferredSize(new Dimension(920,400));
            theFrames.setMaximumSize(new Dimension(920,450));
            theFrames.setBackground(customGrey3);
            JLabel displayedProcessedGifName = new JLabel();
            JLabel displayProcessedGif = new JLabel();
            index = 0;
            Image img = Toolkit.getDefaultToolkit().createImage(outputFolder + "\\" + (listOfProcessedFrames.get(index)).getName());

// Section to be removed
            // Image img = null;
            // for (File f: listOfProcessedFrames){
            //    String name = listOfProcessedFrames[index].getName();
            //    if (name.substring(name.length()-14).equals("_processed.gif"));
            //       img = Toolkit.getDefaultToolkit().createImage(outputFolder + "\\" + listOfProcessedFrames[0].getName());
            //       break;
            //    }
            // }
            // Image img = findImageInOutputFolder(listOfProcessedFrames, index); //should not return null

            ImageIcon iconToDisplay = new ImageIcon(img);
            displayProcessedGif.setIcon(iconToDisplay);
            displayedProcessedGifName.setText((listOfProcessedFrames.get(index)).getName());
            displayedProcessedGifName.setForeground(customGrey1);
            theFrames.add(displayProcessedGif, BorderLayout.CENTER);
            theFrames.add(displayedProcessedGifName, BorderLayout.NORTH);
            displayProcessedGif.setHorizontalAlignment(JLabel.CENTER);
            displayProcessedGif.setVerticalAlignment(JLabel.CENTER);

            JPanel theButtons = new JPanel();
            theButtons.setBackground(customGrey3);
            JButton previous = new JButton("Previous");
            theButtons.add(previous);
            JButton next = new JButton("Next");
            theButtons.add(next);
            
            if ((index - 1)<= -1){
                previous.setEnabled(false);
            }

            previous.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    if (index > 0){
                       index--;
                       if ((index + 1) < listOfProcessedFrames.size()){
                           next.setEnabled(true);
                       }
                       if ((index - 1)<= -1){
                           previous.setEnabled(false);
                       }
                       // Image imgPrev = findImageInOutputFolder(listOfProcessedFrames, index);
                       Image imgPrev = Toolkit.getDefaultToolkit().createImage(outputFolder + "\\" + (listOfProcessedFrames.get(index)).getName());
                       ImageIcon prevIconToDisplay = new ImageIcon(imgPrev);
                       displayProcessedGif.setIcon(prevIconToDisplay);
                       displayedProcessedGifName.setText((listOfProcessedFrames.get(index)).getName());
                    }
                }
            });

            
            if ((index + 1)>= listOfProcessedFrames.size()){
                next.setEnabled(false);
            }

            next.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    if (index<(listOfProcessedFrames.size()-1)){
                        index++;
                        if ((index + 1)>= (listOfProcessedFrames.size())){
                            next.setEnabled(false);
                        }
                        if ((index - 1)> -1){
                            previous.setEnabled(true);
                        }
                        // Image imgNext = findImageInOutputFolder(listOfProcessedFrames, index);
                        Image imgNext = Toolkit.getDefaultToolkit().createImage(outputFolder + "\\" + (listOfProcessedFrames.get(index)).getName());
                        ImageIcon nextIconToDisplay = new ImageIcon(imgNext);
                        displayProcessedGif.setIcon(nextIconToDisplay);
                        displayedProcessedGifName.setText((listOfProcessedFrames.get(index)).getName());
                    }
                }
            });
            
            panelFrames.add(theFrames, BorderLayout.CENTER);
            panelFrames.add(theButtons, BorderLayout.SOUTH);
            panelFrames.revalidate();
            panelFrames.repaint();
        }
        else{
            System.out.println("File is not a directory.");
        }
    }

    public JPanel createPanelOutputLabels(){
        col = new String[][]{{"Number of Frames Detected: ","     ", "Number of Disks in Red Cat.: ", "     "}, //easier solution to create JTable
                {"Number of Objects Detected: ", "     ","Number of Disks in Green Cat.:", "     "},
                {"Number of Disks Detected: ", "    ","Number of Disks in Blue Cat.: ", "    "}};
        String[] a = {"1","2","3","4"};
        tableModel = new DefaultTableModel(col, a);
        JTable table = new JTable(tableModel){
            public boolean getScrollableTracksViewportWidth(){
                return getPreferredSize().width < panelOutputLabels.getWidth();
            }

            public boolean getScrollableTracksViewportHeight(){
                return getPreferredSize().height < panelOutputLabels.getHeight();
            }
        };
        table.setTableHeader(null);
        table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
        table.setBackground(customGrey2);
        table.setForeground(Color.white);
        // table.setShowGrid(false); //not able to remove white outline on gui

        panelOutputLabels = new JPanel();
        panelOutputLabels.setPreferredSize(new Dimension(920,150));
        panelOutputLabels.setBorder(BorderFactory.createEmptyBorder(30,30,50,50));
        panelOutputLabels.setLayout(new BorderLayout());
        panelOutputLabels.setMaximumSize(new Dimension(920,180));
        panelOutputLabels.setBackground(customGrey2);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        panelOutputLabels.add(scrollPane,BorderLayout.CENTER);

        return panelOutputLabels;
    }

    public void changeNumberOfFramesDetected(int nF){tableModel.setValueAt(Integer.toString(nF),0,1);}
    public void changeNumberOfObjectsDetected(int nO){tableModel.setValueAt(Integer.toString(nO),1,1);}
    public void changeNumberOfDisksDetected(int nD){tableModel.setValueAt(Integer.toString(nD),2,1);}
    public void changeNumberDisksInRed(int nDR){tableModel.setValueAt(Integer.toString(nDR),0,3);}
    public void changeNumberDisksInGreen(int nDG){tableModel.setValueAt(Integer.toString(nDG),1,3);}
    public void changeNumberDisksInBlue(int nDB){tableModel.setValueAt(Integer.toString(nDB),2,3);}

    public static void main(String args[]){
        gui = new GUI();
    }
}
