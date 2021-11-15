import javax.swing.*;
import java.awt.Image;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.Math;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Puzzle extends JPanel {
  public int[] arrayTable;
  public int zeroPos;
  public int counter = 0;
  public int counterLimitNum;
  public String counterLimit = "50";
  public Image bdImage;


  public Puzzle() {
    String img = "/image/background.jpg";
    ImageIcon i = new ImageIcon(this.getClass().getResource(img));
    bdImage = i.getImage();
    setFocusable(true);
    JOptionPane.showMessageDialog(null, "Dùng các phím mũi tên sắp xếp theo thứ tự tăng dần để chiền thắng!","Chào mừng!", 1 );
    arrayTable = CreatArrayTable();
    counterLimitNum = inputLimitNum();
    Sound("start");
    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        zeroPos = ZeroPos(arrayTable);
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
          arrayTable = CreatArrayTable();
          counter = 0 ;
          counterLimitNum = inputLimitNum();
          Sound("start");
        }
        switch (e.getKeyCode()) {
          case KeyEvent.VK_LEFT:
            for (int i = 0; i < arrayTable.length; i++) {
              if (i == 2 || i == 5 || i == 8){
                continue;
              }else if(arrayTable[i] == 0){
                Sound("move");
                left(arrayTable);
                counter++;
                break;
              }
            }
            break;
          case KeyEvent.VK_RIGHT:
          for (int i = 0; i < arrayTable.length; i++) {
            if (i == 0 || i == 3 || i == 6){
              continue;
            }else if(arrayTable[i] == 0){
              Sound("move");
              right(arrayTable);
              counter++;
              break;
            }
          }
            break;
          case KeyEvent.VK_DOWN:
          for (int i = 0; i < arrayTable.length; i++) {
            if (i == 0 || i == 1 || i == 2){
              continue;
            }else if(arrayTable[i] == 0){
              Sound("move");
              down(arrayTable);
              counter++;
              break;
            }
          }
            break;
          case KeyEvent.VK_UP:
          for (int i = 0; i < arrayTable.length; i++) {
            if (i == 6 || i == 7 || i == 8){
              continue;
            }else if(arrayTable[i] == 0){
              Sound("move");
              up(arrayTable);
              counter++;
              break;
            }
          }
            break;
        }
        repaint();
        counter = CheckCounters(counter, counterLimitNum);
        arrayTable = CheckWin(arrayTable);
        repaint();
      }
    });
  }

  public int[] left(int[] arrayTable) {
    switch(zeroPos) {
      case 0:{
        swap(arrayTable,0,1);
        break;
      }
      case 1:{
        swap(arrayTable,1,2);
        break;
      }
      case 3:{
        swap(arrayTable,3,4);
        break;
      }
      case 4:{
        swap(arrayTable,4,5);
        break;
      }
      case 6:{
        swap(arrayTable,6,7);
        break;
      }
      case 7:{
        swap(arrayTable,7,8);
        break;
      }
    }
    return arrayTable;
  }

  public int[] right(int[] arrayTable) {
    switch(zeroPos) {
      case 1:{
        swap(arrayTable,0,1);
        break;
      }
      case 2:{
        swap(arrayTable,1,2);
        break;
      }
      case 4:{
        swap(arrayTable,3,4);
        break;
      }
      case 5:{
        swap(arrayTable,4,5);
        break;
      }
      case 7:{
        swap(arrayTable,6,7);
        break;
      }
      case 8:{
        swap(arrayTable,7,8);
        break;
      }
    }
    return arrayTable;
  }

  public int[] up(int[] arrayTable) {
    switch(zeroPos) {
      case 0:{
        swap(arrayTable,0,3);
        break;
      }
      case 1:{
        swap(arrayTable,1,4);
        break;
      }
      case 2:{
        swap(arrayTable,2,5);
        break;
      }
      case 3:{
        swap(arrayTable,3,6);
        break;
      }
      case 4:{
        swap(arrayTable,4,7);
        break;
      }
      case 5:{
        swap(arrayTable,5,8);
        break;
      }
    }
    return arrayTable;
  }

  public int[] down(int[] arrayTable) {
    switch(zeroPos) {
      case 3:{
        swap(arrayTable,0,3);
        break;
      }
      case 4:{
        swap(arrayTable,1,4);
        break;
      }
      case 5:{
        swap(arrayTable,2,5);
        break;
      }
      case 6:{
        swap(arrayTable,3,6);
        break;
      }
      case 7:{
        swap(arrayTable,4,7);
        break;
      }
      case 8:{
        swap(arrayTable,5,8);
        break;
      }
    }
    return arrayTable;
  }

  public int[] CheckWin(int [] arrayTable){
    for (int i = 1; i < arrayTable.length -1 ; i++){
      if( arrayTable[0] != 1 || arrayTable[i] < arrayTable[i - 1]){
        return arrayTable;
      }
    }
      Sound("win");
      JOptionPane.showMessageDialog(null, "Bạn đã chiến thắng","Chúc mừng!", 1 );
      JOptionPane.showMessageDialog(null, "Tiếp tục nhé!","Cố Lên!", 1 );
      counterLimitNum = inputLimitNum();
      counter = 0 ;
      arrayTable = CreatArrayTable();
      repaint();
      Sound("start");
      return arrayTable;
  }

  public int CheckCounters(int counter, int counterLimitNum){
    if (counter == counterLimitNum){
      Sound("loss");
      JOptionPane.showMessageDialog(null, "Bạn đã thua!","Rất tiết!", 1 );
      JOptionPane.showMessageDialog(null, "Làm lại nhé!","Cố Lên!", 1 );
      arrayTable = CreatArrayTable();
      counterLimitNum = inputLimitNum();
      Sound("start");
      repaint();
      return counter = 0 ;
    }
    return counter;
  }

  public static boolean isNumberic(String strNum) {
    if (strNum == null) {
        return false;
    }
    try {
        int d = Integer.parseInt(strNum);
    } catch (NumberFormatException nfe) {
        return false;
    }
    return true;
  }

  public int[] swap(int[] arrayTable, int a, int b) {
      int temp = arrayTable[a];
      arrayTable[a] = arrayTable[b];
      arrayTable[b] = temp;
      return arrayTable;
  }

  public int[] CreatArrayTable(){
    int[] arrayTable = new int[9];
    for (int i = 0 ; i <= 7 ; i++){
      arrayTable[i] = i+1;
    }
    arrayTable[8] = 0;
    zeroPos = 8;  
    down(arrayTable);
    zeroPos = ZeroPos(arrayTable);
    right(arrayTable);
    zeroPos = ZeroPos(arrayTable);
    int Temp = new Random().nextInt(20)+20;
    int TempSwap;
    for (int i = 0 ; i < Temp; i++){
      TempSwap = new Random().nextInt(4);
      switch (TempSwap){
        case 0:
          left(arrayTable);
        break;
        case 1:
          right(arrayTable);
        break;
        case 2:
          down(arrayTable);
        break;
        case 3:
          up(arrayTable);
        break;
      }
      zeroPos = ZeroPos(arrayTable);
    }
    return arrayTable;
  }

  public int ZeroPos ( int[] arrayTable ){ 
    for (int i = 0; i < arrayTable.length; i++){
        if(arrayTable[i]==0){
            return i;
        }
    }
    return 8;
  }

  @Override
  public void paint(Graphics g) {
    int n = 0;
    super.paint(g);
    {for (int k = 0; k < 10; k++){
      g.setColor(new Color(156+4*k, 121+4*k, 91+4*k));
      g.fillRect(30+k, 110+k ,330-2*k,330-2*k);
    }};
    //Background
    g.clearRect(40, 120 ,310,310);
    g.drawImage(bdImage, 40, 120, 310, 310, this);
    //Cac o so
    for (int j = 1; j <= 3; j++){
      for (int i = 0; i <= 2; i++){
        //Vẽ một ô số.
        if (arrayTable[n] != 0)
        {for (int k = 0; k < 8; k++){
          g.setColor(new Color(136+4*k, 101+4*k, 71+4*k));
          g.fillRect((i*100)+50+2*k,100*j+30+2*k,90 - 4*k, 90 - 4*k);
        }};
        //Vẽ giá trị lên ô số
        g.setColor(Color.black);
        g.setFont(new Font("TimesRoman", Font.BOLD, 30));
        if (arrayTable[n] != 0) g.drawString(String.valueOf(arrayTable[n]),(i*100)+87,(j*100)+30+55);
        n++;
      }
    } 
    g.setColor(Color.red);
    g.setFont(new Font("TimesRoman", Font.BOLD, 22));
    g.drawString("Bấm ESC để làm mới Game!",45,70);
    g.setColor(new Color( (int)Math.round(255*(1.0*counter/counterLimitNum)),0,0));
    g.setFont(new Font("TimesRoman", Font.BOLD, 14));
    g.drawString("Lượt di chuyển: "+String.valueOf(counter),15,30);
    g.setColor(Color.red);
    g.drawString("Giới hạn di chuyển: "+String.valueOf(counterLimitNum),215,30);

  }

  public int inputLimitNum() {
      do{
        counterLimit = JOptionPane.showInputDialog("Chọn giới hạn số lần di chuyển. Mặc định 50.",50);
        if (!isNumberic(counterLimit) || Integer.parseInt(counterLimit) <= 0) {
          JOptionPane.showMessageDialog(null, "Vui lòng giá trị thích hợp (chỉ nhập số lớn hơn 0) Mặc định 50.","Giá trị không hợp lệ!", 1 );
        }
    }while(!isNumberic(counterLimit) || Integer.parseInt(counterLimit) <= 0);
    return    counterLimitNum = Integer.parseInt(counterLimit);
  }

  public static synchronized void Sound(String name) {
    new Thread(new Runnable() {
      public void run() {
        try {
          Clip clip = AudioSystem.getClip();
          AudioInputStream inputStream = AudioSystem.getAudioInputStream(
            Puzzle.class.getResourceAsStream("./sound/"+name+".wav"));
          clip.open(inputStream);
          clip.start(); 
        } catch (Exception e) {
          System.err.println(e.getMessage());
        }
      }
    }).start();
  }
  public static void main(String[] args) {
    JFrame game = new JFrame();
    game.setTitle("Game Puzzle");
    game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    game.setSize(400, 500);
    game.setResizable(false);
    game.add(new Puzzle());
    game.setLocationRelativeTo(null);
    game.setVisible(true);
  }
}
