import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.Base64;
import java.net.ServerSocket;
import java.net.Socket;
public class guess {
    // 密碼類型為AES
    // AES加密的鑰匙為128-bit
    private static String ENCRYPTION_KEY = "Do0bnvp60FWDKWyH";
    // 加密
    public static String encrypt(String data) throws Exception {
        // 創建一個SecretKeySpec，用於儲存密鑰數據(取得密鑰字數,表示說要使用AES算法)
        // 所以這行的目的是將密鑰包裝成SecretKeySpec，以便後續加密和解密過程中使用該密鑰
        SecretKeySpec keySpec = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), "AES");
        // Cipher是java提供的加密解密的工具
        // 創建cipher後，可以使用他來加解密，通過下一行的init()方法初始化加密和密鑰，再用下下行dofinal進行解密
        Cipher cipher = Cipher.getInstance("AES");
        // (Cipher.ENCRYPT_MODE)表示加密模式，在這種模式下，cipher將用給定密鑰執行加密操作
        // 因此，這行是將cipher對象初始化未使用給定的密鑰，在加密模式下執行加密，之後可以調用dofinal加密數據
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        // encodeToString(encryptedBytes)將數字經過Base64編碼字符表示
        // 這行是將加密後的文字轉換成Base64的編碼格式，以便儲存傳輸
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // 解密
    public static String decrypt(String encryptedData) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        // 將剛剛轉成Base64編碼的加密文解碼為byte array
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("請選擇模式");
        System.out.println("1.人猜 2.電腦猜 3.自訂義A幾B 4.連機模式(需要再開一個client端)");
        int mod = sc.nextInt();
        if (mod == 1) {
            long startTime = System.currentTimeMillis();
            char[] all = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
            int x, y;
            char t;
            Scanner sc1 = new Scanner(System.in);
            System.out.print("Do you want to read a file? If you want to read a file, please enter 'load'.");
            String load = sc1.nextLine();
            if (load.equals("load")) {
                //存檔路徑
                BufferedReader bfr = new BufferedReader(new FileReader("C:\\Users\\Administrator\\Desktop\\java 期末報告\\java期末報告\\save.txt"));
                String line = bfr.readLine();
                String decrypttxt = decrypt(line);
                String[] all1 = new String[10];
                for (int i = 0; i < 10; i++) {
                    all1 = decrypttxt.split(" ");
                }
                //答案
                for(int i=0;i<10;i++){
                    System.out.print(all1[i]+" ");
                }
                int a = 0, b = 0, cnt = 0;
                while (a != 4) {
                    a = 0;
                    b = 0;
                    Scanner sc2 = new Scanner(System.in);
                    String str = sc2.nextLine();
                    cnt++;
                    String[] guess = str.split("");
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            // 存檔
                            if (str.equals("save")) {
                                //存檔路徑
                                BufferedWriter bfw = new BufferedWriter(new FileWriter("C:\\Users\\Administrator\\Desktop\\java 期末報告\\java期末報告\\save.txt"));
                                for (int k = 0; k < 10; k++) {
                                    bfw.write(all[k] + " ");
                                }
                                bfw.flush();
                                bfw.close();
                                try {
                                    //存檔路徑
                                    BufferedReader bfrtoencrypt = new BufferedReader(new FileReader("C:\\Users\\Administrator\\Desktop\\java 期末報告\\java期末報告\\save.txt"));
                                    String originalData = bfrtoencrypt.readLine();
                                    bfrtoencrypt.close();
                                    String encryptedData = encrypt(originalData);
                                    BufferedWriter bfwtoencrypt =new BufferedWriter(new FileWriter("C:\\Users\\Administrator\\Desktop\\java 期末報告\\java期末報告\\save.txt"));
                                    bfwtoencrypt.write(encryptedData);
                                    bfwtoencrypt.flush();
                                    bfwtoencrypt.close();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                System.exit(0);
                            }
                            if (all1[i].equals(guess[j])) {
                                if (i == j) {
                                    a++;
                                } else {
                                    b++;
                                }
                            }
                        }
                    }
                    System.out.println(str + ":" + a + "A" + b + "B");
                }
            } else {
                for (int i = 0; i < 30; i++) {
                    x = (int) (Math.random() * 10);
                    y = (int) (Math.random() * 10);
                    t = all[x];
                    all[x] = all[y];
                    all[y] = t;
                }
                for(int i=0;i<10;i++){
                    System.out.print(all[i]+" ");
                }
                int a = 0, b = 0, cnt = 0;
                while (a != 4) {
                    a = 0;
                    b = 0;
                    Scanner sc2 = new Scanner(System.in);
                    String str = sc2.nextLine();
                    cnt++;
                    char[] guess = str.toCharArray();
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            // 存檔
                            if (str.equals("save")) {
                                BufferedWriter bfw = new BufferedWriter(new FileWriter("C:\\Users\\Administrator\\Desktop\\java 期末報告\\java期末報告\\save.txt"));
                                for (int k = 0; k < 10; k++) {
                                    bfw.write(all[k] + " ");
                                }
                                bfw.flush();
                                bfw.close();
                                try {
                                    BufferedReader bfr = new BufferedReader(new FileReader("C:\\Users\\Administrator\\Desktop\\java 期末報告\\java期末報告\\save.txt"));
                                    String originalData = bfr.readLine();
                                    bfr.close();
                                    String encryptedData = encrypt(originalData);
                                    BufferedWriter bfwtoencrypt =new BufferedWriter(new FileWriter("C:\\Users\\Administrator\\Desktop\\java 期末報告\\java期末報告\\save.txt"));
                                    bfwtoencrypt.write(encryptedData);
                                    bfwtoencrypt.flush();
                                    bfwtoencrypt.close();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                System.exit(0);
                            }
                            if (all[i] == guess[j]) {
                                if (i == j) {
                                    a++;
                                } else {
                                    b++;
                                }
                            }
                        }
                    }
                    System.out.println(str + ":" + a + "A" + b + "B");

                }
                long endTime = System.currentTimeMillis();
                //計算遊戲時間
                long elapsedTimeInMillis = endTime - startTime;
                long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTimeInMillis);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTimeInMillis)
                        - TimeUnit.MINUTES.toSeconds(minutes);
                if (a == 4) {
                    System.out.println("遊戲時間：" + minutes + "分" + seconds + "秒");
                }
                Scanner scanner = new Scanner(System.in);
                BufferedReader bfr = new BufferedReader(new FileReader("C:\\Users\\Administrator\\Desktop\\java 期末報告\\java期末報告\\data.txt"));
                String[][] data = new String[5][5];
                for (int i = 0; i < 5; i++)
                    data[i] = bfr.readLine().split(" ");
                bfr.close();

                int last = Integer.parseInt(data[4][2]);
                if (cnt < last) {
                    System.out.print("請輸入你的名字:");
                    String playername = scanner.nextLine();
                    data[4][2] = Integer.toString(cnt);
                    int position = 0;
                    for (int i = 4; i > 0; i--) {
                        if (Integer.parseInt(data[i][2]) < Integer.parseInt(data[i - 1][2])) {
                            String t1 = "";
                            t1 = data[i - 1][2];
                            data[i - 1][2] = data[i][2];
                            data[i][2] = t1;
                            position = i - 1;

                        }
                    }
                    data[position][1] = playername;

                    BufferedWriter bfw = new BufferedWriter(new FileWriter("C:\\Users\\Administrator\\Desktop\\java 期末報告\\java期末報告\\data.txt"));
                    for (int i = 0; i < 5; i++) {
                        bfw.write(data[i][0] + " " + data[i][1] + " " + data[i][2]);
                        bfw.newLine();
                    }
                    bfw.flush();
                    bfw.close();
                }
                scanner.close();
                BufferedReader btr2 = new BufferedReader(new FileReader("C:\\Users\\Administrator\\Desktop\\java 期末報告\\java期末報告\\data.txt"));
                String str123 = "";
                while ((str123 = btr2.readLine()) != null) {
                    System.out.println(str123);
                }
            }
        } else if (mod == 2) {
            //檢查是否有重複值
            //長度9877是0~9876最大輸入得值為9876
            boolean[] check = new boolean[9877];
            for (int j = 123; j < 9877; j++) {
                String str = Integer.toString(j);
                //如果數值為3位數，自動補0進去
                char[] tmp = str.length() == 3 ? ("0" + str).toCharArray() : str.toCharArray();
                //排列陣列裡面的值
                Arrays.sort(tmp);
                //檢查是否有重複的值，如果有重複的話加入true
                for (int i = 0; i < 3; i++) {
                    if (tmp[i] == (tmp[i + 1])) {
                        check[j] = true;
                    }
                }
            }
            Scanner sc1 = new Scanner(System.in);
            String ans = "";
            int p = 123;
            int avalue, bvalue;
            char[] myguess;
            do {
                //如果值是true就跳過
                while (check[p]) {
                    p++;
                }
                //p<4位數就加0
                String s = p < 1000 ? "0" + Integer.toString(p) : Integer.toString(p);
                System.out.print("我猜:" + s);
                ans = sc1.nextLine();
                //取前兩位數進行比較
                avalue = Integer.parseInt(ans.substring(0, 1));
                bvalue = Integer.parseInt(ans.substring(2, 3));
                myguess = s.toCharArray();
                for (int j = p; j < 9877; j++) {
                    if (!check[j]) {
                        int wav = 0;
                        int wbv = 0;
                        String str = Integer.toString(j);
                        char[] wp = str.length() == 3 ? ("0" + str).toCharArray() : str.toCharArray();
                        for (int k = 0; k < 4; k++) {
                            for (int m = 0; m < 4; m++) {
                                if (wp[k] == myguess[m]) {
                                    if (k == m) {
                                        wav += 1;
                                    } else {
                                        wbv += 1;
                                    }
                                }
                            }
                        }
                        if (avalue != wav || bvalue != wbv) {
                            check[j] = true;
                        }
                    }
                }
            } while (!ans.equals("4A0B"));
            sc.close();
        } else if (mod == 3) {
            char[] all = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
            Scanner sc1 = new Scanner(System.in);
            System.out.print("請輸入你要玩幾A幾B最多10:");
            int many = 0;
            many = sc1.nextInt();
            int x, y;
            char t;
            for (int i = 0; i < 30; i++) {
                x = (int) (Math.random() * 10);
                y = (int) (Math.random() * 10);
                t = all[x];
                all[x] = all[y];
                all[y] = t;
            }
            //答案
            for(int i=0;i<10;i++){
                System.out.print(all[i]+" ");
            }
            int a = 0, b = 0, cnt = 0;
            while (a != many) {
                a = 0;
                b = 0;
                Scanner sc2 = new Scanner(System.in);
                String str = sc2.nextLine();
                cnt++;
                char[] guess = str.toCharArray();
                for (int i = 0; i < many; i++) {
                    for (int j = 0; j < many; j++) {
                        if (all[i] == guess[j]) {
                            if (i == j) {
                                a++;
                            } else {
                                b++;
                            }
                        }
                    }
                }

                System.out.print(a + "A" + b + "B");

            }
        } else if (mod == 4) {
            try {
                String response;
                int playid = -1;
                String[] all = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
                int x, y;
                String t;
                ServerSocket serverSocket = new ServerSocket(8082);
                Socket clientSocket = serverSocket.accept();
                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();
                for (int i = 0; i < 30; i++) {
                    x = (int) (Math.random() * 10);
                    y = (int) (Math.random() * 10);
                    t = all[x];
                    all[x] = all[y];
                    all[y] = t;
                }
                for(int i=0;i<10;i++){
                    System.out.print(all[i]+" ");
                }
                System.out.println("遊戲開始");
                int a = 0, b = 0;
                while (a != 4) {
                    String str = "";
                    byte[] buffer;
                    int bytesRead;
                    String message;
                    a = 0;
                    b = 0;
                    Scanner sc1 = new Scanner(System.in);
                    if (playid == -1) {
                        buffer = new byte[1024];
                        bytesRead = inputStream.read(buffer);
                        message = new String(buffer, 0, bytesRead);
                        str = message;
                    }
                    if (playid == 1) {
                        System.out.print("這回合換我輸入:");
                        str = sc1.nextLine();
                    }
                    String[] guess = str.split("");
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            if (all[i].equals(guess[j])) {
                                if (i == j) {
                                    a++;
                                } else {
                                    b++;
                                }
                            }
                        }
                    }
                    System.out.println(str + ":" + a + "A" + b + "B");
                    if (playid == -1) {
                        response = str + ":" + a + "A" + b + "B";
                        outputStream.write(response.getBytes());
                    } else {
                        response = str + ":" + a + "A" + b + "B";
                        outputStream.write(response.getBytes());
                    }
                    playid *= -1;
                }
                playid *= -1;
                if (playid == -1 && a == 4) {
                    outputStream.write("win".getBytes());
                    System.out.print("lose");
                } else if (playid == 1 && a == 4) {
                    outputStream.write("lose".getBytes());
                    System.out.print("win");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
