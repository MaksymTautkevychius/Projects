import java.awt.*;
import java.io.*;

public class S26871Set01
        extends Frame {
    public S26871Set01()
    {
        setSize( 640, 480);
        setVisible(true);
    }

    public static void main(String[] args) {
        new S26871Set01();
    }
    public void paint(Graphics g) {
        super.paint(g);
        int size = 640*460*20;
        int n = 1, x = this.getWidth() / 2, y = this.getHeight() / 2, count = 0,j=0;
        boolean end=false;
        Write(640*460*11);
        int[] primes = Read();
        while(!end) {
            for (int k=0;k<n;k++)
            {
                x+=1;

                if (count == primes[j])
                {
                    g.fillRect(x ,y, 1, 1);
                    j++;
                }
                count++;
            }
            for (int k=0;k<n;k++)
            {
                y+=1;
                if (count == primes[j])
                {
                    g.fillRect(x,y , 1, 1);
                    j++;
                }
                count++;
            }
            n++;
            for (int k=0;k<n;k++)
            {
                x-=1;
                if (count == primes[j])
                {
                    g.fillRect(x ,y , 1, 1);
                    j++;
                }
                count++;
            }
            for (int k=0;k<n;k++)
            {
                y-=1;
                if (count == primes[j])
                {
                    g.fillRect(x ,y, 1, 1);
                    j++;
                }
                count++;
            }
            n++;
            if (count>size)end=true;
        }
    }
    public static boolean isPrimeMethod(int n) {
        if (n == 1) return false;
        for(int i=2;i <= Math.sqrt(n);i++) {
            if(n%i==0)
                return false;
        }
        return true;
    }
    public static void Write(int size)
    {
        try (DataOutputStream write =new DataOutputStream(new FileOutputStream("WriterBin.bin"))){
            long[] cont = new long[7];
            int count = 0;
            int ppow = 8;
            int semi=8;
            int n= 0;
            for(int i = 1, j = 0; i < size; i++) {

                if(i == Math.pow(2, ppow)) {
                    ppow += 8;
                    cont[j] = count;
                    count = 0;
                    j++;
                }
                if(isPrimeMethod(i))
                {
                    count++;
                }
            }
            write.write((byte)(cont[n] >>> 56) & 0xFF);
            write.write((byte)(cont[n] >>> 48) & 0xFF);
            write.write((byte)(cont[n] >>> 40) & 0xFF);
            write.write((byte)(cont[n] >>> (32)) & 0xFF);
            write.write((byte)(cont[n] >>> 24) & 0xFF);
            write.write((byte)(cont[n] >>> 16) & 0xFF);
            write.write((byte)(cont[n] >>> 8) & 0xFF);
            write.write((byte)(cont[n] >>> 0) & 0xFF);

            n++;
            for (int i=1, j = 0; i<size;i++)
            {
                if (i == Math.pow(2,semi))
                {
                    write.write('\n');
                    write.write((byte)(cont[n] >>> 56) & 0xFF);
                    write.write((byte)(cont[n] >>> 48) & 0xFF);
                    write.write((byte)(cont[n] >>> 40) & 0xFF);
                    write.write((byte)(cont[n] >>> 32) & 0xFF);
                    write.write((byte)(cont[n] >>> 24) & 0xFF);
                    write.write((byte)(cont[n] >>> 16) & 0xFF);
                    write.write((byte)(cont[n] >>> 8) & 0xFF);
                    write.write((byte)(cont[n] >>> 0) & 0xFF);
                    semi+=8;
                    n++;
                    j++;
                }
                if(isPrimeMethod(i)){
                    for (int k = j; k >= 0; k--)
                    {
                        write.write((byte)(i>>>(8 * k)) & 0xFF);
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static int[] Read()
    {
        int[] buffer = new int[2000000];
        try (InputStream read = new FileInputStream("WriterBin.bin")) {
            long count =0;
            int j=1 ,v=0;
            for (int i = 0;i<3;i++) {
                count = (count << 8) + read.read();
                count = (count << 8) + read.read();
                count = (count << 8) + read.read();
                count = (count << 8) + read.read();
                count = (count << 8) + read.read();
                count = (count << 8) + read.read();
                count = (count << 8) + read.read();
                count = (count << 8) + read.read();

                System.out.println(count);

                //
                //
                while (count>0)
                {
                    for (int h=0;h<j;h++)
                    {
                        buffer[v] = (buffer[v] << 8)+ read.read();
                    }
                    System.out.println(buffer[v]);
                    count--;
                    v++;
                }
                j++;
                read.read();
            }
            for (int i=0;i<buffer.length;i++)
            {
                System.out.println(buffer[i]);
            }
        }

        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        return buffer;
    }
}
