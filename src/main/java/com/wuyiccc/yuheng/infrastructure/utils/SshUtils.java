package com.wuyiccc.yuheng.infrastructure.utils;

import com.wuyiccc.yuheng.infrastructure.pojo.dto.SshConnectionConfigDTO;
import com.wuyiccc.yuheng.infrastructure.pojo.dto.SshExecResultDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ChannelExec;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.session.ClientSession;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    public static boolean testConnect(SshConnectionConfigDTO sshConnectionConfigDTO) {

        SshClient client = null;
        ClientSession session = null;

        try {
            client = SshClient.setUpDefaultClient();
            client.start();
            session = client.connect(sshConnectionConfigDTO.getUsername(), sshConnectionConfigDTO.getHost(), sshConnectionConfigDTO.getPort()).verify(3000).getClientSession();
            session.addPasswordIdentity(sshConnectionConfigDTO.getPassword());
            session.auth().verify(3000);
            return true;
        } catch (Exception e) {
            log.error("连接校验失败", e);
            return false;
        } finally {
            closeData(client, session);
        }
    }

    /**
     * 执行命令
     */
    public static SshExecResultDTO execCmd(SshConnectionConfigDTO config, String cmd) throws Exception {

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

                // 换行
                String[] msgArray = outputStream.toString().split("\\n");
                String[] errorMsgArray = errorOutPutStream.toString().split("\\n");
                List<String> msgList = new ArrayList<>(msgArray.length);
                msgList.addAll(Arrays.asList(msgArray));
                List<String> errorMsg = new ArrayList<>(errorMsgArray.length);
                errorMsg.addAll(Arrays.asList(errorMsgArray));
                return new SshExecResultDTO(msgList, errorMsg);
            }
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
