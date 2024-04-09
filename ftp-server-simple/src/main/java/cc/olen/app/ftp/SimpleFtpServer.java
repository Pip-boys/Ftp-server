package cc.olen.app.ftp;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.usermanager.impl.BaseUser;

/**
 * 简单的ftp服务器
 *
 * @author JiJunpeng
 * @date 2018-12-20 00:00
 */
public class SimpleFtpServer {

    /**
     * 使用ftp工具进行链接 ftp://localhost
     */
    public static void main(String[] args) throws FtpException {
        FtpServerFactory serverFactory = new FtpServerFactory();
        initAnonymousUser(serverFactory);
        FtpServer server = serverFactory.createServer();
        server.start();
    }

    /**
     * 设置匿名用户及其根目录位置
     */
    private static void initAnonymousUser(FtpServerFactory serverFactory) throws FtpException {
        BaseUser user = new BaseUser();
        user.setName("anonymous");
        // 注意修改根目录位置
        user.setHomeDirectory("/Users/jijunpeng");
        serverFactory.getUserManager().save(user);
    }
}
