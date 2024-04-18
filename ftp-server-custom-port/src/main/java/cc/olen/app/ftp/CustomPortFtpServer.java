package cc.olen.app.ftp;

import org.apache.ftpserver.DataConnectionConfigurationFactory;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义端口的ftp服务器
 *
 * @author JiJunpeng
 * @date 2018-12-20 14:32
 */
@Component
public class CustomPortFtpServer {

    @Value("${ftp.username}")
    private String userName;
    @Value("${ftp.port}")
    private Integer port;
    @Value("${ftp.password}")
    private String passWord;
    @Value("${ftp.rootDir}")
    private String rootDir;
    @Value("${ftp.ip}")
    private String ip;
    @Value("${ftp.portList}")
    private String portList;


    @PostConstruct
    public void init() throws Exception {
        FtpServerFactory serverFactory = new FtpServerFactory();
        ListenerFactory factory = new ListenerFactory();
        // 设置监听器端口
        factory.setPort(port);

        //设置被动模式的IP地址和端口范围
        DataConnectionConfigurationFactory dccFactory = new DataConnectionConfigurationFactory();
        dccFactory.setPassivePorts(portList); // 设置被动模式端口范围
        dccFactory.setPassiveExternalAddress(ip); // 设置公网IP地址
        factory.setDataConnectionConfiguration(dccFactory.createDataConnectionConfiguration());

        // 替换默认监听器
        serverFactory.addListener("default", factory.createListener());
        // 启动服务
        initAnonymousUser(serverFactory);
        FtpServer server = serverFactory.createServer();
        server.start();
    }

    /**
     * 使用ftp工具进行链接 ftp://localhost:2221
     */
    public static void main(String[] args) throws Exception {
        CustomPortFtpServer customPortFtpServer = new CustomPortFtpServer();
        customPortFtpServer.init();
    }

    /**
     * 设置匿名用户及其根目录位置
     */
    private void initAnonymousUser(FtpServerFactory ftpServerFactory) throws FtpException {
        BaseUser userAnonymous = new BaseUser();
//        userAnonymous.setName("anonymous");
//        // 注意修改根目录位置
//        userAnonymous.setHomeDirectory("D:\\subject");
//        ftpServerFactory.getUserManager().save(userAnonymous);

        BaseUser user = new BaseUser();
        user.setName(userName);
        user.setPassword(passWord);
        user.setHomeDirectory(rootDir);
        List<Authority> authorities = new ArrayList<Authority>();
        authorities.add(new WritePermission());

        user.setAuthorities(authorities);
        ftpServerFactory.getUserManager().save(user);
    }
}
