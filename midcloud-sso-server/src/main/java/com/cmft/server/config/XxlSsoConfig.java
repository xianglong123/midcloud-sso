package com.cmft.server.config;

import com.cmft.core.store.SsoLoginStore;
import com.cmft.core.util.JedisUtil;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author xuxueli 2018-04-03 20:41:07
 */
@Configuration
public class XxlSsoConfig implements InitializingBean, DisposableBean {

    @Value("${midcloud.sso.redis.address}")
    private String redisAddress;

    @Value("${midcloud.sso.redis.minute}")
    private int redisExpireMinute;

    @Override
    public void afterPropertiesSet() throws Exception {
        SsoLoginStore.setRedisExpireMinute(redisExpireMinute);
        JedisUtil.init(redisAddress);
    }

    @Override
    public void destroy() throws Exception {
        JedisUtil.close();
    }

}
