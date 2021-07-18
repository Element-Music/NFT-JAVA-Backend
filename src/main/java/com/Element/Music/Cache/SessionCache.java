package com.Element.Music.Cache;

import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;

public class SessionCache {
    //先用一个map暂存，之后需要用redis存，做服务和数据的分离，防止服务挂了而导致数据的丢失
    private static Map<String, Long> sessionMap;

    public static boolean ifExist(String sessionId) throws Exception {
        if (sessionId == null || sessionId == "") {
            throw new Exception("please login");
        }
        if (sessionMap.containsKey(sessionId)) {
            if (!ifTimeout(sessionId))
                sessionMap.put(sessionId, System.currentTimeMillis());
            return true;
        }
        return false;
    }

    private static boolean ifTimeout(String sessionId) {
        if (sessionMap.get(sessionId) == null)
            return true;
        long nowTime = System.currentTimeMillis();
        long gap = nowTime - sessionMap.get(sessionId);
        if (gap > 3600000) {
            sessionMap.remove(sessionId);
            return true;
        }
        return false;
    }

    public static boolean set(String sessionId) throws Exception {
        if (sessionId == null || sessionId == "") {
            throw new Exception("please login first");
        }
        sessionMap.put(sessionId, System.currentTimeMillis());
        return true;
    }

    @Scheduled(cron = "0 */60 * * * ?")
    private void cleanCache() {
        //TODO 定时清理sessionId防止内存泄漏
        for (Map.Entry<String, Long> entry : sessionMap.entrySet()) {
            long sessionTime = entry.getValue();
            long nowTime = System.currentTimeMillis();
            long gap = nowTime - sessionTime;
            if (gap > 3600000) {
                sessionMap.remove(entry.getKey());
            }
        }
    }
}
