package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;
	
	@GetMapping("/comfirm/order")
	public ResponseEntity<Order> updateToComfirm(@RequestParam(value="id")Integer order_id) throws ResourceNotFound {
		Order order = orderRepository.findById(order_id)
				.orElseThrow(() -> new ResourceNotFound("Error not found"));
		order.setStatus(1);
		orderRepository.save(order);
		return ResponseEntity.ok().body(order);
	}
	
	@GetMapping("/packing/order")
	public ResponseEntity<Order> updateToPacking(@RequestParam(value="id")Integer order_id) throws ResourceNotFound {
		Order order = orderRepository.findById(order_id)
				.orElseThrow(() -> new ResourceNotFound("Error not found"));
		order.setStatus(2);
		orderRepository.save(order);
		return ResponseEntity.ok().body(order);
	}
	
	@GetMapping("/sending/order")
	public ResponseEntity<Order> updateToSending(@RequestParam(value="id")Integer order_id) throws ResourceNotFound {
		Order order = orderRepository.findById(order_id)
				.orElseThrow(() -> new ResourceNotFound("Error not found"));
		order.setStatus(3);
		orderRepository.save(order);
		return ResponseEntity.ok().body(order);
	}
	
	@GetMapping("/arrived/order")
	public ResponseEntity<Order> updateToArrived(@RequestParam(value="id")Integer order_id) throws ResourceNotFound {
		Order order = orderRepository.findById(order_id)
				.orElseThrow(() -> new ResourceNotFound("Error not found"));
		order.setStatus(4);
		orderRepository.save(order);
		return ResponseEntity.ok().body(order);
	}
}
