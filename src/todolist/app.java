package todolist;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;

public class app extends JFrame{
    private JPanel panel1;
    static ArrayList<String[]> data = new ArrayList<String[]>();

    public static void main(String args[]) throws IOException {

            try {
                File dataR = new File("data.txt");
                Scanner sc = new Scanner(dataR, "UTF-8");
                while(sc.hasNextLine()){
                    String[]temp = new String[2];
                    String line = sc.nextLine();
                    temp[0] = line.split("~~~")[0];
                    temp[1] = "";
                    String[] temp2 = line.split("~~~")[1].split("```");
                    for(int i = 0;i < temp2.length; i++){
                        temp[1] += temp2[i] + "\n";
                    }
                    data.add(temp);



                }
                sc.close();
            }
            catch(Exception e){

            }







        app form = new app();
        form.setVisible(true);




    }

    public app(){
        setBackground(Color.decode("#ecf0f1"));
        add(panel1);
        panel1.setBorder(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                try {
                    exitProcedure();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        setTitle("To-Do List");
        setSize(350,500);
        JScrollPane scrollPane = new JScrollPane(panel1);
        add(scrollPane);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));

        for(int i =0; i<data.size();i++){
            panel1.add(getNewRow(i, data.get(i)[0], data.get(i)[1]));
        }
        JPanel options = new JPanel();
        options.setLayout(new BorderLayout());
        Button add = new Button("Add");
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String [] empty = {"New Course", "\n"};
                data.add(empty);
                panel1.add(getNewRow(data.size()-1, "New Course", ""));
                panel1.updateUI();
            }
        });
        options.add(BorderLayout.PAGE_END,add);
        add(options, BorderLayout.PAGE_END);

    }

    public void exitProcedure() throws IOException {
        String line = "";
        for(int i = 0; i < data.size(); i++){
            line += data.get(i)[0] + "~~~" + data.get(i)[1].replaceAll("\\n", "```") + "```\n";
        }
        try{
        PrintWriter dataW = new PrintWriter("data.txt");
        dataW.print(line);
        dataW.flush();
        dataW.close();
        dispose();
        System.exit(0);
        }
        catch(Exception e){

        }
    }

    public JPanel getNewRow(int index, String course, String content){
        JPanel pnl = new JPanel();
        pnl.setLayout(new BorderLayout());
        JTextField label = new JTextField(course);
        label.setBorder(null);
        label.setBackground(null);
        label.setHorizontalAlignment(JLabel.CENTER);
        pnl.add(BorderLayout.NORTH,label);
        JTextArea text = new JTextArea(content);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setSize(250,250);
        pnl.add(text);
        text.setBackground(Color.decode("#ecf0f1"));
        if(text.getText().length() > 0){
            label.setBackground(Color.decode("#c0392b"));
        }
        else{
            label.setBackground(Color.decode("#2ecc71"));
        }
        text.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent arg0) {
                update();
            }

            public void update(){
                String course = label.getText();
                String content = text.getText();
                data.get(index)[0] = course;
                data.get(index)[1] = content + "\n";
                if(text.getText().length() > 0){
                    label.setBackground(Color.decode("#c0392b"));
                }
                else{
                    label.setBackground(Color.decode("#2ecc71"));
                }
                panel1.updateUI();
            }

        });
        label.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent arg0) {
                update();
            }

            public void update(){
                if(label.getText().equals("DELETE")){
                    data.remove(index);
                    panel1.remove(index);
                    panel1.updateUI();
                }
                String course = label.getText();
                String content = text.getText();
                data.get(index)[0] = course;
                data.get(index)[1] = content + "\n";
                if(text.getText().length() > 0){
                    label.setBackground(Color.decode("#c0392b"));
                }
                else{
                    label.setBackground(Color.decode("#2ecc71"));
                }
                panel1.updateUI();
            }

        });


        return pnl;
    }

}


