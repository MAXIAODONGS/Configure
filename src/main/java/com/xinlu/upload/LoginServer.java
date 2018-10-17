package com.xinlu.upload;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.StreamGobbler;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.xinlu.utils.Config;

public class LoginServer {
    /**
     * @param ip              服务器IP
     * @param user            服务器用户名
     * @param pwd             服务器密码
     * @param port            端口
     * @param privateKeyPath  可为空
     * @param passphrase      可为空
     * @param sourcePath      本地文件路径
     * @param destinationPath 上传路径
     */
    private static void downLoadFile(String ip, String user, String pwd, String port, String privateKeyPath, String passphrase, String sourcePath, String destinationPath) {
        doWrite(ip, user, pwd, port, privateKeyPath, passphrase, sourcePath, destinationPath);
    }

    public static Properties getProperties(String fileName) {
        Properties properties = new Properties();
        properties.setProperty("ip", Config.hostname);
        properties.setProperty("user", Config.username);
        properties.setProperty("pwd", Config.password);
        properties.setProperty("port", String.valueOf(Config.port));
        properties.setProperty("sourcePath", Config.sourcePath + fileName);
        properties.setProperty("destinationPath", Config.destinationPath);
        return properties;
    }

    /**
     * @param properties
     * @param isRead     true表示读取 false表示写入
     */
    public static void login(Properties properties, boolean isRead) {
        String ip = properties.getProperty("ip");
        String user = properties.getProperty("user");
        String pwd = properties.getProperty("pwd");
        String port = properties.getProperty("port");
        String privateKeyPath = properties.getProperty("privateKeyPath");
        String passphrase = properties.getProperty("passphrase");
        String sourcePath = properties.getProperty("sourcePath");
        String destinationPath = properties.getProperty("destinationPath");
        if (!isRead) {
            //写入本地文件到远程服务器
            doWrite(ip, user, pwd, port, privateKeyPath, passphrase, sourcePath, destinationPath);
        } else {
            //读取远程文件到本地
            readConnect();
        }
    }

    /**
     * @throws IOException
     * @description 读文件
     */
    public static String readTxtFile(File fileName) throws IOException {
        String result = null;
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        fileReader = new FileReader(fileName);
        InputStreamReader isr = new InputStreamReader(new FileInputStream(fileName), "UTF-8");
        BufferedReader bufferedReader1 = new BufferedReader(isr);
        String read = null;
        int count = 0;
        while ((read = bufferedReader1.readLine()) != null) {
            result = read + "\r\n";
            count++;
        }
        if (bufferedReader != null) {
            bufferedReader.close();
        }
        if (fileReader != null) {
            fileReader.close();
        }
        return result;
    }

    /**
     * @throws UnsupportedEncodingException
     * @throws IOException
     * @description 写文件
     */
    public static boolean writeTxtFile(String content, File fileName) throws UnsupportedEncodingException, IOException {
        FileOutputStream o = null;
        o = new FileOutputStream(fileName);
        o.write(content.getBytes("UTF-8"));
        o.close();
        return true;
    }

    private static void doWrite(String ip, String user, String pwd, String port, String privateKeyPath, String passphrase, String sourcePath, String destinationPath) {
        if (ip != null && !ip.equals("") && user != null && !user.equals("") && port != null && !port.equals("") && sourcePath != null && !sourcePath.equals("") && destinationPath != null && !destinationPath.equals("")) {
            if (privateKeyPath != null && !privateKeyPath.equals("")) {
                sshSftp2(ip, user, Integer.parseInt(port), privateKeyPath,
                        passphrase, sourcePath, destinationPath);
            } else if (pwd != null && !pwd.equals("")) {
                sshSftp(ip, user, pwd, Integer.parseInt(port), sourcePath,
                        destinationPath);
            } else {
                Console console = System.console();
                System.out.print("Enter password:");
                char[] readPassword = console.readPassword();
                sshSftp(ip, user, new String(readPassword),
                        Integer.parseInt(port), sourcePath, destinationPath);
            }
        } else {
            System.out.println("请先设置配置文件");
        }
    }

