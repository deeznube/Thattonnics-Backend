package com.example.demo;

import java.util.Calendar;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;
	
	@GetMapping("/confirm/order")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<Order> updateToComfirm(@RequestParam(value="id")Integer order_id) throws ResourceNotFound {
		Order order = orderRepository.findById(order_id)
				.orElseThrow(() -> new ResourceNotFound("Error not found"));
		order.setStatus(1);
		orderRepository.save(order);
		return ResponseEntity.ok().body(order);
	}
	
	@GetMapping("/packing/order")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<Order> updateToPacking(@RequestParam(value="id")Integer order_id) throws ResourceNotFound {
		Order order = orderRepository.findById(order_id)
				.orElseThrow(() -> new ResourceNotFound("Error not found"));
		order.setStatus(2);
		orderRepository.save(order);
		return ResponseEntity.ok().body(order);
	}
	
	@GetMapping("/sending/order")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<Order> updateToSending(@RequestParam(value="id")Integer order_id) throws ResourceNotFound {
		Order order = orderRepository.findById(order_id)
				.orElseThrow(() -> new ResourceNotFound("Error not found"));
		order.setStatus(3);
		orderRepository.save(order);
		return ResponseEntity.ok().body(order);
	}
	
	@GetMapping("/arrived/order")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<Order> updateToArrived(@RequestParam(value="id")Integer order_id) throws ResourceNotFound {
		Order order = orderRepository.findById(order_id)
				.orElseThrow(() -> new ResourceNotFound("Error not found"));
		order.setStatus(4);
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
		order.setArrived_date(calendar.getTime());
		orderRepository.save(order);
		return ResponseEntity.ok().body(order);
	}

	@GetMapping("/success/order")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<Order> updateToSuccess(@RequestParam(value="id")Integer order_id) throws ResourceNotFound {
		Order order = orderRepository.findById(order_id)
				.orElseThrow(() -> new ResourceNotFound("Error not found"));
		order.setStatus(5);
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
		order.setClosed_date(calendar.getTime());
		orderRepository.save(order);
		return ResponseEntity.ok().body(order);
	}

	@GetMapping("/cancel/order")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<Order> updateToCancel(@RequestParam(value="id")Integer order_id) throws ResourceNotFound {
		Order order = orderRepository.findById(order_id)
				.orElseThrow(() -> new ResourceNotFound("Error not found"));
		order.setStatus(6);
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
		order.setClosed_date(calendar.getTime());
		orderRepository.save(order);
		return ResponseEntity.ok().body(order);
	}
}
