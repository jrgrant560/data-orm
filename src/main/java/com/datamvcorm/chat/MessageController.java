package com.datamvcorm.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {

    @Autowired
    MessageRepository dao; //the repository object

    //Get all messages
    @GetMapping("/chat")
    public List<Message> getAllMessages() {
        
        //pull all data objects from the dao and store them in a List
        List<Message> foundMessages = dao.findAll();

        //return the List of requested data
        return foundMessages;
    }

    //Get a specific message by id
    @GetMapping("/chat/{id}")
    public ResponseEntity<Message> getMessage(@PathVariable(value="id") Integer id) {

        //messsage object that is pulled from the dao by id, or is null if not found
        Message foundMessage = dao.findById(id).orElse(null);

        if(foundMessage == null) {
            //if the message isn't found, return an http response with this message as a header
            return ResponseEntity.notFound().header("Message","Nothing found with that id").build();
        }

        // if the message is found successfully, return the message an http OK response
        return ResponseEntity.ok(foundMessage);
    }


    // Post a new message
    @PostMapping("/chat")
    public ResponseEntity<Message> postMessage(@RequestBody Message message) {

        // Saving to DB using an instance of the repo interface.
        Message createdMessage = dao.save(message);

        // RespEntity crafts response to include correct status codes.
        return ResponseEntity.ok(createdMessage);
    }

    // Update a specific message by id
    @PutMapping("/chat/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable(value="id") Integer id, @RequestBody Message message) {

        //messsage object that is pulled from the dao by id, or is null if not found
        Message foundMessage = dao.findById(id).orElse(null);
        Message updatedMessage = new Message();

        if(foundMessage == null) {
            //if the message isn't found, return an http response with this message as a header
            return ResponseEntity.notFound().header("Message","Nothing found with that id").build();
        } else {

            updatedMessage = foundMessage; //sets updated message defaults from found message

            //NOTE: bugfix: I chose to split up the methods instead of just using dao.save; otherwise the PUT would just create a new object when it found any valid id number, if there was no id specified in the body
            updatedMessage.setId(id); //set updatedMessage to this id
            if (message.getName() != null) {
                updatedMessage.setName(message.getName()); // override Name property if sent
            }
            if (message.getContent() != null) {
                updatedMessage.setContent(message.getContent()); // override Content property if sent
            }

            // Saving to DB using an instance of the repo interface.
            foundMessage = dao.save(updatedMessage);

            //return an http response with the updated message
            return ResponseEntity.ok(foundMessage);
        }
       
    }

    // Delete a specific message by id
    @DeleteMapping("/chat/{id}")
    public ResponseEntity<Message> deleteMessage(@PathVariable(value="id") Integer id) {
        
        //messsage object that is pulled from the dao by id, or is null if not found
        Message foundMessage = dao.findById(id).orElse(null);
        
        if(foundMessage == null) {
            //if the message isn't found, return an http response with this message as a header
            return ResponseEntity.notFound().header("Message","Nothing found with that id").build();
        }else {
            //if the message IS found, delete it
            dao.delete(foundMessage);
        }

        // if the message is deleted successful, return an http OK response
        return ResponseEntity.ok().build();
    }
}