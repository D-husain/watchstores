package com.example.demo.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.UserDao;
import com.example.demo.dto.OrderDTO;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetails;
import com.example.demo.entity.ShippingAddress;

@Controller
public class OrderController {

	@Autowired
	private UserDao udao;
	
	@GetMapping("/orders")
	public String showOrders(Model model) {
	    List<Order> orders = udao.ShowUserOrders();
	    model.addAttribute("orders", orders);
	    
	    Map<Integer, List<OrderDetails>> orderDetailsMap = udao.getOrderDetailsMap(orders);
        model.addAttribute("orderDetailsMap", orderDetailsMap);

	    return "admin/orders";
	}

	@GetMapping("/pendingorders")
	public String pendingorders(Model model) {
		List<Order> panding = udao.getPendingOrders();
		model.addAttribute("panding", panding);
		
		 Map<Integer, List<OrderDetails>> orderDetailsMap = new HashMap<>();
		    for (Order order : panding) {
		        List<OrderDetails> orderDetails = udao.getOrderDetailsByOrderId(order.getId());
		        orderDetailsMap.put(order.getId(), orderDetails);
		    }
		    model.addAttribute("orderDetailsMap", orderDetailsMap);
		    
		return "admin/pendingorders";
	}
	
	@GetMapping("/api/pending")
    public ResponseEntity<List<OrderDTO>> getPendingOrders() {
        List<Order> pendingOrders = udao.getPendingOrders();
        List<OrderDTO> Order = new ArrayList<>();
       
        for (Order order : pendingOrders) {
			  OrderDTO orderDTO = new OrderDTO();
			  orderDTO.setId(order.getId());
		       orderDTO.setFname(order.getUser().getFname());
		       orderDTO.setLname(order.getUser().getLname());
		       orderDTO.setEmail(order.getUser().getEmail());
		       orderDTO.setOrderdate(order.getOrderdate());
		       orderDTO.setStatus(order.getStatus());
		        Order.add(orderDTO);
		    }
		  return new ResponseEntity<>(Order, HttpStatus.OK);
    }
	

	@GetMapping("/activeorders")
	public String activeorders(Model model) {
		List<Order> active = udao.getActiveOrders();
		model.addAttribute("active", active);
		
		 Map<Integer, List<OrderDetails>> orderDetailsMap = new HashMap<>();
		    for (Order order : active) {
		        List<OrderDetails> orderDetails = udao.getOrderDetailsByOrderId(order.getId());
		        orderDetailsMap.put(order.getId(), orderDetails);
		    }
		    model.addAttribute("orderDetailsMap", orderDetailsMap);
		    
		return "admin/activeorders";
	}

	@GetMapping("/active-order/{orderId}")
	public String activeOrderStatus(@PathVariable("orderId") int orderId) {
		udao.updateActiveStatus(orderId, 2); // Update to "Active" status (2)
		return "redirect:/activeorders";
	}
	

	@GetMapping("/shippingorders")
	public String shippingorders(Model model) {
		List<Order> shippingorders = udao.getShippingOrders();
		model.addAttribute("shipping", shippingorders);
		

		 Map<Integer, List<OrderDetails>> orderDetailsMap = new HashMap<>();
		    for (Order order : shippingorders) {
		        List<OrderDetails> orderDetails = udao.getOrderDetailsByOrderId(order.getId());
		        orderDetailsMap.put(order.getId(), orderDetails);
		    }
		    model.addAttribute("orderDetailsMap", orderDetailsMap);
		    
		return "admin/shippingorders";
	}

	@GetMapping("/shipping-order/{orderId}")
	public String shippingOrderStatus(@PathVariable int orderId) {
		udao.updateShippingStatus(orderId, 3); // Update to "Shipping" status (3)
		return "redirect:/shippingorders";
	}

