package com.example.demo;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	
	//http://localhost:8081/api/order/
	@GetMapping("/order")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<List<Order>> getOrder() throws ResourceNotFound {
		List<Order> orders = (List<Order>) orderRepository.findAll();
		System.out.println(productRepository);
		return ResponseEntity.ok().body(orders);
	}
	
	
	//http://localhost:8081/api/order/create
	@PostMapping("/order/create")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<?> createOrder(
			@Valid @RequestBody Order orderRequest){

		if (orderRequest.getBuyer() != null
			&& orderRequest.getVendor() != null
			&& orderRequest.getQuantity() > 0
			&& orderRequest.getProduct() != null) {
				Order order = new Order();
				
				Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
				order.setBuyer(orderRequest.getBuyer());
				order.setVendor(orderRequest.getVendor());
				order.setQuantity(orderRequest.getQuantity());
				order.setCreated_by(orderRequest.getCreated_by());
				order.setCreated_date(calendar.getTime());
				order.setStatus(0);

				Optional<Product> product = productRepository.findById(orderRequest.getProduct().getProduct_id());
				if (product.isPresent()) {
					order.setProduct(product.get());
				}
				else{
					return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
				}

				orderRepository.save(order);
				return ResponseEntity.ok().body(order);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	//http://localhost:8081/api/order/update?id=1
	@PutMapping("/order/update")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<Order> updateOrder(@RequestParam(value="id")Integer id, @RequestBody Order orderRequest) throws ResourceNotFound {
		Optional<Order> order = orderRepository.findById(id);
		if (order.isPresent()) {
			Order _order = order.get();
			if (orderRequest.getBuyer() != null) {
				_order.setBuyer(orderRequest.getBuyer());
			}
			if (orderRequest.getCreated_by() != null) {
				_order.setCreated_by(orderRequest.getCreated_by());
			}
			if (orderRequest.getVendor() != null) {
				_order.setVendor(orderRequest.getVendor());
			}
			if (orderRequest.getQuantity() != null) {
				_order.setQuantity(orderRequest.getQuantity());
			}
			if (orderRequest.getProduct() != null) {
				_order.setProduct(orderRequest.getProduct());
			}
			return new ResponseEntity<>(orderRepository.save(_order), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//http://localhost:8081/api/order/delete?id=1
    @DeleteMapping("/order/delete")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<Order> deleteOrder(
    		@RequestParam(value="id")Integer id) throws ResourceNotFound{
    	Optional<Order> order = orderRepository.findById(id);	
    	Order _order = order.get();
    	orderRepository.delete(_order);
    	return ResponseEntity.ok().body(_order);
    }
	
	@GetMapping("/order/confirm")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<Order> updateToComfirm(@RequestParam(value="id")Integer order_id) throws ResourceNotFound {
		Order order = orderRepository.findById(order_id)
				.orElseThrow(() -> new ResourceNotFound("Error not found"));
		order.setStatus(1);
		orderRepository.save(order);
		return ResponseEntity.ok().body(order);
	}
	
	@GetMapping("/order/packing")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<Order> updateToPacking(@RequestParam(value="id")Integer order_id) throws ResourceNotFound {
		Order order = orderRepository.findById(order_id)
				.orElseThrow(() -> new ResourceNotFound("Error not found"));
		order.setStatus(2);
		orderRepository.save(order);
		return ResponseEntity.ok().body(order);
	}
	
	@GetMapping("/order/sending")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<Order> updateToSending(@RequestParam(value="id")Integer order_id) throws ResourceNotFound {
		Order order = orderRepository.findById(order_id)
				.orElseThrow(() -> new ResourceNotFound("Error not found"));
		order.setStatus(3);
		orderRepository.save(order);
		return ResponseEntity.ok().body(order);
	}
	
	@GetMapping("/order/arrived")
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

	@GetMapping("/order/success")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<Order> updateToSuccess(@RequestParam(value="id")Integer order_id, @RequestParam(value="isImport")boolean isImport) throws ResourceNotFound {
		Order order = orderRepository.findById(order_id)
				.orElseThrow(() -> new ResourceNotFound("Error not found"));
		Optional<Product> product = productRepository.findById(order.getProduct().getProduct_id());
		if (product.isPresent()) {
			if (isImport == true) {
				product.get().addQuantity(order.getQuantity());
			}
			else {
				product.get().removeQuantity(order.getQuantity());
			}
			productRepository.save(product.get());
			order.setStatus(5);
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
			order.setClosed_date(calendar.getTime());
			orderRepository.save(order);
			return ResponseEntity.ok().body(order);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}		
	}

	@GetMapping("/order/cancel")
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
