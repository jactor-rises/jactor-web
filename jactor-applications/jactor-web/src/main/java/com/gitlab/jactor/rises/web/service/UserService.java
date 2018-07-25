package com.gitlab.jactor.rises.web.service;

import com.gitlab.jactor.rises.commons.datatype.Username;
import com.gitlab.jactor.rises.commons.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    public Optional<UserDto> find(Username username) {
        return Optional.empty();
    }

    public List<String> findAllUsernames() {
        return Collections.emptyList();
    }
}
