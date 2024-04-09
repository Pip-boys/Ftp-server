package cc.olen.app.ftp;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;

/**
 * 自定义端口的ftp服务器
 *
 * @author JiJunpeng
 * @date 2018-12-20 14:32
 */
public class CustomPortFtpServer {

    /**
     * 使用ftp工具进行链接 ftp://localhost:2221
     */
    public static void main(String[] args) throws FtpException {
        FtpServerFactory serverFactory = new FtpServerFactory();
        ListenerFactory factory = new ListenerFactory();
        // 设置监听器端口
        factory.setPort(2221);
        // 替换默认监听器
        serverFactory.addListener("default", factory.createListener());
        // 启动服务
        initAnonymousUser(serverFactory);
        FtpServer server = serverFactory.createServer();
        server.start();
    }

    /**
     * 设置匿名用户及其根目录位置
     */
    private static void initAnonymousUser(FtpServerFactory ftpServerFactory) throws FtpException {
        BaseUser userAnonymous = new BaseUser();
        userAnonymous.setName("anonymous");
        // 注意修改根目录位置
        userAnonymous.setHomeDirectory("D:");
        ftpServerFactory.getUserManager().save(userAnonymous);

        BaseUser user = new BaseUser();
        user.setName("wxf");
        user.setPassword("123456");
        user.setHomeDirectory("D:");
        ftpServerFactory.getUserManager().save(user);
    }
}
