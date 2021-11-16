package lk.dbay.security;

import lk.dbay.dto.DbayUserDTO;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtCache {

    private static Set<DbayUserDTO> users = new HashSet<>();

    public JwtCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(60 * 60 * 1000);
                        if (LocalTime.now().getHour() == 3) {
                            users.clear();
                            System.out.println("Cache Cleared");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public DbayUserDTO getUser(DbayUserDTO user) {
        for (DbayUserDTO userDTO : users) {
            if (userDTO.equals(user)) {
                return userDTO;
            }
        }
        return null;
    }

    public void setUser(DbayUserDTO user) {
        DbayUserDTO userObj = getUser(user);
        if (userObj != null) {
            userObj.setSecurityKey(user.getSecurityKey());
        } else {
            JwtCache.users.add(user);
        }
    }

    public boolean removeUser(DbayUserDTO user) {
        return users.remove(user);
    }
}
