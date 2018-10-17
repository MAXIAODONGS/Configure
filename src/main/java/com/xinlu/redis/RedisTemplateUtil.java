package com.xinlu.redis;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 可自己封装适合自己的工具方法
 */
public class RedisTemplateUtil {

    /**
     * 日志记录
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * redis基础操作类
     */
    private RedisTemplate redisTemplate;

    public RedisTemplateUtil(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 设置某个缓存key失效时间，单位秒
     *
     * @param prefix
     * @param key
     * @param timeout
     * @return
     */
    public boolean expire(String prefix, String key, long timeout) {
        return redisTemplate.expire(prefix + key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 缓存String类型
     *
     * @param prefix 前缀
     * @param key    键
     * @param value  值
     * @return 是否成功
     */
    public boolean cacheStringValue(String prefix, String key, String value) {
        String keys = prefix + key;
        try {
            redisTemplate.boundValueOps(keys).set(value);
            return true;
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
    }

    /**
     * 缓存String类型，并设置超时时间
     *
     * @param prefix  前缀
     * @param key     键
     * @param value   值
     * @param timeout 超时时间
     * @return 是否成功
     */
    public boolean cacheStringValue(String prefix, String key, String value, long timeout) {
        String keys = prefix + key;
        try {
            redisTemplate.boundValueOps(keys).set(value, timeout, TimeUnit.SECONDS);
            return true;
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
    }

    /**
     * 获取缓存String类型
     *
     * @param prefix 前缀
     * @param key    键
     * @return 是否成功
     */
    public String getStringValue(String prefix, String key) {
        try {
            String keys = prefix + key;
            ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
            return valueOps.get(keys);
        } catch (Throwable t) {
            t.printStackTrace();
            return "";
        }
    }

    /**
     * 获取缓存Hash类型
     *
     * @param prefix 前缀
     * @param key    键
     * @return 是否成功
     */
    public Map<Object, Object> getHashMap(String prefix, String key) {
        try {
            String keys = prefix + key;
            HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
            return hash.entries(keys);
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    /**
     * 缓存list
     *
     * @param k
     * @param v
     * @return
     */
    public boolean cacheList(String prefix, String k, List<String> v) {
        return cacheList(prefix, k, v, -1);
    }

    /**
     * 缓存list
     *
     * @param k
     * @param v
     * @param time
     * @return
     */
    public boolean cacheList(String prefix, String k, List<String> v, long time) {
        String key = prefix + k;
        try {
            ListOperations listOps = redisTemplate.opsForList();
            long l = listOps.rightPushAll(key, v);
            if (time > 0) redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } catch (Throwable t) {
            logger.error("缓存[" + key + "]失败, value[" + v + "]", t);
            return false;
        }
    }

    /**
     * 获取list缓存
     *
     * @param k
     * @param start
     * @param end
     * @return
     */
    public List<String> getList(String prefix, String k, long start, long end) {
        try {
            ListOperations<String, String> listOps = redisTemplate.opsForList();
            return listOps.range(prefix + k, start, end);
        } catch (Throwable t) {
            logger.error("获取list缓存失败key[" + prefix + k + ", error[" + t + "]");
            return null;
        }
    }

    /**
     * 获取list缓存
     *
     * @param prefix
     * @param k
     * @return
     */
    public List<String> getList(String prefix, String k) {
        try {
            ListOperations<String, String> listOps = redisTemplate.opsForList();
            return listOps.range(prefix + k, 0, getListSize(prefix, k));
        } catch (Throwable t) {
            logger.error("获取list缓存失败key[" + prefix + k + ", error[" + t + "]");
            return null;
        }
    }

    /**
     * 移除并返回列表 key 的头元素。
     *
     * @param prefix
     * @param k
     * @return
     */
    public String getListLeftPop(String prefix, String k) {
        try {
            return redisTemplate.opsForList().leftPop(prefix + k).toString();
        } catch (Throwable t) {
            logger.error("获取list缓存失败key[" + prefix + k + ", error[" + t + "]");
            return null;
        }
    }


    /**
     * 获取总条数, 可用于分页
     *
     * @param k
     * @return
     */
    public long getListSize(String prefix, String k) {
        try {
            ListOperations<String, String> listOps = redisTemplate.opsForList();
            return listOps.size(prefix + k);
        } catch (Throwable t) {
            logger.error("获取list长度失败key[" + prefix + k + "], error[" + t + "]");
            return 0;
        }
    }

    /**
     * 获取总条数, 可用于分页
     *
     * @param listOps
     * @param k
     * @return
     */
    public long getListSize(String prefix, ListOperations<String, String> listOps, String k) {
        try {
            return listOps.size(prefix + k);
        } catch (Throwable t) {
            logger.error("获取list长度失败key[" + prefix + k + "], error[" + t + "]");
            return 0;
        }
    }

    /**
     * 移除list缓存
     *
     * @param k
     * @return
     */
    public boolean removeOneOfList(String prefix, String k) {
        String key = prefix + k;
        try {
            ListOperations<String, String> listOps = redisTemplate.opsForList();
            listOps.rightPop(prefix + k);
            return true;
        } catch (Throwable t) {
            logger.error("移除list缓存失败key[" + prefix + k + ", error[" + t + "]");
            return false;
        }
    }

    /**
     * 移除缓存
     *
     * @param key
     * @return
     */
    public boolean remove(String prefix, String key) {
        try {
            redisTemplate.delete(prefix + key);
            return true;
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
    }


    /**
     * 缓存set操作
     *
     * @param k
     * @param v
     * @param time
     * @return
     */
    public boolean cacheSet(String prefix, String k, String v, long time) {
        String key = prefix + k;
        try {
            SetOperations<String, String> valueOps = redisTemplate.opsForSet();
            valueOps.add(key, v);
            if (time > 0) redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } catch (Throwable t) {
            logger.error("缓存[" + key + "]失败, value[" + v + "]", t);
            return false;
        }
    }

    /**
     * 缓存set
     *
     * @param k
     * @param v
     * @return
     */
    public boolean cacheSet(String prefix, String k, String v) {
        return cacheSet(prefix, k, v, -1);
    }

    /**
     * 缓存set
     *
     * @param k
     * @param v
     * @param time
     * @return
     */
    public boolean cacheSet(String prefix, String k, Set<String> v, long time) {
        String key = prefix + k;
        try {
            SetOperations<String, String> setOps = redisTemplate.opsForSet();
            setOps.add(key, v.toArray(new String[v.size()]));
            if (time > 0) redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } catch (Throwable t) {
            logger.error("缓存[" + key + "]失败, value[" + v + "]", t);
            return false;
        }
    }

    /**
     * 缓存set
     *
     * @param k
     * @param v
     * @return
     */
    public boolean cacheSet(String prefix, String k, Set<String> v) {
        return cacheSet(prefix, k, v, -1);
    }

    /**
     * 获取缓存set数据
     *
     * @param k
     * @return
     */
    public Set<String> getSet(String prefix, String k) {
        try {
            SetOperations<String, String> setOps = redisTemplate.opsForSet();
            return setOps.members(prefix + k);
        } catch (Throwable t) {
            logger.error("获取set缓存失败key[" + prefix + k + ", error[" + t + "]");
            return null;
        }
    }

    public boolean removeHash(String prefix, String k, String hashKey) {
        String key = prefix + k;
        try {
            redisTemplate.opsForHash().delete(key, hashKey);
            return true;
        } catch (Throwable t) {
            logger.error("删除hash失败key[" + prefix + k + ", error[" + t + "]");
            return false;
        }
    }

    public boolean cacheHash(String prefix, String k, String hashKey, String value) {
        return cacheHash(prefix, k, hashKey, value, -1);
    }

    public boolean cacheHash(String prefix, String k, String hashKey, String value, long time) {
        String key = prefix + k;
        try {
            BoundHashOperations<String, String, String> boundHashOps = redisTemplate.boundHashOps(key);
            boundHashOps.put(hashKey, value);
            if (time > 0) redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } catch (Throwable t) {
            logger.error("保存hash缓存失败key[" + prefix + k + ", error[" + t + "]");
            return false;
        }
    }

    public boolean cacheHashAll(String prefix, String k, Map<String, String> hashKey, long time) {
        String key = prefix + k;
        try {
            BoundHashOperations<String, String, String> boundHashOps = redisTemplate.boundHashOps(key);
            boundHashOps.putAll(hashKey);
            if (time > 0) redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } catch (Throwable t) {
            logger.error("保存hash缓存失败key[" + prefix + k + ", error[" + t + "]");
            return false;
        }
    }

    public boolean cacheHashAll(String prefix, String k, Map<String, String> hashKey) {
        return cacheHashAll(prefix, k, hashKey, -1);
    }

    public String getHash(String prefix, String k, String hashKey) {
        String key = prefix + k;
        try {
            BoundHashOperations<String, String, String> boundHashOps = redisTemplate.boundHashOps(key);
            return boundHashOps.get(hashKey);
        } catch (Throwable t) {
            logger.error("获取hash缓存失败key[" + prefix + k + ", error[" + t + "]");
            return null;
        }
    }
    /**
     * 校验对应的key是否存在
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * redis分页获取page对象
     *
     * @param prefix
     * @param k
     * @param current
     * @param size
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> PageDTO<T> getRedisPaging(String prefix, String k, int current, int size, Class<T> tClass) {
        Long total = getListSize(prefix, k);
        PageDTO pageDto = new PageDTO(current, size, total.intValue());
        List list = this.getList(prefix, k, (long) pageDto.getStartIndex(), (long) pageDto.getEndIndex());
        List tList = JSONObject.parseArray(list.toString(), tClass);
        pageDto.setRecords(tList);
        return pageDto;
    }

    /**
     * 缓存zSet
     *
     * @param prefix
     * @param k
     * @param v
     * @param score
     * @param time
     * @return
     */
    public boolean cacheZSet(String prefix, String k, String v, double score, long time) {
        String key = prefix + k;

        try {
            ZSetOperations<String, String> valueOps = this.redisTemplate.opsForZSet();
            valueOps.add(key, v, score);
            if (time > 0L) {
                this.redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }

            return true;
        } catch (Throwable var10) {
            this.logger.error("缓存[" + key + "]失败, value[" + v + "]", var10);
            return false;
        }
    }

    /**
     * 缓存zSet
     *
     * @param prefix
     * @param k
     * @param v
     * @param score
     * @return
     */
    public boolean cacheZSet(String prefix, String k, String v, double score) {
        return this.cacheZSet(prefix, k, v, score, -1L);
    }

    /**
     * 获取zSet
     *
     * @param prefix
     * @param k
     * @param start
     * @param end
     * @return
     */
    public Set<String> getZSet(String prefix, String k, long start, long end) {
        String key = prefix + k;

        try {
            ZSetOperations<String, String> zSetOps = this.redisTemplate.opsForZSet();
            return zSetOps.rangeByScore(key, (double) start, (double) end);
        } catch (Throwable var9) {
            this.logger.error("获取zSet缓存失败key[" + prefix + k + ", error[" + var9 + "]");
            return null;
        }
    }

    /**
     * 分页获取zSet
     *
     * @param prefix
     * @param k
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    public Set<String> getZSet(String prefix, String k, double min, double max, long offset, long count) {
        String key = prefix + k;

        try {
            ZSetOperations<String, String> zSetOps = this.redisTemplate.opsForZSet();
            return zSetOps.reverseRangeByScore(key, min, max, offset, count);
        } catch (Throwable var13) {
            this.logger.error("获取zSet缓存失败key[" + prefix + k + ", error[" + var13 + "]");
            return null;
        }
    }

    /**
     * 获取集合总数
     *
     * @param prefix
     * @param k
     * @param min
     * @param max
     * @return
     */
    public long getZSetCount(String prefix, String k, double min, double max) {
        String key = prefix + k;
        ZSetOperations<String, String> zSetOps = this.redisTemplate.opsForZSet();
        return zSetOps.count(key, min, max).longValue();
    }

    /**
     * redis分页获取zsetPage对象
     *
     * @param prefix
     * @param k
     * @param current
     * @param size
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> PageDTO<T> getZSetPaging(String prefix, String k, double min, double max, int current, int size, Class<T> tClass) {
        Long total = getZSetCount(prefix, k, min, max);
        PageDTO pageDto = new PageDTO(current, size, total.intValue());
        Set<String> list = this.getZSet(prefix, k, min, max, (long) pageDto.getStartIndex(), (long) pageDto.getEndIndex());
        List tList = JSONObject.parseArray(list.toString(), tClass);
        pageDto.setRecords(tList);
        return pageDto;
    }


}
