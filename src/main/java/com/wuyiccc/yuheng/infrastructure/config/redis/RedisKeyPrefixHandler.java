package com.wuyiccc.yuheng.infrastructure.config.redis;


import org.apache.commons.lang3.StringUtils;
import org.redisson.api.NameMapper;

/**
 * @author wuyiccc
 * @date 2024/1/18 23:19
 */
public class RedisKeyPrefixHandler implements NameMapper {

    private final String keyPrefix;

    public RedisKeyPrefixHandler(String keyPrefix) {
        //前缀为空 则返回空前缀
        this.keyPrefix = StringUtils.isBlank(keyPrefix) ? "" : keyPrefix + ":";
    }

    /**
     * 增加前缀
     */
    @Override
    public String map(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        if (StringUtils.isNotBlank(keyPrefix) && !name.startsWith(keyPrefix)) {
            return keyPrefix + name;
        }
        return name;
    }

    /**
     * 去除前缀
     */
    @Override
    public String unmap(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        if (StringUtils.isNotBlank(keyPrefix) && name.startsWith(keyPrefix)) {
            return name.substring(keyPrefix.length());
        }
        return name;
    }

}
