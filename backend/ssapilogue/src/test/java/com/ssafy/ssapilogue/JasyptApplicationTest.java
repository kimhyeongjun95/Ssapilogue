package com.ssafy.ssapilogue;

import org.assertj.core.api.Assertions;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JasyptApplicationTest {

    @Value("${jasypt.encryptor.password}")
    private String encryptKey;

    @Test
    public void jasypt() {
        String url = "jdbc:mysql://k6c104.p.ssafy.io:3306/ssapilogue?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Seoul&zeroDateTimeBehavior=convertToNull&rewriteBatchedStatements=true&useSSL=false&allowPublicKeyRetrieval=true";
        String username = "ssapilogue";
        String password = "ssapilogue104";

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(encryptKey);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);


        String encryptText = encryptor.encrypt(password);
        System.out.println(encryptText);
        String decryptText = encryptor.decrypt(encryptText);
        System.out.println(decryptText);
        Assertions.assertThat(password).isEqualTo(decryptText);
    }

}
