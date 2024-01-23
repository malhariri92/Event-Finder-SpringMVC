package com.mutasem.event.finder.services;

import com.mutasem.event.finder.models.User;
import com.mutasem.event.finder.models.WebUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {

    public User findByUserName(String userName);

    void save(WebUser theWebUser);

    void updateUser(User user);
}
