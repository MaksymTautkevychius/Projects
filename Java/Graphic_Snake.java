import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class S26871Project04 {

    public static class BinaryWriting {
        private final String filename = "datas.bin";

        public void Encode(String name, int value) throws IOException {
            ArrayList<String> USRS = Decode();
            USRS.add(name + " - " + value);
            sort(USRS);

            try (FileOutputStream writer = new FileOutputStream(filename, false)) {
                for (String USRSCR : USRS) {
                    String[] A = USRSCR.split(" - ");
                    writer.write(A[0].length());
                    for (char c : A[0].toCharArray()) {
                        writeCharToBytes(c, writer);
                    }
                    WriteInt(writer, Integer.parseInt(A[1]));
                }
            }
        }

        public ArrayList<String> Decode() throws IOException {
            ArrayList<String> USRS = new ArrayList<>();
            try (FileInputStream reader = new FileInputStream(filename)) {
                while (reader.available() > 0) {
                    int lengthOfUser = reader.read();
                    StringBuilder name = new StringBuilder();
                    for (int i = 0; i < lengthOfUser; i++) {
                        name.append(readCharFromBytes(reader));
                    }
                    int score = readInt(reader);
                    USRS.add(name.toString() + " - " + score);
                }
            }
            sort(USRS);
            return USRS;
        }

        public void sort(ArrayList<String> scores) {
            Collections.sort(scores, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    String[] split1 = o1.split(" - ");
                    String[] split2 = o2.split(" - ");
                    int a = Integer.parseInt(split1[1]);
                    int b = Integer.parseInt(split2[1]);

                    return Integer.compare(b, a);
                }
            });

            while (scores.size() > 10) {
                scores.remove(scores.size() - 1);
            }
        }

        public void writeCharToBytes(char ch, FileOutputStream writer) throws IOException {
            int val = (int) ch;
            byte first = (byte) ((val >> 8) & 0xFF);
            byte second = (byte) (val & 0xFF);
            writer.write(first);
            writer.write(second);
        }

        public char readCharFromBytes(FileInputStream in) throws IOException {
            int fisrt = in.read() << 8;
            int second = in.read();
            return (char) (fisrt | second);
        }

        public void WriteInt(FileOutputStream writer, int value) throws IOException {
            writer.write((value >> 24) & 0xFF);
            writer.write((value >> 16) & 0xFF);
            writer.write((value >> 8) & 0xFF);
            writer.write(value & 0xFF);
        }

        public int readInt(FileInputStream reader) throws IOException {
            return ((reader.read() << 24) + (reader.read() << 16) + (reader.read() << 8) + reader.read());
        }


    }

    public static class Event {
        private String name;

        public Event(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static interface EventListener {
        void onGameEvent(Event event);
    }

    public static class Frame extends JFrame implements Runnable, KeyListener, GraphicControl, ActionListener, PropertyChangeListener {
        ArrayList<String>  scoreArr;
        public JPanel gameOverPanel;
        public LogicControl s;
        public  SnakeGame stb;
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 0));
        int Level;
        Frame f = this;
        String namer;
        private JLabel scoreLabel;
        String score;
        ScorePanelGame panel = new ScorePanelGame();

        public void run() {
            this.pack();
            this.setLocationRelativeTo(null);
            JLabel Snake = new JLabel("S26871Projekt04.Snake Game");
            Snake.setPreferredSize(new Dimension(200, 100));
            Snake.setHorizontalAlignment(SwingConstants.CENTER);
            Snake.setBackground(Color.green);
            Snake.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 30));
            this.add(Snake, BorderLayout.NORTH);
            buttonPanel.setPreferredSize(new Dimension(200, 300));
            Dimension buttonSize = new Dimension(100, 50);

            JButton play = new JButton("Play");
            play.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
            play.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent eh) {
                    namer = JOptionPane.showInputDialog(f, "May I ask your name sir?");
                    buttonPanel.setVisible(false);
                    s.init(Frame.this);
                    stb = new SnakeGame(s);
                    add(stb);
                    pack();
                    setVisible(true);
                    requestFocus();
                    f.setSize(500, 700);
                    score=String.valueOf(s.getScore());
                    panel.getNewVal(score);
                    f.add(panel,BorderLayout.SOUTH);
                    panel.setVisible(true);
                    s.StartLogic();
                }
            });
            play.setBackground(Color.LIGHT_GRAY);
            play.setPreferredSize(buttonSize);

            JButton score = new JButton("Score");
            score.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        scoreArr=s.scores();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    JPanel scores = new JPanel(new BorderLayout());
                    buttonPanel.setVisible(false);
                    f.add(scores);
                    ScorePanel scoresPanel = new ScorePanel(scoreArr);
                    scoresPanel.setPreferredSize(new Dimension(200,200));
                    f.add(scoresPanel);
                    f.pack();
                    f.setVisible(true);
                    JButton back = new JButton("BACK");
                    back.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
                    back.setBackground(Color.GRAY);
                    f.add(back,BorderLayout.SOUTH);
                    back.setVisible(true);
                    back.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            scoresPanel.setVisible(false);
                            back.setVisible(false);
                            buttonPanel.setVisible(true);
                            f.setSize(640,480);
                        }
                    });
                }
            });
            score.setBackground(Color.gray);
            score.setPreferredSize(buttonSize);
            score.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
            JButton exit = new JButton("Exit");
            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            exit.setBackground(Color.DARK_GRAY);
            exit.setPreferredSize(buttonSize);
            exit.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
            buttonPanel.add(play);
            buttonPanel.add(score);
            buttonPanel.add(exit);
            this.add(buttonPanel);
            this.setSize(640, 480);
            this.setVisible(true);
            buttonPanel.setBackground(Color.LIGHT_GRAY);

            s.addListener(this);
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    Level = 1;
                    break;
                case KeyEvent.VK_DOWN:
                    Level = 2;
                    break;
                case KeyEvent.VK_LEFT:
                    Level = 3;
                    break;
                case KeyEvent.VK_RIGHT:
                    Level = 4;
                    break;
            }
        }

        public void init(LogicControl s) {
            this.s = s;
            this.addKeyListener(this);
            this.setFocusable(true);
        }

        @Override
        public int onLevelChange() {
            return Level;
        }

        @Override
        public String nameOfParticipant() {
            return namer;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
        }

        @Override
        public void propertyChange(PropertyChangeEvent a) {
            if ("score".equals(a.getPropertyName())) {
                int newScore = (int) a.getNewValue();
                panel.getNewVal(String.valueOf(newScore));
            } else if (a.getNewValue() instanceof GameOverEvent) {
                stb.setVisible(false);
                try {
                    scoreArr=s.scores();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                JPanel scores = new JPanel(new BorderLayout());
                buttonPanel.setVisible(false);
                f.add(scores);
                ScorePanel scoresPanel = new ScorePanel(scoreArr);
                scoresPanel.setPreferredSize(new Dimension(200,200));
                f.add(scoresPanel);
                f.pack();
                f.setVisible(true);
                JButton back = new JButton("Exit");
                back.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
                back.setBackground(Color.GRAY);
                f.add(back,BorderLayout.SOUTH);
                back.setVisible(true);
                back.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                       System.exit(0);
                    }
                });
            }
            }
            public void GetNewVal(String val)
            {
                panel.getNewVal(val);
            }
    }

    public static class GameOverEvent extends Event {
        public GameOverEvent() {
            super("GameOver");
        }
    }

    public static interface GraphicControl {
        int onLevelChange();
        String nameOfParticipant();
    }

    public static interface LogicControl {
        int[][] GetBoard();
        int[][] GetOldBoard();
        void StartLogic();
        void init(GraphicControl i);

        void initModel(Model m);
        String getScore();
        void addListener(PropertyChangeListener listener);
        ArrayList<String> scores() throws IOException;
        void SetGameOver(boolean gameOver);

        boolean CellChanged(int x, int y);
        }

    public static class Main implements Runnable  {
        Snake game = new Snake();
        Frame g = new Frame();

        public static void main(String[] args) {
            new Thread(new Main()).start();
        }
        @Override
        public void run() {
            g.init(game);
            game.init(g);
            new Thread(g).start();
        }
    }

    public static class Model extends AbstractTableModel {

        LogicControl snake;
        public Model(LogicControl snake)
        {
            this.snake=snake;
        }

        @Override
        public int getRowCount() {
            return snake.GetBoard().length;
        }

        @Override
        public int getColumnCount() {
            return snake.GetBoard()[0].length;
        }

        @Override
        public Object getValueAt(int x, int y) {
            return snake.GetBoard()[x][y];
        }

        @Override
        public void setValueAt(Object aValue, int x, int y) {
            snake.GetBoard()[y][x] = (int) aValue;
            fireTableCellUpdated(x, y);
        }

        @Override
        public void fireTableCellUpdated(int x, int y) {
            super.fireTableCellUpdated(x, y);
        }
    }

    public static class Point {
        private int x;
       private int y;
        public Point(int x, int y)
        {
            this.x=x;
            this.y=y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public static class Render extends DefaultTableCellRenderer {

        ImageIcon head = new ImageIcon("pngegg.png");
        ImageIcon tail = new ImageIcon("tail.png");
        ImageIcon apple = new ImageIcon("apple.png");

        @Override
        public Component getTableCellRendererComponent(JTable gametable, Object val, boolean S, boolean F, int width, int height) {
            JLabel cell  = (JLabel) super.getTableCellRendererComponent(gametable,"",S,F,width,height);
            cell.setBackground(getColor((int) val));
            cell.setIcon(head);
            cell.setHorizontalAlignment(SwingConstants.CENTER);
            cell.setIcon(getImage(val,cell));
            return cell;
        }

        public Color getColor(Object val) {
            return switch ((int)val) {
                case 0 -> Color.BLACK;
                case 1 -> Color.black;
                case 2 -> Color.black;
                case -1 -> Color.black;
                default -> throw new IllegalStateException("not in range"  + val);
            };
        }

        public ImageIcon getImage(Object val,JLabel cell)
        {
            return switch ((int)val) {
                case 1 -> head;
                case 2 -> tail;
                case -1 -> apple;
                default -> null;
            };
        }
    }

    static class ScorePanel extends JPanel {
        private final ArrayList<String> scoreArr;

        public ScorePanel(ArrayList<String> scoreArr) {
            this.scoreArr = scoreArr;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int y = 20;
            g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));

            if (scoreArr != null) {
                for (String score : scoreArr) {
                    g.drawString(score, 10, y);
                    y += 15;
                }
            }
        }
    }

    public static class ScorePanelGame extends JPanel {
        String score;
        public ScorePanelGame()
        {
            this.setSize(200,100);
            this.setAlignmentY(300);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Font font = new Font(Font.MONOSPACED, Font.BOLD, 20);
            g.setFont(font);
            FontMetrics fm = g.getFontMetrics();
            int Sx = fm.stringWidth(score);
            int sd = fm.getAscent();
            int sa = fm.getDescent();
            int Sy = sd + sa;
            int x = (getWidth() - Sy) / 2;
            int y = (getHeight() - Sy) / 2 + sd;
            g.drawString(score, x, y);
        }
        public void getNewVal(String val)
        {
            score= val;
            repaint();
        }
    }

    public static class ScoreUpdateEvent extends Event {
        private int newScore;

        public ScoreUpdateEvent(String name, int newScore) {
            super(name);
            this.newScore = newScore;
        }

        public int getNewScore() {
            return newScore;
        }
    }

    public static class Snake implements Runnable,SnakeMovements, LogicControl, EventListener {

        Model m;
        int[][] Board = new int[25][16];
        int[][] OldBoard = new int[25][16];
        int mils=300;
        BinaryWriting bw = new BinaryWriting();
        String name;
        public List<EventListener> eventListeners;
        public int score;
        boolean gameover = false;
        public boolean FirstStep = true;
        ArrayList<Point> Segments = new ArrayList<>();
        public PropertyChangeSupport support = new PropertyChangeSupport(this);

        public GraphicControl gc;


        public Snake() {
            eventListeners = new ArrayList<>();
        }

        private void makeTrueListeners(Event event) {
            for (EventListener listener : eventListeners) {
                listener.onGameEvent(event);
            }
        }

        @Override
        public void init(GraphicControl gc) {
            this.gc = gc;
        }
        @Override
        public void initModel(Model m)
        {
            this.m=m;
        }

        @Override
        public String getScore() {
            return String.valueOf(score);
        }

        @Override
        public void addListener(PropertyChangeListener listener) {
            support.addPropertyChangeListener(listener);
        }

        @Override
        public ArrayList<String> scores() throws IOException {
            return bw.Decode();
        }

        @Override
        public void SetGameOver(boolean gameOver) {
            boolean oldGameOver = this.gameover;
            this.gameover = gameOver;
            support.firePropertyChange("gameOver", oldGameOver, gameOver);
            System.out.println("is game over");
        }

        @Override
        public boolean CellChanged(int x, int y) {
            if (x >= 0 && x < OldBoard[0].length && y >= 0 && y < OldBoard.length) {
                int currentCellValue = (int)m.getValueAt(x,y);
                if (currentCellValue != OldBoard[y][x]) {
                    OldBoard[y][x] = currentCellValue;
                    return true;
                }
            }
            return false;
        }


        @Override
        public void run() {
            while (!gameover) {
                if (FirstStep) {
                    System.out.println("Logic");
                    this.FillBoard();
                    this.FirstFill();
                    AddApple();
                    FirstStep=false;
                }

                Level=gc.onLevelChange();
                switch (Level)
                {
                    case 1:
                        try {
                            MoveUp();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 2:
                        try {
                            MoveDown();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 3:
                        try {
                            MoveLeft();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 4:
                        try {
                            MoveRight();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                }
                try {
                    Thread.sleep(mils-score*5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        public void setLocationHead(int x, int y)
        {
            Board[y][x]=1;
        }
        public void setLocationTail(int x, int y)
        {
            Board[y][x]=2;
        }
        public void FillBoard()
        {for (int i=0;i<this.Board.length;i++) {for (int j=0; j<Board[i].length;j++) {Board[i][j]=0;}}}
        public void FirstFill()
        {
            Point head = new Point(1,10);
            Segments.add(head);
        }
        public void MoveDown() throws IOException {
            int fiX = Segments.get(0).getX();
            int fiY = Segments.get(0).getY();
            if (fiY + 1 >= Board.length || TailCollieds(fiX, fiY + 1)) {
                SetGameOver(gameover);
                OnGameOver();
                return;
            }
            if (Board[fiY + 1][fiX] == 0) {
                setLocationHead(fiX, fiY + 1);
                Segments.get(0).setX(fiX);
                Segments.get(0).setY(fiY + 1);
                for (int i = 1; i < Segments.size(); i++) {
                    int newX = Segments.get(i).getX();
                    int newY = Segments.get(i).getY();
                    setLocationTail(fiX, fiY);
                    Segments.get(i).setX(fiX);
                    Segments.get(i).setY(fiY);
                    fiX = newX;
                    fiY = newY;
                }
                Board[fiY][fiX] = 0;
            }
            else if (Board[fiY + 1][fiX] == -1) {
                updateScore(1);
                AddSegment();
                AddApple();
                setLocationHead(fiX, fiY + 1);
                Segments.get(0).setX(fiX);
                Segments.get(0).setY(fiY + 1);

                for (int i = 1; i < Segments.size(); i++) {
                    int newX = Segments.get(i).getX();
                    int newY = Segments.get(i).getY();

                    setLocationTail(fiX, fiY);
                    Segments.get(i).setX(fiX);
                    Segments.get(i).setY(fiY);

                    fiX = newX;
                    fiY = newY;
                }

                Board[fiY][fiX] = 0;
            }
                else System.out.println("cant move down");

        }

        @Override
        public void MoveUp() throws IOException {
            int fiX = Segments.get(0).getX();
            int fiY = Segments.get(0).getY();
            if (fiY - 1 < 0 || TailCollieds(fiX, fiY - 1)) {
                OnGameOver();
                return;
            }
            if (fiY - 1 >= 0 && Board[fiY - 1][fiX] == 0) {
                setLocationHead(fiX, fiY - 1);
                Segments.get(0).setX(fiX);
                Segments.get(0).setY(fiY - 1);
                for (int i = 1; i < Segments.size(); i++) {
                    int newX = Segments.get(i).getX();
                    int newY = Segments.get(i).getY();
                    setLocationTail(fiX, fiY);
                    Segments.get(i).setX(fiX);
                    Segments.get(i).setY(fiY);
                    fiX = newX;
                    fiY = newY;
                }
                Board[fiY][fiX] = 0;
            }
            else if (Board[fiY - 1][fiX] == -1) {
                updateScore(1);
                AddSegment();
                AddApple();
                setLocationHead(fiX, fiY - 1);
                Segments.get(0).setX(fiX);
                Segments.get(0).setY(fiY - 1);
                for (int i = 1; i < Segments.size(); i++) {
                    int newX = Segments.get(i).getX();
                    int newY = Segments.get(i).getY();
                    setLocationTail(fiX, fiY);
                    Segments.get(i).setX(fiX);
                    Segments.get(i).setY(fiY);
                    fiX = newX;
                    fiY = newY;
                }
                Board[fiY][fiX] = 0;
            }
            else {System.out.println("cant move up");}
            if (Segments.get(0).getX()>15 || Segments.get(0).getY()>25) System.exit(1);
        }

        @Override
        public void MoveLeft() throws IOException {
            int fiX = Segments.get(0).getX();
            int fiY = Segments.get(0).getY();
            if (fiX - 1 < 0 || TailCollieds(fiX - 1, fiY)) {
                OnGameOver();
                return;
            }
            if (fiX - 1 >= 0 && Board[fiY][fiX - 1] == 0) {
                setLocationHead(fiX - 1, fiY);
                Segments.get(0).setX(fiX - 1);
                Segments.get(0).setY(fiY);
                for (int i = 1; i < Segments.size(); i++) {
                    int newX = Segments.get(i).getX();
                    int newY = Segments.get(i).getY();
                    setLocationTail(fiX, fiY);
                    Segments.get(i).setX(fiX);
                    Segments.get(i).setY(fiY);
                    fiX = newX;
                    fiY = newY;
                }
                Board[fiY][fiX] = 0;
            }
            else if (Board[fiY][fiX - 1] == -1) {
                updateScore(1);
                AddSegment();
                AddApple();
                setLocationHead(fiX - 1, fiY);
                Segments.get(0).setX(fiX - 1);
                Segments.get(0).setY(fiY);
                for (int i = 1; i < Segments.size(); i++) {
                    int newX = Segments.get(i).getX();
                    int newY = Segments.get(i).getY();
                    setLocationTail(fiX, fiY);
                    Segments.get(i).setX(fiX);
                    Segments.get(i).setY(fiY);
                    fiX = newX;
                    fiY = newY;
                }
                Board[fiY][fiX] = 0;
            }
            else System.out.println("cant move left");

        }
        @Override
        public void MoveRight() throws IOException {
            int fiX = Segments.get(0).getX();
            int fiY = Segments.get(0).getY();
            if (fiX + 1 >= Board[0].length || TailCollieds(fiX + 1, fiY)) {
                OnGameOver();
                return;
            }
            if (fiX + 1 < Board[0].length && Board[fiY][fiX + 1] == 0) {
                setLocationHead(fiX + 1, fiY);
                Segments.get(0).setX(fiX + 1);
                Segments.get(0).setY(fiY);
                for (int i = 1; i < Segments.size(); i++) {
                    int newX = Segments.get(i).getX();
                    int newY = Segments.get(i).getY();
                    setLocationTail(fiX, fiY);
                    Segments.get(i).setX(fiX);
                    Segments.get(i).setY(fiY);
                    fiX = newX;
                    fiY = newY;
                }
                Board[fiY][fiX] = 0;
            } else if (Board[fiY][fiX + 1] == -1) {
                updateScore(1);
                AddSegment();
                AddApple();
                setLocationHead(fiX + 1, fiY);
                Segments.get(0).setX(fiX + 1);
                Segments.get(0).setY(fiY);
                for (int i = 1; i < Segments.size(); i++) {
                    int newX = Segments.get(i).getX();
                    int newY = Segments.get(i).getY();
                    setLocationTail(fiX, fiY);
                    Segments.get(i).setX(fiX);
                    Segments.get(i).setY(fiY);
                    fiX = newX;
                    fiY = newY;
                    Event score = new Event("EventScore");
                    makeTrueListeners(score);
                }
                Board[fiY][fiX] = 0;
            }
            else System.out.println("cant move right");

        }


        @Override
        public void AddApple()
        {
            Point apple;
            int[] cords = Random_Coords();
            apple = new Point(cords[1],cords[0]);
            boolean check=false;
            while (!check)
            {
                cords = Random_Coords();
                apple = new Point(cords[1],cords[0]);
                if (Board[apple.getY()][apple.getX()]==0) check=true;
            }
            Board[apple.getY()][apple.getX()]=-1;
        }
        @Override
        public void AddSegment() {
            int x = Segments.get(Segments.size() - 1).getX();
            int y = Segments.get(Segments.size() - 1).getY();
            int newX = x;
            int newY = y;
            int secondX = Segments.get(Segments.size() - 1).getX();
            int secondY = Segments.get(Segments.size() - 1).getY();
            if (x == secondX) {
                newY = y + (y - secondY);
            } else if (y == secondY) {
                newX = x + (x - secondX);
            }
            Point newSegment = new Point(newX, newY);
            Segments.add(newSegment);
            Board[newY][newX] = 2;
        }
        public int[] Random_Coords()
        {//0 - y , 1 - x
            int max=15;
            int min=0;
            int range = (max - min) + 1;
            int[] cords = new int[2];
            cords[0] = (int)(Math.random() * range) + min;
            max=15;
            range = (max - min) + 1;
            cords[1] = (int)(Math.random() * range) + min;
            return cords;
        }

        @Override
        public int[][] GetBoard() {
            return Board;
        }

        @Override
        public int[][] GetOldBoard() {
            return OldBoard;
        }


        @Override
        public void StartLogic() {
            new Thread(this).start();
        }

        public boolean TailCollieds(int x, int y) {
            for (int i = 1; i < Segments.size(); i++) {
                if (Segments.get(i).getX() == x && Segments.get(i).getY() == y) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public void onGameEvent(Event event) {
            if (event instanceof ScoreUpdateEvent) {
                ScoreUpdateEvent scoreUpdateEvent = (ScoreUpdateEvent) event;
                System.out.println("Score updates to" + scoreUpdateEvent.getNewScore());
            }
        }
        public void updateScore(int increment) {
            int oldScore = score;
            score += increment;
            support.firePropertyChange("score", oldScore, score);
        }

        public void OnGameOver() throws IOException {
            gameover = true;
            name = gc.nameOfParticipant();
            bw.Encode(name, score);
            GameOverEvent event = new GameOverEvent();
            for (EventListener listener : eventListeners) {
                listener.onGameEvent(event);
            }
            support.firePropertyChange("GameOver", false, event);
        }
        int Level = 1;
    }

    public static class SnakeGame extends JTable {
        public LogicControl snake;
        Model m;

        public SnakeGame(LogicControl snake) {
            super();
            this.snake = snake;
            this.m = new Model(snake);
            snake.initModel(m);
            this.setModel(m);
            this.setDefaultRenderer(Object.class, new Render());
            this.setRowHeight(25);
            this.setShowGrid(true);
            this.setGridColor(Color.BLACK);
            gen();
        }

        private void gen() {
            Thread gameLogicThread = new Thread(() -> {
                while (true) {
                    EventQueue.invokeLater(this::updator);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            gameLogicThread.start();
        }

        public void updator() {
            Rectangle ref = null;
            for (int i = 0; i < snake.GetBoard().length; i++) {
                for (int j = 0; j < snake.GetBoard()[i].length; j++) {
                    if (snake.CellChanged(i,j)) {
                        Rectangle rect = getCellRect(i, j, false);
                        ref = (ref == null) ? rect : ref.union(rect);
                    }
                }
            }
            if (ref != null) {
                repaint(ref);
            }
        }
    }

    public static interface SnakeMovements {
        void MoveDown() throws IOException;
        void MoveUp() throws IOException;
        void MoveLeft() throws IOException;
        void MoveRight() throws IOException;
        void AddApple();
        void AddSegment();

        int[] Random_Coords();
    }
}
