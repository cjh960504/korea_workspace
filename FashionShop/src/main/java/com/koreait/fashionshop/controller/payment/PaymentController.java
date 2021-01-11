package com.koreait.fashionshop.controller.payment;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.koreait.fashionshop.exception.CartException;
import com.koreait.fashionshop.exception.LoginRequiredException;
import com.koreait.fashionshop.model.common.MessageData;
import com.koreait.fashionshop.model.domain.Cart;
import com.koreait.fashionshop.model.domain.Member;
import com.koreait.fashionshop.model.domain.OrderSummary;
import com.koreait.fashionshop.model.domain.Receiver;
import com.koreait.fashionshop.model.payment.service.PaymentService;
import com.koreait.fashionshop.model.product.service.TopCategoryService;

@Controller
public class PaymentController {
	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
	@Autowired
	private PaymentService paymentService;
	@Autowired 
	private TopCategoryService topCategoryService;
	
	//장바구니에 상품 담기 요청
	@RequestMapping(value="/shop/cart/regist", method=RequestMethod.POST)
	@ResponseBody
	public MessageData registCart(Cart cart, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("member");
		logger.debug("product_id : "+cart.getProduct_id());
		logger.debug("quantity : "+cart.getQuantity());
		cart.setMember_id(member.getMember_id());
		paymentService.insert(cart);
		
		//MessageConverter에 의해 VO는 JSON형태로 응답되어질 수 있다!
		MessageData messageData = new MessageData();
		messageData.setResultCode(1);//성공을 전제로 한 데이터, 예외가 발생하면 예외로 뛰어가기때문에
		messageData.setMsg("장바구니에 상품이 담겼습니다.");
		messageData.setUrl("/shop/cart/list");
		
		return messageData;
	}
	
	//장바구니 목록 요청
	@RequestMapping(value="/shop/cart/list", method=RequestMethod.GET)
	public ModelAndView getCartList(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("member");
		ModelAndView mav = new ModelAndView("shop/cart/cart_list");
		List topList =topCategoryService.selectAll();//카테고리 가져오기
		mav.addObject("topList", topList);
		List cartList = paymentService.selectCartList(member.getMember_id());
		mav.addObject("cartList", cartList);
		return mav;
	}
	
	//장바구니 비우기
	@RequestMapping(value="/shop/cart/del", method=RequestMethod.GET)
	public String delCart(HttpSession session) {
		if(session.getAttribute("member")==null) {
			throw new LoginRequiredException("로그인이 필요한 서비스 입니다.");
		}
		paymentService.delete((Member)session.getAttribute("member"));
		return "redirect:/shop/cart/list";
	}
	
	//장바구니 수정
	@RequestMapping(value="/shop/cart/edit", method=RequestMethod.POST)
	public ModelAndView editCart(@RequestParam("cart_id")int[] cartArray, @RequestParam("quantity")int[] quantity) {
		//넘겨받은 파라미터 출력하기!! cart_id, quantity
		logger.debug("quantity[] length"+ quantity.length);
		List<Cart> cartList = new ArrayList<Cart>();
		for(int i=0;i<cartArray.length;i++) {
			Cart cart = new Cart();
			cart.setCart_id(cartArray[i]);
			cart.setQuantity(quantity[i]);
			cartList.add(cart);
		}
		paymentService.update(cartList);
		//수정되었다는 메시지를 보고싶다면.. message.jsp로 응답하자!
		ModelAndView mav = new ModelAndView("shop/error/message");
		MessageData messageData = new MessageData();
		messageData.setResultCode(1);
		messageData.setMsg("장바구니가 수정되었습니다.");
		messageData.setUrl("/shop/cart/list");
		mav.addObject("messageData", messageData);
		return mav;
	}
	
	//체크아웃 페이지 요청
	@GetMapping("/shop/payment/form")
	public String payForm(Model model, HttpSession session) {
		List topList = topCategoryService.selectAll();
		model.addAttribute("topList", topList);
		
		//결제수단 가져오기
		List paymethodList = paymentService.selectPaymethodList();
		model.addAttribute("paymethodList", paymethodList);
		
		//장바구니 정보도 가져오기
		Member member= (Member)session.getAttribute("member");
		List cartList = paymentService.selectCartList(member.getMember_id());
		model.addAttribute("cartList", cartList);
		return "shop/payment/checkout";
	}
	
	//결제요청 처리
	@PostMapping("/shop/payment/regist")
	public String pay(OrderSummary orderSummary, Receiver receiver, HttpSession session) {
		logger.debug("받을 사람 이름 : "+receiver.getReceiver_name());
		logger.debug("받을 사람 연락처 : "+receiver.getReceiver_phone());
		logger.debug("받을 사람 주소 : "+receiver.getReceiver_addr());
		logger.debug("결제방법은 : "+orderSummary.getPaymethod_id());
		logger.debug("상품가액은 : "+orderSummary.getTotal_price());
		logger.debug("결제방법은 : "+orderSummary.getTotal_pay());
		paymentService.registOrder(orderSummary, receiver, null);
		return "";
	}
	
	//장바구니와 관련된 예외처리 핸들러
	@ExceptionHandler(CartException.class)
	@ResponseBody
	public MessageData handleException(CartException e) {
		MessageData messageData = new MessageData();
		messageData.setResultCode(0);
		messageData.setMsg(e.getMessage());
		
		return messageData;
	} 
	
}