	@GetMapping("/deliverdorders")
	public String deliverdorders(Model model) {
		List<Order> deliverdorders = udao.getDeliveredOrders();
		model.addAttribute("deliver", deliverdorders);

		 Map<Integer, List<OrderDetails>> orderDetailsMap = new HashMap<>();
		    for (Order order : deliverdorders) {
		        List<OrderDetails> orderDetails = udao.getOrderDetailsByOrderId(order.getId());
		        orderDetailsMap.put(order.getId(), orderDetails);
		    }
		    model.addAttribute("orderDetailsMap", orderDetailsMap);
		    
		return "admin/deliverdorders";
	}

	@GetMapping("/Delivered-order/{orderId}")
	public String DeliveredOrderStatus(@PathVariable int orderId) {
		udao.updateDeliveredStatus(orderId, 4); // Update to "Delivered" status (4)
		return "redirect:/deliverdorders";
	}

	@GetMapping("/cancelledorders")
	public String cancelledorders(Model model) {
		List<Order> cancelledorders = udao.getCancelledOrders();
		model.addAttribute("cancelled", cancelledorders);
		

		 Map<Integer, List<OrderDetails>> orderDetailsMap = new HashMap<>();
		    for (Order order : cancelledorders) {
		        List<OrderDetails> orderDetails = udao.getOrderDetailsByOrderId(order.getId());
		        orderDetailsMap.put(order.getId(), orderDetails);
		    }
		    model.addAttribute("orderDetailsMap", orderDetailsMap);
		    
		return "admin/cancelledorders";
	}

	@PostMapping("/CancellationStatus")
	public String updateCancelledStatus(@RequestParam("orderId") int OrderId,
			@RequestParam("cancelorder") String cancelOrder) {
		Order o = new Order();
		o.setId(OrderId);
		o.setCancelorder(cancelOrder);
		udao.updateCancelledStatus(OrderId, 5, cancelOrder);
		return "redirect:/cancelledorders";
	}
	
	/*
	 * @GetMapping("/sales") public String sales(Model model) {
	 * //model.addAttribute("orders", udao.ShowUserOrders());
	 * 
	 * List<Order> SalesOrders = udao.ShowUserOrders(); model.addAttribute("orders",
	 * SalesOrders);
	 * 
	 * Map<Integer, List<OrderDetails>> orderDetailsMap =
	 * udao.getOrderDetailsMap(SalesOrders); model.addAttribute("orderDetailsMap",
	 * orderDetailsMap);
	 * 
	 * return "admin/sales"; }
	 */
	
	/*
	 * @GetMapping("/sales") public String sales(
	 * 
	 * @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern
	 * = "dd-MM-yyyy") String startDate,
	 * 
	 * @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern =
	 * "dd-MM-yyyy") String endDate,
	 * 
	 * @RequestParam(value = "period", required = false) String period, Model model)
	 * {
	 * 
	 * List<Order> salesOrders;
	 * 
	 * if (startDate == null || endDate == null) { salesOrders =
	 * udao.ShowUserOrders(); model.addAttribute("orders", salesOrders);
	 * 
	 * Map<Integer, List<OrderDetails>> orderDetailsMap =
	 * udao.getOrderDetailsMap(salesOrders); model.addAttribute("orderDetailsMap",
	 * orderDetailsMap); } else if ("lastmonth".equals(period)) { LocalDate
	 * startDatel = LocalDate.now().minusMonths(1).withDayOfMonth(1); LocalDate
	 * endDatel = LocalDate.now().withDayOfMonth(1).minusDays(1); salesOrders =
	 * udao.findByDateRangeAndOrderByDates(startDatel, endDatel); } else if
	 * ("lastweek".equals(period)) { LocalDate startDatew =
	 * LocalDate.now().minusWeeks(1); LocalDate endDatew = LocalDate.now();
	 * salesOrders = udao.findByDateRangeAndOrderByDates(startDatew, endDatew); }
	 * else if ("today".equals(period)) { LocalDate startDatet = LocalDate.now();
	 * LocalDate endDatet = LocalDate.now(); salesOrders =
	 * udao.findByDateRangeAndOrderByDates(startDatet, endDatet); } else {
	 * salesOrders = udao.findByDateRangeAndOrderByDate(startDate, endDate);
	 * model.addAttribute("orders", salesOrders);
	 * 
	 * Map<Integer, List<OrderDetails>> orderDetailsMap =
	 * udao.getOrderDetailsMap(salesOrders); model.addAttribute("orderDetailsMap",
	 * orderDetailsMap);
	 * 
	 * model.addAttribute("startDate", startDate); model.addAttribute("endDate",
	 * endDate); System.out.println("Filter Date: "+startDate +" or "+endDate); }
	 * return "admin/sales"; }
	 */
	
