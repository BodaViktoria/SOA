package com.ubbcluj.transaction.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubbcluj.transaction.dto.Notification;
import com.ubbcluj.transaction.dto.TransactionDto;
import com.ubbcluj.transaction.repository.TransactionEntity;
import com.ubbcluj.transaction.repository.TransactionRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionListener {

    private final TransactionRepository transactionRepository;

    private final SimpMessagingTemplate simpMessagingTemplate;

    public TransactionListener(TransactionRepository transactionRepository, SimpMessagingTemplate simpMessagingTemplate) {
        this.transactionRepository = transactionRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @KafkaListener(topics = "transaction", groupId = "my_group")
    public void listen(ConsumerRecord<String, String> record) {
        System.out.println("Received message: " + record.value());

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            TransactionDto transactionDto = objectMapper.readValue(record.value(), TransactionDto.class);

            TransactionEntity transaction = new TransactionEntity(transactionDto.getCustomerId(), transactionDto.getAmount());
            transactionRepository.save(transaction);
            simpMessagingTemplate.convertAndSend(
                    "/topic/transaction",
                    new Notification("Your transaction was successful!")
            );

        }
        catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
