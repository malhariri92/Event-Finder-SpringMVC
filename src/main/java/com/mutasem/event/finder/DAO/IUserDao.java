package com.mutasem.event.finder.DAO;

import com.mutasem.event.finder.models.User;
import com.mutasem.event.finder.models.WebUser;

public interface IUserDao {
    User findByEmail(String email);

    void save(User user);

    void updateUser(User user);
}
