import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
//God bless
class S26871Project03 {
    public static class Mainer {
        static ArrayList<String> numbers = new ArrayList<>();
        String eg;

        int queueIndex=0;
        static int qu=0;

        public static void main(String[] args) throws InterruptedException {
            Main main = new Main();
            MainLogic ml = new MainLogic() {

                @Override
                public void StartTheWork() {
                    new Thread(main).start();
                }
                @Override
                public void addBSC() {
                    BSS a = new BSS(main, main.a);
                    new Thread(a);
                    main.BSSList.add(a);
                    System.out.println("added S26871Project03.BSC");
                }

                @Override
                public void stopBSC() {

                }

                @Override
                public String addNewQueues(String message,int Frequency) throws InterruptedException {
                    VBD senders = main.VBDList.get(Main.Rand(main.VBDList.size()-1));
                    VBD receivers = main.VBDList.get(Main.Rand(main.VBDList.size()-1));
                    String sender;
                    sender=senders.getAddress_Value();
                    sender+=receivers.getAddress_Value();
                    Mainer.numbers.add(sender);
                    main.queues.add(sender);
                    VBDsender a = new VBDsender(main, qu,message);
                    main.getQueues().put(qu, sender);
                    new Thread(a).start();
                    qu++;
                    StringBuilder sb = new StringBuilder(sender);
                    for (int i=0;i<6;i++)
                    {
                        sb.deleteCharAt(i);
                    }
                    System.out.println(sender);
                    return sender;
                }

                @Override
                public int getCounter() {
                    return main.getCounter();
                }

                @Override
                public void delete_BTS() {
                    int ind= main.BTSListSender.size()-1;
                    main.BTSListSender.get(Main.Rand(ind)).setFlag(false);
                    main.BTSListSender.remove(ind);
                }

                @Override
                public int CheckHowManyBTSS() {
                    return  main.BTSListSender.size()-1;
                }

                @Override
                public int CheckHowManyBTSR() {
                    return main.BTSListReceiver.size()-1;
                }

                @Override
                public void ClearTheQueueOf() {

                }
            };
            ml.StartTheWork();
            new Thread(new BTSS(ml)).start();
            SwingUtilities.invokeLater(() -> {
                Centre centre = new Centre(ml);
                centre.setVisible(true);
            });


        }
    }

