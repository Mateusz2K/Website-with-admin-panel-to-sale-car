package pl.konkretnefury.konkretnefury.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    private final Logger logger = LoggerFactory.getLogger(LoginAttemptService.class);
    private static final int MAX_ATTEMPT = 5; // Maksymalna liczba prób
    private LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptService() {
        super();
        attemptsCache = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.DAYS) // Blokada wygasa po 1 dniu
                .build(new CacheLoader<String, Integer>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    public void loginSucceeded(String key) {
        logger.info("Użytkownik zalogowany pomyślnie: {}", key);
        attemptsCache.invalidate(key);
    }

    public void loginFailed(String key) {
        int attempts = 0;
        try {
            attempts = attemptsCache.get(key);
        } catch (ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(key, attempts);
        logger.warn("Nieudana próba logowania z IP: {}. Liczba prób: {}", key, attempts);
    }

    public boolean isBlocked(String key) {
        try {
            boolean blocked = attemptsCache.get(key) >= MAX_ATTEMPT;
            if (blocked) {
                logger.error("BLOKADA: Próba logowania z zablokowanego IP: {}", key);
            }
            return blocked;
        } catch (ExecutionException e) {
            return false;
        }
    }
    
    public List<BlockedIP> getBlockedIPs() {
        List<BlockedIP> blockedList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : attemptsCache.asMap().entrySet()) {
            if (entry.getValue() >= MAX_ATTEMPT) {
                blockedList.add(new BlockedIP(entry.getKey(), entry.getValue()));
            }
        }
        return blockedList;
    }
    
    public void unblockIP(String ip) {
        logger.info("Ręczne odblokowanie IP: {}", ip);
        attemptsCache.invalidate(ip);
    }
    
    public static class BlockedIP {
        private final String ip;
        private final int attempts;

        public BlockedIP(String ip, int attempts) {
            this.ip = ip;
            this.attempts = attempts;
        }

        public String getIp() { return ip; }
        public int getAttempts() { return attempts; }
    }
}
