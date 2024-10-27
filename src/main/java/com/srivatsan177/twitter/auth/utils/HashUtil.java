package com.srivatsan177.twitter.auth.utils;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.srivatsan177.twitter.auth.exceptions.RestException;

@Service
public class HashUtil {
    private static int workFactor = 12;

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(workFactor));
    }

    public boolean checkPassword(String password, String storedHash) throws RestException {
        if (storedHash == null || !storedHash.startsWith("$2a$")) {
            throw new RestException("hash is null or invalid");
        }
        return BCrypt.checkpw(password, storedHash);
    }
}