    public static class Sender_Panel extends JPanel {
        private Graphics g;
        MainLogic ml;
        Sender_Panel(MainLogic ml) {
            this.ml = ml;
            this.setBackground(Color.WHITE);
            this.setPreferredSize(new Dimension(200, 300));
            this.setLayout(new BorderLayout()); // Set BorderLayout as the layout manager

            SenderPane sp = new SenderPane(ml);
            this.add(sp, BorderLayout.CENTER);

            JButton jButton = new JButton("add");
            this.add(jButton, BorderLayout.SOUTH);
            jButton.addActionListener(e -> {
                String message = JOptionPane.showInputDialog(this, "Enter a message:");

                if (message != null && !message.isEmpty()) {
                    sp.addNewContainer(message);
                    try {
                        ml.addNewQueues(message,3);
                        System.out.println("Starting Transmission");
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            });
        }
    }

    public static class SenderPane extends JScrollPane {
        int indd=0;
        MainLogic ml;
        ArrayList<VBDPanels> sps = new ArrayList<>();
        JPanel container = new JPanel();
        public SenderPane(MainLogic ml) {
            this.ml=ml;
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
            setViewportView(container);
        }
        public void addNewContainer(String message)
        {
            VBDPanels a = new VBDPanels(ml,indd);
            indd++;
            sps.add(a);
            container.add(a);
        }
    }

    public static class VBD implements Runnable{
        private final Main main;
        public int g =0;

        @Override
        public synchronized void run() {

                    try {
                        if (g==0) {
                            GenerateNumber();
                            this.encodeTheSMSC(this.getAddress_Value(), this.getAddress());
                            main.PhoneNumbers[main.getIndex()] = this.getAddress_Value();
                            main.setIndex(main.getIndex() + 1);
                            Thread.sleep(10);
                            g++;
                        }

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
        }

        private int SMSC_TypeOfNumber;
        private int SMSC_NumberingPlanIdentification;
        private String massage;
        private String address_Value="";

        public String getType_of_Number() {
            return Type_of_Number;
        }

        public VBD(Main main, String type_of_Number) {
            super();
            this.main = main;
            Type_of_Number = type_of_Number;
        }

        private String Type_of_Number;
        private byte address;
        private static final String charObjects = "0123456789";

        public String getMassage() {
            return massage;

        }
        public static void writeByte(DataOutputStream write, byte value) throws IOException {
            write.write(value & 0xFF);
        }
        public void encodeTheSMSC(String Address_Value,byte Type_Of_Address_Address/*Or just address from vbd*/)/*number of S26871Project03.VBD*/ {
            String nameOfVBD = Address_Value + ".bin";
            VBD_type_ByteSend(this,"N","R");
            try (DataOutputStream write = new DataOutputStream(new FileOutputStream(nameOfVBD))) {

                byte[] Address = Address_Value.getBytes(StandardCharsets.UTF_8);
                byte Address_length = (byte) ((Address.length/2) + 2);
                writeByte(write, Address_length);
                writeByte(write, Type_Of_Address_Address);
                System.out.println(Address_Value);
                for (int i = 0; i < Address_Value.length(); i += 2) {
                    char a = Address_Value.charAt(i);
                    char b = 'F';
                    if (i + 1 < Address_Value.length()) {
                        b = Address_Value.charAt(i + 1);
                    }
                    byte semiOctetByte = semiOctetByte(a, b);
                    write.write(semiOctetByte);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        public void VBD_type_ByteSend(VBD object, String Type_Of_Number, String extnsn)
        {
            //System.out.println("Unknown/International/National Number/ Extension : U/I/N/E");

            switch (Type_Of_Number)
            {
                case "U": object.setSMSC_TypeOf_Number(0b1000);break;
                case "I": object.setSMSC_TypeOf_Number(0b1001);break;
                case "N": object.setSMSC_TypeOf_Number(0b1010);break;
                case "E": object.setSMSC_TypeOf_Number(0b1111);break;
                default:
                    object.setSMSC_TypeOf_Number(0b1000);break;

            }
            //System.out.println("Unknown/ISDN/TelephoneNumberingPlan/ReservedForExtension : U/I/T/R");
            Type_Of_Number = extnsn;
            switch (Type_Of_Number)
            {
                case "U": object.setSMSC_NumberingPlanIdentification(0b0000);break;
                case "I": object.setSMSC_NumberingPlanIdentification(0b0001);break;
                case "R": object.setSMSC_NumberingPlanIdentification(0b1111);break;
                default:
                    object.setSMSC_NumberingPlanIdentification(0b1111);break;
            }

            byte excluded = (byte) ((object.getSMSC_TypeOfNumber() << 4) | object.getSMSC_NumberingPlanIdentification());
            object.setAddress(excluded);
        }
        public void GenerateNumber() {

            String symbols = "3";
            address_Value+=symbols;
            genStringLastDigits();
        }

        public void genStringLastDigits() {
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            for (int i = 0; i < 5; i++) {
                int randomI = random.nextInt(charObjects.length());
                char rChar = charObjects.charAt(randomI);
                sb.append(rChar);
            }
            address_Value += sb.toString();
        }

        public String getAddress_Value() {
            return address_Value;
        }

        public byte getAddress() {
            return address;
        }


        public void setAddress(byte address) {
            this.address = address;
        }

        public void setSMSC_TypeOf_Number(int SMSC_TypeOf_Nmber) {
            this.SMSC_TypeOfNumber = SMSC_TypeOf_Nmber;
        }

        public void setSMSC_NumberingPlanIdentification(int SMSC_NumberingPlanIdentification) {
            this.SMSC_NumberingPlanIdentification = SMSC_NumberingPlanIdentification;
        }

        public int getSMSC_NumberingPlanIdentification() {
            return SMSC_NumberingPlanIdentification;
        }

        public int getSMSC_TypeOfNumber() {
            return SMSC_TypeOfNumber;
        }

        private static byte semiOctetByte(char a, char b) {
            int v1 = Character.digit(a, 16);
            int v2 = Character.digit(b, 16);

            int semiOctet = (v1 << 4) | v2;
            return (byte) semiOctet;
        }
    }

    public static class VBDPanels extends JPanel {
        private Graphics g;
        MainLogic ml;
        JComboBox<Object> jb = new JComboBox<>();

        private int nomer;

        public VBDPanels(MainLogic ml, int nomer) {
            this.ml = ml;
            this.nomer = nomer;
            Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
            this.setBorder(border);
            this.setBackground(Color.WHITE);
            this.setPreferredSize(new Dimension(100, 100));
            this.setLayout(new BorderLayout());

            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel FR = new JLabel("Frequency");
            jb.addItem("ACTIVE");
            jb.addItem("WAITING");

            JSlider jSlider = new JSlider();
            JButton jButton = new JButton("Terminate");
            panel.add(new JLabel(String.valueOf(nomer)), BorderLayout.CENTER);
            panel.add(FR, BorderLayout.CENTER);
            panel.add(jSlider);
            this.add(jb, BorderLayout.NORTH);
            this.add(panel, BorderLayout.CENTER);
            this.add(jButton, BorderLayout.SOUTH);
            jButton.addActionListener(e -> {
                Container parent = this.getParent();
                if (parent instanceof Container) {
                    (parent).remove(this);
                    parent.revalidate();
                    parent.repaint();
                }
            });

            jb.addActionListener(b -> {
                    ml.ClearTheQueueOf();
                    this.remove(this);
            });
        }


    }

    public static class VBDsender implements Runnable {
        Main main;
        int queueIndex;
        boolean stop = true;
        String message;

        public VBDsender(Main main, int queueIndex,String message) {
            this.main = main;
            this.queueIndex = queueIndex;
            this.message=message;
        }

        @Override
        public void run() {
            try {
                while (stop) {
                    String queue = main.queues.get(queueIndex);
                    main.SendSMS(this.message, queue);
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class VRD
    implements Runnable{
    Main main;
        public VRD(Main main) {
            this.main=main;
        }

        public void run()
        {
            try {

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static class VRDPanels extends JPanel {
        private Graphics g;
        int counter;
       private int num=1;
        JLabel l = new JLabel(String.valueOf(num));
        MainLogic ml;
        VRDPanels(MainLogic ml) {
            this.ml=ml;
            Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
            this.setBorder(border);

            this.setBackground(Color.WHITE);
            this.setPreferredSize(new Dimension(100, 100));
            this.setLayout(new BorderLayout());

            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JButton jButton = new JButton("Terminate");
            JButton jButton2 = new JButton("Update");
            l.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(l);
            this.add(jButton2,BorderLayout.NORTH);
            jButton2.addActionListener(e -> {
                updateCounter(ml.getCounter());
            }
            );
            this.add(panel, BorderLayout.CENTER);
            this.add(jButton, BorderLayout.SOUTH);
            jButton.addActionListener(e -> {
                Container parent = this.getParent();
                if (parent instanceof Container) {
                    (parent).remove(this);
                    parent.revalidate();
                    parent.repaint();
                }
            });
        }
        public void updateCounter(int c) {
            l.setText(String.valueOf(c));
        }
    }

    public static class BinaryWriteAFile implements Runnable{
        @Override
        public void run() {

        }
    }

    public static class BSC extends JScrollPane {
        ArrayList<BSCpanels> bscp = new ArrayList<>();
        JPanel container = new JPanel();
        public BSC() {
            this.setViewportView(container);
            container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
            this.setBackground(Color.WHITE);
            this.setPreferredSize(new Dimension(300, 300));
            BSCpanels a =new BSCpanels();
            container.add(a);

        }

        public void addNewContainerBSC() {
            BSCpanels a = new BSCpanels();
            bscp.add(a);
            container.add(a);
        }
    }

    public static class BSCpanels extends JPanel {
        public BSCpanels()
        {
            Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
            this.setBorder(border);
            this.setBackground(Color.WHITE);
            this.setPreferredSize(new Dimension(100, 100));
            this.setLayout(new BorderLayout());

            JLabel l = new JLabel("S26871Project03.BSS");
            l.setHorizontalAlignment(SwingConstants.CENTER);
            this.add(l);
        }
    }

    public static class BSS implements Runnable {
        private final Main main;
        private byte[] Massage;
        boolean flaglockerLast = false;
        boolean flagLockerCentre=false;
        boolean flagLockerStart=false;
        public boolean notstoped;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        private int index = 0;
        private boolean stopper = true;

        public BSS(Main main, byte[] Massage) {
            super();
            this.main = main;
            this.Massage = Massage;
        }
       synchronized public void run() {
            int ind = main.BSSList.indexOf(this);
           System.out.println(ind);
                try {
                    if (main.isFirst(main.BSSList,this) && main.BSSQueue.size()>0)
                    {
                        setMassage(main.BSSQueue.take());
                        System.out.println("start bss");
                    }
                    else
                    {
                        while (getMassage() == null)
                    {
                        Thread.sleep(2000);
                    }
                        if (isInCentre(main.BSSList,this))
                        {
                            main.BSSList.get(ind+1).setMassage(this.getMassage());
                            this.setMassage(null);
                            System.out.println("middle: "+ind);
                        }
                        if (main.isLast(main.BSSList,this))
                        {
                            System.out.println("S26871Project03.BSS end");
                            main.BTSReceiverQueue.add(getMassage());
                        }
                    }

                    Thread.sleep(RandomTime());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }


        private void processData(byte[] data) {
            // Process the data here
            System.out.println("Processing data: " + data);
        }

        public int RandomTime()
        {
            int max = 15000;
            int min = 3000;
            int RandomTime = (int)Math.floor(Math.random() * (max - min + 1) + min);
            return RandomTime;
        }
        public static boolean PreLast(java.util.List<BSS> list, BSS object) {
            int lastIndex = list.size() - 1;
            if (lastIndex >= 1) {
                BSS preLastItem = list.get(lastIndex - 1);
                return preLastItem.equals(object);
            }
            return false;
        }
        private boolean isInCentre(List<BSS> list, Object obj) {
            int index = list.indexOf(obj);
            int lastIndex = list.size() - 1;

            return index > 0 && index < lastIndex;
        }
        public void sendMassage(BSS a, BSS b)
        {
            b.setMassage(a.getMassage());
        }
        public synchronized void setMassage(byte[] massage) {
            Massage = massage;
        }
        public  synchronized byte[] getMassage() {
            return Massage;
        }
    }

    public static class BSS_Panel extends JPanel {
        Mainer m = new Mainer();
        ArrayList<BTSRpanels> btsr = new ArrayList<>();
        ArrayList<BTSSpanels> btss = new ArrayList<>();
        ArrayList<BSCpanels> bscp = new ArrayList<>();
        JPanel container = new JPanel();

        BSC bsc = new BSC();
        MainLogic ml;
        public BSS_Panel(MainLogic ml) {
            this.ml=ml;


            this.setLayout(new BorderLayout());
            this.setBackground(Color.WHITE);
            this.setPreferredSize(new Dimension(300, 300));

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

            BTSS btss = new BTSS(ml);
            this.add(btss, BorderLayout.LINE_START);

            new Thread(btss).start();

            this.add(bsc, BorderLayout.CENTER);

            BTSR btsR = new BTSR(ml);
            this.add(btsR, BorderLayout.LINE_END);

            JButton jButton1 = new JButton("+");
            jButton1.addActionListener(e -> {
                bsc.addNewContainerBSC();
                ml.addBSC();
                revalidate();
                repaint();
            });
            JButton jButton2 = new JButton("-");
            jButton2.addActionListener(e->{
                ml.stopBSC();
            });

            buttonPanel.add(jButton1);
            buttonPanel.add(jButton2);

            this.add(buttonPanel, BorderLayout.SOUTH);
        }
    }

    public static class BSSStarter implements Runnable{
    private final Main main;

    public boolean stop=true;
        public BSSStarter(Main main)
        {
            this.main=main;
        }
        @Override
        public void run() {
                    for (BSS i : main.BSSList)
                    {
                     Thread c =   new Thread(i);
                     c.start();
                    }
                }
            }

    public static class BTS implements Runnable{
        public void setReceiverNumber(String receiverNumber) {
            this.receiverNumber = receiverNumber;
        }

        public void setSenderNumber(String senderNumber) {
            this.senderNumber = senderNumber;
        }

        public String getReceiverNumber() {
            return receiverNumber;
        }

        public String getSenderNumber() {
            return senderNumber;
        }
        private boolean flag=true;

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public boolean isFlag() {
            return flag;
        }

        private final Main main;
        String receiverNumber;
        String senderNumber;
        String status;
        public BTS(Main main,String status) {
            this.main = main;
            this.status = status;
        }
    private byte[] sendingInfromation;
        @Override
        public void run() {
            while (flag){

                try {
                    Thread.sleep(3000);
                    if (main.BTSSenderQueue.size() == 0)
                    {
                        Thread.sleep(3000);
                        System.out.println("Waiting for Queue");
                    }
                     else {
                     if (main.BTSSenderQueue.size()/main.BSSList.size() > 3) {
                        main.CreateNewBTS();
                    }
                        if (status == "S") {

                            String data = main.BTSSenderQueue.take();
                            String sender="";
                            String receiver="";
                            String massage="";
                            for (int i=0;i<6;i++)
                            {
                               sender += data.charAt(i);
                               setSenderNumber(sender);
                            }
                            for (int i=6;i<12;i++)
                            {
                                receiver += data.charAt(i);
                                setReceiverNumber(receiver);
                            }
                            for (int i=12;i<data.length();i++)
                            {
                                massage += data.charAt(i);
                                setMassage(massage);
                            }
                            Thread.sleep(10);
                            byte[] a = this.TPDU(false, false, "cls_0", "X.400", "7bit", main,main.getMassage());
                            main.BSSQueue.add(a);
                        }
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            }

        private String massage;

        public byte[] TPDU(boolean uncompressed, boolean hasMassageClass, String Massage_Class, String interworking, String codingAlphabet, Main main,String message) throws FileNotFoundException {

            int l;
            try {
                DataInputStream reader = new DataInputStream(new FileInputStream(getSenderNumber()+".bin"));
                l=reader.readByte();
                reader.readByte();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            byte[] sends = new byte[l];
            try {
                DataInputStream reader = new DataInputStream(new FileInputStream(getSenderNumber()+".bin"));
                reader.read(sends);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String address_value;
            String name= "+";
            String bin = "TPDU.bin";
            //name+=receiver.getAddress_Value();

            try {
                int max =Main.Rand(main.getVBDList().size()-1);
                DataInputStream reader = new DataInputStream(new FileInputStream(main.PhoneNumbers[max]+".bin"));
               int length = reader.readByte();
                reader.readByte();
                byte[] infoNum = new byte[length-2];
                reader.read(infoNum);
                address_value = decodeSemiOctets(infoNum);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            name+=address_value;
            name+=bin;
            try (DataOutputStream write = new DataOutputStream(new FileOutputStream(name)))//tp_da
            {//tp_da
                write.write(sends);
                byte FirstOctet = (byte) 0b00000001;//tp_da
                write.write(FirstOctet);//tp_da
                byte[] r = TP_DA(address_value + ".bin");//tp_da
                for (int i : r)//tp_da
                {//tp_da
                    if (i!=0)//tp_da
                    {//tp_da
                        write.write(i);//tp_da
                    }//tp_da
                }//tp_da
                write.write(TP_PID(interworking,name));
                write.write(TP_DCS(uncompressed, hasMassageClass, Massage_Class, interworking));//dcs
                byte TP_VP__ValidityPeriod = 0b0000000; // simplified
                write.write(TP_VP__ValidityPeriod);
                byte UDL = TP__UDL(message,codingAlphabet);//UD LENGTH
                write.write(UDL);
                byte[] UD = TP__UD(message,codingAlphabet);//USERDATA
                write.write(UD);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            int i = 0;
            byte[] info;
            try (DataInputStream read = new DataInputStream(new FileInputStream(name))) {
                while (true) {
                    read.readByte();
                    i++;
                }
            } catch (EOFException e) {
                info = new byte[i];
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

           try (DataInputStream read = new DataInputStream(new FileInputStream(name))){
               read.read(info);
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
           return info;
        }
        public String getMassage() {
            return massage;
        }
        public byte FirstOctetData(boolean Submited)
        {
            byte FOctet;
            if (Submited) {
                FOctet = 0b00000001;
                return FOctet;
            }

            else FOctet = 0b00000000; return FOctet;
        }
        public byte[] TP_DA(String nameFromWhereToTake) throws IOException {
            DataInputStream read = new DataInputStream(new FileInputStream(nameFromWhereToTake));
            byte[] r = new byte[300];
            read.read(r);
            return r;
        }
        public byte TP_PID(String interworking_scheme,String name)
        {
                if (interworking_scheme == "")
                {
                    return 0b00000000;
                }
                else
                {
                        switch (interworking_scheme)
                        {
                            case "Implicit":
                                return 0b00100000;
                                //break;
                            case "telex":
                                return 0b00100001;
                                //break;
                            case "group_3_telefax":
                                return 0b00100010;
                                //break;
                            case "voice_telephone":
                                return 0b00100100;
                               // break;
                            case "ERMES":
                                return 0b00100101;
                                //break;
                            case "NationalPagingSystem":
                                return 0b00110110;
                                //break;
                            case "X.400":
                                return 0b00110001;
                                //break;
                            case "InternetElectronicMail":
                                return  0b00110010;
                                //break;
                        }
                }
            return 0b00000000;
        }
        public byte TP_DCS(boolean uncompressed, boolean hasMessageClass, String messageClass, String typeOfAlphabet) {
            int first = 0b00;
            int five;
            int fourth;
            int thirdSecond;
            int firstSecond;

            if (uncompressed) {
                five = 0b0;
            } else {
                five = 0b1;
            }

            if (hasMessageClass) {
                fourth = 0b1;
            } else {
                fourth = 0b0;
            }

            switch (messageClass) {
                case "Cls_1":
                    firstSecond = 0b01;
                    break;
                case "Cls_2":
                    firstSecond = 0b10;
                    break;
                case "Cls_3":
                    firstSecond = 0b11;
                    break;
                default:
                    firstSecond = 0b00;
                    break;
            }

            switch (typeOfAlphabet) {
                case "8_bit":
                    thirdSecond = 0b01;
                    break;
                case "UCS2":
                    thirdSecond = 0b10;
                    break;
                case "Reserved":
                    thirdSecond = 0b11;
                    break;
                default:
                    thirdSecond = 0b00;
                    break;
            }

            byte DCS;
            DCS = (byte) ((first << 6) | (five << 5) | (fourth << 4) | (thirdSecond << 2) | firstSecond);
            return DCS;
        }

        public byte TP__UDL(String massage,String type)
        {
                byte[] byteMassage;
                switch (type)
                {
                    case "7bit":byteMassage = encodeGSM7Bit(massage);
                        return (byte) byteMassage.length;
                    case "UCS2":byteMassage = encodeUCS2(massage);
                        return (byte) byteMassage.length;
                    default:byteMassage = encode8Bit(massage);
                        return (byte) byteMassage.length;
                }
        }
        public byte[] TP__UD(String massage, String type)
        {
                byte[] byteMassage;
                switch (type)
                {
                    case "7bit":byteMassage = encodeGSM7Bit(massage);
                        return byteMassage;
                    case "UCS2":byteMassage = encodeUCS2(massage);
                        return byteMassage;
                    default:byteMassage = encode8Bit(massage);
                        return byteMassage;
                }
        }
        private static byte semiOctetByte(char a, char b) {
            int v1 = Character.digit(a, 16);
            int v2 = Character.digit(b, 16);

            int semiOctet = (v1 << 4) | v2;
            return (byte) semiOctet;
        }
        public static String decode7Bit(byte[] bytes) {
            StringBuilder sb = new StringBuilder();
            int shift = 0;
            int carryOver = 0;

            for (byte b : bytes) {
                int current = b & 0xFF;
                int charValue = ((current << shift) | carryOver) & 0x7F;
                sb.append((char) charValue);

                if (shift == 6) {
                    carryOver = (current >> 1) & 0x7F;
                    shift = 0;
                } else {
                    carryOver = (current >> (shift + 1)) & 0x7F;
                    shift++;
                }
            }

            return sb.toString();
        }
        public static String decodeUCS2(byte[] bytes) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < bytes.length; i += 2) {
                int highByte = bytes[i] & 0xFF;
                int lowByte = bytes[i + 1] & 0xFF;
                int charValue = (highByte << 8) | lowByte;
                sb.append((char) charValue);
            }

            return sb.toString();
        }
        public static String decodeSemiOctets(byte[] data) {
            StringBuilder sb = new StringBuilder();

            for (byte b : data) {
                int value = b & 0xFF;
                String semiOctet = String.format("%02X", value);
                sb.append(semiOctet);
            }

            return sb.toString();
        }
        /*S26871Project03.BSS last needs here*/
        public static byte[] encode8Bit(String massage)
        {
            return massage.getBytes(StandardCharsets.UTF_8);
        }
        public static byte[] encodeGSM7Bit(String message) {
            return message.getBytes(StandardCharsets.US_ASCII);
        }
        public static byte[] encodeUCS2(String inputString) {
            Charset ucs2Charset = StandardCharsets.UTF_16BE;
            return inputString.getBytes(ucs2Charset);
        }
        public static String decode8Bit(byte[] bytes) {
            return new String(bytes, StandardCharsets.UTF_8);
        }

        public static String decodeGSM7Bit(byte[] bytes) {
            return new String(bytes, StandardCharsets.US_ASCII);
        }

        public byte[] getSendingInfromation() {
            return sendingInfromation;
        }

        public void setSendingInfromation(byte[] sendingInfromation) {
            this.sendingInfromation = sendingInfromation;
        }
        public void setMassage(String massage) {
            this.massage = massage;
        }

    }

    public static class BTSR extends JScrollPane implements Runnable {
        private ArrayList<BTSRpanels> arr = new ArrayList<>();
        private JPanel container = new JPanel();
        private MainLogic ml;

        public BTSR(MainLogic ml) {
            this.ml = ml;
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
            this.setViewportView(container);
            BTSRpanels a = new BTSRpanels();
            container.add(a);

            this.setBackground(Color.WHITE);
            this.setPreferredSize(new Dimension(300, 300));
        }

        public  void addNewContainer() {
            BTSRpanels panel = new BTSRpanels();
            container.add(panel);
            arr.add(panel);
            container.revalidate();
            container.repaint();
        }

        public void removeContainer() {
            int componentCount = container.getComponentCount();
            if (componentCount > 0) {
                Component lastComponent = container.getComponent(componentCount - 1);
                container.remove(lastComponent);
                container.revalidate();
                container.repaint();
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(5000);
                    if (ml.CheckHowManyBTSR() > arr.size() - 1) {
                        removeContainer();
                    } else if (ml.CheckHowManyBTSR() < arr.size() - 1) {
                        addNewContainer();
                    } else {
                        System.out.println("no changes");
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static class BTSReceiver implements Runnable{
            public byte[] getSenderNumber() {
                return senderNumber;
            }


        private final Main main;
            String receiverNumber;
            byte[] BreceiverNumber;
            byte[] senderNumber;
            byte[] lastMassage;
            String status;
            int count;

            public BTSReceiver(Main main,String status) {
                this.main = main;
                this.status = status;
            }
            private byte[] sendingInfromation;
            @Override
            public void run() {
                while (true){

                    try {
                            if (main.BSSQueue.size() == 0)
                            {
                                Thread.sleep(3000);
                                System.out.println("S26871Project03.BTSR wait");
                            }
                            else
                            {
                                byte[] info = main.BSSQueue.take();
                                lastMassage=info;
                                this.TPDUReader(info);
                                Thread.sleep(3000);
                                this.TPDUSend();
                                main.setCounter(main.getCounter()+1);

                                    count++;
                                    main.setCounter(count);
                                    Thread.sleep(3000);
                            }
                        Runtime.getRuntime().addShutdownHook(new Thread() {
                            @Override
                            public void run() {
                                System.out.println("Started writing end file");
                                try
                                {DataOutputStream write = new DataOutputStream(new FileOutputStream("endfile.bin"));
                                    write.write(BreceiverNumber);
                                    write.write(count);
                                    write.write(lastMassage);

                                }
                                catch (FileNotFoundException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                System.out.println("ClosingFilePreparing");
                            }
                        });


                    }
                    catch (InterruptedException  e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        private void writeBinaryFile() {

        }

        private String massage;

            public byte[] TPDU(boolean uncompressed, boolean hasMassageClass, String Massage_Class, String interworking, String codingAlphabet, Main main,String massage) throws FileNotFoundException {

                int l;
                try {
                    DataInputStream reader = new DataInputStream(new FileInputStream(getSenderNumber()+".bin"));
                    reader.readByte();
                    l=reader.readByte();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                byte[] sends = new byte[l];
                try {
                    DataInputStream reader = new DataInputStream(new FileInputStream(getSenderNumber()+".bin"));
                    reader.read(sends);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                String address_value;
                String name= "+";
                String bin = "TPDU.bin";
                //name+=receiver.getAddress_Value();

                try {
                    int max =Main.Rand(main.getVBDList().size()-1);
                    DataInputStream reader = new DataInputStream(new FileInputStream(main.PhoneNumbers[max]+".bin"));
                    int length = reader.readByte();
                    reader.readByte();
                    byte[] infoNum = new byte[length-2];
                    reader.read(infoNum);
                    address_value = decodeSemiOctets(infoNum);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                name+=address_value;
                name+=bin;
                System.out.println(name);
                try (DataOutputStream write = new DataOutputStream(new FileOutputStream(name)))//tp_da
                {//tp_da
                    write.write(sends);
                    byte FirstOctet = (byte) 0b00000001;//tp_da
                    write.write(FirstOctet);//tp_da
                    byte[] r = TP_DA(address_value + ".bin");//tp_da
                    for (int i : r)//tp_da
                    {//tp_da
                        if (i!=0)//tp_da
                        {//tp_da
                            write.write(i);//tp_da
                        }//tp_da
                    }//tp_da
                    write.write(TP_PID(interworking,name));
                    write.write(TP_DCS(uncompressed, hasMassageClass, Massage_Class, interworking));//dcs
                    byte TP_VP__ValidityPeriod = 0b0000000; // simplified
                    write.write(TP_VP__ValidityPeriod);
                    byte UDL = TP__UDL("massage",codingAlphabet);//UD LENGTH
                    write.write(UDL);
                    byte[] UD = TP__UD("massage",codingAlphabet);//USERDATA
                    write.write(UD);
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
                int i = 0;
                byte[] info;
                try (DataInputStream read = new DataInputStream(new FileInputStream(name))) {
                    while (true) {
                        read.readByte();
                        i++;
                    }
                } catch (EOFException e) {
                    info = new byte[i];
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                try (DataInputStream read = new DataInputStream(new FileInputStream(name))){
                    read.read(info);
                    System.out.println("recieved data TPDU");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return info;
            }
            public String getMassage() {
                return massage;
            }
            public byte FirstOctetData(boolean Submited)
            {
                byte FOctet;
                if (Submited) {
                    FOctet = 0b00000001;
                    return FOctet;
                }

                else FOctet = 0b00000000; return FOctet;
            }
            public byte[] TP_DA(String nameFromWhereToTake) throws IOException {
                DataInputStream read = new DataInputStream(new FileInputStream(nameFromWhereToTake));
                byte[] r = new byte[300];
                read.read(r);
                return r;
            }
            public byte TP_PID(String interworking_scheme,String name)
            {
                if (interworking_scheme == "")
                {
                    return 0b00000000;
                }
                else
                {
                    switch (interworking_scheme)
                    {
                        case "Implicit":
                            return 0b00100000;
                        //break;
                        case "telex":
                            return 0b00100001;
                        //break;
                        case "group_3_telefax":
                            return 0b00100010;
                        //break;
                        case "voice_telephone":
                            return 0b00100100;
                        // break;
                        case "ERMES":
                            return 0b00100101;
                        //break;
                        case "NationalPagingSystem":
                            return 0b00110110;
                        //break;
                        case "X.400":
                            return 0b00110001;
                        //break;
                        case "InternetElectronicMail":
                            return  0b00110010;
                        //break;
                    }
                }
                return 0b00000000;
            }
            public byte TP_DCS(boolean uncompressed, boolean hasMessageClass, String messageClass, String typeOfAlphabet) {
                int first = 0b00;
                int five;
                int fourth;
                int thirdSecond;
                int firstSecond;

                if (uncompressed) {
                    five = 0b0;
                } else {
                    five = 0b1;
                }

                if (hasMessageClass) {
                    fourth = 0b1;
                } else {
                    fourth = 0b0;
                }

                switch (messageClass) {
                    case "Cls_1":
                        firstSecond = 0b01;
                        break;
                    case "Cls_2":
                        firstSecond = 0b10;
                        break;
                    case "Cls_3":
                        firstSecond = 0b11;
                        break;
                    default:
                        firstSecond = 0b00;
                        break;
                }

                switch (typeOfAlphabet) {
                    case "8_bit":
                        thirdSecond = 0b01;
                        break;
                    case "UCS2":
                        thirdSecond = 0b10;
                        break;
                    case "Reserved":
                        thirdSecond = 0b11;
                        break;
                    default:
                        thirdSecond = 0b00;
                        break;
                }

                byte DCS;
                DCS = (byte) ((first << 6) | (five << 5) | (fourth << 4) | (thirdSecond << 2) | firstSecond);
                return DCS;
            }

            public byte TP__UDL(String massage,String type)
            {
                byte[] byteMassage;
                switch (type)
                {
                    case "7bit":byteMassage = encodeGSM7Bit(massage);
                        return (byte) byteMassage.length;
                    case "UCS2":byteMassage = encodeUCS2(massage);
                        return (byte) byteMassage.length;
                    default:byteMassage = encode8Bit(massage);
                        return (byte) byteMassage.length;
                }
            }
            public byte[] TP__UD(String massage, String type)
            {
                byte[] byteMassage;
                switch (type)
                {
                    case "7bit":byteMassage = encodeGSM7Bit(massage);
                        return byteMassage;
                    case "UCS2":byteMassage = encodeUCS2(massage);
                        return byteMassage;
                    default:byteMassage = encode8Bit(massage);
                        return byteMassage;
                }
            }
            private static byte semiOctetByte(char a, char b) {
                int v1 = Character.digit(a, 16);
                int v2 = Character.digit(b, 16);

                int semiOctet = (v1 << 4) | v2;
                return (byte) semiOctet;
            }
            public static String decode7Bit(byte[] bytes) {
                StringBuilder sb = new StringBuilder();
                int shift = 0;
                int carryOver = 0;

                for (byte b : bytes) {
                    int current = b & 0xFF;
                    int charValue = ((current << shift) | carryOver) & 0x7F;
                    sb.append((char) charValue);

                    if (shift == 6) {
                        carryOver = (current >> 1) & 0x7F;
                        shift = 0;
                    } else {
                        carryOver = (current >> (shift + 1)) & 0x7F;
                        shift++;
                    }
                }

                return sb.toString();
            }
            public static String decodeUCS2(byte[] bytes) {
                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < bytes.length; i += 2) {
                    int highByte = bytes[i] & 0xFF;
                    int lowByte = bytes[i + 1] & 0xFF;
                    int charValue = (highByte << 8) | lowByte;
                    sb.append((char) charValue);
                }

                return sb.toString();
            }
            public static String decodeSemiOctets(byte[] data) {
                StringBuilder sb = new StringBuilder();

                for (byte b : data) {
                    int value = b & 0xFF;
                    String semiOctet = String.format("%02X", value);
                    sb.append(semiOctet);
                }

                return sb.toString();
            }
            /*S26871Project03.BSS last needs here*/
            public void TPDUReader(byte[] informationFromBSS) {
                String namer;
                int Type;
                try(DataOutputStream write = new DataOutputStream(new FileOutputStream("decoder.bin"))) {

                    write.write(informationFromBSS);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try(DataInputStream read = new DataInputStream(new FileInputStream("decoder.bin"))) {

                    int lengh = read.readByte();
                    read.readByte();
                    read.readByte();
                    read.readByte();
                    read.readByte();
                    read.readByte();
                    String r;
                    int length = read.readByte();
                    byte[] semi = new byte[length - 2];
                    for (int i = 0; i < length - 2; i++) {
                        semi[i] = read.readByte();
                    }// OriginatingAddressEnd at least 2 numbers
                    r=decodeSemiOctets(semi);
                    namer=r;
                }
                    catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                try {
                    DataOutputStream write = new DataOutputStream(new FileOutputStream("-"+namer+".bin"));
                    DataInputStream read = new DataInputStream(new FileInputStream("decoder.bin"));
                    int lengthsender = read.readByte();
                    write.write(lengthsender);
                    byte TypeSender=read.readByte();
                    write.write(TypeSender);
                    byte[] semiSender = new byte[lengthsender-2];
                    for (int i = 0; i < lengthsender - 2; i++) {
                        semiSender[i] = read.readByte();
                    }
                    senderNumber=semiSender;
                    write.write(semiSender);
                    String sender = decodeSemiOctets(semiSender);
                    byte FirstOctet = read.readByte();
                    if (FirstOctet == 0b00000001) {
                        write.write((0b0000000) & 0xFF);//FirstOctet
                    }
                    byte length = read.readByte();
                    write.write(length);
                    String r;

                    byte[] semi = new byte[length - 2];
                    for (int i = 0; i < 3 ; i++) {
                        semi[i] = read.readByte();
                    }
                    write.write(semi); // OriginatingAddressEnd at least 2 numbers
                    r=decodeSemiOctets(semi);
                    BreceiverNumber=semi;

                    byte ProtocolIdentifier = read.readByte();
                    write.write(ProtocolIdentifier);//PID

                    byte CodingScheme = read.readByte();
                    write.write(CodingScheme);//DCS
                    /*Date*/
                    LocalTime currentTime = LocalTime.now();
                    ZoneId timeZone = ZoneId.systemDefault();
                    String timeZoneString = timeZone.toString();
                    LocalDate today = LocalDate.now();

                    int year = today.getYear();
                    int month = today.getMonthValue();
                    int monthday = today.getDayOfMonth();
                    int hour = currentTime.getHour();
                    int minute = currentTime.getMinute();
                    int second = currentTime.getSecond();

                    int i=0;

                    String Year = Integer.toString(year);
                    write.write(semiOctetByte(Year.charAt(i),Year.charAt(i+1)));
                    write.write(semiOctetByte(Year.charAt(i+2),Year.charAt(i+3)));

                    write.write((byte) month);

                    String day = Integer.toString(monthday);
                    write.write(semiOctetByte(day.charAt(i),day.charAt(i+1)));

                   String Hour = Integer.toString(hour);
                   write.write(semiOctetByte((char) 1, (char) 0));

                    //String Minute = Integer.toString(minute);
                    //write.write(semiOctetByte(Minute.charAt(i),Minute.charAt(i+1)));

                    // String Second = Integer.toString(second);
                    // write.write(semiOctetByte(Second.charAt(i),Second.charAt(i+1)));
                    /*Date*/
                    read.readByte();
                    byte lengthOfMassage = read.readByte();
                    write.write(lengthOfMassage);
                    //System.out.println(lengthOfMassage + " length");
                    byte[] Massage = new byte[lengthOfMassage+2];
                    for (int l = 0; l < lengthOfMassage; l++) {
                        Massage[l] = read.readByte();
                    }

                    write.write(Massage);
                    receiverNumber=namer;

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            public void TPDUSend()
            {
                try {
                    DataInputStream read = new DataInputStream(new FileInputStream("-"+receiverNumber + ".bin"));
                    int lgh= read.readByte();
                    read.readByte();
                    byte[] semi = new byte[lgh - 2];
                    for (int i = 0; i < 3 ; i++) {
                        semi[i] = read.readByte();
                    }
                    String r = decodeSemiOctets(semi);
                    senderNumber=semi;
                    read.readByte();
                    int lgh2=read.readByte();
                    byte[] semi2 = new byte[lgh2 - 2];
                    for (int i = 0; i < 3 ; i++) {
                        semi2[i] = read.readByte();
                    }
                    String r2 = decodeSemiOctets(semi2);
                    BreceiverNumber=semi2;

                    read.readByte();
                    read.readByte();
                    read.readByte();
                    read.readByte();
                    read.readByte();
                    read.readByte();
                    read.readByte();
                    /*Date*/

                    byte lengthOfMassage = read.readByte();//lengthMSG
                    int length = (int) lengthOfMassage;
                    byte[] Massage = new byte[length];
                    for (int l = 0; l < length; l++) {
                        Massage[l] = read.readByte();
                    }
                    String decodedMessage;
                    switch ("8bit")
                    {
                        case "7bit":decodedMessage=decode7Bit(Massage);break;
                        case "8bit":decodedMessage=decode8Bit(Massage);break;
                        case "UCS2":decodedMessage=decodeUCS2(Massage);break;
                        default:decodedMessage=decode7Bit(Massage);break;
                    }
                    System.out.println(decodedMessage + " SMS to "+r2);


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
            public static byte[] encode8Bit(String massage)
            {
                return massage.getBytes(StandardCharsets.UTF_8);
            }
            public static byte[] encodeGSM7Bit(String message) {
                return message.getBytes(StandardCharsets.US_ASCII);
            }
            public static byte[] encodeUCS2(String inputString) {
                Charset ucs2Charset = StandardCharsets.UTF_16BE;
                return inputString.getBytes(ucs2Charset);
            }
            public static String decode8Bit(byte[] bytes) {
                return new String(bytes, StandardCharsets.UTF_8);
            }

            public static String decodeGSM7Bit(byte[] bytes) {
                return new String(bytes, StandardCharsets.US_ASCII);
            }

        }

    public static class BTSRpanels extends JPanel {

        public BTSRpanels()
        {
            Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
            this.setBorder(border);
            this.setBackground(Color.lightGray);
            this.setPreferredSize(new Dimension(100, 100));
            this.setLayout(new BorderLayout());

            JLabel l = new JLabel("S26871Project03.BTSR");
            l.setHorizontalAlignment(SwingConstants.CENTER);
            this.add(l);

        }


    }

    public static class BTSS extends JScrollPane implements Runnable {
        private ArrayList<BTSSpanels> arr = new ArrayList<>();
        private JPanel container = new JPanel();
        private MainLogic ml;

        public BTSS(MainLogic ml) {
            this.ml = ml;
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
            this.setViewportView(container);
            BTSSpanels a = new BTSSpanels();
            container.add(a);

            this.setBackground(Color.WHITE);
            this.setPreferredSize(new Dimension(300, 300));
        }

        public  void addNewContainer() {
            BTSSpanels panel = new BTSSpanels();
            container.add(panel);
            arr.add(panel);
            container.revalidate();
            container.repaint();
        }

        public void removeContainer() {
            int componentCount = container.getComponentCount();
            if (componentCount > 0) {
                Component lastComponent = container.getComponent(componentCount - 1);
                container.remove(lastComponent);
                container.revalidate();
                container.repaint();
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(5000);
                    if (ml.CheckHowManyBTSS() > arr.size() - 1 && ml.CheckHowManyBTSS()>0) {
                        removeContainer();
                    } else if (ml.CheckHowManyBTSS() < arr.size() - 1  ) {
                        addNewContainer();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static class BTSSpanels extends JPanel {
        public BTSSpanels()
        {
            Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
            this.setBorder(border);
            this.setBackground(Color.darkGray);
            this.setPreferredSize(new Dimension(100, 100));
            this.setLayout(new BorderLayout());

            JLabel l = new JLabel("S26871Project03.BTSS");
            l.setHorizontalAlignment(SwingConstants.CENTER);
            this.add(l);
        }
    }

    public static class Centre extends JFrame {
        MainLogic m;

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> new Centre(null));
        }

        public Centre(MainLogic m) {
            this.m = m;
            Sender_Panel leftPanel = new Sender_Panel(m);
            this.getContentPane().add(leftPanel, BorderLayout.LINE_START);

            Receiver_Panel rightPanel = new Receiver_Panel(m);
            this.getContentPane().add(rightPanel, BorderLayout.LINE_END);

            BSS_Panel bss = new BSS_Panel(m);
            this.getContentPane().add(bss, BorderLayout.CENTER);

            this.setSize(1280, 720);
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }

    public static class Main implements Runnable
    {
        private Map<Integer,String> Queues = new HashMap<>();
        private int counter=0;

        public int getCounter() {
            return counter;
        }

        public void setCounter(int counter) {
            this.counter = counter;
        }

        public Map<Integer, String> getQueues() {
            return Queues;
        }
        public byte[] a = {0,1,2};

        public List<VRD> VRDList = new ArrayList<>();
        public List<BSS> BSSList = new ArrayList<>();
        public List<VBD> VBDList = new ArrayList<>();
        public List<BTS> BTSListSender = new ArrayList<>();
        public List<BTSReceiver> BTSListReceiver = new ArrayList<>();
        public List<String> queues = new ArrayList<>();
        private String massage = "s";
        public BlockingDeque<byte[]> BSSQueue = new LinkedBlockingDeque<>();
        BlockingQueue<String> BTSSenderQueue = new LinkedBlockingDeque<>();
            BlockingDeque<byte[]> BTSReceiverQueue = new LinkedBlockingDeque<>();

        public String[] PhoneNumbers = new String[6];

        public void StartTheWork()
        {
            new Thread(new Main()).start();
        }



        public void main(String[] args) {
            new Thread(new Main()).start();
        }

        public void run() {
            try {
                VBDStart();
                for (int i = 0; i < 4; i++) {
                    BSS a = new BSS(this,this.a);
                    BSSList.add(a);
                }
               // S26871Project03.BSSStarter a = new S26871Project03.BSSStarter(this);
                Thread.sleep(3000);
                System.out.println("S26871Project03.BTS added");
                CreateNewBTS();
                CreateReceiverSMS();
                for (String i : PhoneNumbers) {System.out.println(i + " added");}
                Thread.sleep(3000);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public boolean isLast(List<BSS> list, BSS element) {
            if (list.isEmpty()) {return false;}
            BSS lastElement = list.get(list.size() - 1);
            return lastElement.equals(element);
        }
        public boolean isFirst(List<BSS> list, BSS element) {
            if (list.isEmpty()) {return false;}
            BSS firstElement = list.get(0);
            return firstElement.equals(element);
        }
        public boolean isTheres(List<BSS> list, BSS element) {
            if (list.isEmpty()) {return false;}
            BSS firstElement = list.get(0);
            return firstElement.equals(element);
        }
        public void SendSMS(String massage,String numbers) {
            this.setMassage(massage);
            StringBuilder senders= new StringBuilder();
            StringBuilder receivers= new StringBuilder();
            for (int i=0;i<6;i++)
            {
                senders.append(numbers.charAt(i));
            }
            for (int i=6;i<12;i++)
            {
                receivers.append(numbers.charAt(i));
            }
            String dataBTS = senders + receivers.toString() + massage;
            this.BTSSenderQueue.add(dataBTS);
            System.out.println("dataBTS: " + dataBTS);
        }


        public String getMassage() {
            return massage;
        }

                 public String[] getPhoneNumbers() {
                     return PhoneNumbers;
                 }

                 private int index;
                 public List<VBD> getVBDList() {
                     return VBDList;
                 }
                 public void setIndex(int index) {
                     this.index = index;
                 }

                 public int getIndex() {
                     return index;
                 }


        public static int Rand(int length) {
        Random random = new Random();
        Set<Integer> generator = new HashSet<>();
        int rand;
        do {rand = random.nextInt(length) + 1;}
        while (generator.contains(rand));
        generator.add(rand);
        return rand;
          }
          public void VBDStart()
          {
              for (int i = 0; i < 6; i++) {
                  VBD ister = new VBD(this, "N");
                  VBDList.add(ister);
                  String phoneNumber = ister.getAddress_Value();
                  PhoneNumbers[i] = phoneNumber;
                  new Thread(ister).start();
              }
          }
          public void CreateReceiverSMS()
          {
             BTSReceiver receiver =  new BTSReceiver(this,"R");
              new Thread(receiver).start();
             this.BTSListReceiver.add(receiver);
          }

        public void setMassage(String massage) {
            this.massage = massage;
        }



        public void CreateNewBTS()
        {
            BTS a = new BTS(this,"S");
            BTSListSender.add(a);
            new Thread(a).start();
        }
        public void removeBTS(BTS bts) {
            bts.setFlag(false);
            BTSListSender.remove(bts);
        }
    }

    public static interface MainLogic {
        public void StartTheWork();
        public void addBSC();
        public void stopBSC();
        public String addNewQueues(String message,int FR) throws InterruptedException;
        public int getCounter();
        public void delete_BTS();
        public int CheckHowManyBTSS();
        public int CheckHowManyBTSR();
        public void ClearTheQueueOf();
    }

    public static class Receiver_Panel extends JPanel  {
        MainLogic mainLogic;
        Receiver_Panel(MainLogic ml) {
            mainLogic=ml;
            this.setBackground(Color.WHITE);
            this.setPreferredSize(new Dimension(200, 300));
            this.setLayout(new BorderLayout());

            ReceiverPane rp = new ReceiverPane(ml);
            this.add(rp, BorderLayout.CENTER);

            JButton jButton = new JButton("Add");
            this.add(jButton, BorderLayout.SOUTH);
            jButton.addActionListener(e -> {
                rp.addNewContainer();
            });
        }
    }

    public static class ReceiverPane extends JScrollPane {
        ArrayList<VRDPanels> rps = new ArrayList<>();
        JPanel container = new JPanel();
        MainLogic ml;
        public  ReceiverPane(MainLogic ml)
        {
            this.ml=ml;
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

            VRDPanels rpp = new VRDPanels(ml);

            container.add(rpp);
            rps.add(rpp);

            setViewportView(container);
        }
        public void addNewContainer()
        {
            VRDPanels a = new VRDPanels(ml);
            rps.add(a);
            container.add(a);
        }
    }
}
