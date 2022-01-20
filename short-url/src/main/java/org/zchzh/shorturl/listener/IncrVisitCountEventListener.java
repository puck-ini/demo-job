package org.zchzh.shorturl.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.zchzh.shorturl.model.event.IncrVisitCountEvent;
import org.zchzh.shorturl.repo.UrlMapRepo;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author zengchzh
 * @date 2022/1/19
 */
@Slf4j
@Component
public class IncrVisitCountEventListener {

    private final IncrVisitCountEventListenerThread thread;

    @Autowired
    public IncrVisitCountEventListener(UrlMapRepo urlMapRepo) {
        thread = new IncrVisitCountEventListenerThread(urlMapRepo);
        thread.start();
    }

    @EventListener(IncrVisitCountEvent.class)
    public void incr(IncrVisitCountEvent event) {
        thread.pushEvent(event);
    }

    static class IncrVisitCountEventListenerThread extends Thread {

        private final UrlMapRepo urlMapRepo;

        private final LinkedBlockingQueue<IncrVisitCountEvent> eventQueue = new LinkedBlockingQueue<>();

        public IncrVisitCountEventListenerThread(UrlMapRepo urlMapRepo) {
            this.urlMapRepo = urlMapRepo;
            setName(IncrVisitCountEventListenerThread.class.getSimpleName());
        }

        @Override
        public void run() {
            while (!this.isInterrupted()) {
                IncrVisitCountEvent event = null;
                try {
                    event = eventQueue.poll(3L, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Objects.nonNull(event)) {
                    IncrVisitCountEvent finalEvent = event;
                    urlMapRepo.findById(event.getId()).ifPresent(urlMap -> {
                        urlMap.incrVisitCount();
                        try {
                            urlMapRepo.save(urlMap);
                        } catch (Exception e) {
                            pushEvent(finalEvent);
                        }
                    });
                }
            }
            if (eventQueue.size() != 0) {
                log.info("eventQueue size : " + eventQueue.size());
            }
        }

        public void pushEvent(IncrVisitCountEvent event) {
            eventQueue.add(event);
        }
    }
}
