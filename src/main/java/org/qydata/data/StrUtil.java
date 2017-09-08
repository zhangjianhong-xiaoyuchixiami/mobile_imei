package org.qydata.data;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.StringUtils;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jonhn on 2017/3/8.
 */
public class StrUtil {


    public static void main(String[] args) {
        String string = "{\"q\":[\"001882386d47077a4a60d95184bacf65\",\"007dfbd41ca6331af8bd838c7c046098\",\"007dfbd41ca6331af8bd838c7c046098\",\"007dfbd41ca6331af8bd838c7c046098\",\"00e9069fe143c72ea1405760d6a82297\",\"00f6b00e4237852226a2fa1e5e33cf15\",\"01276aad6b6418605e48bf400dfa54e1\",\"0219bc0cb4d772bc63807b85324e58af\",\"0292d393e6fd394c38bd6bab1bdae9f8\",\"04264b924632a519abbb30c83d2a16de\",\"04264b924632a519abbb30c83d2a16de\",\"046eba70bffb7e076946c226b07360e4\",\"04c28231547b739aa8e866cf2d6113a1\",\"06d3162d308f59215fecf450c2c8c312\",\"06f2bb95d554a6f7e99cd3f4be1296c0\",\"06f2bb95d554a6f7e99cd3f4be1296c0\",\"072355e4de167b078c9d7b571a7f8bf6\",\"072355e4de167b078c9d7b571a7f8bf6\",\"0848241d6209fa4a2e5411b49541096d\",\"0860489a120cd395e9e024373effb46c\",\"09a01b49e2a1805664ae5e5bdf59d18c\",\"0aeaa3407740d64b203e016bde11ce2b\",\"0c4f02166ab380efbaff7df72e386c91\",\"0c9bf9aa906256a1cb793b39042c966c\",\"0d32caa0fd786d47ffefafaa583d6a39\",\"0d32caa0fd786d47ffefafaa583d6a39\",\"0d805acf6ae197c6f82faa651ed80791\",\"0d8804f4dfcd439698a2c1ab0c0f2516\",\"0daea51f2a6906e0213a73faeec8c5ee\",\"0e1d18c732d40fe60259ac44284fb647\",\"0e1d18c732d40fe60259ac44284fb647\",\"0e2e84dc8d9647ec542d3212a8b17e29\",\"1016e45a3cac8da6ab9164ce0365a267\",\"123fdc2426a948d5cac5280f5fc100fb\",\"123fdc2426a948d5cac5280f5fc100fb\",\"12fda6ae513b2049fe46f47cd4bec2d5\",\"13194305bb475a00bba9db0b10516636\",\"150e74d88dc61fd9a8b61e7774407012\",\"150e74d88dc61fd9a8b61e7774407012\",\"153eae150b7914efedf4448e0436f54d\",\"15b296a633f1b26cf4b8faa16c86aaed\",\"15b296a633f1b26cf4b8faa16c86aaed\",\"15b296a633f1b26cf4b8faa16c86aaed\",\"163d449609c3efe48f96b4b188b7781e\",\"163d449609c3efe48f96b4b188b7781e\",\"163d449609c3efe48f96b4b188b7781e\",\"16da3a39e9d1167b2042081d07731e5c\",\"173404053e91da845b1f5bb6806442e1\",\"17bebee136d60ba77553243761647641\",\"18652c9c4f763eb5de98d453191885ad\",\"18da4e3cfcf19415d141325bb63cdd4c\",\"195a2c336aff993b9aa1492372d3f166\",\"1961514104ded9bcbc1dcb2b5cfdf28f\",\"19f8f59b7bde7843107951a2941fc86f\",\"19f8f59b7bde7843107951a2941fc86f\",\"1a8cf6fc7f1d7e743c5c762a37550506\",\"1a8cf6fc7f1d7e743c5c762a37550506\",\"1c0afc4f57d99caf32bce0a7280c641f\",\"1c8a06bd744e7c3d9bd0d34ef299426b\",\"1cf66d73cfcf1c9c3a24363e82bb9ee5\",\"1d61a4966a28c67aaa53a8c7b96fa82c\",\"1ee6271c74f34172fa7dbf27b2ffa656\",\"1f00e67166294bafe57944a81842ff3f\",\"1f00e67166294bafe57944a81842ff3f\",\"1f3966b698b37ea89870d6b7c0312f1a\",\"1f3966b698b37ea89870d6b7c0312f1a\",\"1f8a3f6944070f33a4f7f0827c266d20\",\"1f8a3f6944070f33a4f7f0827c266d20\",\"1f8a3f6944070f33a4f7f0827c266d20\",\"21d70f0b47771339e2f2f8b9147d00e9\",\"2210eaf81136d372e0ae05eb7161d828\",\"222aa4b1224e7124109f58103790a048\",\"2269f7d2c9ef233c3602f505231bdb81\",\"2269f7d2c9ef233c3602f505231bdb81\",\"2269f7d2c9ef233c3602f505231bdb81\",\"23a3e5ee599361e8e7e7d53a7a239669\",\"246f427d22b60db131fb339a3b7253f5\",\"246f427d22b60db131fb339a3b7253f5\",\"24e7ff903c3c34fe6610b50248eb2842\",\"24e7ff903c3c34fe6610b50248eb2842\",\"255a80993fdb945b6df3cf10d9aa8b77\",\"25caf62da70c63c189da76ae38e2eae4\",\"2633975c793e962953df2917f0807dbd\",\"264b3b3c9316463cd541e0724d724674\",\"265513c1a6215851eeb89c3163221bc2\",\"267dcb0e26e6af6511f38dff36718cc2\",\"26e1fccdb1af02e1a55fee0db0ffceb0\",\"26e815e5b3c294d85b117fab0a12ff91\",\"26e815e5b3c294d85b117fab0a12ff91\",\"2734a3d70f0c3515a89f9a70ad370503\",\"27dabdeb3a4470a6e63f326d3be528ad\",\"27f067ad423caf0513fa64764a2c9417\",\"27f067ad423caf0513fa64764a2c9417\",\"27f067ad423caf0513fa64764a2c9417\",\"27fa1d9eef3a2cff86ef44cc9eec0618\",\"282612765b2ee0e715950d7b4501731c\",\"282612765b2ee0e715950d7b4501731c\",\"28b498937820734c03ef1425277d3abe\",\"28fce88cf303a7f7e93e9419691fc6b5\",\"28fda94c068c9cde739803744c43017e\"],\"sid\":\"20170904-qy-1\",\"useTestApi\":false,\"useFakeApiRes\":false,\"fakeApiError\":false,\"omitLocal\":false,\"authId\":\"qydata04\",\"ts\":1504526893300,\"reqId\":\"504526893300\",\"sign\":\"F6A69C350F45848AD52067DE4EEA5123\"}";

        JSONObject jsonObject = JSONObject.fromObject(string);
        JSONArray q = jsonObject.getJSONArray("q");
        System.out.println(q.size());
    }

