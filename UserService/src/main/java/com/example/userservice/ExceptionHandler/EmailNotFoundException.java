package com.example.userservice.ExceptionHandler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailNotFoundException extends RuntimeException {
  private static final Logger logger = LoggerFactory.getLogger(EmailNotFoundException.class);

    public EmailNotFoundException(Object dto) {

      super("Bu mail ile kayıtlı kullanıcı yok "+dto);
        logger.error("Bu mail ile kayıtlı kullanıcı bulunamadı {}", dto);

    }
}
