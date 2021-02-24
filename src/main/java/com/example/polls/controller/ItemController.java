package com.example.polls.controller;

import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.Category;
import com.example.polls.model.Item;
import com.example.polls.payload.CreateItemRequest;
import com.example.polls.repository.CategoryRepository;
import com.example.polls.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateItemRequest createItemRequest) {
        Category category = categoryRepository.findById(createItemRequest.getCategoryId()).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", createItemRequest.getCategoryId()));
        Item item = new Item(createItemRequest.getDescription(), createItemRequest.getPrice(), category);
        itemRepository.save(item);
        return ResponseEntity.ok("Created successfully");
    }

    @GetMapping
    public List<Item> retrieve() {
        return itemRepository.findAll();
    }
}
