package com.jig.blog.config;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.AttributeConverter;


/**
 * Entity와 DB 사이에서 데이터를 변환하고 dateformat을 지정하는 역할
 *  - 날짜 계산을 용이하게 하기 위해 java에서는 LocalDateTime, db에서는 Timestamp-> datetime 을 사용
 */
public class DateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime entityValue) {
        return entityValue == null ? null : Timestamp.valueOf(entityValue.format(dateTimeFormatter));
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp databaseValue) {
        return databaseValue == null ? null : databaseValue.toLocalDateTime();
    }
}











//    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//    private static final ZoneId zoneId = ZoneId.of("Asia/Seoul");
//
//    @Override
//    public Timestamp convertToDatabaseColumn(String entityValue) {
//        return entityValue == null ? null : Timestamp.valueOf(LocalDateTime.parse(entityValue, dateTimeFormatter).atZone(zoneId).toLocalDateTime());
//    }
//
//    @Override
//    public String convertToEntityAttribute(Timestamp databaseValue) {
//        return databaseValue == null ? null : LocalDateTime.ofInstant(databaseValue.toInstant(), zoneId).format(dateTimeFormatter).replace("T", " ");
//    }


//    @Override
//    public Timestamp convertToDatabaseColumn(LocalDateTime entityValue) {
//        return entityValue == null ? null : Timestamp.valueOf(entityValue.atZone(zoneId).toLocalDateTime().format(dateTimeFormatter));
//    }
//
//    @Override
//    public LocalDateTime convertToEntityAttribute(Timestamp databaseValue) {
//        if (databaseValue == null) {
//            return null;
//        } else {
//            String dateTimeString = databaseValue.toLocalDateTime().format(dateTimeFormatter);
//            LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString.replace("T", " "), dateTimeFormatter);
//            return localDateTime;
//        }
//        return databaseValue == null ? null : LocalDateTime.parse(databaseValue.toString().replace("T", " ").substring(0, 19), dateTimeFormatter);
//        return databaseValue == null ? null : LocalDateTime.ofInstant(databaseValue.toInstant(), zoneId).format(dateTimeFormatter);
//        return databaseValue == null ? null : LocalDateTime.ofInstant(databaseValue.toInstant(), zoneId);
//    }

//    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//    @Override
//    public Timestamp convertToDatabaseColumn(LocalDateTime entityValue) {
//        return entityValue == null ? null : Timestamp.valueOf(entityValue.format(dateTimeFormatter));
//    }
//
//    @Override
//    public LocalDateTime convertToEntityAttribute(Timestamp databaseValue) {
//        return databaseValue == null ? null : LocalDateTime.parse(simpleDateFormat.format(new Date(databaseValue.getTime())), dateTimeFormatter);
//        //return databaseValue == null ? null : LocalDateTime.parse(databaseValue.toString().split("\\.")[0], formatter);
//    }
//}