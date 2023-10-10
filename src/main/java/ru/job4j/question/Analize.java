package ru.job4j.question;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Analize {
    public static Info diff(Set<User> previous, Set<User> current) {
        Map<Integer, User> map = new HashMap<>();
        previous.forEach(u -> map.put(u.getId(), u));
        int added = 0;
        int changed = 0;
        for (User user : current) {
            User curUser = map.get(user.getId());
            if (curUser == null) {
                added += 1;
                continue;
            }
            if (!user.getName().equals(curUser.getName())) {
                changed += 1;
            }
            map.remove(curUser.getId());
        }
        return new Info(added, changed, map.size());
    }
}