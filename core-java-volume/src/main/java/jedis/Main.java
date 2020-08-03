package jedis;

import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.GeoRadiusParam;

import java.util.List;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/10
 * @Description: 使用jedis连接redis
 */
public class Main {

    public static void main(String[] args) {
//        Jedis jedis = new Jedis("localhost",6379);
//        //所有指令都在jedis下
//        jedis.flushAll();
//        jedis.set("name","xxx"); //例如set
////        jedis.multi();
//        System.out.println(jedis.get("name"));//get等
//        jedis.close();//使用结束后应该关闭



        // 1 new jedis 连接到 redis
        Jedis jedis = new Jedis("127.0.0.1",6379);
        // jedis拥有所有的命令
        jedis.flushAll();
        System.out.println(jedis.ping());
        jedis.geoadd("china",116.40,39.90,"beijing");
        jedis.geoadd("china",121.47,31.23,"shanghai");
        jedis.set("key","v1");
        System.out.println(jedis.get("key"));
        List<GeoCoordinate> list = jedis.geopos("china","beijing");
        //System.out.println(jedis.geopos("china","beijin"));
        System.out.println(jedis.geodist("china","beijing","shanghai", GeoUnit.KM));
        //指定经纬度下,多少范围内
        List<GeoRadiusResponse> l2  = jedis.georadius("china",114,38,1000,
                GeoUnit.KM, GeoRadiusParam.geoRadiusParam());
        System.out.println(l2.get(0));

        //geo底层用的zset,可以用zset命令控制geo
        System.out.println(jedis.zrange("china",0,-1));
        System.out.println(jedis.zrem("china","beijing"));
        System.out.println(jedis.zrange("china",0,-1));
    }
}
