package chatting.application;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Client extends JFrame implements ActionListener
{

    JPanel p1;
    JTextField t1;
    JButton b1;
    static JPanel a1;
    static JFrame f1 = new JFrame();

    static Box vertical = Box.createVerticalBox();

    static Socket s;
    static DataOutputStream dout;
    static DataInputStream din;
    Timer t;
    boolean typing;

    Client(){
        f1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,   450,70);
        f1.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/chat/3.png"));
        Image i2 = i1.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);
        l1.setBounds(5,17,30,30);
        p1.add(l1);

        l1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                System.exit(0);
            }
        });


        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/chat/girl.png"));
        Image i5 = i4.getImage().getScaledInstance(60,60,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel l2 = new JLabel(i6);
        l2.setBounds(40,5,60,60);
        p1.add(l2);

        JLabel l3 = new JLabel("Alice");
        l3.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        l3.setForeground(Color.WHITE);
        l3.setBounds(110,15,100,18);
        p1.add(l3);

        JLabel l4 = new JLabel("Active Now");
        l4.setFont(new Font("SAN_SERIF",Font.PLAIN,12));
        l4.setForeground(Color.WHITE);
        l4.setBounds(110,35,100,20);
        p1.add(l4);

        t = new Timer(1, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                if(!typing){
                    l4.setText("Active Now");
                }
            }
        });

        t.setInitialDelay(1500);



        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/chat/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel l5 = new JLabel(i9);
        l5.setBounds(315,20,30,30);
        p1.add(l5);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/chat/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(30,35,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel l6 = new JLabel(i12);
        l6.setBounds(370,20,30,35);
        p1.add(l6);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/chat/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(13,25,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel l7 = new JLabel(i15);
        l7.setBounds(420,23,13,25);
        p1.add(l7);

        t1 = new JTextField();
        t1.setBounds(5,655,310,40);
        t1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f1.add(t1);

        t1.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                l4.setText("typing......");
                typing = true;
                t.stop();

            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                typing = false;
                if(!t.isRunning())
                {
                    t.start();
                }
            }
        });

        b1 = new JButton("Send");
        b1.setBounds(320,657,123,35);
        b1.setBackground(new Color(7,94,84));
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("SAN_SERIF",Font.PLAIN,15));
        b1.setOpaque(true);
        b1.addActionListener(this);
        b1.setBorderPainted(false);
        f1.add(b1);

        a1 = new JPanel();
        a1.setBounds(5,75,440,570);
        a1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f1.add(a1);

        f1.getContentPane().setBackground(Color.WHITE);
        f1.setLayout(null);
        f1.setSize(450,700);
        f1.setLocation(1100,200);

        f1.setUndecorated(true);
        f1.setVisible(true);
    }
    public static void main(String[] args){
        new Client().f1.setVisible(true);

        String msginput = "";
        try{
            s = new Socket("192.168.1.9",6001);
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            while(true)
            {
                msginput = din.readUTF();
                JPanel p2 = formatLabel(msginput);

                JPanel left = new JPanel(new BorderLayout());
                left.add(p2, BorderLayout.LINE_START);
                vertical.add(left);
                f1.validate();

            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
            try
            {
                String out = t1.getText();
                JPanel p2 = formatLabel(out);

                a1.setLayout(new BorderLayout());

                JPanel right = new JPanel(new BorderLayout());
                right.add(p2, BorderLayout.LINE_END);
                vertical.add(right);
                vertical.add(Box.createVerticalStrut(15));

                a1.add(vertical, BorderLayout.PAGE_START);
                dout.writeUTF(out);
                t1.setText("");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }

    public static JPanel formatLabel(String out)
    {
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));

        JLabel l1 = new JLabel("<html><p style = \"width : 150px\">" + out + "</p></html>");
        l1.setFont(new Font("Fahoma", Font.PLAIN, 16));
        l1.setBackground(new Color(255, 255, 200));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(15, 15, 15, 50));

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));

        p3.add(l1);
        p3.add(l2);
        return p3;
    }


}


