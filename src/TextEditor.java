import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TextEditor implements ActionListener {
    //Declaring properties of text editor
    JFrame frame;
    JMenuBar menuBar;
    JMenu file, edit;
    JMenuItem newFile, openFile, saveFile;
    JMenuItem cut, copy, paste, selectAll, exit;
    JTextArea textArea;
    JFileChooser fileChooser;
    DateTimeFormatter formatter;
    LocalDateTime now;
    String date;
    String displayFileName;


    TextEditor(){
        //initializing frame
        frame = new JFrame();

        // initializing menubar
        menuBar = new JMenuBar();

        // initialize menu Item
        newFile = new JMenuItem("New Window");
        openFile = new JMenuItem("Open File");
        saveFile = new JMenuItem("Save");
        cut = new JMenuItem("Cut");
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("Paste");
        selectAll = new JMenuItem("Select All");
        exit = new JMenuItem("Exit");

        // initializing file chooser
        fileChooser = new JFileChooser("G://learn//folder-name");

        // initializing date and time for file name
        formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        now = LocalDateTime.now();
        date = now.format(formatter);

        // adding action listener (Strictly before adding menu item to menu)
        newFile.addActionListener(this);
        openFile.addActionListener(this);
        saveFile.addActionListener(this);
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);
        exit.addActionListener(this);

        //initiallize menu
        file = new JMenu("File");
        edit = new JMenu("Edit");

        // initialize text area
        textArea = new JTextArea();

        //add menus to menu bar
        menuBar.add(file);
        menuBar.add(edit);

        // add menu items to menus
        file.add(newFile);
        file.add(openFile);
        file.add(saveFile);
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);
        edit.add(exit);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5,5,5,5));
        panel.setLayout(new BorderLayout(0,0));
        //Add text area to the panel
        panel.add(textArea,BorderLayout.CENTER);
        //Create Scroll pane
        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        panel.add(scrollPane);

        frame.add(panel);

        frame.setBounds(100,100,600,400);

        displayFileName = "Notepad_" + date;
        frame.setTitle("Notepad");

        // setting menu bar
        frame.setJMenuBar(menuBar);

        // initializing dimension
        frame.setBounds(150,150,500,500);
        frame.setVisible(true);
        frame.setLayout(null);



    }
    public static void main(String[] args) {
        TextEditor textEditor = new TextEditor();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // cut
        if(e.getSource() == cut){
            textArea.cut();
        }

        // copy
        if(e.getSource() == copy){
            textArea.copy();
        }

        // paste
        if(e.getSource() == paste){
            textArea.paste();
        }

        // select all
        if(e.getSource() == selectAll){
            textArea.selectAll();
        }

        // close the window
        if(e.getSource() == exit){
            System.exit(0);
        }

        // open file from a location
        if(e.getSource() == openFile){
            int chooseOption = fileChooser.showOpenDialog(null);
            if(chooseOption == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();
                String filePath = file.getPath();
                displayFileName = FileName(filePath);
                frame.setTitle(displayFileName);
                System.out.println(filePath+"-NotePad");
                try{
                    FileReader fileReader = new FileReader(filePath);
                    BufferedReader bufferedReader  = new BufferedReader(fileReader);
                    String intermediate ="", output ="";
                    while((intermediate = bufferedReader.readLine()) != null){
                        output += intermediate +"\n";
                    }
                    textArea.setText(output);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        // save file
        if(e.getSource() == saveFile){
            fileChooser.setDialogTitle("Save");
            fileChooser.setSelectedFile(new File(displayFileName));
            int chooseOption = fileChooser.showSaveDialog(null);
            if(chooseOption == 0){
//                frame.setTitle(displayFileName);
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath()+".txt");
                try{
                    FileWriter fileWriter = new FileWriter(file);

                    BufferedWriter bw =new BufferedWriter(fileWriter);
                    textArea.write(bw);
                    bw.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

        }
        if(e.getSource() == newFile){
            TextEditor textEditor = new TextEditor();
        }
    }

    public String FileName(String filePath){
        String[] dir = filePath.split("\\\\");
        String res = dir[dir.length -1].substring(0,dir[dir.length -1].length()-4);
        return  res;
    }
}
