package com.jspxcms.core.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class RedisHelperTest {

    private static final Logger logger = LoggerFactory.getLogger(RedisHelperTest.class);

    private static JedisPool pool = null;

    public static String addr = null;

    public static String port = null;

    public static String auth = null;

/**
 * 构建redis连接池
 *
 * @return JedisPool
 */

    public JedisPool getPool() {
    	logger.error(addr+"--"+port+"--"+auth);
        if (pool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            // 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
            // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxTotal(-1);
            // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(100);
            // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            config.setTestOnReturn(true);
            // 获取连接池 100000毫秒延时
            // pool = new JedisPool(config, "10.98.103.6", 6379, 100000);
            logger.debug(addr+"--"+port+"--"+auth);
            if (addr != null && port != null) {
                pool = new JedisPool(config, addr, Integer.parseInt(port), 100000, auth);
            } else {
                logger.debug("Please check configuration file.");
            }
        }
        return pool;
    }

    /**
     * 返还到连接池
     *
     * @param pool
     * @param redis
     */
    public static void returnResource(JedisPool pool, Jedis redis) {
        if (redis != null)
            pool.returnResource(redis);
    }


    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public String get(String key) {
        String value = null;

        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            // 释放redis对象
            pool.returnBrokenResource(jedis);
            logger.error("redis get " +e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }

        return value;
    }

    /**
     * 获取数据(对象)
     *
     * @param key
     * @return
     */
    public Object getOjbect(String key) {
        Object object = null;
        JedisPool pool = null;
        Jedis jedis = new Jedis();
        try {
            pool = getPool();
            jedis = pool.getResource();
            byte[] value = jedis.get( key.getBytes());
            object = SerializeUtil. unserialize(value);
        } catch (Exception e) {
            // 释放redis对象
            pool.returnBrokenResource(jedis);
            logger.error("redis get object " +e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }

        return object;
    }

    /**
     * 设置数据
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, String value) {
    	logger.error("11111111111");
        JedisPool pool = null;
        Jedis jedis = null;
        boolean bool = false;
        try {
        	logger.error("2222222");
            pool = getPool();
            jedis = pool.getResource();
            jedis.set(key, value);
            bool = true;
        } catch (Exception e) {
            // 释放redis对象
            pool.returnBrokenResource(jedis);
            logger.error("redis set  " +e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return bool;
    }

    /**
     * 设置数据过期时间
     *
     * @param key
     * @param time
     * @return
     */
    public boolean setExpire(String key, String value, int time) {
        JedisPool pool = null;
        Jedis jedis = null;
        boolean bool = false;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.set(key, value);
            jedis.expire(key, time);
            bool = true;
        } catch (Exception e) {
            // 释放redis对象
            pool.returnBrokenResource(jedis);
            logger.error("redis setExpire " +e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return bool;
    }

    /**
     * 设置数据过期时间
     *
     * @param key
     * @return
     */
    public boolean exists(String key) {
        JedisPool pool = null;
        Jedis jedis = null;
        boolean bool = true;
        try {
            pool = getPool();
            jedis = pool.getResource();
            if(jedis.exists(key) == null){
                logger.info("this key not exisits");
                bool = false;
            }
            logger.info("this key is exisitis");
        } catch (Exception e) {
            // 释放redis对象
            pool.returnBrokenResource(jedis);
            logger.error("redis key exist " +e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return bool;
    }
    /**
     * 设置数据(对象)
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setObject(String key, Object value) {
        JedisPool pool = null;
        Jedis jedis = null;
        boolean bool = false;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.set(key.getBytes(), SerializeUtil.serialize(value));
            bool = true;
        } catch (Exception e) {
            // 释放redis对象
            pool.returnBrokenResource(jedis);
            logger.error("redis set object " +e);
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return bool;
    }

    /**
     * 订阅频道
     *
     * @param channelName
     * @throws Exception
     */
    public void subscribe(BinaryJedisPubSub binaryJedisPubSub, byte[] channelName) {
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.subscribe(binaryJedisPubSub, new byte[][]{channelName});
        } catch (Exception e) {
            // 释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
    }

    /**
     * 取消订阅
     *
     * @param binaryJedisPubSub
     * @return
     */
    public static boolean unsubscribe(BinaryJedisPubSub binaryJedisPubSub) {
        boolean bool = false;
        try {
            binaryJedisPubSub.unsubscribe();
            bool = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bool;
    }

    /**
     * 取消订阅
     *
     * @param binaryJedisPubSub
     * @param
     * @return
     */
    public static boolean unsubscribeWithChannelName(BinaryJedisPubSub binaryJedisPubSub, String[] params) {
        boolean bool = false;
        byte[][] bytes = new byte[params.length][];
        int i = 0;
        try {
            String[] arr$ = params;
            int len$ = arr$.length;
            for (int i$ = 0; i$ < len$; ++i$) {
                String param = arr$[i$];
                bytes[i] = param.getBytes();
                ++i;
            }
            binaryJedisPubSub.punsubscribe(bytes);
            bool = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bool;
    }

    /**
     * 推送信息
     *
     * @param channelName
     * @param msg
     * @return
     */
    public boolean publishMsgs(byte[] channelName, byte[] msg) {
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.publish(channelName, msg);

            return true;
        } catch (Exception e) {
            // 释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            return false;

        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
    }

    /**
     * 清空redis
     *
     * @return
     */
    public boolean flushDB() {
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.flushDB();
            return true;
        } catch (Exception e) {
            // 释放redis对象
            pool.returnBrokenResource(jedis);
            logger.error("redis flushDB " +e);
            return false;
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
    }
}

