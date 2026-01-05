package project.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import project.backend.dto.messageDto.MessageReq;
import project.backend.dto.messageDto.MessageResp;
import project.backend.service.MessageService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/messages")
@Slf4j
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/chat/{room-id}")
    @SendTo("/topic/messages/{room-id}")
    public MessageReq sendChatMessage(@Valid @RequestBody MessageReq message,
                                          @DestinationVariable Long roomId) {
        log.info("message={}", message.getMessage());
        String escapedMessage = HtmlUtils.htmlEscape(message.getMessage());

        messageService.createMessage(message);

        return new MessageReq(message.getOttShareRoom(), message.getOttRoomMemberResp(), escapedMessage);
    }

    /**
     * 메시지 조회
     */
    @GetMapping("/{room-id}")
    public Page<MessageResp> getMessages(@PathVariable Long roomId) {
        return messageService.getMessages(roomId);
    }

}