	@GetMapping("/sales")
	public String sales(
	    @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String startDate,
	    @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String endDate,
	    @RequestParam(value = "period", required = false) String period,
	    Model model) {

	    List<Order> salesOrders;

	    if ("lastmonth".equals(period)) {
	        String startDatel = LocalDate.now().minusMonths(1).withDayOfMonth(1).toString();
	        String endDatel = LocalDate.now().withDayOfMonth(1).minusDays(1).toString();
	        salesOrders = udao.findByDateRangeAndOrderByDate(startDatel, endDatel);
	    } else if ("lastweek".equals(period)) {
	        String startDatew = LocalDate.now().minusWeeks(1).toString();
	        String endDatew = LocalDate.now().toString();
	        salesOrders = udao.findByDateRangeAndOrderByDate(startDatew, endDatew);
	    } else if ("today".equals(period)) {
	        String startDatet = LocalDate.now().toString() ;
	        String endDatet = LocalDate.now().toString() ;
	        salesOrders = udao.findByDateRangeAndOrderByDate(startDatet, endDatet);
	    } else if (startDate != null && endDate != null) {
	        String start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();
	        String end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();
	        
	        salesOrders = udao.findByDateRangeAndOrderByDate(start, end);
	        model.addAttribute("startDate", startDate);
	        model.addAttribute("endDate", endDate);
	        System.out.println("Filter Date: " + startDate + " or " + endDate);
	    } else {
	        salesOrders = udao.ShowUserOrders();
	    }

	    model.addAttribute("orders", salesOrders);
	    model.addAttribute("period", period);

	    Map<Integer, List<OrderDetails>> orderDetailsMap = udao.getOrderDetailsMap(salesOrders);
	    model.addAttribute("orderDetailsMap", orderDetailsMap);

	    return "admin/sales";
	}

