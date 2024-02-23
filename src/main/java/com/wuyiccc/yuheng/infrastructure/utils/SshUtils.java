package com.wuyiccc.yuheng.infrastructure.utils;

import com.jcraft.jsch.*;
import com.wuyiccc.yuheng.infrastructure.pojo.dto.SshConnectionConfigDTO;
import com.wuyiccc.yuheng.infrastructure.pojo.dto.SshExecResultDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

/**
 * @author wuyiccc
 * @date 2024/1/14 22:22
 * <br>
 * ssh操作工具
 * <br>
 * JshUtils instance = JshUtils.getInstance();
 * <p>
 * instance.init("example.com", 22, "root", "password");
 * <p>
 * System.out.println(instance.execCmd("ls"));
 * <p>
 * instance.init("example.com", 22, "root", "password");
 * <p>
 * System.out.println(instance.execCmd("pwd"));
 * <p>
 * instance.init("example.com", 22, "root", "password");
 * <p>
 * System.out.println(instance.execCmd("df -h"));
 * <p>
 * instance.init("example.com", 22, "root", "password");
 *
 * <p>
 * System.out.println(instance.execCmd("docker ps"));
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SshUtils {


    private Session session;

    private Channel channel;

    private ChannelExec channelExec;


    public static SshUtils getInstance() {
        return new SshUtils();
    }

    /**
     * 初始化ssh链接
     *
     * @param host     远程Linux地址
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
        session.connect(6 * 1000);
        // 打开执行shell指令的通道
        channel = session.openChannel("exec");
        channelExec = (ChannelExec) channel;
    }

    /**
     * 执行一条命令
     */
    public SshExecResultDTO execCmd(String command) throws IOException, JSchException {

        if (session == null || channel == null || channelExec == null) {
            throw new RuntimeException("连接未成功建立, 请执行init方法初始化连接");
        }

        channelExec.setCommand(command);
        channel.setInputStream(null);
        // 设置错误输出流
        PipedOutputStream errorStream = new PipedOutputStream();
        PipedInputStream errorInputStream = new PipedInputStream(errorStream);
        channelExec.setErrStream(errorStream);
        channel.connect();
        StringBuilder sb = new StringBuilder(16);
        try (InputStream in = channelExec.getInputStream();
             InputStreamReader isr = new InputStreamReader(in, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(isr)) {
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                sb.append("\n").append(buffer);
            }
            String errMsg = saveErrorOutput(errorInputStream);
            if (StringUtils.isNotBlank(errMsg)) {
                return new SshExecResultDTO(false, errMsg);
            }
            return new SshExecResultDTO(true, sb.toString());
        } finally {
            errorInputStream.close();
            errorStream.close();
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

    /**
     * 保存错误信息
     */
    private String saveErrorOutput(InputStream errorInputStream) throws IOException {

        String errorOutput;
        try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorInputStream, StandardCharsets.UTF_8))) {
            StringBuilder errorOutputBuilder = new StringBuilder();
            String errorBuffer;
            while ((errorBuffer = errorReader.readLine()) != null) {
                errorOutputBuilder.append("\n").append(errorBuffer);
            }
            errorOutput = errorOutputBuilder.toString();
        }
        return errorOutput;
    }


    /**
     * 执行命令
     */
    public static SshExecResultDTO execCmd(SshConnectionConfigDTO sshConnectionConfigDTO, String command) throws JSchException, IOException {
        SshUtils instance = SshUtils.getInstance();
        instance.init(sshConnectionConfigDTO.getHost()
                , sshConnectionConfigDTO.getPort()
                , sshConnectionConfigDTO.getUsername()
                , sshConnectionConfigDTO.getPassword());
        return instance.execCmd(command);
    }
}
