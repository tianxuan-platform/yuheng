package com.wuyiccc.yuheng.infrastructure.utils;

import com.wuyiccc.yuheng.infrastructure.pojo.dto.SshConnectionConfigDTO;
import com.wuyiccc.yuheng.infrastructure.pojo.dto.SshExecResultDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ChannelExec;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.sftp.client.SftpClientFactory;
import org.apache.sshd.sftp.client.fs.SftpFileSystem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author wuyiccc
 * @date 2024/1/14 22:22
 * <br>
 * ssh操作工具
 * <br>
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SshUtils {


    /**
     * 测试服务器连接
     */
    public static SshExecResultDTO testConnect(SshConnectionConfigDTO sshConnectionConfigDTO) {

        SshClient client = null;
        ClientSession session = null;

        try {
            client = SshClient.setUpDefaultClient();
            client.start();
            session = client.connect(sshConnectionConfigDTO.getUsername(), sshConnectionConfigDTO.getHost(), sshConnectionConfigDTO.getPort()).verify(3000).getClientSession();
            session.addPasswordIdentity(sshConnectionConfigDTO.getPassword());
            session.auth().verify(3000);
            List<String> msgList = new ArrayList<>();
            msgList.add("连接测试成功");
            return new SshExecResultDTO(msgList);
        } catch (Exception e) {
            log.error("连接测试失败", e);
            List<String> msgList = new ArrayList<>();
            msgList.add("连接测试失败: " + e.getMessage());
            return new SshExecResultDTO(msgList);
        } finally {
            closeData(client, session);
        }
    }

    /**
     * 执行命令
     */
    public static SshExecResultDTO execCmd(SshConnectionConfigDTO config, String cmd) {

        SshClient client = null;
        ClientSession session = null;

        try {
            client = SshClient.setUpDefaultClient();
            client.start();
            session = client.connect(config.getUsername(), config.getHost(), config.getPort()).verify(3000).getClientSession();
            session.addPasswordIdentity(config.getPassword());
            session.auth().verify(3000);


            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                 ByteArrayOutputStream errorOutPutStream = new ByteArrayOutputStream();
                 ChannelExec channel = session.createExecChannel(cmd)
            ) {
                channel.setOut(outputStream);
                channel.setErr(errorOutPutStream);
                channel.open().verify(3000);
                channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), 0L);

                List<String> msgList = new ArrayList<>();
                String outputStr = outputStream.toString();
                if (StringUtils.isNotEmpty(outputStr)) {
                    // 换行
                    String[] msgArray = outputStr.split("\\n");
                    if (msgArray.length > 0) {
                        msgList.addAll(Arrays.asList(msgArray));
                    }
                }
                String errorStr = errorOutPutStream.toString();
                if (StringUtils.isNotEmpty(errorStr)) {
                    String[] errorMsgArray = errorStr.split("\\n");
                    if (errorMsgArray.length > 0) {
                        msgList.addAll(Arrays.asList(errorMsgArray));
                    }
                }
                return new SshExecResultDTO(msgList);
            }
        } catch (Exception e) {
            log.error("执行命令失败", e);
            List<String> msgList = new ArrayList<>();
            msgList.add("执行命令失败: " + e.getMessage());
            return new SshExecResultDTO(msgList);
        } finally {
            closeData(client, session);
        }
    }

    public static SshExecResultDTO uploadFile(SshConnectionConfigDTO config, String localFilePath, String remoteFilePath) {
        SshClient client = null;
        ClientSession session = null;

        try {
            client = SshClient.setUpDefaultClient();
            client.start();
            session = client.connect(config.getUsername(), config.getHost(), config.getPort()).verify(3000).getClientSession();
            session.addPasswordIdentity(config.getPassword());
            session.auth().verify(3000);

            List<String> msgList = new ArrayList<>();

            // 删除原文件
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                 ByteArrayOutputStream errorOutPutStream = new ByteArrayOutputStream();
                 ChannelExec channel = session.createExecChannel("rm -rf " + remoteFilePath)
            ) {
                channel.setOut(outputStream);
                channel.setErr(errorOutPutStream);
                channel.open().verify(3000);
                channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), 0L);

                String outputStr = outputStream.toString();
                if (StringUtils.isNotEmpty(outputStr)) {
                    // 换行
                    String[] msgArray = outputStr.split("\\n");
                    if (msgArray.length > 0) {
                        msgList.addAll(Arrays.asList(msgArray));
                    }
                }
                String errorStr = errorOutPutStream.toString();
                if (StringUtils.isNotEmpty(errorStr)) {
                    String[] errorMsgArray = errorStr.split("\\n");
                    if (errorMsgArray.length > 0) {
                        msgList.addAll(Arrays.asList(errorMsgArray));
                    }
                }
            }
            // 上传现在文件
            SftpFileSystem fs = SftpClientFactory.instance().createSftpFileSystem(session);
            Files.copy(Paths.get(localFilePath), fs.getDefaultDir().resolve(remoteFilePath)); // 将目标文件拷贝至目标目录
            msgList.add("执行成功");
            return new SshExecResultDTO(msgList);
        } catch (Exception e) {
            log.error("执行命令失败", e);
            List<String> msgList = new ArrayList<>();
            msgList.add("执行命令失败: " + e.getMessage());
            return new SshExecResultDTO(msgList);
        } finally {
            closeData(client, session);
        }
    }


    private static void closeData(SshClient client, ClientSession session) {
        if (Objects.nonNull(session)) {
            try {
                session.close();
            } catch (IOException e) {
                log.error("关闭session失败", e);
            }
        }
        if (Objects.nonNull(client)) {
            try {
                client.close();
            } catch (IOException e) {
                log.error("关闭client失败");
            }
        }
    }
}
