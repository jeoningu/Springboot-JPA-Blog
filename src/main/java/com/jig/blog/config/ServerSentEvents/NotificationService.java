package com.jig.blog.config.ServerSentEvents;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;


import com.jig.blog.model.Board;
import com.jig.blog.model.RspNotification;
import com.jig.blog.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Slf4j
public class NotificationService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepository emitterRepository;

    public NotificationService(EmitterRepository emitterRepository) {
        this.emitterRepository = emitterRepository;
    }

    public SseEmitter subscribe(Long userId, String lastEventId) {
        /* #1. Last-Event-Id 생성 */
        // 유실된 데이터를 찾아서 다시 전송해줘야 하는데, 그럴려면 같은 id에 대해서도 구분이 필요하다.
        // id에 시간을 포함시켜서 구분한다.
        String id = userId + "_" + System.currentTimeMillis();
        
        /* #2. 클라이언트의 sse연결 요청에 응답하기 위한 SseEmitter 객체를 만들어 반환 */
        // 유효시간(DEFAULT_TIMEOUT) 만큼 sse 연결이 유지되고, 시간이 지나면 자동으로 클라이언트에서 재연결 요청을 보내게 된다
        SseEmitter emitter = emitterRepository.save(id, new SseEmitter(DEFAULT_TIMEOUT));

        // SseEmitter의 시간 초과 및 네트워크 오류를 포함한 모든 이유로 비동기 요청이 정상 동작할 수 없다면 저장해둔 SseEmitter를 삭제한다.
        emitter.onCompletion(() -> emitterRepository.deleteById(id));
        emitter.onTimeout(() -> emitterRepository.deleteById(id));

	    /* #3. 503 에러를 방지하기 위한 더미 이벤트 전송 */
        // 연결 요청에 의해 SseEmitter가 생성되면 더미 데이터를 보내줘야한다.
        // sse 연결이 이뤄진 후, 하나의 데이터도 전송되지 않는다면 SseEmitter의 유효 시간이 끝나면 503응답이 발생하는 문제가 있다.
        // 따라서 연결시 바로 더미 데이터를 한 번 보내준다.
        sendToClient(emitter, id, "EventStream Created. Dummy event. userId = " + userId );

	    /* #4. 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방 */
        // Last-Event-ID값이 헤더에 있는 경우, 저장된 데이터 캐시에서 id 값과 Last-Event-ID값을 통해 유실된 데이터들만 다시 보내준다.
        if (!lastEventId.isEmpty()) {
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithId(String.valueOf(userId));
            events.entrySet().stream()
                  .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                  .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
        }

        return emitter;
    }

    // 3
    private void sendToClient(SseEmitter emitter, String id, Object data) {
        try {
            emitter.send(SseEmitter.event()
                                   .id(id)
                                   .name("sse")
                                   .data(data));

            log.info("SseEmitter send to client. id = {}, data = '{}'", id, data);
        } catch (IOException exception) {
            emitterRepository.deleteById(id);
            throw new RuntimeException("연결 오류!");
        }
    }

    public void send(RspNotification rspNotification/*, Review review, String content*/) {
        //Notification notification = createNotification(receiver, review, content);
        // 알림 발을 user의 id (게시글 작성자)
        String id = String.valueOf(rspNotification.getBoardUserId());

        // 로그인 한 유저의 SseEmitter 모두 가져오기
        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllStartWithById(id);
        sseEmitters.forEach(
                (key, emitter) -> {
                    // 데이터 캐시 저장(유실된 데이터 처리하기 위함)
                    emitterRepository.saveEventCache(key, rspNotification);

                    // 데이터 전송
                    sendToClient(emitter, key, rspNotification);
                }
        );
    }

//    public void send(Member receiver, Review review, String content) {
//        Notification notification = createNotification(receiver, review, content);
//        String id = String.valueOf(receiver.getId());
//
//        // 로그인 한 유저의 SseEmitter 모두 가져오기
//        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllStartWithById(id);
//        sseEmitters.forEach(
//                (key, emitter) -> {
//                    // 데이터 캐시 저장(유실된 데이터 처리하기 위함)
//                    emitterRepository.saveEventCache(key, notification);
//                    // 데이터 전송
//                    sendToClient(emitter, key, NotificationResponse.from(notification));
//                }
//        );
//    }
//
//    private Notification createNotification(Member receiver, Review review, String content) {
//        return Notification.builder()
//                .receiver(receiver)
//                .content(content)
//                .review(review)
//                .url("/reviews/" + review.getId())
//                .isRead(false)
//                .build();
//    }

}