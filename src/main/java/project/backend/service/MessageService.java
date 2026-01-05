package project.backend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import project.backend.dto.messageDto.MessageReq;
import project.backend.dto.messageDto.MessageResp;
import project.backend.entity.Message;
import project.backend.repository.MessageRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;

    //메세지 생성
    @Transactional
    public void createMessage(MessageReq messageReq){
        Message entity = Message.from(messageReq);
        messageRepository.save(entity);
    }
    // 메세지 목록
    public Page<MessageResp> getMessages(Long roomId) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        Page<Message> messages = messageRepository.findAllByOttShareRoomIdOrderByCreatedDate(roomId, pageable);
        return messages.map(MessageResp::from);
    }
}