    public static Integer parse(String str){

        try{
            JSONObject jsonObject = JSONObject.fromObject(str);
            if (jsonObject == null){
                return null;
            }
            JSONObject flowNew = jsonObject.getJSONObject("flowNew");
            if (flowNew == null){
                return null;
            }
            //00000表示成功，其余失败
            String code = flowNew.getString("code");
            if (!StringUtils.equals("00000",code)){
                return null;
            }
            JSONObject result = flowNew.getJSONObject("result");
            if (result == null){
                return null;
            }
            if (result.get("qryInfoRsp") instanceof net.sf.json.JSONNull || result.get("qryInfoRsp") == null){
                return 0;
            }
            JSONObject qryInfoRsp = result.getJSONObject("qryInfoRsp");
            if (qryInfoRsp.get("mealInfoIn") instanceof net.sf.json.JSONNull || qryInfoRsp.get("mealInfoIn") == null){
                return 0;
            }
            JSONArray mealInfoIn = qryInfoRsp.getJSONArray("mealInfoIn");
            HashSet<Integer> setCost = new HashSet<Integer>();
            for (int i = 0; i < mealInfoIn.size(); i++) {
                JSONObject myjsonObject = mealInfoIn.getJSONObject(i);
                if (myjsonObject != null){
                    String mealName = myjsonObject.getString("mealName");
                    HashSet<Integer> set = judge(mealName);
                    if (set != null){
                        for (Integer cost:set) {
                            setCost.add(cost);
                        }
                    }
                }
            }
            if (setCost == null){
                return 0;
            }
            Integer sum = 0;
            for (Integer cost:setCost) {
                sum += cost;
            }
            return  sum;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static HashSet<Integer> judge(String mealName){
        if (mealName == null){
            return null;
        }
        HashSet<Integer> set =  new HashSet<Integer>();
        if (mealName.contains("套餐") || mealName.contains("元")){
            String regEx="\\d+([e-zE-Z]+|分钟|元/[a-zA-Z]+|条|分|个|档|天|（档）|\\(档\\)|\\.\\d+|/|:|月)";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(mealName);
            String mealName_2 = m.replaceAll("").trim();
            String regEx_2 = "\\d+";
            Pattern p_2 = Pattern.compile(regEx_2);
            Matcher m_2 = p_2.matcher(mealName_2);
            while(m_2.find()){
                String s = m_2.group();
                if (s.length() <= 3){
                    set.add(Integer.parseInt(s));
                }
            }
        }
        return set;
    }

}
