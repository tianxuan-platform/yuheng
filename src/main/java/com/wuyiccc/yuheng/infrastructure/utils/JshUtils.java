package com.wuyiccc.yuheng.infrastructure.utils;

import com.jcraft.jsch.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

/**
 * @author wuyiccc
 * @date 2024/1/14 22:22
 * <br>
 * ssh操作工具
 * <br>
 *         JshUtils instance = JshUtils.getInstance();
 * <p>         
 *         instance.init("example.com", 22, "root", "password");
 * <p>
 *         System.out.println(instance.execCmd("ls"));
 * <p>         
 *         instance.init("example.com", 22, "root", "password");
 * <p>         
 *         System.out.println(instance.execCmd("pwd"));
 * <p>         
 *         instance.init("example.com", 22, "root", "password");
 * <p>         
 *         System.out.println(instance.execCmd("df -h"));
 * <p>         
 *         instance.init("example.com", 22, "root", "password");
 *
 * <p>
 *         System.out.println(instance.execCmd("docker ps"));
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JshUtils {


    private Session session;

    private Channel channel;

    private ChannelExec channelExec;


    public static JshUtils getInstance() {
        return new JshUtils();
    }

    /**
     * 初始化ssh链接
     *
     * @param host       远程Linux地址
     * @param port     端口
     * @param username 用户名
     * @param password 密码
     */
    public void init(String host, Integer port, String username, String password) throws JSchException {
        JSch jsch = new JSch();
        jsch.getSession(username, host, port);
        session = jsch.getSession(username, host, port);
        session.setPassword(password);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        session.setConfig(sshConfig);
        session.connect(60 * 1000);
        // 打开执行shell指令的通道
        channel = session.openChannel("exec");
        channelExec = (ChannelExec) channel;
    }

    /**
     * 执行一条命令
     */
    public String execCmd(String command) throws Exception {

        if (session == null || channel == null || channelExec == null) {
            throw new RuntimeException("连接未成功建立, 请执行init方法初始化连接");
        }

        channelExec.setCommand(command);
        channel.setInputStream(null);
        channelExec.setErrStream(System.err);
        channel.connect();
        StringBuilder sb = new StringBuilder(16);
        try (InputStream in = channelExec.getInputStream();
             InputStreamReader isr = new InputStreamReader(in, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(isr)) {
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                sb.append("\n").append(buffer);
            }
            return sb.toString();
        } finally {
            close();
        }
    }

    /**
     * 释放资源
     */
    private void close() {
        if (Objects.nonNull(channelExec) && channelExec.isConnected()) {
            channelExec.disconnect();
        }
        if (Objects.nonNull(channel) && channel.isConnected()) {
            channel.disconnect();
        }
        if (Objects.nonNull(session) && session.isConnected()) {
            session.disconnect();
        }
    }
}