	@GetMapping("/sales/download-excel")
    public ResponseEntity<byte[]> downloadSalesAsExcel(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") String startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") String endDate,Model model) {

        List<Order> salesOrders;
        if (startDate == null || endDate == null) {
            salesOrders = udao.ShowUserOrders();
        } else {
            salesOrders = udao.findByDateRangeAndOrderByDate(startDate, endDate);
        }
        Map<Integer, List<OrderDetails>> orderDetailsMap = udao.getOrderDetailsMap(salesOrders);
        model.addAttribute("orderDetailsMap", orderDetailsMap);
        
        try {
            Workbook workbook = createExcelWorkbook(salesOrders,orderDetailsMap);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byte[] excelContent = outputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "sales_data.xlsx");

            return new ResponseEntity<>(excelContent, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    
    @GetMapping("/sales/period")
    public ResponseEntity<byte[]> downloadSalesData(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate,
            @RequestParam(value = "period", required = false) String period,Model model) {

        List<Order> salesOrders = null;

        if ("lastmonth".equals(period)) {
	        String startDatel = LocalDate.now().minusMonths(1).withDayOfMonth(1).toString();
	        String endDatel = LocalDate.now().withDayOfMonth(1).minusDays(1).toString();
	        salesOrders = udao.findByDateRangeAndOrderByDate(startDatel, endDatel);
	    } else if ("lastweek".equals(period)) {
	        String startDatew = LocalDate.now().minusWeeks(1).toString();
	        String endDatew = LocalDate.now().toString();
	        salesOrders = udao.findByDateRangeAndOrderByDate(startDatew, endDatew);
	    } else if ("today".equals(period)) {
	        String startDatet = LocalDate.now().toString() ;
	        String endDatet = LocalDate.now().toString() ;
	        salesOrders = udao.findByDateRangeAndOrderByDate(startDatet, endDatet);
	    }
        Map<Integer, List<OrderDetails>> orderDetailsMap = udao.getOrderDetailsMap(salesOrders);
        model.addAttribute("orderDetailsMap", orderDetailsMap);

        try {
            Workbook workbook = createExcelWorkbook(salesOrders,orderDetailsMap);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byte[] excelContent = outputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "sales_data.xlsx");

            return new ResponseEntity<>(excelContent, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

	/*
	 * private Workbook createExcelWorkbook(List<Order> orders) { Workbook workbook
	 * = new XSSFWorkbook(); Sheet sheet = workbook.createSheet("Sales Data");
	 * 
	 * Row headerRow = sheet.createRow(0);
	 * headerRow.createCell(0).setCellValue("Order ID");
	 * headerRow.createCell(1).setCellValue("Order Date");
	 * headerRow.createCell(2).setCellValue("Customer Email");
	 * headerRow.createCell(3).setCellValue("Customer Name");
	 * 
	 * int rowNum = 1; for (Order order : orders) { Row row =
	 * sheet.createRow(rowNum++); row.createCell(0).setCellValue(order.getId());
	 * row.createCell(1).setCellValue(order.getOrderdate() != null ?
	 * order.getOrderdate().toString() : ""); row.createCell(2).setCellValue(
	 * order.getShippingAddress() != null && order.getShippingAddress().getEmail()
	 * != null ? order.getShippingAddress().getEmail().toString() : "" );
	 * row.createCell(3).setCellValue( order.getShippingAddress() != null &&
	 * order.getShippingAddress().getFname() != null ?
	 * order.getShippingAddress().getFname().toString() : "" );
	 * 
	 * }
	 * 
	 * return workbook; }
	 */
    
    private Workbook createExcelWorkbook(List<Order> orders, Map<Integer, List<OrderDetails>> orderDetailsMap) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sales Data");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Order ID");
        headerRow.createCell(1).setCellValue("Order Date");
        headerRow.createCell(2).setCellValue("Customer Name");
        headerRow.createCell(3).setCellValue("Customer Contact");
        headerRow.createCell(4).setCellValue("Customer Email");
        headerRow.createCell(5).setCellValue("Product Name");
        headerRow.createCell(6).setCellValue("Price");
        headerRow.createCell(7).setCellValue("Quantity");
        headerRow.createCell(8).setCellValue("Total");

        int rowNum = 1;
        for (Order order : orders) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(order.getId());
            row.createCell(1).setCellValue(order.getOrderdate() != null ? order.getOrderdate().toString() : "");

            ShippingAddress shippingAddress = order.getShippingAddress();
            if (shippingAddress != null) {
                String customerName = shippingAddress.getFname() + " " + shippingAddress.getLname();
                row.createCell(2).setCellValue(customerName);
                row.createCell(3).setCellValue(shippingAddress.getPhone());
                row.createCell(4).setCellValue(shippingAddress.getEmail());
            }

            List<OrderDetails> orderDetails = orderDetailsMap.get(order.getId());
            if (orderDetails != null) {
                for (OrderDetails details : orderDetails) {
                    Row productRow = sheet.createRow(rowNum++);
                    productRow.createCell(5).setCellValue(details.getProductname());
                    productRow.createCell(6).setCellValue(details.getPrice());
                    productRow.createCell(7).setCellValue(details.getQty());
                    productRow.createCell(8).setCellValue(details.getTotal());
                }
            }
        }

        return workbook;
    }

	
	
	//------------------------------------------- API -----------------------------------------------------------------
	
	@GetMapping("/allorders")
	public ResponseEntity<List<Order>> getAllOrders() {
		List<Order> orders = udao.ShowUserOrders();
		return ResponseEntity.ok().body(orders);
	}

	@GetMapping("/details")
	public ResponseEntity<Map<Integer, List<OrderDetails>>> getAllOrderDetails() {
		List<Order> orders = udao.ShowUserOrders();
		Map<Integer, List<OrderDetails>> orderDetailsMap = udao.getOrderDetailsMap(orders);
		return ResponseEntity.ok().body(orderDetailsMap);
	}

}
