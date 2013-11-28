package browser;

/**
 * Developed by North People for North People
 * All you have to do is run our super browser and type : http://localhost:8000/test1.html or test.html or test2.html or you can eneter any web site oyu want
 * but it will be weird :)
 * @author devino
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class Browser {
    private JFrame frame;
    private JPanel panelTop;
    private JEditorPane editor;
    private JScrollPane scroll;
    private JTextField field;
    private JButton button;
    private JButton back;
    private JButton next;
    private URL url;
    List BrowserHistory;
    int currentPage=0;
    int lastpage_number=0;

    public Browser(String title) {
        initComponents();

        //set the title of the frame
        frame.setTitle(title);

        //set the default cloe op of the jframe
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //set size of frame
        frame.setSize(800,600);

        //add jpanel to north of jframe
        frame.add(BorderLayout.NORTH, panelTop);

        //add textfield and navigation button to jpanel.
        panelTop.add(field);
        panelTop.add(button);
        panelTop.add(back);
        panelTop.add(next);

        //add scroll pane to jframe center
        frame.add(BorderLayout.CENTER, scroll);

        
        //set the frame visible
        frame.setVisible(true);
    }//end Browser() constructor

    private void initComponents() {
        //create the JFrame
        frame = new JFrame();
        BrowserHistory=new List();
        //create the JPanel used to hold the text field and button.
        panelTop = new JPanel();
        
        //set the url
        try {
            url = new URL("http://www.javagaming.org");
        }
        catch(MalformedURLException mue) {
            JOptionPane.showMessageDialog(null,mue);
        }
        
        //create the JEditorPane
        try {
            editor = new JEditorPane(url);
            
            //set the editor pane to false.
            editor.setEditable(false);
            editor.addHyperlinkListener(new HyperlinkListener() {
    public void hyperlinkUpdate(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
                       try {
                        editor.setPage(e.getURL());
                    }
                    catch(IOException ioe) {
                        JOptionPane.showMessageDialog(null,ioe);
                    }
            }//end hyperlinkUpdate()
        });//end HyperlinkListener
        }
        catch(IOException ioe) {
            JOptionPane.showMessageDialog(null,ioe);
        }
        
        //create the scroll pane and add the JEditorPane to it.
        scroll = new JScrollPane(editor, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //create the JTextField
        field = new JTextField();

        //set the JTextField text to the url.
        //we're not doing this on the event dispatch thread, so we need to use
        //SwingUtilities.
        SwingUtilities.invokeLater(new Runnable() {
           public void run() {
               field.setText(url.toString());
               BrowserHistory.add(url.toString());
               lastpage_number++;
               currentPage++;
           }
        });

        //create the button for chanign pages.
        button = new JButton("Go");
        
        //add action listener to the button
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String s=field.getText();
                    System.out.println(s);
                    editor.setPage(field.getText());
                    BrowserHistory.add(s);
                    lastpage_number++;
                    currentPage=lastpage_number;
                }
                catch(IOException ioe) {
                    JOptionPane.showMessageDialog(null, ioe);
                }
            }
        });
        back =new JButton("Back");
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if(currentPage>1)
                    {
                        System.out.println(currentPage);
                        System.out.println(BrowserHistory.getItem(currentPage-2));
                        editor.setPage(BrowserHistory.getItem(currentPage-2));
                        currentPage=currentPage-1;
                        field.setText(BrowserHistory.getItem(currentPage-1));
                    }
                }
                catch(IOException ioe) {
                    JOptionPane.showMessageDialog(null, ioe);
                }
            }
        });
        next=new JButton("next");
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if(currentPage<lastpage_number)
                    {
                        System.out.println(currentPage);
                        System.out.println(BrowserHistory.getItem(currentPage));
                        editor.setPage(BrowserHistory.getItem(currentPage));
                        currentPage=currentPage+1;
                        field.setText(BrowserHistory.getItem(currentPage-1));
                    }
                }
                catch(IOException ioe) {
                    JOptionPane.showMessageDialog(null, ioe);
                }
            }
        });
    }//end initComponents()

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                
                new Browser("Simple web browser");
            }
        });
    }//end main method.
}//end Browser class