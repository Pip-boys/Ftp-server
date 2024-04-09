package cc.olen.app.ftp;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.ssl.SslConfigurationFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;

import java.io.File;

/**
 * ftps 服务器
 *
 * @author JiJunpeng
 * @date 2018-12-20 00:00
 */
public class FtpsServer {

    /**
     * 使用ftp工具进行链接 ftps://localhost:2221
     * <p>
     * ftpserver.jks 是使用jdk的keytool工具创建出来的，调用{@link SslConfigurationFactory#setKeystorePassword(String)}时注意密码是否填写正确
     */
    public static void main(String[] args) throws FtpException {
        FtpServerFactory serverFactory = new FtpServerFactory();
        ListenerFactory factory = new ListenerFactory();
        // 设置监听器端口
        factory.setPort(2221);
        // 定义一个ssl配置
        SslConfigurationFactory ssl = new SslConfigurationFactory();
        File keyStoreFile = new File("ftpserver.jks");
        ssl.setKeystoreFile(keyStoreFile);
        ssl.setKeystorePassword("123456");
        // 将ssl配置添加到监听器中
        factory.setSslConfiguration(ssl.createSslConfiguration());
        factory.setImplicitSsl(true);
        // 替换默认的监听器
        serverFactory.addListener("default", factory.createListener());
        // 设置用户信息
        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        userManagerFactory.setFile(new File("myusers.properties"));
        serverFactory.setUserManager(userManagerFactory.createUserManager());

        BaseUser user = new BaseUser();
        user.setName("wxf");
        user.setPassword("123456");
        user.setHomeDirectory("D:\\");
        user.setEnabled(true);
        user.setMaxIdleTime(20);
        serverFactory.getUserManager().save(user);

        // 启动服务
        FtpServer server = serverFactory.createServer();
        server.start();
    }

}