    /**
     * 密码方式登录
     *
     * @param ip
     * @param user
     * @param psw
     * @param port
     * @param sPath
     * @param dPath
     */
    private static void sshSftp(String ip, String user, String psw, int port,
                                String sPath, String dPath) {
        System.out.println("password login");
        Session session = null;

        JSch jsch = new JSch();
        try {
            if (port <= 0) {
                // 连接服务器，采用默认端口
                session = jsch.getSession(user, ip);
            } else {
                // 采用指定的端口连接服务器
                session = jsch.getSession(user, ip, port);
            }
            // 如果服务器连接不上，则抛出异常
            if (session == null) {
                throw new Exception("session is null");
            }
            // 设置登陆主机的密码
            session.setPassword(psw);// 设置密码
            // 设置第一次登陆的时候提示，可选值：(ask | yes | no)
            session.setConfig("StrictHostKeyChecking", "no");
            // 设置登陆超时时间
            session.connect(300000);
            UpLoadFile.upLoadFile(session, sPath, dPath);
            //DownLoadFile.downLoadFile(session, sPath, dPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("success");
    }

    /**
     * 密匙方式登录
     *
     * @param ip
     * @param user
     * @param port
     * @param privateKey
     * @param passphrase
     * @param sPath
     * @param dPath
     */
    private static void sshSftp2(String ip, String user, int port,
                                 String privateKey, String passphrase, String sPath, String dPath) {
        System.out.println("privateKey login");
        Session session = null;
        JSch jsch = new JSch();
        try {
            // 设置密钥和密码
            // 支持密钥的方式登陆，只需在jsch.getSession之前设置一下密钥的相关信息就可以了
            if (privateKey != null && !"".equals(privateKey)) {
                if (passphrase != null && "".equals(passphrase)) {
                    // 设置带口令的密钥
                    jsch.addIdentity(privateKey, passphrase);
                } else {
                    // 设置不带口令的密钥
                    jsch.addIdentity(privateKey);
                }
            }
            if (port <= 0) {
                // 连接服务器，采用默认端口
                session = jsch.getSession(user, ip);
            } else {
                // 采用指定的端口连接服务器
                session = jsch.getSession(user, ip, port);
            }
            // 如果服务器连接不上，则抛出异常
            if (session == null) {
                throw new Exception("session is null");
            }
            // 设置第一次登陆的时候提示，可选值：(ask | yes | no)
            session.setConfig("StrictHostKeyChecking", "no");
            // 设置登陆超时时间
            session.connect(300000);
            UpLoadFile.upLoadFile(session, sPath, dPath);
            System.out.println("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取远程文件到本地
     */
    private static void readConnect() {
        Connection conn = new Connection(Config.hostname, Config.port);
        ch.ethz.ssh2.Session ssh = null;
        try {
            //连接到主机
            conn.connect();
            //使用用户名和密码校验
            boolean isconn = conn.authenticateWithPassword(Config.username, Config.password);
            if (!isconn) {
                System.out.println("用户名称或者是密码不正确");
            } else {
                System.out.println("已经连接OK");
                File folder = new File(Config.writePath);
                if (!folder.exists()) {
                    folder.mkdir();
                }
                SCPClient clt = conn.createSCPClient();
                ssh = conn.openSession();
                ssh.execCommand("find /app/s3-configuration/ -name '*.json'");
                InputStream is = new StreamGobbler(ssh.getStdout());
                BufferedReader brs = new BufferedReader(new InputStreamReader(is));
                while (true) {
                    String line = brs.readLine();
                    if (line == null) {
                        break;
                    }
                    clt.get(line, Config.writePath);
                    List<File> lf = new ArrayList<File>();
                    lf = getFileList(new File(Config.writePath), "json");
                    for (File f : lf) {
                        /*System.out.println(f.getPath());*/
                        String path = f.getPath();
                        File file = new File(path);
                        try {
                            FileReader fr = new FileReader(file);
                            BufferedReader br = new BufferedReader(fr);
                            String s = null;
                            Pattern p = Pattern.compile(".*?error.*?");
                            while ((s = br.readLine()) != null) {
                                Matcher m = p.matcher(s);
                                if (m.find()) {
                                    /*System.out.println(m.matches());*/
                                    System.out.println(line);
                                    System.out.println("find error!");
                                }/*else{
                                    System.out.println("no error");
                                }   */
                            }

                            br.close();
                        } catch (FileNotFoundException e) {
                            System.err.println("file not found");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    System.out.println("文件输出成功，请在" + Config.writePath + "中查看");

                }

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            //连接的Session和Connection对象都需要关闭
            if (ssh != null) {
                ssh.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    private static List<File> getFileList(File fileDir, String fileType) {
        List<File> lfile = new ArrayList<File>();
        File[] fs = fileDir.listFiles();
        for (File f : fs) {
            if (f.isFile()) {
                if (fileType.equals(f.getName().substring(f.getName().lastIndexOf(".") + 1, f.getName().length())))
                    lfile.add(f);
            } else {
                List<File> ftemps = getFileList(f, fileType);
                lfile.addAll(ftemps);
            }
        }
        return lfile;
    }
}
