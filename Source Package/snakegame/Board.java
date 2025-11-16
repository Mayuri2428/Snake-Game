package snakegame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Board extends JPanel implements ActionListener {
    private int dots;

    private int apple_x;
    private int apple_y;

       private Image apple;
       private Image tail;
       private Image head;

    private final int All_Dots = 900;
    private final int Dot_Size = 10;
    private final int RANDOM_POSITION = 29;

    private final int x[] = new int[All_Dots];
    private final int y[] = new int[All_Dots];

    private boolean inGame = true;

    private boolean leftDirection = false;
     private boolean rightDirection = true;
      private boolean upDirection = false;
       private boolean downDirection = false;

    private Timer timer;


    Board(){
        addKeyListener(new TAdapter());


        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(300,300));
        setFocusable(true);

        loadImages();
    }

    public void startGame(){
        initGame();
    }
    private void loadImages(){
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/apple.png"));
        apple = i1.getImage();

        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/tail.png"));
        tail = i2.getImage();

        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/head.png"));
        head = i3.getImage();
    }

    public void initGame(){
        dots=3;
        for(int i=0;i<dots;i++){
            y[i]=50;
            x[i]=50 - Dot_Size*i ;
        }
        locateApple();

     timer = new Timer (140,this);
        timer.start();

    }
    public void locateApple(){

        int r= (int) (Math.random()*RANDOM_POSITION);
        apple_x= r * Dot_Size;

         r= (int) (Math.random()*RANDOM_POSITION);
        apple_y= r * Dot_Size;

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        if(inGame){
        g.drawImage(apple, apple_x, apple_y, this);

        for(int i=0; i<dots;i++){
            if(i==0){
                g.drawImage(head, x[i], y[i], this);
            } else{
                g.drawImage(tail, x[i], y[i], this);
            }
        }
        Toolkit.getDefaultToolkit().sync();
    } else{
        gameOver(g);
    }
    }

    public void gameOver(Graphics g){
        String message ="Game Over!";
        Font font = new Font("SAN SERIF", Font.BOLD, 14);
        FontMetrics metrices =  getFontMetrics(font);

        g.setColor(Color.RED);
        g.setFont(font);
        g.drawString(message,(300 - metrices.stringWidth(message))/2, 300/2);

    }
    
    public void move(){
        for(int i=dots;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        if(leftDirection){
            x[0] = x[0]-Dot_Size;
        }
         if(rightDirection){
            x[0] = x[0]+Dot_Size;
        }
         if(upDirection){
            y[0] = y[0]-Dot_Size;
        }
         if(downDirection){
            y[0] = y[0]+Dot_Size;
        }
    }

    public void checkApple(){
        if(x[0] == apple_x && y[0] == apple_y){
            dots++;
         locateApple();
        }
    }

    public void checkCollision(){
        for (int i = dots; i > 0; i--) {
            if((i>4) && (x[0] == x[i]) && (y[0] == y[i])){
                inGame = false;
            }
            if(y[0] >= 300){
                inGame = false;
            }
             if(x[0] >= 300){
                inGame = false;
            }
             if(y[0] < 0){
                inGame = false;
            }
             if(x[0] < 0){
                inGame = false;
            }
            if(!inGame){
                timer.stop();
            }
            
        }

    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(inGame){
        checkApple();
        checkCollision();
        move();
        }

        repaint();

    }

    public class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            int key= e.getKeyCode();

            if(key == KeyEvent.VK_LEFT && (!rightDirection)){
                leftDirection = true;
                  upDirection = false;
                   downDirection = false;
            }

             if(key == KeyEvent.VK_RIGHT && (!leftDirection)){
                rightDirection = true;
                  upDirection = false;
                   downDirection = false;
            }

             if(key == KeyEvent.VK_UP && (!downDirection)){
                upDirection = true;
                  rightDirection = false;
                   leftDirection = false;
            }

             if(key == KeyEvent.VK_DOWN && (!upDirection)){
                downDirection = true;
                  rightDirection = false;
                   leftDirection = false;
            }


        }
    }
}
