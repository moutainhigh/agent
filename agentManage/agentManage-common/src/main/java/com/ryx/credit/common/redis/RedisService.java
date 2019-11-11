package com.ryx.credit.common.redis;

import java.util.*;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.core.SessionCallback;

public class RedisService {

    public static final long TIME_OUT = 60000*5;       //锁的超时时间
    public static final long ACQUIRE_TIME_OUT = 5000;  //请求超时时间

	@Autowired
    protected RedisTemplate<String, String> redisTemplate;

	private Logger logger = Logger.getLogger(this.getClass());

    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public boolean haveKey(String key){
        return redisTemplate.hasKey(key);
    }


    public long lpushList(final String key,final String value){
        try {
            return redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.lPush(redisTemplate.getStringSerializer().serialize(key),redisTemplate.getStringSerializer().serialize(value));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("lpushList error",e);
        }
        return -1;
    }

    public String lpopList(String key){
        try {
            return redisTemplate.execute(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    byte[] value = connection.lPop(redisTemplate.getStringSerializer().serialize(key));
                    if(value!=null && value.length>0)
                    return redisTemplate.getStringSerializer().deserialize(value);
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("lpopList error",e);
        }
        return null;
    }

    public long rpushList(final String key,final String value){
        try {
            return  redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.rPush(redisTemplate.getStringSerializer().serialize(key),redisTemplate.getStringSerializer().serialize(value));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("rpushList error",e);
        }
        return -1;
    }




    public String rpopList(String key){
        try {
            return  redisTemplate.execute(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    byte[] value = connection.rPop(redisTemplate.getStringSerializer().serialize(key));
                    if(value!=null && value.length>0)
                        return redisTemplate.getStringSerializer().deserialize(value);
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("rpopList error",e);

        }
        return null;
    }


    public List<String> lrange(String key,long start,long end){
        try {
            return redisTemplate.execute(new RedisCallback<List<String>>() {
                @Override
                public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
                   List<String> res = new ArrayList<>();
                   List<byte[]> data =   connection.lRange(redisTemplate.getStringSerializer().serialize(key),start,end);
                   if(data!=null && data.size()>0){
                       for (byte[] datum : data) {
                           if(datum!=null&&datum.length>0) {
                               res.add(redisTemplate.getStringSerializer().deserialize(datum));
                           }
                       }
                   }
                   return res;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("rpopList error",e);
        }
        return null;
    }

    /**
	 * 放置key value 失效时间
	 * @param key
	 * @param value
	 * @param seconds
	 */
    public void setValue(final String key,final String value,final Long seconds) {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setEx(redisTemplate.getStringSerializer().serialize(key),
                		seconds, redisTemplate.getStringSerializer().serialize(value));
                return null;
            }
        });
    }

    /**
     * 是否存在key value 失效时间
     * @param key
     * @param value
     */
    public String setNx(final String key,final String value) {
        try {
            return (String) redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    Boolean b = connection.setNX(redisTemplate.getStringSerializer().serialize(key), redisTemplate.getStringSerializer().serialize(value));
                    return b.toString();
                }
            });
        } catch (Exception e) {
            logger.error("setNx error",e);
        }
        return null;
    }


    public void set(final String key,final String value) {
        redisTemplate.opsForValue().set(key,value);
    }

    /**
     * 失效时间
     * @param key
     */
    public Long ttl(final String key) {
        return (Long) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                Long b = connection.ttl(redisTemplate.getStringSerializer().serialize(key));
                return b;
            }
        });
    }


    /**
     * 获取value
     * @param key
     * @return
     */
    public String getValue(final String key) {
    	
        return redisTemplate.execute(new RedisCallback<String>() {
        	String value = "";
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] byteKey = redisTemplate.getStringSerializer().serialize(key);
                if (connection.exists(byteKey)) {
                    byte[] bytes = connection.get(byteKey);
                    if(bytes.length>0){
                    	value = redisTemplate.getStringSerializer().deserialize(bytes);
                        return value;
                    }
                }
                return value;
            }
        });
    }
    
    /**
     * 返回对象泛型
     * @param key
     * @param cla
     * @return
     */
    public <T> T getObject(final String key,Class<?> cla) {
    	String value = this.getValue(key);
    	if(StringUtils.isEmpty(value)){
    		return null;
    	}
    	try {
    		@SuppressWarnings("unchecked")
			T t = (T) JSON.parseObject(value, cla);
        	return t;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return null;
		}
    }
    
    /**
     * 返回对象list 泛型
     * @param key
     * @param cla
     * @return
     */
    public List<?> getList(final String key,Class<?> cla) {
    	String value = this.getValue(key);
    	if(StringUtils.isEmpty(value)){
    		return null;
    	}
    	try {
    		List<?> tlist = JSON.parseArray(value, cla);
        	return tlist;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return null;
		}
    }
    
    /**  
     * 删除 
     * @param key 
     */  
    public void delete(String key) {  
        List<String> list = new ArrayList<String>();  
        list.add(key);  
        delete(list);  
    }
  
    /**  
     * 删除模糊匹配上的所有缓存
     * 比如redisKey是由时间加随机数生成，deleteByCondition("session")则删除所有包含session信息的缓存
     * @param condition
     */  
    public void deleteByCondition(String condition) {  
    	Set<String> keys = redisTemplate.keys(condition + "*");
        redisTemplate.delete(keys);
    }
    
    /** 
     * 删除多个 
     * @param keys 
     */  
    public void delete(List<String> keys) {
    	redisTemplate.delete(keys);
    }

    /**
     * 加锁
     * @param locaName  锁的key
     * @param acquireTimeout  获取超时时间
     * @param timeout   锁的超时时间
     * @return 锁标识
     */
    public String lockWithTimeout(String locaName,
                                  long acquireTimeout, long timeout) {
        String retIdentifier =null;
        try {
            // 随机生成一个value
            String identifier = UUID.randomUUID().toString();
            // 锁名，即key值
            String lockKey = "lock:" + locaName;
            // 超时时间，上锁后超过此时间则自动释放锁
            int lockExpire = (int)(timeout / 1000);

            // 获取锁的超时时间，超过这个时间则放弃获取锁
            long end = System.currentTimeMillis() + acquireTimeout;
            while (System.currentTimeMillis() < end) {
                if (setNx(lockKey, identifier).equals("true")) {
                    redisTemplate.expire(lockKey, lockExpire, TimeUnit.SECONDS);
                    // 返回value值，用于释放锁时间确认
                    retIdentifier = identifier;
                    return retIdentifier;
                }
                // 返回-1代表key没有设置超时时间，为key设置一个超时时间
                if (ttl(lockKey) == -1) {
                    redisTemplate.expire(lockKey, lockExpire, TimeUnit.SECONDS);
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (Exception e) {
            logger.error("lockWithTimeout error",e);
        }
        return retIdentifier;
    }


    /**
     * 释放锁
     * @param lockName 锁的key
     * @param identifier    释放锁的标识
     * @return
     */
    public boolean releaseLock(String lockName, String identifier) {
        final String lockKey = "lock:" + lockName;
        boolean retFlag = false;
        try {
            while (true) {
                // 监视lock，准备开始事务
                redisTemplate.watch(lockKey);

                // 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
                if (identifier.equals(getValue(lockKey))) {
                    SessionCallback sessionCallback = new SessionCallback() {
                        @Override
                            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                            redisOperations.multi();
                            delete(lockKey);
                            List<Object> results = redisTemplate.exec();
                            return results;
                        }
                    };
                    Object o = redisTemplate.execute(sessionCallback);
                    retFlag = true;
                    redisTemplate.unwatch();
                }
                break;
            }
        } catch (Exception e) {
            logger.error("releaseLock error",e);
        }
        return retFlag;
    }


    /**
     * 添加一个锁
     * @param key
     * @param value
     * @param timeout
     * @return
     */
    public boolean versionLock(String key,String value,long timeout){
        try {
            setValue(key, value, timeout);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }


    /**
     * 释放锁
     * @param key
     * @param value
     * @return
     */
    public boolean releaseVersionLock(String key,String value){
        try {
            String lockaV =  getValue(key);
            if(StringUtils.isEmpty(lockaV) || !lockaV.equals(value)){
                return false;
            }
            delete(key);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 实现命令：HSET key field value，将哈希表 key中的域 field的值设为 value
     *
     * @param key
     * @param field
     * @param value
     */
    public void hSet(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 放入map集合
     * @param key
     * @param m
     */
    public void hSet(String key, Map m) {
        redisTemplate.opsForHash().putAll(key, m);
    }

    /**
     * 实现命令：HGET key field，返回哈希表 key中给定域 field的值
     *
     * @param key
     * @param field
     * @return
     */
    public String hGet(String key, String field) {
        return (String) redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 获取多个key
     * @param key
     * @param field
     * @return
     */
    public List multiGet(String key, String ...field) {
        return  redisTemplate.opsForHash().multiGet(key, Arrays.asList(field));
    }

    /**
     * 实现命令：HDEL key field [field ...]，删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
     *
     * @param key
     * @param fields
     */
    public void hDel(String key, Object... fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 实现命令：HGETALL key，返回哈希表 key中，所有的域和值。
     *
     * @param key
     * @return
     */
    public Map<Object, Object> hGetall(String key) {
        return redisTemplate.opsForHash().entries(key);
    }



    public Set<String> hGet(String key) {
        return (Set) redisTemplate.opsForHash().keys(key);
    }

    /**
     * 设置超时时间（秒）
     * @param key
     * @param timeout
     * @return
     */
    public Boolean expire(String key,long timeout){
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }


    public Long pushListString(String key,List<String> list) {
        return redisTemplate.opsForList().leftPushAll(key, list);
    }

    public Long pushListString(String key,List<String> list,long timeout) {
        Long aLong = redisTemplate.opsForList().leftPushAll(key, list);
        Boolean expire = expire(key, timeout);
        if(!expire){
            return 0L;
        }
        return aLong;
    }

    public List<String> popListString(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    public Long pushListMap(String key,List<Map<String,Object>> list) {
        List<String> reqList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            reqList.add(JSONObject.toJSONString(map));
        }
        return pushListString(key, reqList);
    }

    public Long pushListMap(String key,List<Map<String,Object>> list,long timeout) {
        List<String> reqList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            reqList.add(JSONObject.toJSONString(map));
        }
        return pushListString(key, reqList,timeout);
    }

    public List<Map<String,Object>>  popListMap(String key) {
        List<String> strings = popListString(key);
        List<Map<String,Object>> resultList = new ArrayList<>();
        for (String string : strings) {
            JSONObject jsonObject = JSONObject.parseObject(string);
            resultList.add(jsonObject);
        }
        return resultList;
    }

    /**
     * 添加到set集合
     * @param key
     * @param value
     * @return
     */
    public long sAdd(String key,String ...value){
        return redisTemplate.opsForSet().add(key,value);
    }

    /**
     * 判断是否是集合成员
     * @param key
     * @param value
     * @return
     */
    public boolean sismember(String key,String value){
        return redisTemplate.opsForSet().isMember(key,value);
    }

    /**
     * 返回结合所有元素
     * @param key
     * @return
     */
    public Set<String> smembers(String key){
        return redisTemplate.opsForSet().members(key);
    }

}
